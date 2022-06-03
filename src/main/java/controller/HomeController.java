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
  }

  public void select(ActionEvent event) throws Exception {
    automaton = AutomatonDatabase.getInstance().getMap().get(select.getValue());
    automatonLabel.setText(automaton.toString());
  }

  public void create(ActionEvent event) throws Exception {
    View.create();
  }

  public void changeText(ActionEvent event) throws Exception {
    testButton.setDisable(testInput.getText().isEmpty() || automaton == null);
  }

  public void test(ActionEvent event) {
    var result = automaton.test(testInput.getText());

    seeLogs.setVisible(result);
    if (result) {
      testResult.setTextFill(Paint.valueOf("#00ff00"));
      testResult.setText("Entrada válida!");
    } else {
      testResult.setTextFill(Paint.valueOf("#ff0000"));
      testResult.setText("Entrada inválida!");
    }
  }

  public void logs(ActionEvent event) throws Exception {
    View.logs();
  }
}
