import scala.math._

object Main extends App {
  //2. Вычислите квадратный корень из 3, а затем возведите результат в квадрат.
  val sqrtpow = pow(sqrt(3), 2)
  println(s"pow(sqrt(3), 2) = $sqrtpow")

  //4. Язык Scala позволяет умножать строки на числа – попробуйте выполнить выражение "crazy" * 3
  val crazy3 = "crazy" * 3
  println(s"\"crazy\" * 3 = $crazy3")

  //5. Что означает выражение 10 max 2? В каком классе определен метод max?
  // Метод определен в классе RichInt
  val max2 = 10 max 2
  println(s"10 max 2 = $max2")

  //6. Используя число типа BigInt, вычислите 2^1024
  val pow2x1024 = BigInt(2) pow 1024
  println(s"2^1024 = $pow2x1024")

  //7. Что нужно импортировать, чтобы найти случайное простое
  //число вызовом метода probablePrime(100, Random) без использования
  // каких-либо префиксов перед именами probablePrime и Random?
  import scala.math.BigInt.probablePrime
  import scala.util.Random
  val probablePrime100 = probablePrime(100, Random)
  println(s"probablePrime(100, Random) = $probablePrime100")

  //8. Один из способов создать файл или каталог со случайным
  //именем состоит в том, чтобы сгенерировать случайное число
  //типа BigInt и преобразовать его в систему счисления по
  //основанию 36, в результате получится строка, такая как
  //"qsnvbevtomcj38o06kul". Отыщите в Scaladoc методы, которые
  //можно было бы использовать для этого.
  val randomString = probablePrime(256, Random).toString(36)
  println(s"randomString = $randomString")

  //9. Как получить первый символ строки в языке Scala? А последний символ?
  val someString = "qwerty"
  println(s"first char = ${someString(0)}")
  println(s"last char = ${someString.last}")

  //10. Что делают строковые функции take, drop, takeRight и dropRight?
  //Какие преимущества и недостатки они имеют в сравнении с substring?
  println(s"$someString.take(2) = ${someString.take(2)}")
  println(s"$someString.drop(2) = ${someString.drop(2)}")
  println(s"$someString.takeRight(2) = ${someString.takeRight(2)}")
  println(s"$someString.dropRight(2) = ${someString.dropRight(2)}")

}
