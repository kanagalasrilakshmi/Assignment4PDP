package controller;

import java.io.IOException;
import java.text.ParseException;

import model.Portfolio;
import view.GUIView;

public class ControllerImplGUI implements Controller{
  private GUIView guiView;
  private Portfolio portfolio;
  private String rootDir;
  public ControllerImplGUI(Portfolio portfolio,GUIView guiView){
    this.guiView = guiView;
    this.portfolio = portfolio;
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }
  public void goStocks() throws ParseException, IOException{

  }
}
