package main.java;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
  public static void main(String[] args) {
    var objectMapper = new ObjectMapper();

    try {
      var json = Files.readString(Path.of("automaton.json"));

      var config = objectMapper.readValue(json, AutomatonConfig.class);

      var automaton = new Automaton(config);

      var csv = Files.readString(Path.of("transition.csv"));

      var transitions = List.of(csv.split("\n")).stream().map(line -> {
        var lineItems = line.split(",");
        return new Transition(
            lineItems[0],
            lineItems[1],
            lineItems[2],
            lineItems[3],
            List.of(lineItems[4].split(" ")));
      }).toList();

      System.out.println(transitions);

      // System.out.println(config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
