package matchtypesregex

import scala.compiletime.ops.{any, boolean}

@annotation.experimental
trait Eval { self: HListUtil =>
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