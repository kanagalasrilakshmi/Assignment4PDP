package model.portfolioimplhelper;

public interface IStocksObj {
    /**
     * Get the tickr symbol of the company.
     *
     * @return string type of the tickr symbol.
     */
    String getTickr();

    /**
     * Get number of stocks value.
     *
     * @return float type of number of stocks
     */
    int getNumStocks();
}
