package main.java.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.java.automaton.AutomatonLogger;

public class LogsController {
  @FXML
  TextArea logsArea;

  @FXML
  public void initialize() {
    File file = AutomatonLogger.getInstance().getFile();

    try {
      if (file.exists()) {
        logsArea.setText(Files.readString(Path.of(file.getAbsolutePath())));
      } else {
        logsArea.setText("Não foi possível abrir o arquivo de logs");
      }
    } catch (Exception e) {
      logsArea.setText("Não foi possível abrir o arquivo de logs");
    }
  }
}
