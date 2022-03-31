object Main extends App {
  //1. Напишите функцию, возвращающую для указанной строки
  //отображение индексов всех символов. Например, вызов
  //indexes("Mississippi") должен вернуть ассоциативный массив,
  //связывающий 'M' со множеством {0}, 'i' – со множеством
  // {1, 4, 7, 10} и т. д. Используйте изменяемый ассоциативный
  //массив, отображающий символы в изменяемые множества. Как
  //обеспечить сортировку индексов в пределах множеств?
  {
    import scala.collection.mutable
    def indexes(s: String) = {
      val map = mutable.Map[Char, mutable.Set[Int]]().withDefault(k => mutable.SortedSet())
      s.zipWithIndex.foldLeft(map)((map, p) => map += ((p._1, map(p._1) + p._2)))
    }
    val res = indexes("Mississippi")
    println(res)
  }

  //2. Реализуйте предыдущее упражнение с использованием
  //неизменяемого ассоциативного массива символов в списки.
  {
    def indexes(s: String) = s.zipWithIndex.groupBy(_._1).map((k, v) => (k, v.map(_._2)))
    val res = indexes("Mississippi")
    println(res)
  }

  //3. Напишите функцию, удаляющую каждый второй элемент из
  //ListBuffer. Попробуйте два способа. Вызовите remove(i) для
  //всех четных индексов i, начиная с конца списка.
  //Скопируйте каждый второй элемент в новый список.
  //Сравните производительность обоих способов.
  {
    import scala.collection.mutable.ListBuffer
    def removeEven1(lb: ListBuffer[Int]): Unit = {
      lb.zipWithIndex.filter((v, i) => i % 2 != 0).reverse.foreach((v, i) => lb.remove(i))
    }
    val buf1 = ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val now1 = System.nanoTime
    removeEven1(buf1)
    println("removeEven1 take " + (System.nanoTime - now1) + "ns")
    println("buf1 = " + buf1)

    def removeEven2(lb: ListBuffer[Int]) = {
      lb.zipWithIndex.filter((v, i) => i % 2 == 0).map(_._1)
    }
    var buf2 = ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val now2 = System.nanoTime
    buf2 = removeEven2(buf2)
    println("removeEven2 take " + (System.nanoTime - now2) + "ns")
    println("buf2 = " + buf2)
  }

  //4. Напишите функцию, принимающую коллекцию строк и ассоциативный
  //массив, отображающий строки в целые числа. Она должна возвращать
  //коллекцию целых чисел, значения которых соответствуют строкам в
  //ассоциативном массиве, повторяющимся в исходной коллекции.
  {
    import scala.collection.immutable.Map
    val a = Array("Tom", "Fred", "Harry")
    val m = Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)
    val res = a.flatMap(m.get(_))
    println("res = " + res.mkString(", "))
  }

  //5. Реализуйте функцию, действующую подобно mkString,
  //используя reduceLeft.
  {
    def mkString(iter: Iterable[Any], separator: String): String = {
      iter.reduceLeft((s, v) => s"$s$separator$v").toString
    }
    val a = Array("Tom", "Fred", "Harry")
    println("Custom mkString = " + mkString(a, ", "))
  }

  //6. Пусть имеется список lst целых чисел, что означает выражение
  //(lst :\ List[Int]())(_ :: _)? (List[Int]() /: lst)(_ :+ _)? Что
  //можно изменить здесь, чтобы перевернуть список?
  {
    import scala.collection.immutable.List
    val lst = List(1, 2, 3)

    println((lst :\ List[Int]())(_ :: _)) // эквивалентно
    println(lst.foldRight(List[Int]())(_ :: _))

    println((List[Int]() /: lst)(_ :+ _)) // эквивалентно
    println(lst.foldLeft(List[Int]())(_ :+ _))
  }

  //7. В разделе 13.10 «Функция zip» выражение (prices zip quantities)
  //map { p => p._1 * p._2 } выглядит несколько грубовато. Мы
  //не можем написать (prices zip quantities) map { _ * _ }, потому
  //что _ * _ – это функция с двумя аргументами, а нам нужна
  //функция, принимающая один аргумент – кортеж. Метод tupled
  //класса Function изменяет функцию с двумя аргументами,
  //превращая ее в функцию, принимающую кортеж. Примените
  //метод tupled к функции умножения так, чтобы можно было
  //применить map к списку пар.
  {
    import scala.collection.immutable.List
    val prices = List(5.0, 20.0, 9.95)
    val quantities = List(10, 2, 1)
    val fun = (a: Double, b: Int) => a * b
    (prices zip quantities) map { fun.tupled }
  }

  //8. Напишите функцию, превращающую массив значений Double
  //в двумерный массив. Число колонок должно передаваться
  //в виде параметра. Например, для Array(1, 2, 3, 4, 5, 6) и трех
  //колонок функция должна вернуть Array(Array(1, 2, 3), Array(4,
  //5, 6)). Используйте метод grouped.
  {
    def grouping(a: Array[Double], cnt: Int) = a.grouped(cnt)
    println(grouping(Array(1, 2, 3, 4, 5, 6), 3).map(_.mkString("[", ", ", "]")).mkString("[", ", ", "]"))
  }

  //10. Метод java.util.TimeZone.getAvailableIDs возвращает часовые
  //пояса, такие как Africa/Cairo и Asia/Chungking. На каком континенте
  //больше всего часовых поясов?
  {
    import java.util.TimeZone
    val res = TimeZone.getAvailableIDs.groupBy(s => s.split("/")(0))//.maxBy(p => p._2.length)
      .map((k, v) => (k, v.length))
      .maxBy(_._2)

    println(res)
  }

  //11. Гарри Хакер читает файл в строку и хотел бы использовать
  // параллельную коллекцию для обновления частот встречаемости
  //символов в строке. Он использует следующий код:
  //val frequencies = new scala.collection.mutable.HashMap[Char, Int]
  //for (c <- str.par) frequencies(c) = frequencies.getOrElse(c, 0) + 1
  //Почему этот прием не даст желаемых результатов? Как можно
  //было бы распараллелить вычисления?
  {
    import scala.collection.parallel.CollectionConverters.IterableIsParallelizable
    import scala.collection.immutable.Map
    val str = "vhgfjjhgjhvjgghfhgvghvghaavghvhgvghvghvghvghcghcghcnb"
    val res = str.toList.par.aggregate(Map[Char, Int]().withDefaultValue(0))(
      (map, c) => map + ((c, map(c) + 1)),
      (map1, map2) => (map1.keySet ++ map2.keySet).map(k => (k, map1(k) + map2(k))).toMap.withDefaultValue(0)
    )
    println(res)
  }
}

