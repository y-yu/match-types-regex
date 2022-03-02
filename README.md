Match Types Regex
============================

[![CI](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml/badge.svg)](https://github.com/y-yu/match-types-regex/actions/workflows/ci.yml)

Simple compile-time Regular Expression matcher of [Scala 3 Match Types](https://dotty.epfl.ch/docs/reference/new-types/match-types.html) 

## Example

```scala
def a9: Match[
  Star[
    Alt[
      Alphabet["a"],
      Alphabet["b"]
    ]
  ], "abaaabaabbaa"
] = true

def a10: Match[
  Star[
    Alt[
      Alphabet["a"],
      Alphabet["b"]
    ]
  ], "abaaabaabbaa!"
] = false
```

See also [MatchTypesRegex.scala](https://github.com/y-yu/match-types-regex/blob/master/src/main/scala/matchtypesregex/MatchTypesRegex.scala).