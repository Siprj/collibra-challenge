# How to build

```
sbt compile
sbt test
```

# Result form repl

```scala
scala> import ast._; import fastparse._; val res = SelectParser.parseSelect("SELECT a,b ,c FROM t, d")
res: fastparse.Parsed[ast.Select] = Parsed.Success(Select(List(a, b, c),List(t, d)), 23)
```

# Nice to have

```scala
scala> val columnCount = res.get.value.columns.size
columnCount: Int = 3
```

