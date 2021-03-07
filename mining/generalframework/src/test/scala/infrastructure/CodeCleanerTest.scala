package infrastructure

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CodeCleanerTest extends AnyFlatSpec with Matchers {

    val textCode = "if( test == 50 ){\n\t int testritorno = pip;\n}"

    "The cleanCode procedure" should "equal the string" in {
        CodeCleaner.cleanCodeString(textCode) shouldEqual "if == int ="
    }
}

