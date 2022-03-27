import scala.beans.BeanProperty

object Main extends App {
  //1. Усовершенствуйте класс Counter в разделе 5.1 «Простые классы
  //и методы без параметров», чтобы значение счетчика не
  //превращалось в отрицательное число по достижении Int.MaxValue.
  class Counter {
    private var value = 0
    def increment() = if (value == Int.MaxValue) value = 0 else value + 1
    def current() = value
  }

  //2. Напишите класс BankAccount с методами deposit и withdraw
  //и свойством balance, доступным только для чтения.
  class BankAccount {
    private var privateBalance = 0
    def deposit(amount: Int): Unit = privateBalance += amount
    def withdraw(amount: Int): Unit = privateBalance -= amount
    def balance: Int = privateBalance
  }

  //3. Напишите класс Time со свойствами hours и minutes, доступны-
  //ми только для чтения, и методом before(other: Time): Boolean,
  //который проверяет, предшествует ли время this времени other.
  //Объект Time должен конструироваться как new Time(hrs, min),
  //где hrs – время в 24-часовом формате.
  class Time(hrs: Int, min: Int) {
    def hours: Int = hrs
    def minutes: Int = min
    def before(other: Time): Boolean =
      if (this.hours < other.hours) true
      else if (this.hours == other.hours) this.minutes < other.minutes
      else false
  }

  //4. Перепишите класс Time из предыдущего упражнения, чтобы
  //внутри время было представлено количеством минут, прошедших
  //с начала суток (между 0 и 24 × 60 – 1). Общедоступный
  //интерфейс при этом не должен измениться. То есть эти изменения
  //не должны оказывать влияния на клиентский код.
  class Time2(hrs: Int, min: Int) {
    private val value = hrs * 60 + min
    def hours: Int = value / 60
    def minutes: Int = value % 60
    def before(other: Time2): Boolean = this.value < other.value
  }

  //5.Создайте класс Student со свойствами в формате JavaBeans
  //name (типа String) и id (типа Long), доступными для
  // чтения/записи.
  class Student(@BeanProperty var name: String, @BeanProperty var id: Long)
  val s = new Student("Max", 1)
  //val id = s.getId //requires scala 2.x
  //s.setId(2)

  //6. В классе Person из раздела 5.1 «Простые классы и методы без
  //параметров» реализуйте главный конструктор, преобразующий
  //отрицательное значение возраста в 0.
  class Person(ageArg: Int) {
    val age: Int = if (ageArg < 0) 0 else ageArg
  }

  //7. Напишите класс Person с главным конструктором, принимающим
  //строку, которая содержит имя, пробел и фамилию, например:
  // new Person("Fred Smith"). Сделайте свойства firstName
  //и lastName доступными только для чтения.
  class Person2(name: String) {
    val firstName: String = name.split(" ")(0)
    val lastName: String = name.split(" ")(1)
  }
  val p = new Person2("John Smith")
  println(s"firstName = ${p.firstName}, lastName = ${p.lastName}")

  //8. Создайте класс Car со свойствами, определяющими
  // производителя, название модели и год производства,
  // которые доступны только для чтения, и свойство с
  // регистрационным номером автомобиля, доступным для
  // чтения/записи. Добавьте четыре конструктора.
  class Car (
    val manufacturer:String,
    val model: String,
    val fabricationYear: Int,
    var registration: String
  ) {
    def this(manufacturer:String, model: String, fabricationYear: Int) = {
      this(manufacturer, model, fabricationYear, "")
    }
    def this(manufacturer:String, model: String) = {
      this(manufacturer, model, -1)
    }
  }

  //10.Взгляните на следующее определение класса:
  //class Employee(val name: String, var salary: Double) {
  //def this() { this("John Q. Public", 0.0) }
  //}
  //Перепишите его так, чтобы он содержал явные определения
  //полей и имел главный конструктор по умолчанию.
  class Employee {
    var name: String = ""
    var salary: Double = 0.0
    def this(name: String, salary: Double) = {
      this()
      this.name = name
      this.salary = salary
    }
  }
}
