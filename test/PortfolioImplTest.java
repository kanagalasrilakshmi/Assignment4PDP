
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class PortfolioImplTest {

  @Test
  public void checkIfDateInRightFormat0() {
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(true, portfolioObj.checkIfRightFormat("2022-02-01"));
  }

  @Test
  public void checkIfDateInRightFormat1() {
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkIfRightFormat("01/02/2020"));
  }

  @Test
  public void checkIfDateInRightFormat2() {
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkIfRightFormat("01022020"));
  }

  @Test
  public void checkIfDateInRightFormat3() {
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkIfRightFormat("01-02-2020"));
  }

  @Test
  public void ifPortfolioNameRightFormatValid() {
    // Without any white spaces
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(true, portfolioObj.checkValidpfName("healthPortfolio"));
  }

  @Test
  public void ifPortfolioNameRightFormatInvalid1() {
    // With any white spaces is invalid
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName("Health Portfolio"));
  }

  @Test
  public void ifPortfolioNameRightFormatInvalidEmptyName() {
    // With any white spaces is invalid
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName(""));
  }
  @Test
  public void ifPortfolioNameRightFormatInvalidNullName() {
    // With any white spaces is invalid
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName(null));
  }

  @Test
  public void ifPortfolioNameRightFormatInvalidBigName() {
    // Portfolio name greater than 25 letters not allowed
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName("abcdefghijklmnopqrstuvwxyz"));
  }

  @Test
  public void ifPortfolioNameRightFormatInvalidSpecialCharacters() {
    // Name greater than 25 letters not allowed
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName("ab*b"));
  }

  @Test
  public void ifPortfolioNameRightFormatInvalidNumbers() {
    // Name greater than 25 letters not allowed
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkValidpfName("2345678"));
  }

  @Test
  public void checkDirectoryName() {
    // Name greater than 25 letters not allowed
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(true, portfolioObj.checkLastEndingCharacter("/Users/"));
  }

  @Test
  public void checkDirectoryNameInvalid() {
    // Name greater than 25 letters not allowed
    Portfolio portfolioObj = new PortfolioImpl();
    assertEquals(false, portfolioObj.checkLastEndingCharacter("/Users"));
  }

}