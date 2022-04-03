import scala.language.implicitConversions

object Main extends App {
  //2. Определите оператор +%, добавляющий указанный процент
  //к значению. Например, выражение 120 +% 10 должно вернуть
  //132. Используйте неявный класс.
  class CustomRichInt(val v: Int) extends AnyVal {
    def +%(percent: Int): Int = v + v*percent/100
  }
  implicit def intToCustomRichInt(i: Int): CustomRichInt = new CustomRichInt(i)
  println(120 +% 10) //132

  //3. Определите оператор ! вычисления факториала, чтобы выражение
  //5.! возвращало 120. Используйте неявный класс.
  {
    implicit class Factorial(val v: Int) {
      def `!`:Int = (1 to v).product
    }
    println(5.!)
  }

  //5. Реализуйте все необходимое для вычисления выражения
  {
    class Fraction(n: Int, d: Int) {
      val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
      val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
      override def toString: String = num + "/" + den
      def sign(a: Int): Int = if (a > 0) 1 else if (a < 0) -1 else 0
      def gcd(a: Int, b: Int): Int = {
        if (b == 0) math.abs(a) else gcd(b, a % b)
      }
      def +(that: Fraction): Fraction = {
        new Fraction(
          this.num * that.den + that.num * this.den,
          this.den * that.den
        )
      }
      def -(that: Fraction): Fraction = {
        new Fraction(
          this.num * that.den - that.num * this.den,
          this.den * that.den
        )
      }
      def *(that: Fraction): Fraction = {
        new Fraction(
          this.num * that.num,
          this.den * that.den
        )
      }
      def /(that: Fraction): Fraction = {
        new Fraction(
          this.num * that.den,
          this.den * that.num
        )
      }
    }

    implicit class RichFraction(f: Fraction) extends Ordered[Fraction] {
      override def compare(that: Fraction): Int = (f - that).den
    }

    println(Fraction(1,2) > Fraction(1,3)) // true
  }
}

