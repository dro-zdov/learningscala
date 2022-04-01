import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future, Promise}
import java.util.concurrent.Executors

object Main extends App {
  val pool = Executors.newCachedThreadPool()
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

  //1. Как это выражение преобразуется в вызовы методов map и flatMap?
  //Как выполняются задания Future, параллельно или последовательно?
  //В каком потоке произойдет вызов println?
  {
    for (
      n1 <- Future {
        Thread.sleep(2000); println("Future 1 done"); 2
      };
      n2 <- Future {
        Thread.sleep(2000);
        println("Future 2 done"); 40
      }
    ) println(n1 + n2) // выполняется последовательно

    //эквивалентно
    Future {
      Thread.sleep(2000); println("Future 1 done"); 2
    }.flatMap(v1 =>
      Future {
        Thread.sleep(2000);
        println("Future 2 done"); v1 + 40
      }
    ).foreach(v2 => println(v2))
  }

  //2. Напишите функцию doInOrder, принимающую функции f: T =>
  //Future[U] и g: U => Future[V] и возвращающую функцию T =>
  //Future[V], которая для заданного значения t в конечном счете
  //возвращает g(f(t)).
  {
    def doInOrder[T, U, V](f: T => Future[U], g: U => Future[V]): T => Future[V] = {
      (arg: T) => f(arg).flatMap(u => g(u))
    }
  }

  //3. Повторите предыдущее упражнение для произвольной последовательности
  //функций типа T => Future[T].
  {
    def doInOrder[T](functions: T => Future[T]*): T => Future[T] = {
      (arg: T) => doInOrderInternal(arg, functions: _*)
    }

    def doInOrderInternal[T](arg: T, functions: T => Future[T]*): Future[T] = {
      if (functions.isEmpty) {
        Future {
          arg
        }
      }
      else {
        val f = functions(0)
        f(arg).flatMap(t => doInOrderInternal(t, functions.drop(1): _*))
      }
    }

    val f1 = (x: Int) => Future {
      x + 1
    }
    val f2 = (x: Int) => Future {
      x + 2
    }
    val f3 = (x: Int) => Future {
      x - 3
    }
    val combined = doInOrder(f1, f2, f3)
    combined(1).foreach(x => println(s"x = $x"))
  }

  //4. Напишите функцию doTogether, принимающую функции f:
  //T => Future[U] и g: T => Future[V] и возвращающую функцию
  //T => Future[(U, V)], которая выполняет два задания
  //параллельно и для заданного значения t в конечном счете
  //возвращает (f(t), g(t)).
  {
    def doTogether[T, U, V](f: T => Future[U], g: T => Future[V]): T => Future[(U, V)] = {
      (arg: T) => {
        val ft = f(arg)
        val gt = g(arg)
        ft.flatMap(u => gt.map(v => (u, v)))
      }
    }

    val f1 = (x: Int) => Future {
      println(s"Future x start")
      Thread.sleep(3000)
      println(s"Future x done")
      x * x
    }
    val f2 = (y: Int) => Future {
      println(s"Future y start")
      Thread.sleep(3000)
      println(s"Future y done")
      y / 2
    }
    val combined = doTogether(f1, f2)
    val res = combined(10)
    res.onComplete(r => println(r))
  }

  //5. Напишите функцию, принимающую последовательность объектов
  //Future и возвращающую объект Future, который в конечном счете
  //возвращает последовательность всех результатов.
  {
    import scala.collection.Seq
    def futuresToSeq[T](futures: Future[T]*): Future[Seq[T]] = Future.sequence(futures)
  }

  //6. Напишите метод
  //Future[T] repeat(action: => T, until: T => Boolean)
  //который асинхронно продолжает выполнять action, пока не
  //получит значения, соответствующего предикату until. Предикат
  //так же должен выполняться асинхронно.
  {
    import scala.concurrent.duration._
    def repeat[T](action: => T, until: T => Boolean): Future[T] = Future {
      val res = action
      if (until(res)) res else Await.result(repeat(action, until), 1.hour)
    }

    //repeat(Console.in.readLine(), _ == "secret")
  }

  //7. Напишите программу, подсчитывающую количество простых
  //чисел между 1 и n с использованием BigInt.isProbablePrime.
  //Разбейте интервал на p частей, где p – количество доступных
  //процессоров (ядер). Подсчитайте количество простых чисел
  //в каждой части с помощью заданий Future, выполняющихся
  //параллельно, и объедините полученные результаты.
  {
    import scala.math.BigInt
    val numberOfCores = 8
    val n = 100_000
    val partitionSize = n / numberOfCores
    def isPrime(i: Int) = BigInt(i).isProbablePrime(1024)
    val resFuture = Future.traverse(0 until numberOfCores)(i => Future {
      val start = partitionSize * i
      val end = start + partitionSize
      (start until end).filter(isPrime)
    }).map(_.flatten)

    resFuture.foreach(println)
  }

  //13. Используйте Promise для отмены задания. Разбейте заданный
  //диапазон больших целых чисел на несколько поддиапазонов
  //и выполните параллельный поиск палиндромных простых чисел.
  //При обнаружении такого числа установите его как значение
  //объекта Future. Все задания должны периодически проверять,
  //завершился ли объект Promise, и завершаться, если это
  //произошло.
  {
    import scala.math.BigInt
    def isPrime(i: Int) = BigInt(i).isProbablePrime(1024)
    def isPolindrom(i: Int) = i.toString == i.toString.reverse
    def findPrimePolindrom(n: Int): Future[Int] = {
      val numberOfCores = 8
      val partitionSize = n / numberOfCores
      val p = Promise[Int]()
      Future.traverse(0 until numberOfCores)(i => Future {
        val start = partitionSize * i
        val end = start + partitionSize
        (for (i <- start until end if !p.isCompleted) yield i)
          .filter(isPrime)
          .filter(isPolindrom)
          .foreach(i => {
            p.trySuccess(i)
          })
      })
      p.future
    }

    findPrimePolindrom(100_000).onComplete(i => println(s"primary polindrom = ${i}"))
  }
}

