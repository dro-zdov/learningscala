import scala.collection.mutable.ArrayBuffer

object Main extends App {
  //1. Напишите фрагмент кода, который записывает в массив a целые
  //числа в диапазоне от 0 (включительно) до n (исключая его).
  val n = 10
  val a = new Array[Int](n)
  for (i <- 0 until n) a(i) = i
  println(s"n = $n, a = ${a.mkString("[", ",", "]")}")

  //2. Напишите цикл, меняющий местами смежные элементы в массиве целых чисел.
  val a2 = Array(1, 2, 3, 4, 5)
  for (i <- 1 until a2.length by 2) {
    val tmp = a2(i)
    a2(i) = a2(i-1)
    a2(i-1) = tmp
  }
  println(s"a2 = ${a2.mkString("[", ",", "]")}")

  //3. Повторите предыдущее упражнение, но создайте новый массив
  //с переставленными элементами. Используйте for/yield.
  val a3 = Array(1, 2, 3, 4, 5)
  val a3swap = for (
    i <- a3.indices;
    idx = if (i % 2 == 0) math.min(i + 1, a3.indices.last) else i - 1
  ) yield a3(idx)
  println(s"a3swap = ${a3swap.mkString("[", ",", "]")}")

  //4. Дан массив целых чисел, создайте новый массив, в котором
  //сначала будут следовать положительные значения из оригинального
  //массива, в оригинальном порядке, а за ними отрицательные и
  //нулевые значения, тоже в оригинальном порядке.
  val a4 = Array(1, -1, 0, 2, 5,-9, 1, -7)
  val a4transform = ((for (a <- a4 if a > 0) yield a).toBuffer ++= (for (a <- a4 if a <= 0) yield a)).toArray
  println(s"a4transform = ${a4transform.mkString("[", ",", "]")}")

  //5. Как бы вы вычислили среднее значение элементов массива
  //Array[Double]?
  val a5 = Array(1.25, 8.52, 0.4, 2.206, 5.5)
  val middle = a5.sum / a5.length
  println(s"middle of ${a5.mkString("[", ",", "]")} = $middle")

  //6. Как бы вы переупорядочили элементы массива Array[Int] так,
  //чтобы они следовали в обратном отсортированном порядке?
  //Как бы вы сделали то же самое с буфером ArrayBuffer[Int]?
  val a6 = Array(1, 2, 3, 4, 5)
  println(s"reversed a6 = ${a6.reverse.mkString("[", ",", "]")}")
  println(s"reversed a6 buf = ${a6.toBuffer.reverse.mkString("[", ",", "]")}")

  //7. Напишите фрагмент программного кода, выводящий значения
  //всех элементов из массива, кроме повторяющихся.
  val a7 = Array(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
  println(s"unique elements of a7 = ${a7.distinct.mkString("[", ",", "]")}")

  //8. Представьте, что имеется буфер целых чисел и вам требуется
  // удалить все отрицательные значения, кроме первого.
  val a8 = ArrayBuffer(1, -1, 0, 2, 5, -9, 1, -7)
  val negativeIndices8 = for (i <- a8.indices if a8(i) < 0) yield i
  for (i <- negativeIndices8.tail.reverse) a8.remove(i)
  println(s"elements of a8 = ${a8.mkString("[", ",", "]")}")

  //9. Улучшите решение из предыдущего упражнения.
  var a9 = ArrayBuffer(1, -1, 0, 2, 5, -9, 1, -7, -8, 10, 55)
  val negativeIndices9 = (for (i <- a9.indices if a9(i) < 0) yield i).tail.reverse
  for (i <- negativeIndices9; j <- i + 1 until a9.length) {
    a9(j -1) = a9(j)
  }
  a9 = a9.dropRight(negativeIndices9.length)
  println(s"elements of a9 = ${a9.mkString("[", ",", "]")}")

  //10. Создайте коллекцию всех часовых поясов, возвращаемых
  //методом java.util.TimeZone.getAvailableIDs для Америки.
  // Отбросьте префикс "America/" и отсортируйте результат.
  import java.util.TimeZone
  val america = "America/"
  val timezones = for (tz <- TimeZone.getAvailableIDs if tz.startsWith(america)) yield tz.drop(america.length)
  println(s"timezones = ${timezones.sorted.mkString("[", ",", "]")}")

  //11. Импортируйте java.awt.datatransfer._ и создайте объект типа
  //SystemFlavorMap. Затем вызовите метод getNativesForFlavor с параметром DataFlavor.
  //imageFlavor и получите возвращаемое значение как буфер Scala.
  import java.awt.datatransfer._
  val flavors = SystemFlavorMap.getDefaultFlavorMap.asInstanceOf[SystemFlavorMap]
  flavors.getNativesForFlavors(Array(DataFlavor.imageFlavor))
}
