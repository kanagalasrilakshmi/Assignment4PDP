package view;

import controller.ControllerGUI;

public interface GUIView {
    /**
     *
     */
    void makeVisible();

    /**
     *
     * @return
     */
    String givenPath();

    /**
     *
     * @param setMessage
     */
    void setpathStore(String setMessage);

    /**
     *
     */
    void displayCostBasis();

    /**
     *
     * @param message
     */
    void setLabelCostBasisStatus(String message);

    /**
     *
     */
    void displayCreatePf();

    /**
     *
     */
    void displayModifyPf();

    /**
     *
     */
    void displayValuepf();

    /**
     *
     * @param message
     */
    void setCreateLabelStatus(String message);

    /**
     *
     * @param message
     */
    void setcreateDialogStatus(String message);

    /**
     *
     * @param message
     */
    void setModifyLabelStatus(String message);

    /**
     *
     * @param message
     */
    void setmodifyDialogStatus(String message);

    /**
     *
     * @param message
     */
    void setValueLabelStatus(String message);

    /**
     *
     * @param message
     */
    void setvalueDialogStatus(String message);

    /**
     *
     * @param message
     */
    void setCostBasisDialogStatus(String message);

    /**
     *
     * @param message
     */
    void setCreatePfValue(String message);

    /**
     *
     * @param message
     */
    void settickrmodifyValue(String message);

    /**
     *
     * @param message
     */
    void settickrcreateValue(String message);

    /**
     *
     * @param message
     */
    void setnumstockscreateValue(String message);

    /**
     *
     * @param message
     */
    void setdateofcreationValue(String message);

    /**
     *
     * @param message
     */
    void setcommissionfeescreateValue(String message);

    /**
     *
     * @param message
     */
    void setModifyPfValue(String message);

    /**
     *
     * @param message
     */
    void setnumstocksmodifyValue(String message);

    /**
     *
     * @param message
     */
    void setdateofmodifynValue(String message);

    /**
     *
     * @param message
     */
    void setcommissionfeesmodifyValue(String message);

    /**
     *
     * @param message
     */
    void setpfnameVal(String message);

    /**
     *
     * @param message
     */
    void setdateVal(String message);

    /**
     *
     * @param message
     */
    void setpfNameCostBasis(String message);

    /**
     *
     * @param message
     */
    void setDate(String message);

    /**
     *
     * @param message
     */
    void setportfoliosListModify(String message);

    /**
     *
     * @param message
     */
    void setPortfoliosListVal(String message);

    /**
     *
     * @param message
     */
    void setPortfoliosListBasis(String message);

    /**
     *
     * @param features
     */
    void addFeatures(ControllerGUI features);

    /**
     *
     * @param message
     */
    void setRetrievePanelStatus(String message);

    /**
     *
     * @param message
     */
    void setPortfoliosListRetrieve(String message);

    /**
     *
     */
    void displayRetrievepf();

    /**
     *
     * @param message
     */
    void setretrieveDialogStatus(String message);

    /**
     *
     * @param message
     */
    void setpfnameretrieve(String message);

    /**
     *
     * @param message
     */
    void setPortfoliosListComposition(String message);

    /**
     *
     */
    void displayDollarExistingpf();

    /**
     *
     */
    void displayDollarNewpf();

    /**
     *
     * @param message
     */
    void setdollarExistingStatus(String message);

    /**
     *
      * @param message
     */
    void setdollarNewStatus(String message);

    /**
     *
     * @param message
     */
    void setdollarexistpanestatus(String message);

    /**
     *
     * @param message
     */
    void setpfNameExistDollar(String message);

    /**
     *
     * @param message
     */
    void setdollardateexist(String message);

    /**
     *
     * @param message
     */
    void setdollarexistcommisionval(String message);

    /**
     *
     * @param message
     */
    void setstocksexist(String message);

    /**
     *
     * @param message
     */
    void setweightsexist(String message);

    /**
     *
     * @param message
     */
    void setdollarexistval(String message);

    /**
     *
     * @param message
     */
    void setdollarnewpanestatus(String message);

    /**
     *
     * @param message
     */
    void setportfolioslistdollarexist(String message);

    /**
     *
     * @param message
     */
    void setpfnamedollarnew(String message);

    /**
     *
     * @param message
     */
    void setstocksnew(String message);

    /**
     *
     * @param message
     */
    void setweightsnew(String message);

    /**
     *
     * @param message
     */
    void setdollarnewval(String message);

    /**
     *
     * @param message
     */
    void setstartdatenew(String message);

    /**
     *
     * @param message
     */
    void setenddatenew(String message);

    /**
     *
     * @param message
     */
    void setdollardays(String message);

    /**
     * Sets the value for the commission value for the commission field in start to finish dialog pane
     * @param message is the value
     */
    void setdollarcommissionnew(String message);

    /**
     * Sets the value for the strategy name while adding dollar strategy on existing portfolio.
     * @param message is the value that needs to be set for the strategy field while adding strategy rtfolio pane
     */
    void setstrategynameexist(String message);

    /**
     * Sets the value for the strategy name while creating start to finish portfolio.
     * @param message that needs to be set for the strategy field in start to finish dialog pane
     */
    void setstrategynamenewexist(String message);

}
