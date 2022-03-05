Match Types Regex
============================

[![CI](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml/badge.svg)](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml)

Simple compile-time Regular Expression matcher of [Scala 3 Match Types](https://dotty.epfl.ch/docs/reference/new-types/match-types.html).

## Example

```scala
summon[
  Match[
    AST[".*abc.*"],
    "auaoeuaoeu__abc__khaoukrsao"
  ] =:= true
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
```

See also [EvalTest.scala](https://github.com/y-yu/match-types-regex/blob/master/src/test/scala/matchtypesregex/EvalTest.scala) for more examples.

## Documentation

- [Scala 3のMatch Typesでコンパイル時正規表現エンジン](https://zenn.dev/yyu/articles/9a4fd8b7a3383b)

## References

- [MatchTypeParseEval.scala](https://gist.github.com/xuwei-k/521638aa17ebc839c8d8519bcdfdc7ae) by [@xuwei-k](https://github.com/xuwei-k)
