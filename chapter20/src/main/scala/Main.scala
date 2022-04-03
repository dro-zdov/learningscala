import scala.util.parsing.combinator.RegexParsers

object Main extends App with RegexParsers {
  //1. Добавьте в парсер арифметических выражений операции / и %.
  {
    val number = "[0-9]+".r
    def expr: Parser[Int] = term ~ opt(("+" | "-") ~ expr) ^^ {
      case t ~ None => t
      case t ~ Some("+" ~ e) => t + e
      case t ~ Some("-" ~ e) => t - e
    }
    def term: Parser[Int] = factor ~ rep(("*"|"/"|"%") ~ factor) ^^ {
      case f ~ rep => rep.foldLeft(f)((acc, r) => r match {
        case "*" ~ f => acc * f
        case "/" ~ f => acc / f
        case "%" ~ f => acc % f
      })
    }
    def factor: Parser[Int] = number ^^ { _.toInt } | "(" ~> expr <~ ")"
  }
}

