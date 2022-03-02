package matchtypesregex

import scala.compiletime.ops.string.{+, Length, Matches, Substring}
import scala.compiletime.ops.{any, boolean, int}

sealed trait HList {
  def :+:[T](t: T): T :+: this.type = new :+:(t, this)
}
final case class :+:[H, +T <: HList](h: H, t: T) extends HList
sealed trait HNil extends HList
case object HNil extends HNil

sealed trait Regex

// Stringだけど1文字しか入れたらダメ
// 1 length string only!
sealed trait Lit[A <: String] extends Regex
sealed trait Dot extends Regex
case object Dot extends Dot
sealed trait Star[A <: Regex] extends Regex
sealed trait Alt[A <: Regex, B <: Regex] extends Regex
sealed trait Con[A <: Regex, B <: Regex] extends Regex
sealed trait Epsilon extends Regex
case object Epsilon extends Epsilon
sealed trait Void extends Regex

@annotation.experimental
object MatchTypesRegex {
  type ReverseHList[A <: HList] =
    ReverseHList0[HNil, A]

  type ReverseHList0[Acc <: HList, Src <: HList] <: HList=
    Src match
      case HNil =>
        Acc
      case x :+: xs =>
        ReverseHList0[x :+: Acc, xs]

  type ReverseStr[A <: String] =
    ReverseLoop["", A]

  type ReverseLoop[Acc <: String, Src <: String] <: String =
    Length[Src] match
      case 0 =>
        Acc
      case _ =>
        ReverseLoop[Substring[Src, 0, 1] + Acc, Substring[Src, 1, Length[Src]]]

  type StringToHList[A <: String] =
    StringToHList0[HNil, ReverseStr[A]]

  type StringToHList0[+Acc <: HList, Src <: String] <: HList =
    Length[Src] match
      case 0 =>
        Acc
      case _ =>
        StringToHList0[Substring[Src, 0, 1] :+: Acc, Substring[Src, 1, Length[Src]]]

  type CanEmpty[In <: Regex] <: Boolean =
    In match
      case Epsilon | Star[_] => true
      case Lit[_] | Dot | Void => false
      case Alt[r1, r2] =>
        boolean.||[CanEmpty[r1], CanEmpty[r2]]
      case Con[r1, r2] =>
        boolean.&&[CanEmpty[r1], CanEmpty[r2]]

  type Derivative[In <: Regex, Char <: String] <: Regex =
    (In, Char) match
      case (Void, _) => Void
      case (Epsilon, _) => Void
      case (Lit[a], _) =>
        any.==[a, Char] match
          case true => Epsilon
          case false => Void
      case (Dot, x) => Epsilon
      case (Alt[r1, r2], _) =>
        Alt[Derivative[r1, Char], Derivative[r2, Char]]
      case (Con[r1, r2], _) =>
        CanEmpty[r1] match
          case true => Alt[Con[Derivative[r1, Char], r2], Derivative[r2, Char]]
          case false => Con[Derivative[r1, Char], r2]
      case (Star[r], _) =>
        Con[Derivative[r, Char], Star[r]]

  type MatchHList[In <: Regex, A <: HList] <: Boolean =
    A match
      case HNil => CanEmpty[In]
      case x :+: xs =>
        MatchHList[Derivative[In, x], xs]

  type Match[In <: Regex, A <: String] =
    MatchHList[In, StringToHList[A]]
}