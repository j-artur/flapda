package main.java.controller;

import java.io.File;
import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonLogger;
import main.java.view.View;

public class HomeController {
  @FXML
  Label jsonChooserLabel;
  @FXML
  Label csvChooserLabel;
  @FXML
  Label jsonError;
  @FXML
  Label csvError;

  Automaton automaton;

  @FXML
  Label automatonConfig;

  @FXML
  public void initialize() {

  }

  public void chooseJson(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
    File selectedFile = fileChooser.showOpenDialog(View.getPrimaryStage());
    jsonChooserLabel.setText(selectedFile.getAbsolutePath());
  }

  public void chooseCsv(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(View.getPrimaryStage());
    csvChooserLabel.setText(selectedFile.getAbsolutePath());
  }

  public void test(ActionEvent event) {
    try {
      var config = AutomatonConfig.readConfigFrom(jsonChooserLabel.getText());
      var transitionTable = AutomatonConfig.readTransitionTableFrom(csvChooserLabel.getText());
      automaton = new Automaton(config, transitionTable, new AutomatonLogger(new PrintStream(new File("logs.txt"))));

      System.out.println(automaton);

      automatonConfig.setText(automaton.toString());
    } catch (Exception e) {
      automatonConfig.setText(e.getMessage());
    }
  }
}
