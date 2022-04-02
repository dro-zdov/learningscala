object Main extends App {
  //1. Определите неизменяемый класс Pair[T, S] с методом swap,
  //возвращающим новую пару, где компоненты поменяны местами.
  {
    class Pair[+T, +S](val v1: T, val v2: S) {
      def swap: Pair[S, T] = new Pair(v2, v1)
    }
  }

  //2. Определите изменяемый класс Pair[T] с методом swap, который
  //меняет компоненты пары местами.
  {
    class Pair[T](var v1: T, var v2: T) {
      def swap(): Unit = {
        val tmp = v1
        v1 = v2
        v2 = tmp
      }
    }
  }

  //3. Для класса Pair[T, S] напишите обобщенный метод swap,
  //который принимает пару в виде аргумента и возвращает новую
  //пару с компонентами, поменянными местами.
  {
    class Pair[+T, +S](val v1: T, val v2: S)
    def swap[T, S](p: Pair[T, S]) = new Pair[S, T](p.v2, p.v1)
  }

  //4. Почему не требуется объявлять верхнюю границу в методе
  //replaceFirst в разделе 18.3 «Границы изменения типов»,
  //когда предполагается заменить первый компонент в экземпляре
  //Pair[Person] экземпляром Student?
  {
    class Person
    class Student extends Person
    class Pair[T](val first: T, val second: T) {
      def replaceFirst(newFirst: T) = new Pair[T](newFirst, second)
    }
    val p1 = new Pair[Person](new Person, new Person)
    val p2 = p1.replaceFirst(new Student) // Student тоже Person
  }

  //6. Напишите обобщенный метод middle, возвращающий средний
  //элемент из любого экземпляра Iterable[T]. Например, вызов
  //middle("World") должен вернуть 'r'.
  {
    import scala.reflect.ClassTag
    def middle[T : ClassTag](iterable: Iterable[T]): Option[T] = {
      val a = iterable.toArray
      if (a.isEmpty) None
      else Some(a(a.length / 2))
    }
  }
}

