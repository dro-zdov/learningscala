import Main.Cards

object Main extends App {
  //1. Напишите объект Conversions с методами inchesToCentimeters,
  //gallonsToLiters и milesToKilometers.
  object Conversions {
    def inchesToCentimeters(inches: Double): Double = inches * 2.54
    def gallonsToLiters(gallons: Double): Double = gallons * 4.5461
    def milesToKilometers(miles: Double): Double = miles * 1.6903
  }

  //2. Предыдущую задачу трудно назвать объектно-ориентированной.
  //Реализуйте общий суперкласс UnitConversion и определите
  //объекты InchesToCentimeters, GallonsToLiters и MilesToKilometers,
  //наследующие его.
  abstract class UnitConversion {
    def convert(value: Double): Double
  }
  object InchesToCentimeters extends UnitConversion {
    def convert(value: Double): Double = value * 2.54
  }
  object GallonsToLiters extends UnitConversion {
    def convert(value: Double): Double = value * 4.5461
  }
  object MilesToKilometers extends UnitConversion {
    def convert(value: Double): Double = value * 1.6903
  }

  //3. Определите объект Origin, наследующий класс java.awt.Point.
  //Почему это не самая лучшая идея? (Рассмотрите поближе
  // методы класса Point.)
  object Origin extends java.awt.Point
  Origin.x = 10
  Origin.y = 20

  //4. Определите класс Point с объектом-компаньоном, чтобы
  //можно было конструировать экземпляры Point, как Point(3, 4),
  //без ключевого слова new.
  class Point(val x: Double, val y: Double)
  object Point {
    def apply(x: Double, y: Double): Point = new Point(x, y)
  }
  val p = Point(1.25, 3.15)

  //5. Напишите приложение на языке Scala, используя трейт App,
  //которое выводит аргументы командной строки в обратном
  //порядке, разделяя их пробелами. Например, команда scala
  //Reverse Hello World должна вывести World Hello.
  val argsStr = if (args != null) args.reverse.mkString(" ") else ""
  println(s"argsStr = $argsStr")

  //6. Напишите перечисление, описывающее четыре масти
  //игральных карт так, чтобы метод toString возвращал
  //«♣», «♦», «♥» или «♠».
  object Cards extends Enumeration {
    val Clubs: Cards.Value = Value("♣")
    val Diamonds: Cards.Value = Value("♦")
    val Hearts: Cards.Value = Value("♥")
    val Spades: Cards.Value = Value("♠")
  }

  //7. Напишите функцию для проверки масти карты,
  //реализованной в предыдущем упражнении, которая
  //проверяла бы принадлежность карты к красной масти.
  def isRed(suit: Cards.Value) = suit == Cards.Diamonds || suit == Cards.Hearts
}
