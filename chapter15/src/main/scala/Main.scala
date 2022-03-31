object Main extends App {
  //6. Реализуйте объект с volatile-полем типа Boolean. Приостановите
  //выполнение одного потока на некоторое время, затем присвойте этому
  //полю значение true в этом же потоке, выведите сообщение и завершите
  //работу потока. Другой поток, выполняющийся параллельно, должен
  //проверить значение этого поля, и если оно имеет значение true
  // – выводить сообщение и завершаться. В противном случае он должен
  //приостанавливаться на короткое время и повторять попытку.
  //Что случится, если поле не будет объявлено как volatile?
  {
    object twoThreadRunner {
      @volatile var flag = false
      def doStuff(): Unit = {
        new Thread {
          override def run(): Unit = {
            Thread.sleep(2000L)
            flag = true
            println("Thread 1 done")
          }
        }.start()
        new Thread {
          override def run(): Unit = {
            while (!flag) {
              Thread.sleep(500L)
              println("Thread 2 waiting")
            }
            println("Thread 2 done")
          }
        }.start()
      }
    }
    twoThreadRunner.doStuff()
  }

  //7. Приведите пример, демонстрирующий, почему оптимизация
  //хвостовой рекурсии не может быть произведена, если метод
  //допускает возможность переопределения.
  {
    import scala.annotation.tailrec
    class Util {
      /*@tailrec*/ def sum(xs: Seq[Int], partial: BigInt): BigInt =
        if (xs.isEmpty) partial else sum(xs.tail, xs.head + partial)
    }
    class SuperUtil extends Util {
      override def sum(xs: Seq[Int], partial: BigInt): BigInt = xs.sum // Больше не рекурсивный
    }
  }
}

