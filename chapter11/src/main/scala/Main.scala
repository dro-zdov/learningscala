import scala.collection.mutable.ArrayBuffer

object Main extends App {
  //1. Как будут вычисляться следующие выражения с учетом
  //правил приоритетов: 3 + 4 -> 5 и 3 -> 4 + 5?
  {
    println(s"3 + 4 -> 5 = ${3 + 4 -> 5}") // (7, 5)
    //println(s"3 -> 4 + 5 = ${3 -> 4 + 5}") // (3, 4) + 5 - error
  }

  //3. Реализуйте класс Fraction с операциями + - * /. Реализуйте
  //нормализацию рациональных чисел, например чтобы число
  //15/–6 превращалось в –5/3, а также деление на наибольший
  //общий делитель.
  {
    class Fraction(n: Int, d: Int) {
      private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
      private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
      override def toString: String = num + "/" + den
      def sign(a: Int): Int = if (a > 0) 1 else if (a < 0) -1 else 0
      def gcd(a: Int, b: Int): Int = {
        //println(a + " " + b)
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
    val f1 = new Fraction(1, 2)
    val f2 = new Fraction(1, 4)
    println(f1 + f2)
    println(f1 - f2)
    println(f1 * f2)
    println(f1 / f2)
  }

  //4. Реализуйте класс Money с полями для выражения суммы в долларах
  //и центах. Реализуйте операторы +, -, а также операторы
  //сравнения == и <. Например, выражение
  //Money(1, 75) + Money(0,50) == Money(2, 25) должно возвращать true.
  {
    class Money(val dollar: Int, val cent: Int) {
      if (cent > 99) throw new IllegalArgumentException
      override def toString: String = s"$$$dollar.$cent"
      private def toCents: Int = dollar * 100 + cent
      private def this(cents: Int) = this(cents / 100, cents % 100)
      def +(that: Money): Money = new Money(this.toCents + that.toCents)
      def -(that: Money): Money = new Money(this.toCents - that.toCents)
      def <(that: Money): Boolean = this.toCents < that.toCents
      def >(that: Money): Boolean = this.toCents > that.toCents
      override def hashCode(): Int = this.toCents.hashCode
      override def equals(obj: Any): Boolean = {
        obj match {
          case that: Money => this.toCents == that.toCents
          case _ => false
        }
      }
    }
    val m1 = Money(2, 50)
    val m2 = Money(1, 25)
    val m3 = Money(1, 25)
    println(m1 + m2)
    println(m1 - m2)
    println(m1 > m2)
    println(m2 == m3)
  }

  //5. Реализуйте операторы конструирования HTML-таблицы.
  {
    class Table {
      val rows = ArrayBuffer(new TableRow)
      def |(name: String): Table = {
        val column = new TableColumn(name)
        rows.last.columns += column
        this
      }
      def ||(name: String): Table = {
        rows += new TableRow
        this | name
      }
      def render: String = s"<table>${rows.map(_.render).mkString}</table>"
    }
    class TableRow {
      val columns = new ArrayBuffer[TableColumn]
      def render: String = s"<tr>${columns.map(_.render).mkString}</tr>"
    }
    class TableColumn(val value: String) {
      def render: String = s"<td>$value</td>"
    }

    val table = Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET"
    println(table.render)
  }

  //7. Реализуйте класс BitSequence для хранения 64-битных последовательностей
  //в виде значений типа Long. Реализуйте операторы apply и update для
  //получения и установки отдельных битов.
  {
    class BitSequence(private var value: Long = 0) {
      override def toString: String = value.toBinaryString
      def apply(position: Int): Boolean = {
        if (position > 64) throw new IllegalArgumentException
        (value & 1 << position) != 0
      }
      def update(position: Int, state: Boolean): Unit = {
        if (position > 64) throw new IllegalArgumentException
        if (state) {
          value |= 1 << position
        }
        else {
          value &= ~(1 << position)
        }
      }
    }
    val bs = new BitSequence
    bs(3) = true
    bs(1) = true
    println(bs)
    println(bs(3))
  }

  //9. Определите операцию unapply для класса RichFile, извлекающую
  //путь к файлу, имя и расширение. Например, файл /home/cay/readme.txt
  //имеет путь /home/cay, имя readme и расширение txt.
  {
    import java.io.File
    object RichFile {
      def unapply(f: File): Option[(String, String, String)] = {
        val path = f.getParent
        val nameAndExt = f.getName.split("\\.")
        Some((path, nameAndExt(0), nameAndExt(1)))
      }
    }
    val file = new File("/home/cay/readme.txt")
    val RichFile(path, name, ext) = file
    println(s"path = $path, name = $name, ext = $ext")
  }

  //11. Усовершенствуйте динамический селектор свойств из раздела
  //11.11 «Динамический вызов» так, чтобы не требовалось
  //использовать подчеркивания.
  {
    import scala.language.dynamics
    import java.util.Properties
    class DynamicProps(private val props: Properties, private val path: String) extends Dynamic {
      def this(props: Properties) = this(props, null)
      private def getPath(name: String) = if (this.path == null) name else this.path + "." + name
      def updateDynamic(name: String)(value: String): Unit = {
        props.setProperty(path + "." + name, value)
      }
      def selectDynamic(name: String) = new DynamicProps(props, getPath(name))
      def value: String = props.getProperty(path)
    }

    val sysProps = new DynamicProps(System.getProperties)
    println(sysProps.java.home.value)
    sysProps.some.property = "value"
    println(sysProps.some.property.value)
  }

  //13. Реализуйте класс XMLBuilder для динамического конструирования
  //элементов XML в виде: builder.ul(id="42", style="liststyle:lower-alpha;"),
  //где имя метода становится именем элемента, а именованные аргументы
  // – атрибутами. Придумайте удобный способ добавления вложенных элементов.
  {
    import scala.language.dynamics
    class XMLBuilder(
      private val elem: String = null,
      private val attrs: Map[String, String] = null,
      private val parent: XMLBuilder = null
    ) extends Dynamic {
      private val children = new ArrayBuffer[XMLBuilder]
      def applyDynamicNamed(name: String)(args: (String, String)*): XMLBuilder = {
        children += new XMLBuilder(name, args.toMap, this)
        children.last
      }
      def render: String = {
        var root = this
        while (root.parent != null) root = root.parent
        root.children.map(c => renderInternal(c)).mkString
      }
      private def renderInternal(builder: XMLBuilder): String = {
        var XMLBuilder(elem, attrs, children) = builder
        val value = if (attrs.contains("value")) attrs("value") else ""
        attrs -= "value"
        val attrsStr = " " + attrs.map((k, v) => s"$k=\"$v\"").mkString(" ")
        val childrenStr = children.map(c => renderInternal(c)).mkString
        s"<$elem$attrsStr>$value$childrenStr</$elem>"
      }
    }
    object XMLBuilder {
      def unapply(b: XMLBuilder): Option[(String, Map[String, String], ArrayBuffer[XMLBuilder])] = {
        Some((b.elem, b.attrs, b.children))
      }
    }

    val builder = new XMLBuilder().ul(id="42", style="liststyle: lower-alpha;")
    builder.li(value = "value1")
    builder.li(value = "value2")
    builder.li(value = "value3")
    println(builder.render)
  }
}

