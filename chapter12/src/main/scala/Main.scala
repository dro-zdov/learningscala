object Main extends App {
  //1. Напишите функцию values(fun: (Int) => Int, low: Int, high: Int),
  //возвращающую коллекцию из значений в указанном диапазоне.
  //Например, вызов values(x => x * x, -5, 5) должен вернуть коллекцию
  // пар (-5, 25), (-4, 16), (-3, 9), ..., (5, 25).
  {
    def values(fun: (Int) => Int, low: Int, high: Int) = {
      for (i <- low to high) yield (i, fun(i))
    }
    val multiplication = values(x => x * x, -5, 5)
    println(multiplication.mkString(" "))
  }

  //2. Как получить наибольший элемент массива с помощью метода
  //reduceLeft?
  {
    val a = Array(1, 2, 3, 4, 5)
    val max = a.reduceLeft(math.max(_,_))
    println(s"max = $max")
  }

  //3. Реализуйте функцию вычисления факториала с помощью
  // методов to и reduceLeft без применения цикла или рекурсии.
  {
    def factorial(n: Int) = {
      (1 to n).reduceLeft(_ * _)
    }
    println(s"factorial of 5 = ${factorial(5)}")
  }

  //4. Предыдущая реализация должна предусматривать специальный
  //случай, когда n < 1. Покажите, как избежать этого с помощью
  //foldLeft.
  {
    def factorial(n: Int) = {
      (1 to n).foldLeft(1)(_ * _)
    }
    println(s"factorial of -5 = ${factorial(-5)}")
  }

  //5. Напишите функцию largest(fun: (Int) => Int, inputs: Seq[Int]),
  //возвращающую наибольшее значение функции внутри заданной
  //последовательности.
  {
    def largest(fun: (Int) => Int, inputs: Seq[Int]) = {
      inputs.map(fun).max
    }
    val l = largest(x => 10 * x - x * x, 1 to 10)
    println("largest(x => 10 * x - x * x, 1 to 10) = " + l)
  }

  //6. Измените предыдущую функцию так, чтобы она возвращала
  //входное значение, соответствующее наибольшему выходному
  //значению.
  {
    def largest(fun: (Int) => Int, inputs: Seq[Int]) = {
      val mapped = inputs.map(fun)
      inputs(mapped.indexOf(mapped.max))
    }
    val l = largest(x => 10 * x - x * x, 1 to 10)
    println("largest(x => 10 * x - x * x, 1 to 10) = " + l)
  }

  //7. Напишите функцию adjustToPair, принимающую функцию
  //типа (Int, Int) => Int и возвращающую эквивалентную
  //функцию, оперирующую парой
  {
    def adjustToPair(fun: (Int, Int) => Int) = {
      (p: (Int, Int)) => fun(p._1, p._2)
    }
    val res = adjustToPair(_ * _)((6, 7))
    println("adjustToPair(_ * _)((6, 7)) = " + res)
  }

  //8. В разделе 12.8 «Карринг» был представлен метод corresponds,
  //использующий два массива строк. Напишите вызов corresponds,
  //который проверял бы соответствие длин строк в одном
  //массиве целочисленным значениям в другом.
  {
    val a = Array("Hello", "World")
    val b = Array(5, 5)
    val res = a.corresponds(b)(_.length == _)
    println("a.corresponds(b)(_.length == _) = " + res)
  }

  //10. Реализуйте абстракцию управления потоком выполнения unless,
  //действующую подобно if, но с инвертированным толкованием
  //логического условия.
  {
    def unless(cond: => Boolean)(block: => Unit): Unit = {
      if (!cond) block
    }
    val a = 5
    val b = 6
    unless(a == b) { println("a != b") }
  }
}

