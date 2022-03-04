package matchtypesregex

import matchtypesregex.MatchTypesRegex.*

@annotation.experimental
object ParserTest {
  summon[
    HListToTokens[
      StringToHList["\\.\\(\\)\\|\\\\"]
    ] =:= (
      Lit["."] :+:
        Lit["("] :+:
        Lit[")"] :+:
        Lit["|"] :+:
        Lit["\\"] :+:
        HNil
    )
  ]

  summon[
    HListToTokens[
      StringToHList["a\\.b"]
    ] =:= (
      Lit["a"] :+:
        Lit["."] :+:
        Lit["b"] :+:
        HNil
      )
  ]

  summon[
    ShuntingYard[
      Start :+:
        Lit["a"] :+:
        Lit["b"] :+:
        VBar :+:
        Lit["c"] :+:
        Asta :+:
        End :+:
        Lit["d"] :+:
        HNil
    ] =:= (
      Lit["a"] :+:
        Lit["b"] :+:
        Plus :+:
        Lit["c"] :+:
        Asta :+:
        VBar :+:
        Lit["d"] :+:
        Plus :+:
        HNil
      )
  ]

  summon[
    ShuntingYard[
      HListToTokens[
        StringToHList["(ab|c*)d"]
      ]
    ] =:= (
      Lit["a"] :+:
        Lit["b"] :+:
        Plus :+:
        Lit["c"] :+:
        Asta :+:
        VBar :+:
        Lit["d"] :+:
        Plus :+:
        HNil
      )
  ]

  summon[
    AST[
      "(ab|c*)d"
    ] =:= Con[
      Alt[
        Con[Lit["a"], Lit["b"]],
        Star[Lit["c"]]
      ],
      Lit["d"]
    ]
  ]

  summon[
    ShuntingYard[
      HListToTokens[
        StringToHList[".*abc.*"]
      ]
    ] =:= (
      Dot :+:
        Asta :+:
        Lit["a"] :+:
        Lit["b"] :+:
        Lit["c"] :+:
        Dot :+:
        Asta :+:
        Plus :+:
        Plus :+:
        Plus :+:
        Plus :+:
        HNil
      )
  ]

  summon[
    AST[
      ".*abc.*"
    ] =:= Con[
      Star[Dot],
      Con[
        Lit["a"],
        Con[
          Lit["b"],
          Con[
            Lit["c"],
            Star[Dot]
          ]
        ]
      ]
    ]
  ]


  summon[
    ShuntingYard[
      HListToTokens[
        StringToHList["(ab)*"]
      ]
    ] =:= (
      Lit["a"] :+:
        Lit["b"] :+:
        Plus :+:
        Asta :+:
        HNil
      )
  ]

  summon[
    AST[
      "(ab)*"
    ] =:= Star[
      Con[
        Lit["a"],
        Lit["b"]
      ]
    ]
  ]

  summon[
    ShuntingYard[
      HListToTokens[
        StringToHList["(ab(c|d))*"]
      ]
    ] =:= (
      Lit["a"] :+:
        Lit["b"] :+:
        Plus :+:
        Lit["c"] :+:
        Lit["d"] :+:
        VBar :+:
        Plus :+:
        Asta :+:
        HNil
      )
  ]

  summon[
    ShuntingYard[
      HListToTokens[
        StringToHList["(ab(c|d)|e*)*"]
      ]
    ] =:= (
      Lit["a"] :+:
        Lit["b"] :+:
        Plus :+:
        Lit["c"] :+:
        Lit["d"] :+:
        VBar :+:
        Plus :+:
        Lit["e"] :+:
        Asta :+:
        VBar :+:
        Asta :+:
        HNil
      )
  ]

  summon[
    AST[
      "(ab(c|d)|e*)*"
    ] =:= Star[
      Alt[
        Con[
          Con[
            Lit["a"], Lit["b"]
          ],
          Alt[
            Lit["c"], Lit["d"]
          ]
        ],
        Star[Lit["e"]]
      ]
    ]
  ]
}