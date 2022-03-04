package matchtypesregex

import scala.compiletime.ops.string.{+, Length, Substring}

sealed trait HList {
  def :+:[T](t: T): T :+: this.type = new :+:(t, this)
}
final case class :+:[H, +T <: HList](h: H, t: T) extends HList
sealed trait HNil extends HList
case object HNil extends HNil

@annotation.experimental
trait HListUtil {
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
}