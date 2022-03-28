import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Main extends App {
  //1. Напишите на языке Scala код, который размещает строки
  //в файле в обратном порядке (последнюю делает первой и т. д.).
  {
    val source = Source.fromInputStream(getClass.getResource("lines.txt").openStream())
    val reversed = source.getLines.toArray.reverse
    val outputFile = new File("lines_reversed.txt")
    println(s"write lines_reversed to [${outputFile.getAbsolutePath}]")
    val out = new PrintWriter(outputFile)
    for (line <- reversed) out.println(line)
    source.close()
    out.close()
  }

  //2. Напишите программу на языке Scala, которая читает файл
  //с символами табуляции, заменяя их пробелами так, чтобы
  //сохранить правильное расположение границ столбцов, и
  //записывает результат в тот же файл.
  {
    val source = Source.fromInputStream(getClass.getResource("tabs.txt").openStream())
    val stringContent = source.mkString.replace("\t", "  ")
    val outputFile = new File("tabs_spaces.txt")
    println(s"write tabs_spaces to [${outputFile.getAbsolutePath}]")
    val out = new PrintWriter(outputFile)
    out.println(stringContent)
    source.close()
    out.close()
  }

  //3. Напишите фрагмент кода на Scala, который читает файл и
  // выводит в консоль все слова, содержащие 6 или более символов.
  {
    val source = Source.fromInputStream(getClass.getResource("words.txt").openStream())
    println("words with length >= 6:")
    source.mkString.split("\\s").filter(_.length >= 6).foreach(println(_))
    source.close()
  }

  //4. Напишите программу на Scala, которая читает текстовый
  //файл, содержащий только вещественные числа, выводит сумму,
  //среднее, максимальное и минимальное значения.
  {
    val source = Source.fromInputStream(getClass.getResource("numbers.txt").openStream())
    val numbers = source.mkString.split("\\s").map(_.toDouble)
    println(s"min = ${numbers.min}, max = ${numbers.max}, sum = ${numbers.sum}, avg = ${numbers.sum / numbers.length}")
    source.close()
  }

  //5. Напишите программу на Scala, которая записывает степени
  //двойки и их обратные величины в файл с экспонентой от 0
  //до 20. Расположите числа в столбцах:
  {
    val n = 20
    val outputFile = new File("pow_2_n.txt")
    println(s"write pow_2_n to [${outputFile.getAbsolutePath}]")
    val out = new PrintWriter(outputFile)
    val powers = for (i <- 0 to n) yield math.pow(2, i)
    val maxLen = powers.last.toString.length
    for (power <- powers) println(power.toInt + " " * (maxLen - power.toString.length) + "  " + 1/power )
  }

  //6. Напишите регулярное выражение для поиска строк в кавычках
  //"как эта, возможно с \" или \\" в программе на языке Java
  //или C++. Напишите программу на Scala, которая выводит все
  //такие строки, найденные в файле с исходными текстами.
  {
    val regex = """"[^"]*"""".r
    val source = Source.fromInputStream(getClass.getResource("someProgram.java").openStream())
    for (m <- regex.findAllIn(source.mkString)) println(m)
    source.close()
  }

  //7. Напишите программу на Scala, которая читает текстовый файл
  //и выводит все лексемы, не являющиеся вещественными числами.
  //Используйте регулярное выражение.
  {
    val numberRegex = """\d+\.?\d*""".r
    val scanner = new java.util.Scanner(getClass.getResource("words_and_numbers.txt").openStream())
    while (scanner.hasNext) {
      if (scanner.hasNext(numberRegex.pattern)) scanner.next(numberRegex.pattern)
      else println(scanner.next)
    }
    scanner.close()
  }

  //9. Напишите программу на Scala, которая подсчитывает количество
  //файлов с расширением .class в указанном каталоге и во всех его
  //подкаталогах.
  {
    import java.nio.file.{Files, Paths}
    val regex = """^.*\.class$""".r
    val entries = Files.walk(Paths.get("."))
    val count = entries.filter(p => regex.matches(p.toString)).count
    println(s"count of .class files = $count")
    entries.close()
  }

  //10. Дополните пример сериализуемого класса Person из раздела 9.8
  //«Сериализация» возможностью сохранения коллекции друзей.
  //Создайте несколько объектов Person, сделайте некоторые из
  //них друзьями других и затем сохраните массив Array[Person]
  //в файл. Прочитайте массив из файла и проверьте, не
  //потерялись ли связи между друзьями.
  {
    import java.io.ObjectOutputStream
    import java.io.ObjectInputStream
    import java.io.FileOutputStream
    import java.io.FileInputStream
    @SerialVersionUID(1L) class Person extends Serializable {
      val friends = new ArrayBuffer[Person]
    }
    val fred = new Person
    val bob  = new Person
    val mike = new Person
    val john = new Person
    fred.friends += bob
    fred.friends += mike
    john.friends += fred
    john.friends += bob
    john.friends += mike
    val a = Array(fred, bob, mike, john)
    val out = new ObjectOutputStream(new FileOutputStream("friends.obj"))
    out.writeObject(a)
    out.close()
    val in = new ObjectInputStream(new FileInputStream("friends.obj"))
    val savedA = in.readObject().asInstanceOf[Array[Person]]
    in.close()
  }

}

