package matchtypesregex

sealed trait Token

sealed trait Asta extends Token
sealed trait Plus extends Token
sealed trait VBar extends Token
sealed trait Start extends Token
sealed trait End extends Token
sealed trait Absorb extends Token

sealed trait Regex

// Stringだけど1文字しか入れたらダメ
// 1 length string only!
sealed trait Lit[A <: String] extends Regex with Token
sealed trait Dot extends Regex with Token
case object Dot extends Dot
sealed trait Star[A <: Regex] extends Regex
sealed trait Alt[A <: Regex, B <: Regex] extends Regex
sealed trait Con[A <: Regex, B <: Regex] extends Regex
sealed trait Epsilon extends Regex
case object Epsilon extends Epsilon
sealed trait Void extends Regex