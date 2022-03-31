import scala.xml.XML
import scala.xml.Elem
import scala.xml.Text
import scala.collection.immutable.Map

object Main extends App {
  //1. Что означает <fred/>(0)? <fred/>(0)(0)? Почему?
  {
    val fred0 = <fred/> (0)
    val fred00 = <fred/> (0)(0) // Elem является NodeSeq, состоящей из этого же элемента
    println(fred0 == fred00) //true
  }

  //2. Каков будет результат следующего выражения. Как его исправить?
  {
    <ul>
      <li>Opening bracket: [</li>
      <li>Closing bracket: ]</li>
      <li>Opening brace: {{</li>
      <li>Closing brace: }}</li>
    </ul>
  }

  //3. Сравните:
  {
    <li>Fred</li> match {case <li>{Text(t)}</li> => println(t)}
    <li>{"Fred"}</li> match {case <li>{atom}</li> => println(atom)}
    <li>{Text("Fred")}</li> match {case <li>{Text(t)}</li> => println(t)}
  }

  //4. Прочитайте файл XHTML и выведите все элементы img, не
  //имеющие атрибута alt.
  {
    val root = XML.load(getClass.getResource("document.xhtml").openStream())
    val noAlt = (root \\ "img").filter(_.attribute("alt").isEmpty)
    println(noAlt)
  }

  //5. Выведите имена всех изображений в файле XHTML. То есть
  //выведите значения атрибутов src всех элементов img.
  {
    val root = XML.load(getClass.getResource("document.xhtml").openStream())
    val sources = (root \\ "img" \\ "@src").filter(_.attribute("alt").isEmpty)
    println(sources.mkString(" "))
  }

  //6. Прочитайте файл XHTML и выведите таблицу всех гиперссылок
  //в файле вместе с их адресами URL. То есть выведите содержимое
  //дочернего текстового узла и значение атрибута href
  //каждого элемента a.
  {
    val root = XML.load(getClass.getResource("document.xhtml").openStream())
    val hrefs = (root \\ "a" ).map(el => (el.text -> el.attribute("href").map(_.text)))
    println(hrefs.mkString(" "))
  }

  //7. Напишите функцию, принимающую параметр типа Map[String,
  //String] и возвращающую элемент dl с элементом dt – для
  //каждого ключа и dd – для каждого значения.
  {
    def convertMap(map: Map[String, String]): Elem = {
      <dl>{map.flatMap((k, v) => <dt>{Text(k)}</dt><dd>{Text(v)}</dd>)}</dl>
    }
    val xml = convertMap(Map("A" -> "1", "B" -> "2"))
    println(xml)
  }

  //8. Напишите функцию, принимающую элемент dl и превращающую
  //его в Map[String, String]. Эта функция является полной
  //противоположностью функции из предыдущего упражнения.
  {
    def convertXml(root: Elem): Map[String, String] = {
      val dts = root \ "dt"
      val dds = root \ "dd"
      (dts zip dds).map((dt, dd) => (dt.text, dd.text)).toMap
    }
    val xml = <dl><dt>A</dt><dd>1</dd><dt>B</dt><dd>2</dd></dl>
    println(convertXml(xml))
  }
}

