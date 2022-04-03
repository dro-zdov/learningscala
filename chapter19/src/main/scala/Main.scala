object Main extends App {
  //1. Реализуйте класс Bug, моделирующий жука, перемещающегося
  //по горизонтальной линии. Метод move перемещает жука в текущем
  //направлении, метод turn изменяет направление на противоположное,
  //а метод show выводит текущую позицию. Обеспечьте возможность
  // составления цепочек из вызовов этих методов.
  {
    class Bug {
      var position = 0
      var direction = 1
      def move(dx: Int): this.type = {
        position += dx * direction
        this
      }
      def turn(): this.type = {
        direction *= -1
        this
      }
      def show(): this.type = {
        println(s"current position is $position")
        this
      }
    }
    val bug = new Bug
    bug.move(4).show().move(6).show().turn().move(5).show()
  }

  //4. Реализуйте метод equals в классе Member, вложенном в класс
  //Network, в разделе 19.2 «Проекции типов». Два члена сообщества
  //могут быть признаны равными, если только они принадлежат одному
  //сообществу.
  {
    import scala.collection.mutable.ArrayBuffer
    class Network {
      class Member(val name: String) {
        val contacts = new ArrayBuffer[Member]
        override def equals(obj: Any): Boolean = obj match {
          case other: Network.this.Member =>
            this.name == other.name && this.contacts == other.contacts
          case _ => false
        }
      }
      private val members = new ArrayBuffer[Member]
      def join(name: String): Member = {
        val m = new Member(name)
        members += m
        m
      }
    }

    val chatter = new Network
    val myFace = new Network
    val fred = chatter.join("Fred")
    val fred2 = chatter.join("Fred")
    val barney = myFace.join("Barney")
    println(fred.equals(barney)) // false
    println(fred.equals(fred2)) // true
  }

  //7. Реализуйте метод, принимающий экземпляр любого класса,
  //который имеет метод def close(): Unit
  //вместе с функцией обработки этого объекта. Функция должна
  //вызывать метод close по завершении обработки или в случае
  //какого-либо исключения.
  {
    import scala.language.reflectiveCalls
    type Closeable = { def close(): Unit }
    def process[T <: Closeable](obj: T)(f: T => Unit): Unit = {
      try {
        f(obj)
      }
      finally {
        obj.close()
      }
    }
    class SomeClass {
      def close(): Unit = println("SomeClass closed")
      def sayHello(): Unit = println("Hello from SomeClass")
    }
    process(new SomeClass)(s => s.sayHello())
  }

  //8. Напишите функцию printValues с тремя параметрами: f, from
  //и to, выводящую все значения f, для входных значений в заданном
  //диапазоне от from до to.
  {
    import scala.language.reflectiveCalls
    def printValues(f: { def apply(t: Int): Int }, from: Int, to: Int): Unit = {
      (from until to).map(f.apply).mkString(", ")
    }
    //printValues((x: Int) => x * x, 3, 6)
    printValues(Array(1, 1, 2, 3, 5, 8, 13, 21, 34, 55),3,6)
  }
}

