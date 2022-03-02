import matchtypesregex.*
import matchtypesregex.MatchTypesRegex.*

object Main {
  def a3: CanEmpty[Epsilon] =
    true

  def a4: Derivative[Lit["a"], "a"] = Epsilon

  def a5: Match[Lit["a"], "a"] = true

  def a7: CanEmpty[Con[Epsilon, Lit["a"]]] = false

  def a8: Match[
    Con[
      Lit["a"],
      Lit["b"]
    ], "ab"] = true

  def a9: Match[
    Star[
      Alt[
        Lit["a"],
        Lit["b"]
      ]
    ], "abaaabaabbaa"] = true

  def a10: Match[
    Star[
      Alt[
        Lit["a"],
        Lit["b"]
      ]
    ], "abaaabaabbaa!"] = false

  def a11: Match[
    Star[
      Alt[
        Con[Lit["a"], Lit["b"]],
        Lit["b"]
      ]
    ], "abaaabaabbaa"] = false

  def a12: Match[
    Star[
      Alt[
        Con[Lit["a"], Lit["b"]],
        Lit["b"]
      ]
    ], "abababbabbbbbb"] = true

  def a13: Match[
    Star[
      Alt[
        Con[Lit["a"], Lit["b"]],
        Lit["b"]
      ]
    ], ""] = true

  def a14: Match[
    Star[
      Alt[
        Alt[Lit["a"], Lit["b"]],
        Alt[Lit["c"], Lit["d"]]
      ]
    ], "abcddaacaaaaaabbcbc"] = true

  def a15: Match[Star[Dot], "2VstFASKYn8McSYf4x7L"] = true

  def a16: Match[
    Con[
      Star[Dot],
      Con[
        Con[Lit["a"], Con[Lit["b"], Lit["c"]]],
        Star[Dot]
      ]
    ], "auaoeuaoeukhaoukrsao"] = false

  def a17: Match[
    Con[
      Star[Dot],
      Con[
        Con[Lit["a"], Con[Lit["b"], Lit["c"]]],
        Star[Dot]
      ]
    ], "auaoeuaoeu__abc__khaoukrsao"] = true
}
