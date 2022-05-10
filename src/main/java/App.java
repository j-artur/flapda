package main.java;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
  public static void main(String[] args) {
    var objectMapper = new ObjectMapper();

    try {
      var json = Files.readString(Paths.get("automaton.json"));

      var config = objectMapper.readValue(json, AutomatonConfig.class);

      var automaton = new Automaton(config);

      Scanner scanner = new Scanner(new File("transition.csv"));

      List<Transition> transitions = new ArrayList<>();

      // Read line
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        var lineItems = line.split(",");
        Transition transition = new Transition(lineItems[0], lineItems[1], lineItems[2],
            lineItems[3], List.of(lineItems[4].split(" ")));

        transitions.add(transition);

      }

      System.out.println(transitions);

      // System.out.println(config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
