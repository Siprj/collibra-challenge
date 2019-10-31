package ast

import fastparse._, MultiLineWhitespace._


case class Select(columns: List[String], tables: List[String])


object SelectParser
{
  def selectSymbol[_: P] = P(IgnoreCase("SELECT"))
  def fromSymbol[_: P] = P(IgnoreCase("FROM"))
  def nameParser[_: P] = P(CharsWhileIn("A-z").!)
  def namesParser[_: P] = P(nameParser.rep(1, ","))
  def selectParser[_: P]: P[Select] = P(selectSymbol ~ namesParser ~ fromSymbol ~ namesParser ~ End)
    .map({case (c, t) => Select(c.toList, t.toList)})

  def parseSelect(input: ParserInput): Parsed[Select] = parse(input, selectParser(_))
}

