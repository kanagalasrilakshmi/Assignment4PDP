import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class ControllerImplTest {
  @Test
  public void testGo() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C");
    Portfolio model = new PortfolioImpl();
    View view = new ViewImpl(System.out);
    Controller stockController = new ControllerImpl(model, view, System.in);
    stockController.go();
    assertEquals("7\n17\n", out.toString());
  }
}