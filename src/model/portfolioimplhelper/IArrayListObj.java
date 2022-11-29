package model.portfolioimplhelper;

import java.util.ArrayList;

public interface IArrayListObj {
    /**
     * gets the array list of tickr symbols.
     *
     * @return arraylist of tickrsymbols of type string
     */
    ArrayList<String> getTickrSymbols();

    /**
     * gets the array list of corresponding prices for a tickr symbols.
     *
     * @return arraylist of price for tickrsymbols of type string
     */
    ArrayList<String> getPrices();
}
