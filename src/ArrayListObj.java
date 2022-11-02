import java.util.ArrayList;

public class ArrayListObj {
  private ArrayList<String>tickrSymbols;
  private ArrayList<String>prices;
  public ArrayListObj(ArrayList<String>tickrSymbols,ArrayList<String>prices){
    this.tickrSymbols = tickrSymbols;
    this.prices = prices;
  }

  public ArrayList<String> getTickrSymbols() {
    return this.tickrSymbols;
  }
  public ArrayList<String>getPrices(){
    return this.prices;
  }
}
