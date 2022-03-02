Match Types Regex
============================

[![CI](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml/badge.svg)](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml)

Simple compile-time Regular Expression matcher of [Scala 3 Match Types](https://dotty.epfl.ch/docs/reference/new-types/match-types.html).

## Example

```scala
def a9: Match[
  Star[
    Alt[
      Lit["a"],
      Lit["b"]
    ]
  ], "abaaabaabbaa"
] = true

def a10: Match[
  Star[
    Alt[
      Lit["a"],
      Lit["b"]
    ]
  ], "abaaabaabbaa!"
] = false

def a17: Match[
  Con[
    Star[Dot],
    Con[
      Con[Lit["a"], Con[Lit["b"], Lit["c"]]],
      Star[Dot]
    ]
  ], "auaoeuaoeu__abc__khaoukrsao"] = true
```

See also [Main.scala](https://github.com/y-yu/match-types-regex/blob/master/src/main/scala/Main.scala) for more examples.

## References

- [MatchTypeParseEval.scala](https://gist.github.com/xuwei-k/521638aa17ebc839c8d8519bcdfdc7ae) by [@xuwei-k](https://github.com/xuwei-k)
