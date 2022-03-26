object Main extends App {
  //1. Сигнум числа равен 1, если число положительное, -1 – если
  //отрицательное, и 0 – если оно равно нулю. Напишите функцию,
  //вычисляющую это значение.
  def calcSignum(x: Int) = if (x == 0) 0 else if (x > 0) 1 else -1
  println(s"signum of -10 = ${calcSignum(-10)}")
  println(s"signum of 0 = ${calcSignum(0)}")
  println(s"signum of 10 = ${calcSignum(10)}")

  //2. Какое значение возвращает пустой блок {}? Каков его тип?
  val emptyBlock: Unit = {}
  println(s"{} returns ${emptyBlock}") //Unit

  //3. Придумайте ситуацию, когда присваивание x = y = 1 будет
  //допустимым в Scala.
  var y = 0
  val x: Unit = y = 1

  //4. Напишите на языке Scala цикл, эквивалентный циклу for на языке Java:
  // for (int i = 10; i >= 0; i-- ) System.out.println(i);
  for (i <- 10 to 0 by -1) println(s"i = $i")

  //5. Напишите процедуру countdown(n: Int), которая выводит числа
  //от n до 0.
  def countdown(n: Int) = { // Without = not compiled
    for (i <- n to 0 by -1) println(s"countdown i = $i")
  }
  countdown(5)

  //6. Напишите цикл for для вычисления произведения кодовых
  //пунктов Юникода всех букв в строке.
  val someString = "Hello"
  val codePointIterator = someString.codePointStepper.iterator
  var  multiplication = 1
  for (codePoint <- codePointIterator) multiplication *= codePoint
  println(s"multiplication of \"$someString\" = $multiplication")

  //7. Решите предыдущее упражнение без применения цикла.
  val codePointIterator2 = someString.codePointStepper.iterator
  //codePointIterator2.fold(1)((acc, codePoint) => acc * codePoint)
  multiplication = codePointIterator2.product
  println(s"multiplication2 of \"$someString\" = $multiplication")

  //8. Напишите функцию product(s : String), вычисляющую произведение,
  // как описано в предыдущих упражнениях.
  def product(s : String) = s.codePointStepper.iterator.product
  println(s"multiplication3 of \"$someString\" = ${product(someString)}")

  //9. Сделайте функцию из предыдущего упражнения рекурсивной
  def productRecursive(s : String): Int = if (s.isEmpty) 1 else s.head.toInt * productRecursive(s.tail)
  println(s"multiplication4 of \"$someString\" = ${productRecursive(someString)}")

  //10. Напишите функцию, вычисляющую x^n, где n – целое число.
  def powRecursive(x:Int, n: Int): Int = {
    if (n > 0) {
      if (n % 2 == 0) {
        powRecursive(x, n/2) * powRecursive(x, n/2)
      }
      else {
        x * powRecursive(x, n-1)
      }
    }
    else if (n == 0) {
      1
    }
    else {
      1 / powRecursive(x, -n)
    }
  }

  println(s"2^8 = ${powRecursive(2, 8)}")

  //11. Определите интерполятор строк date, чтобы значение типа
  //java.time.LocalDate можно было определить как
  //date"$year-$month-$day".
  //TODO
}
