public class MVCStocks {
  public static void main(String args[]) {
    Portfolio model = new PortfolioImpl();
    View view = new ViewImpl(System.out);
    Controller controller = new ControllerImpl(model, view, System.in);
    controller.go();
  }
}
