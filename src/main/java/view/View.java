package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {
  private static Stage primaryStage;
  private static Stage secondaryStage;

  public static Scene loadXML(String fileName) throws Exception {
    Parent root = FXMLLoader.load(View.class.getResource("../../resources/xml/" + fileName + ".fxml"));
    return new Scene(root);
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  private static Stage setPrimaryWindow(Scene scene, String title) throws Exception {
    primaryStage.show();
    closeSecondaryWindow();
    primaryStage.setScene(scene);
    primaryStage.setTitle(title);
    return primaryStage;
  }

  private static Stage setSecondaryWindow(Scene scene, String title) throws Exception {
    closeSecondaryWindow();
    secondaryStage = new Stage();
    secondaryStage.setScene(scene);
    secondaryStage.setTitle(title);
    secondaryStage.centerOnScreen();
    secondaryStage.setResizable(false);
    secondaryStage.show();
    return secondaryStage;
  }

  public static void closeSecondaryWindow() {
    if (secondaryStage != null) {
      secondaryStage.close();
      secondaryStage = null;
    }
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setWidth(800);
    stage.setHeight(600);
    primaryStage = stage;
    primaryStage.setResizable(false);
    home();
  }

  public static void home() throws Exception {
    setPrimaryWindow(loadXML("home"), "Flapda");
  }

  public static void create() throws Exception {
    setPrimaryWindow(loadXML("create"), "Flapda - Criar autômato");
  }

  public static void logs() throws Exception {
    setSecondaryWindow(loadXML("logs"), "Flapda - Logs");
  }
}
