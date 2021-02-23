package infrastructure

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SolutionTest extends AnyFlatSpec with Matchers {

    val mutation:Solution = new Mutation();
    val bug:Solution = new Bug()

    "The Solution object" should "instantiate Mutation" in {
        mutation.getType shouldEqual "Mutation"
    }

    "The Solution object" should "instantiate Bug" in {
        bug.getType shouldEqual "Bug"
    }
}