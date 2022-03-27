import scala.collection.{mutable, *}

object Main extends App {
  //1. Создайте ассоциативный массив с ценами на вещи, которые вы
  //хотели бы приобрести. Затем создайте второй ассоциативный
  //массив с теми же ключами и ценами с 10%-ной скидкой.
  {
    val prices = Map("New car" -> 3000.0, "Fridge" -> 520.0, "Laptop" -> 710.25)
    val discount = for ((k,v) <- prices) yield (k, v * 0.9)
    println(s"discount = ${discount.mkString(", ")}")
  }

  //2. Напишите программу, читающую слова из файла. Используйте
  //изменяемый ассоциативный массив для подсчета вхожде-
  //ний каждого слова.
  {
    val in = new java.util.Scanner(getClass.getResource("words.txt").openStream())
    val wordsCount = mutable.Map[String, Int]()
    while (in.hasNext) {
      val key = in.next
      if (wordsCount.contains(key)) {
        wordsCount(key) = wordsCount(key) + 1
      }
      else {
        wordsCount(key) = 1
      }
    }
    println(s"wordsCount = ${wordsCount.mkString(", ")}")
  }

  //3. Выполните предыдущее упражнение, используя неизменяемый
  // ассоциативный массив.
  {
    val in = new java.util.Scanner(getClass.getResource("words.txt").openStream())
    var wordsCount = immutable.Map[String, Int]()
    while (in.hasNext) {
      val key = in.next
      if (wordsCount.contains(key)) {
        wordsCount ++= immutable.Map(key -> (wordsCount(key) + 1))
      }
      else {
        wordsCount ++= immutable.Map(key -> 1)
      }
    }
    println(s"wordsCount = ${wordsCount.mkString(", ")}")
  }

  //4. Выполните предыдущее упражнение, используя сортированный
  //ассоциативный массив, чтобы слова выводились в отсортированном
  //порядке.
  {
    val in = new java.util.Scanner(getClass.getResource("words.txt").openStream())
    val wordsCount = mutable.TreeMap[String, Int]()
    while (in.hasNext) {
      val key = in.next
      if (wordsCount.contains(key)) {
        wordsCount(key) = wordsCount(key) + 1
      }
      else {
        wordsCount(key) = 1
      }
    }
    println(s"wordsCount sorted = ${wordsCount.mkString(", ")}")
  }

  //5. Выполните предыдущее упражнение, используя java.util.TreeMap,
  //адаптировав его для работы со Scala API.
  {
    val in = new java.util.Scanner(getClass.getResource("words.txt").openStream())
    import scala.jdk.CollectionConverters._
    val wordsCount = new java.util.TreeMap[String, Int]().asScala
    while (in.hasNext) {
      val key = in.next
      if (wordsCount.contains(key)) {
        wordsCount(key) = wordsCount(key) + 1
      }
      else {
        wordsCount(key) = 1
      }
    }
    println(s"wordsCount sorted java = ${wordsCount.mkString(", ")}")
  }

  //6. Определите связанную хеш-таблицу, отображающую "Monday"
  //в java.util.Calendar.MONDAY, и так далее для других дней
  //недели. Продемонстрируйте обход элементов в порядке их
  //добавления.
  {
    val map = mutable.LinkedHashMap(
      "Monday" -> java.util.Calendar.MONDAY,
      "Tuesday" -> java.util.Calendar.TUESDAY,
      "Wednesday" -> java.util.Calendar.WEDNESDAY,
      "Thursday" -> java.util.Calendar.THURSDAY,
      "Friday" -> java.util.Calendar.FRIDAY,
      "Saturday" -> java.util.Calendar.SATURDAY,
      "Sunday" -> java.util.Calendar.SUNDAY
    )
    for ((k, v) <- map) println(s"$k -> $v")
  }

  //7. Выведите таблицу всех Java-свойств
  {
    import scala.jdk.CollectionConverters._
    val props = System.getenv.asScala
    val maxK = props.keySet.max.length
    for ((k, v) <- props) println(k + " " * (maxK - k.length) + " | " + v)
  }

  //8. Напишите функцию minmax(values: Array[Int]), возвращающую
  //пару, содержащую наименьшее и наибольшее значения.
  {
    def minmax(values: Array[Int]) = (values.max, values.min)
    val minMax = minmax(Array(10, 5, 7, 3, 0, 11))
    println(s"minmax = $minMax")
  }

  //9. Напишите функцию lteqgt(values: Array[Int], v: Int),
  //возвращающую тройку, содержащую счетчик значений, меньших v,
  //равных v и больших v.
  {
    def lteqgt(values: Array[Int], v: Int) = {
      var lt, eq, gt = 0
      for (value <- values) {
        if (value > v) {
          gt += 1
        }
        else if (value == v) {
          eq += 1
        }
        else {
          lt += 1
        }
      }
      (lt, eq, gt)
    }

    val a = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val res = lteqgt(a, 5)
    println(s"lteqgt = $res")
  }

  //10. Что произойдет, если попытаться упаковать две строки, такие
  //как "Hello".zip("World")? Придумайте достаточно реалистичный
  //случай использования.
  {
    val paired = "Hello" zip "World"
    println(s"pair = $paired")
  }
}
