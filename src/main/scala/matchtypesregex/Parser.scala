package matchtypesregex

@annotation.experimental
trait Parser { self: HListUtil =>
  // Fix these for regex parsing.
  // https://zenn.dev/lotz/articles/85577e9d9059cd9e1245
  // https://en.wikipedia.org/wiki/Shunting-yard_algorithm
  type ShuntingYard[In <: HList] =
    ShuntingYard0[HNil, Absorb :+: HNil, In]

  type ShuntingYard0[Out <: HList, Op <: HList, In <: HList] <: HList =
    (Op, In) match
      case (HNil, HNil) =>
        ReverseHList[Out]
      case (sym :+: op, HNil) =>
        ShuntingYard0[sym :+: Out, op, HNil]
      case (Absorb :+: op, Start :+: in) =>
        ShuntingYard0[Out, Absorb :+: Start :+: op, in]
      case (Absorb :+: op, sym :+: in) =>
        ShuntingYard0[sym :+: Out, op, in]
      case (Plus :+: op, Start :+: in) =>
        ShuntingYard0[Plus :+: Out, op, In]
      case (Plus :+: op, End :+: in) =>
        ShuntingYard0[Plus :+: Out, op, In]
      case (Start :+: op, VBar :+: in) =>
        ShuntingYard0[Out, VBar :+: op, in]
      case (sym :+: op, VBar :+: in) =>
        ShuntingYard0[sym :+: Out, op, In]
      case (_, Asta :+: in) =>
        ShuntingYard0[Asta :+: Out, Op, in]
      case (_, Start :+: in) =>
        ShuntingYard0[Out, Absorb :+: Start :+: Plus :+: Op, in]
      case (Start :+: op, End :+: in) =>
        ShuntingYard0[Out, op, in]
      case (VBar :+: op, End :+: in) =>
        ShuntingYard0[VBar :+: Out, op, in]
      case (sym :+: op, End :+: in) =>
        ShuntingYard0[sym :+: Out, op, End :+: in]
      case (VBar :+: op, sym :+: in) =>
        ShuntingYard0[sym :+: Out, Op, in]
      case (_, sym :+: in) =>
        ShuntingYard0[sym :+: Out, Plus :+: Op, in]

  // https://en.wikipedia.org/wiki/Reverse_Polish_notation
  type RPN[Symbols <: HList] =
    RPN0[HNil, Symbols]

  type RPN0[Stack <: HList, Symbols <: HList] =
    (Stack, Symbols) match
      case (x :+: HNil, HNil) =>
        x
      case (x1 :+: xs, Asta :+: ys) =>
        RPN0[Star[x1] :+: xs, ys]
      case (x1 :+: x2 :+: xs, VBar :+: ys) =>
        RPN0[Alt[x2, x1] :+: xs, ys]
      case (x1 :+: x2 :+: xs, Plus :+: ys) =>
        RPN0[Con[x2, x1] :+: xs, ys]
      case (xs, y :+: ys) =>
        RPN0[y :+: xs, ys]

  type StrToEscapedToken[A <: String] <: Token =
    A match
      case "*" => Lit["*"]
      case "." => Lit["."]
      case "|" => Lit["|"]
      case "(" => Lit["("]
      case ")" => Lit[")"]
      case "\\" => Lit["\\"]
      case _ => Lit[A]

  type StrToToken[A <: String] <: Token =
    A match
      case "*" => Asta
      case "." => Dot
      case "|" => VBar
      case "(" => Start
      case ")" => End
      case _ => Lit[A]

  type HListToTokens[A <: HList] <: HList =
    A match
      case x1 :+: x2 :+: xs =>
        x1 match
          case "\\" =>
            StrToEscapedToken[x2] :+: HListToTokens[xs]
          case _ =>
            StrToToken[x1] :+: HListToTokens[x2 :+: xs]
      case x :+: xs =>
        StrToToken[x] :+: HListToTokens[xs]
      case HNil =>
        HNil

  type AST[A <: String] =
    RPN[
      ShuntingYard[
        HListToTokens[
          StringToHList[A]
        ]
      ]
    ]
}
