package fish.philwants

import org.scalatest.{Matchers, FlatSpec}
import scala.io.Source

class CredentialParsingTest extends FlatSpec with Matchers {
  "parseCredentialsFromFile" should "parse credentials with default regex" in {
    val testFile = Source.fromURL(getClass.getResource("/creds.txt"))
    val creds = Runner.parseCredsFromFile(testFile, Runner.defaultCredentialRegex.r)
    creds.size shouldBe 4
    creds.head shouldBe Credentials("username1", "password1")
    creds(3) shouldBe Credentials("user\"name4", "pass\"word4")
  }

  it should "parse credentials with custom regex" in {
    val testFile = Source.fromURL(getClass.getResource("/creds-custom.txt"))
    val regex = """\{.*"username":"([^"]+)","password","([^"]+)"}"""
    val creds = Runner.parseCredsFromFile(testFile, regex.r)
    creds.size shouldBe 3
    creds.head shouldBe Credentials("username1", "password1")
  }
}
