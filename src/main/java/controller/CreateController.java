package main.java.controller;

import java.io.File;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonDatabase;
import main.java.automaton.AutomatonLogger;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;
import main.java.view.View;

public class CreateController {
  @FXML
  Label jsonChooserLabel;
  @FXML
  Label csvChooserLabel;
  @FXML
  Label automatonConfig;
  @FXML
  TextField automatonName;
  @FXML
  Button createButton;

  AutomatonConfig config;
  Map<TransitionArguments, Set<TransitionResult>> transitionTable;
  Automaton automaton;

  @FXML
  public void initialize() {
    automatonName.textProperty().addListener((observable, oldValue, newValue) -> {
      testAndCreate();
    });
  }

  public void chooseJson(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
    fileChooser.setInitialDirectory(new File("./"));
    File selectedFile = fileChooser.showOpenDialog(View.getPrimaryStage());
    jsonChooserLabel.setText(selectedFile.getAbsolutePath());

    try {
      config = AutomatonConfig.readConfigFrom(jsonChooserLabel.getText());
      testAndCreate();
    } catch (Exception e) {
      automatonConfig.setText(e.getMessage());
    }
  }

  public void chooseCsv(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setInitialDirectory(new File("./"));
    File selectedFile = fileChooser.showOpenDialog(View.getPrimaryStage());
    csvChooserLabel.setText(selectedFile.getAbsolutePath());

    try {
      transitionTable = AutomatonConfig.readTransitionTableFrom(csvChooserLabel.getText());
      testAndCreate();
    } catch (Exception e) {
      automatonConfig.setText(e.getMessage());
    }
  }

  public void create(ActionEvent event) throws Exception {
    if (!automatonName.getText().isEmpty()) {
      AutomatonDatabase.getInstance().add(automatonName.getText(), automaton);
      AutomatonDatabase.getInstance().flush();
      View.home();
    } else {
      automatonConfig.setText("Você precisa escolher um nome");
    }
  }

  private void testAndCreate() {
    if (config != null && transitionTable != null) {
      try {
        config = AutomatonConfig.readConfigFrom(jsonChooserLabel.getText());
        transitionTable = AutomatonConfig.readTransitionTableFrom(csvChooserLabel.getText());
        automaton = new Automaton(config, transitionTable, AutomatonLogger.getInstance());

        automatonConfig.setText(automaton.toString());

        createButton.setDisable(false);
      } catch (Exception e) {
        e.printStackTrace();
        automatonConfig.setText(e.getMessage());
        createButton.setDisable(true);
      }
    } else {
      createButton.setDisable(true);

    }
  }
}
