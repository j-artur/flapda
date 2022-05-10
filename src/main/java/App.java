package main.java;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
  public static void main(String[] args) {
    var objectMapper = new ObjectMapper();

    try {
      var json = Files.readString(Paths.get("automaton.json"));

      var config = objectMapper.readValue(json, AutomatonConfig.class);

      var automaton = new Automaton(config);

      System.out.println(config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
