import scala.collection.mutable.ArrayBuffer

object Main extends App {
  //1. Определите класс CheckingAccount, наследующий класс BankAccount,
  //который взимает $1 комиссионных за каждую операцию
  //пополнения или списания.
  class BankAccount(initialBalance: Double) {
    private var balance = initialBalance
    def currentBalance: Double = balance
    def deposit(amount: Double): Double = { balance += amount; balance }
    def withdraw(amount: Double): Double = { balance -= amount; balance }
  }
  class CheckingAccount (initialBalance: Double) extends BankAccount(initialBalance) {
    override def deposit(amount: Double): Double = { super.deposit(amount - 1) }
    override def withdraw(amount: Double): Double = { super.withdraw(amount + 1) }
  }

  //2. Определите класс SavingsAccount, наследующий класс BankAccount
  //из предыдущего упражнения, который начисляет проценты каждый месяц
  //(вызовом метода earnMonthlyInterest) и позволяет бесплатно выполнять
  // три операции зачисления или списания каждый месяц.
  // Метод earnMonthlyInterest должен сбрасывать счетчик транзакций.
  class SavingsAccount (initialBalance: Double) extends BankAccount(initialBalance) {
    private var transactionCount = 0
    private val freeTransactionsCount = 3
    override def deposit(amount: Double): Double = {
      if (transactionCount > freeTransactionsCount) {
        super.deposit(amount - 1)
      }
      else {
        super.deposit(amount)
      }
      transactionCount += 1
      currentBalance
    }
    override def withdraw(amount: Double): Double = {
      if (transactionCount > freeTransactionsCount) {
        super.withdraw(amount + 1)
      }
      else {
        super.withdraw(amount)
      }
      transactionCount += 1
      currentBalance
    }
    def earnMonthlyInterest(): Double = {
      transactionCount = 0
      super.deposit(currentBalance * 0.3)
    }
  }

  //4. Определите абстрактный класс элемента Item с методами
  //price и description. Определите подкласс простого элемента
  //SimpleItem, представляющий элемент, цена и описание
  // которого определяются в конструкторе. Используйте тот факт,
  //что объявление val может переопределять def. Определите класс
  //Bundle – пакет элементов, содержащий другие элементы. Его
  //цена должна определяться как сумма цен элементов в пакете.
  //Реализуйте также механизм добавления элементов в пакет
  //и соответствующий метод description.
  abstract class Item {
    def description: String
    def price: Double
  }

  class SimpleItem(val description: String, val price: Double) extends Item

  class Bundle extends Item {
    private val items = new ArrayBuffer[Item]
    def description: String = items.map(_.description).mkString(", ")
    def price: Double = items.map(_.price).sum
    def add(item: Item): Unit = items += item
  }
  val bundle = new Bundle
  bundle.add(new SimpleItem("Laptop", 300.0))
  bundle.add(new SimpleItem("Fridge", 250.0))
  bundle.add(new SimpleItem("Bed", 50.0))
  println(s"bundle price = ${bundle.price}")
  println(s"bundle description = ${bundle.description}")

  //5. Спроектируйте класс точки Point, значения координат x и y
  //которой передаются конструктору. Реализуйте подкласс точки
  //с подписью LabeledPoint, конструктор которого будет принимать
  //строку с подписью и значения координат x и y.
  class Point(val x: Double, val y: Double)
  class LabeledPoint(val label: String, x: Double, y: Double) extends Point(x, y)

  //6. Определите абстрактный класс геометрической фигуры Shape
  //с абстрактным методом centerPoint и подклассы прямоуголь-
  //ника и окружности, Rectangle и Circle. Реализуйте соответствующие
  //конструкторы в подклассах и переопределите метод
  //centerPoint в каждом подклассе.
  abstract class Shape {
    def centerPoint: Point
  }
  class Rectangle(val width: Double, val height: Double, val x: Double, val y: Double)
    extends Shape{
    def centerPoint: Point = new Point(x, y)
  }
  class Circle(val radius: Double, val x: Double, val y: Double) extends Shape {
    def centerPoint: Point = new Point(x, y)
  }

  //9. В классе Creature из раздела 8.10 «Порядок создания и
  // опережающие определения» замените val range на def.
  //Что произойдет, когда вы также будете использовать def в
  //подклассе Ant? Что произойдет, если в подклассе использовать val?
  class Creature {
    def range: Int = 10
    val env: Array[Int] = new Array[Int](range)
  }
  /*Используем val в подклассе*/ {
    class Ant extends Creature {
      override val range = 2
    }
    val ant = new Ant
    println(s"ant.env.size = ${ant.env.length}")
  }
  /*Используем def в подклассе*/ {
    class Ant extends Creature {
      override def range = 2
    }
    val ant = new Ant
    println(s"ant.env.size = ${ant.env.length}")
  }
}

