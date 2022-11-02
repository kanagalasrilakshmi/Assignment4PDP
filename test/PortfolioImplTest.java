
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



}