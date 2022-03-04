package matchtypesregex

import matchtypesregex.MatchTypesRegex.*
import org.scalatest.flatspec.AnyFlatSpec

@annotation.experimental
class EvalTest extends AnyFlatSpec {
  summon[
    CanEmpty[Epsilon] =:= true
  ]

  summon[
    Derivative[Lit["a"], "a"] =:= Epsilon
  ]

  summon[
    Match[Lit["a"], "a"] =:= true
  ]

  summon[
    CanEmpty[Con[Epsilon, Lit["a"]]] =:= false
  ]

  summon[
    Match[
      Con[
        Lit["a"],
        Lit["b"]
      ],
      "ab"
    ] =:= true
  ]

  summon[
    Match[
      Star[
        Alt[
          Lit["a"],
          Lit["b"]
        ]
      ],
      "abaaabaabbaa"
    ] =:= true
  ]

  summon[
    Match[
      Star[
        Alt[
          Lit["a"],
          Lit["b"]
        ]
      ],
      "abaaabaabbaa!"
    ] =:= false
  ]

  summon[
    Match[
      Star[
        Alt[
          Con[Lit["a"], Lit["b"]],
          Lit["b"]
        ]
      ],
      "abaaabaabbaa"
    ] =:= false
  ]

  summon[
    Match[
      Star[
        Alt[
          Con[Lit["a"], Lit["b"]],
          Lit["b"]
        ]
      ],
      "abababbabbbbbb"
    ] =:= true
  ]

  summon[
    Match[
      Star[
        Alt[
          Con[Lit["a"], Lit["b"]],
          Lit["b"]
        ]
      ],
      ""
    ] =:= true
  ]

  summon[
    Match[
      Star[
        Alt[
          Alt[Lit["a"], Lit["b"]],
          Alt[Lit["c"], Lit["d"]]
        ]
      ],
      "abcddaacaaaaaabbcbc"
    ] =:= true
  ]

  summon[
    Match[Star[Dot], "2VstFASKYn8McSYf4x7L"] =:= true
  ]

  summon[
    Match[
      Con[
        Star[Dot],
        Con[
          Con[Lit["a"], Con[Lit["b"], Lit["c"]]],
          Star[Dot]
        ]
      ],
      "auaoeuaoeukhaoukrsao"
    ] =:= false
  ]

  summon[
    Match[
      Con[
        Star[Dot],
        Con[
          Con[Lit["a"], Con[Lit["b"], Lit["c"]]],
          Star[Dot]
        ]
      ],
      "auaoeuaoeu__abc__khaoukrsao"
    ] =:= true
  ]

  summon[
    Match[
      AST[".*abc.*"],
      "auaoeuaoeu__abc__khaoukrsao"
    ] =:= true
  ]

  summon[
    Match[
      AST[
        "(ab(c|d)|e*)*"
      ],
      "abceeeeeeee"
    ] =:= true
  ]

  summon[
    Match[
      AST[
        "(ab(c|.)|e*)*"
      ],
      "ab8eeeeabee"
    ] =:= true
  ]

  summon[
    Match[
      AST[
        "\\.*"
      ],
      "....."
    ] =:= true
  ]

  "Match[(ab(c|d)|e*)*]" should "doesn't compile if given string is `ddddddddd`" in {
    assertDoesNotCompile(
      """summon[
        |  Match[
        |    AST[
        |      "(ab(c|d)|e*)*"
        |    ],
        |    "abceeeeeeee"
        |  ] =:= false
        |]
        |""".stripMargin
    )
  }
}
