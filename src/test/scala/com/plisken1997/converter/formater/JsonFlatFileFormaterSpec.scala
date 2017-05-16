package com.plisken1997.converter.formater

import org.scalatest.FlatSpec

class JsonFlatFileFormaterSpec extends FlatSpec {

  val incompleteJson = "{\"created_at\":\"Wed May 28 17:40:11 +0000 2014\",\"id\":471707521"
  val wellFormattedJson = "{\"created_at\":\"Wed May 28 17:40:11 +0000 2014\",\"id\":471707521414344704,\"id_str\":\"471707521414344704\"}"
  val withWellFormattedJson = s"$wellFormattedJson,$incompleteJson"

  "extractJson with an incomplete JSON" should "returns the incomplete sequence" in {
    assert(JsonExtractor.extractJson(incompleteJson).isLeft)
    assert(JsonExtractor.extractJson(incompleteJson).left.get == incompleteJson)
  }

  "extractJson with a well formatted JSON" should "returns the JSON sequence" in {
    assert(JsonExtractor.extractJson(withWellFormattedJson).isRight)
    assert(JsonExtractor.extractJson(withWellFormattedJson).right.get._1 == wellFormattedJson)
    assert(JsonExtractor.extractJson(withWellFormattedJson).right.get._2 == s",$incompleteJson")
  }

  "extractJson with an empty JSON" should "not be implemented yet" in {
    assertThrows[UnsupportedOperationException] {
      JsonExtractor.extractJson("")
    }
  }

}
