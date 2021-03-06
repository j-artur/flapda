package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import main.java.automaton.Automaton;
import main.java.automaton.AutomatonDatabase;
import main.java.automaton.AutomatonLogger;
import main.java.view.View;

public class HomeController {
  @FXML
  ComboBox<String> select;

  Automaton automaton;

  @FXML
  Label automatonLabel;

  @FXML
  TextField testInput;
  @FXML
  Button testButton;
  @FXML
  Label testResult;

  @FXML
  Button seeLogs;

  @FXML
  public void initialize() {
    AutomatonDatabase.getInstance().getMap().forEach((key, value) -> {
      select.getItems().add(key);
    });

    testInput.textProperty().addListener((observable, oldValue, newValue) -> {
      testButton.setDisable(newValue.isEmpty() || automaton == null);
    });
  }

  public void select(ActionEvent event) throws Exception {
    automaton = AutomatonDatabase.getInstance().getMap().get(select.getValue());
    automatonLabel.setText(automaton.toString());
    testButton.setDisable(testInput.getText().isEmpty() || automaton == null);
  }

  public void create(ActionEvent event) throws Exception {
    View.create();
  }

  public void test(ActionEvent event) {
    String string = testInput.getText();

    AutomatonLogger.getInstance().getStream().println("\n<TESTING STRING \"" + string + "\">\n");

    boolean result = automaton.test(string);

    AutomatonLogger.getInstance().printAllLogs();

    if (result) {
      testResult.setTextFill(Paint.valueOf("#00bb00"));
      testResult.setText("Entrada válida!");

      AutomatonLogger.getInstance().getStream().println("\n<STRING ACCEPTED>\n");
      AutomatonLogger.getInstance().getStream().println("\n-- Transition Path --\n");
      AutomatonLogger.getInstance().printSuccessLogs();
    } else {
      testResult.setTextFill(Paint.valueOf("#dd0000"));
      testResult.setText("Entrada inválida!");

      AutomatonLogger.getInstance().getStream().println("\n<STRING DENIED>\n");
    }

    seeLogs.setVisible(true);
  }

  public void logs(ActionEvent event) throws Exception {
    View.logs();
  }
}
