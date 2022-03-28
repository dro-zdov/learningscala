object Main extends App {
  //3. Напишите пакет random с функциями nextInt(): Int, nextDouble():
  //Double и setSeed(seed: Int): Unit. Для генерации случайных чисел
  //используйте линейный конгруэнтный генератор
  {
    import random._
    setSeed(1024)
    println(s"next int = ${nextInt()}")
    println(s"next double = ${nextDouble()}")
  }

  //6. Напишите программу, копирующую все элементы из Java-хеша
  //в Scala-хеш. Используйте операцию импортирования для
  //переименования обоих классов.
  //7. В предыдущем упражнении перенесите все инструкции import
  //в самую внутреннюю область видимости, насколько это возможно.
  {
    import java.util.{HashMap => JavaHashMap}
    import collection.mutable.{HashMap => ScalaHashMap}
    val javaHash = new JavaHashMap[String, Int]
    javaHash.put("Apple",  10)
    javaHash.put("Orange", 20)
    javaHash.put("Banana", 30)
    val scalaHash = new ScalaHashMap[String, Int]
    javaHash.forEach((k: String, v:Int) =>{
      scalaHash += (k -> v)
    })
    println(s"scala hash = ${scalaHash.mkString(", ")}")
  }

  //9. Напишите программу, импортирующую класс java.lang.System,
  //читающую имя пользователя из системного свойства user.name,
  //пароль из объекта Console и выводящую сообщение в стандартный
  //поток ошибок, если пароль недостаточно «секретный». В противном
  //случае программа должна вывести приветствие в стандартный
  // поток вывода. Не импортируйте ничего другого и не используйте
  //полных квалифицированных имен (с точками).
  {
    import java.lang.System
    val userName = System.getenv.get("USERNAME")
    print("Enter password> ")
    val password = Console.in.readLine()
    if (password.length < 6) {
      Console.err.println("Too weak password")
    }
    else {
      Console.out.println(s"Hello $userName, your password is strong")
    }
  }
}

