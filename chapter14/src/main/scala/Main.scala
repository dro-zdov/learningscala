object Main extends App {
  //2. Используя сопоставление с образцом, напишите функцию
  //swap, которая принимает пару целых чисел и возвращает ту
  //же пару, поменяв компоненты местами.
  {
    def swap(pair: (Int, Int)) = {
      val (a, b) = pair
      (b, a)
    }
  }

  //3. Используя сопоставление с образцом, напишите функцию
  //swap, которая меняет местами первые два элемента массива,
  //если он имеет длину не меньше двух.
  {
    def swap(arr: Array[Int]) = {
      arr match {
        case Array(a1, a2, rest @ _*) => a2 +: a1 +: Array(rest: _*)
        case _ => arr
      }
    }
    println(swap(Array(1, 2, 3)).mkString(", "))
    println(swap(Array(1, 2)).mkString(", "))
  }

  //4. Добавьте case-класс Multiple, наследующий класс Item.
  //Например, Multiple(10, Article("Blackwell Toaster", 29.95))
  //описывает десять тостеров. Разумеется, должна предусматриваться
  //возможность обрабатывать любые элементы, такие как пакет или
  //множитель, во втором аргументе. Расширьте функцию price,
  //чтобы она могла обрабатывать этот новый класс.
  {
    abstract class Item
    case class Article(description: String, price: Double) extends Item
    case class Bundle(description: String, discount: Double, items: Item*) extends Item
    case class Multiple(count: Int, item: Item) extends Item
    def price(it: Item): Double = it match {
      case Article(_, p) => p
      case Bundle(_, discount, items @ _*) => items.map(price).sum - discount
      case Multiple(count, item) => count * price(item)
    }
    val bundle = Bundle("Some bundle", 10,
      Multiple(10, Article("Scala", 100)),
      Article("Some other article", 500)
    )
    println("price = " + price(bundle))
  }

  //9. Напишите функцию, вычисляющую сумму всех непустых значений
  //в List[Option[Int]]. Не используйте выражения match.
  {
    import scala.collection.immutable.List
    val list = List(1, 2, 0, 3, 0, 4, 5, 0).map(v => if (v == 0) None else Some(v))
    def calcSum(list: List[Option[Int]]) = list.flatten.sum
  }

  //10. Напишите функцию, получающую две функции типа Double =>
  //Option[Double] и конструирующую на их основе третью функцию
  //того же типа. Новая функция должна возвращать None,
  //если любая из двух исходных функций вернет это значение.
  {
    def f(x: Double) = if (x != 1) Some(1 / (x - 1)) else None
    def g(x: Double) = if (x >= 0) Some(math.sqrt(x)) else None
    def compose(f1: (Double) => Option[Double], f2: (Double) => Option[Double]) = (x: Double) => {
      f1(x) match {
        case Some(f1res) => f2(f1res)
        case None => None
      }
    }
    val h = compose(f ,g)
    println("h(2) = "+ h(2))
    println("h(1) = "+ h(1))
    println("h(0) = "+ h(0))
  }
}

