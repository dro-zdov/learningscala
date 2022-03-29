import scala.collection.mutable.ArrayBuffer

object Main extends App {
  //1. Класс java.awt.Rectangle имеет очень удобные методы translate
  //и grow, которые, к сожалению, отсутствуют в таких классах,
  //как java.awt.geom.Ellipse2D. В Scala эту проблему легко
  // исправить. Определите трейт RectangleLike с конкретными методами
  //translate и grow. Добавьте любые абстрактные методы, которые
  //потребуются для реализации, чтобы трейт можно было подмешивать.
  {
    import java.awt.Rectangle
    import java.awt.geom.Ellipse2D
    trait RectangleLike extends Ellipse2D {
      def translate(dx: Double, dy: Double): Unit = {
        setFrame(getX + dx, getY + dy, getWidth, getHeight)
      }
      def grow(w: Double, h: Double): Unit = {
        setFrame(getX, getY, w, h)
      }
    }
    val egg = new Ellipse2D.Double(5,10,20,30) with RectangleLike
    egg.translate(1, 2)
    egg.grow(25, 40)
  }

  //2. Определите класс OrderedPoint, подмешав scala.math.Ordered
  //[Point] в java.awt.Point. Используйте лексикографическое упорядочение,
  //то есть (x, y) < (x′, y′), если x < x′ или x = x′ и y < y′.
  import java.awt.Point
  import scala.math.Ordered
  class OrderedPoint extends Point with Ordered[Point] {
    override def compare(that: Point): Int = {
      val dx = this.x - that.x
      val dy = this.y - that.y
      if (dx == 0) dy else dx
    }
  }

  //4. Реализуйте класс CryptoLogger, выполняющий шифрование
  //сообщений с помощью алгоритма Caesar. По умолчанию должен
  //использоваться ключ 3, но должна быть предусмотрена
  //возможность изменить его. Напишите примеры использования
  //с ключом по умолчанию и с ключом –3.
  {
    trait Logger {
      def log(msg: String): Unit
    }
    trait ConsoleLogger extends Logger {
      def log(msg: String): Unit = { println(msg) }
    }
    trait CryptoLogger extends Logger {
      val key: Int = 3
      abstract override def log(msg: String): Unit = {
        val cryptoMsg = for (char <- msg) yield (char - key).toChar
        super.log(cryptoMsg)
      }
    }
    object Greeter1 extends ConsoleLogger with CryptoLogger {
      def sayHello(): Unit = {
        log("Hello")
      }
    }
    object Greeter2 extends ConsoleLogger with CryptoLogger {
      override val key: Int = -3
      def sayHello(): Unit = {
        log("Hello")
      }
    }
    Greeter1.sayHello()
    Greeter2.sayHello()
  }

  //5. Спецификация JavaBeans включает понятие обработчика события
  //изменения свойства (property change listener) – стандартный
  // способ организации взаимодействий между компонентами
  //посредством изменения их свойств. Класс PropertyChangeSupport
  //является суперклассом для любых компонентов, желающих
  // обеспечить поддержку обработчиков событий изменений
  //свойств. К сожалению, класс, который уже наследует другой
  //суперкласс, такой как JComponent, должен повторно реализовать
  //методы. Определите свою реализацию PropertyChangeSupport
  //в виде трейта и подмешайте его в класс java.awt.Point.
  {
    import java.beans.PropertyChangeSupport
    import java.beans.PropertyChangeListener
    import java.beans.PropertyChangeEvent
    import java.awt.Point
    trait MyPropertyChangeSupport {
      private val pcs = new PropertyChangeSupport(this)
      def addPropertyChangeListener(listener: PropertyChangeListener): Unit = {
        pcs.addPropertyChangeListener(listener)
      }
      def removePropertyChangeListener(listener: PropertyChangeListener): Unit = {
        pcs.removePropertyChangeListener(listener)
      }
      def firePropertyChange(propertyName: String, oldValue: Any, newValue: Any): Unit = {
        pcs.firePropertyChange(propertyName, oldValue, newValue)
      }
    }
    class MyPoint extends Point with MyPropertyChangeSupport {
      override def setLocation(x: Int, y: Int): Unit = {
        super.setLocation(x, y)
        firePropertyChange("x", getX, x)
        firePropertyChange("y", getY, y)
      }
    }
    val mp = new MyPoint
    mp.addPropertyChangeListener((e: PropertyChangeEvent) => {
      println(s"new value of ${e.getPropertyName} = ${e.getNewValue}")
    })
    mp.setLocation(1, 1)
  }

  //6. В библиотеке Java AWT имеется класс Container, наследующий
  //класс Component, являющийся коллекцией нескольких компонентов.
  //Например, Button – это Component, но Panel – это Container.
  //Это пример составных компонентов в действии. Библиотека
  //Swing имеет классы JComponent и JContainer, но, если
  //взглянуть внимательнее, можно заметить одну странность.
  //JComponent наследует Container, даже притом, что нет смысла
  //добавлять другие компоненты, например в JButton. В идеале
  //проектировщики предпочли бы организовать иерархию классов,
  //изображенную на рис. 10.4.
  //Но в Java это невозможно. Объясните, почему. Как можно было
  //бы организовать классы с применением трейтов в Scala?
  {
    class Component(val w: Int, val h: Int)
    class JComponent(val background: Int, w: Int, h: Int) extends Component(w, h)
    class JButton(val label: String, background: Int, w: Int, h: Int) extends JComponent(background, w, h)
    trait Container extends Component {
      val children = new ArrayBuffer[Component]
    }
    class JContainer(background: Int, w: Int, h: Int) extends JComponent(background, w, h) with Container
    class JPanel(background: Int, w: Int, h: Int) extends JContainer(background, w, h)

    val button1 = new JButton("Button1", 0xFF25DA, 300, 100)
    val button2 = new JButton("Button2", 0xACF0B9, 300, 100)
    val panel = new JPanel(0, 300, 200)
    panel.children += button1
    panel.children += button2
  }

  //9. В библиотеке java.io имеется возможность добавить буферизацию
  //в поток ввода с помощью декоратора BufferedInputStream.
  //Реализуйте буферизацию как трейт. Для простоты
  //переопределите метод read.
  {
    import java.io.BufferedInputStream
    import java.io.FileInputStream
    import java.io.InputStream
    import java.io.File
    trait Buffering {
      this: InputStream =>
      private val bis = new BufferedInputStream(this)
      override def read(): Int = bis.read()
      override def read(b: Array[Byte], off: Int, len: Int): Int = bis.read(b, off, len)
      override def skip(n: Long): Long = bis.skip(n)
      override def available(): Int = bis.available()
      override def mark(readlimit: Int): Unit = bis.mark(readlimit)
      override def reset(): Unit = bis.reset()
      override def markSupported(): Boolean = bis.markSupported()
      override def close(): Unit = bis.close()
    }
    val in = new FileInputStream(new File(".gitignore")) with Buffering
    val a = new Array[Byte](1024)
    //in read(a, 0, a.length) // TODO: StackOverflowError
    in.close()
  }

  //11. Реализуйте класс IterableInputStream, наследующий
  //java.io.InputStream и подмешивающий трейт Iterable[Byte].
  {
    import java.io.InputStream
    abstract class IterableInputStream extends InputStream with Iterable[Byte] {
      def iterator: Iterator[Byte] = new Iterator[Byte] {
        def hasNext: Boolean = available > 0
        def next(): Byte = read().toByte
      }
    }
  }
}

