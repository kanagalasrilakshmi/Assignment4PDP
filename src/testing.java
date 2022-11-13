import java.io.IOException;

import Model.FlexiblePortfolioImpl;

public class testing {
  public static void main(String[] args) throws java.text.ParseException, IOException {
    FlexiblePortfolioImpl obj = new FlexiblePortfolioImpl();
    String portfolioPath = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" ;
    obj.modifyPortfolio(portfolioPath+"newpf.json","01-02-2022", (float) 2.23, 5, "GOOG");
}
}
