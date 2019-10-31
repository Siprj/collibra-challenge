package ast

import fastparse._
import org.scalatest._

class AstSpec extends FlatSpec with Matchers {
  "parseName" should "parse \"abcd\"" in {
    val res = parse("abcd", SelectParser.nameParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual "abcd"
  }
  "parseName" should "parse \"ABCD\"" in {
    val res = parse("ABCD", SelectParser.nameParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual "ABCD"
  }
  "parseName" should "parse \"AbCd\"" in {
    val res = parse("AbCd", SelectParser.nameParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual "AbCd"
  }
  "parseName" should "not parse \"1AbCd\"" in {
    val res = parse("1AbCd", SelectParser.nameParser(_))
    assert(!res.isSuccess)
  }
  "parseName" should "not parse \"Ab8Cd\"" in {
    val res = parse("Ab8Cd", SelectParser.nameParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual "Ab"
  }
  
  "namesParser" should "parse \"Ab\"" in {
    val res = parse("Ab", SelectParser.namesParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual List("Ab")
  }
  "namesParser" should "parse \"Ab,Cd\"" in {
    val res = parse("Ab,Cd", SelectParser.namesParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual List("Ab", "Cd")
  }
  "namesParser" should "parse \"Ab,  Cd\"" in {
    val res = parse("Ab,  Cd", SelectParser.namesParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual List("Ab", "Cd")
  }
  "namesParser" should "parse \"Ab,\\tCd\"" in {
    val res = parse("Ab,\tCd", SelectParser.namesParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual List("Ab", "Cd")
  }

  "selectParser" should "parse \"SELECT a, b FROM t, d\"" in {
    val res = parse("SELECT a, b FROM t, d", SelectParser.selectParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual Select(List("a", "b"), List("t", "d"))
  }
  "selectParser" should "parse \"SELECT     a, b \\tFROM    t,   d\"" in {
    val res = parse("SELECT     a, b    \tFROM t,   d", SelectParser.selectParser(_))
    assert(res.isSuccess)
    res.get.value shouldEqual Select(List("a", "b"), List("t", "d"))
  }
 

  // Test challenge strings.
  "\"SELECT a FROM t\"" should "succeed" in {
    val res = SelectParser.parseSelect("SELECT a FROM t")
    assert(res.isSuccess)
    res.get.value shouldEqual Select(List("a"), List("t"))
  }
  // Test challenge strings.
  "\"SELECT a,b FROM t,d\"" should "succeed" in {
    val res = SelectParser.parseSelect("SELECT a,b FROM t,d")
    assert(res.isSuccess)
    res.get.value shouldEqual Select(List("a", "b"), List("t", "d"))
  }
  // Test challenge strings.
  "\"SELECT a,b, c FROM t, d ,e\"" should "succeed" in {
    val res = SelectParser.parseSelect("SELECT a,b, c FROM t, d ,e")
    assert(res.isSuccess)
    res.get.value shouldEqual Select(List("a", "b", "c"), List("t", "d", "e"))
  }
}
