package main.java;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonLogger;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class App {
  public static void main(String[] args) {
    try {
      var json = Files.readString(Path.of("automaton.json"));
      var config = new ObjectMapper().readValue(json, AutomatonConfig.class);

      var csv = Files.readString(Path.of("transition.csv"));
      var transitionMap = new HashMap<TransitionArguments, Set<TransitionResult>>();
      List.of(csv.split("\n")).forEach(csvLine -> {
        var line = csvLine.split(",", -1);

        var currentState = line[0];
        var input = line[1];
        var topOfStack = line[2];
        var nextState = line[3];
        var stackBuffer = line[4].isEmpty() ? List.<String>of() : List.of(line[4].split(""));

        var arguments = new TransitionArguments(currentState, input, topOfStack);
        var result = new TransitionResult(nextState, stackBuffer);

        if (!transitionMap.containsKey(arguments)) {
          transitionMap.put(arguments, Set.of(result));
        } else {
          var resultSet = Stream
              .concat(transitionMap.get(arguments).stream(), Stream.of(result))
              .collect(Collectors.toUnmodifiableSet());
          transitionMap.put(arguments, resultSet);
        }
      });

      var automaton = new Automaton(config, transitionMap);
      var string = "10aa01";

      System.out.println(automaton);

      System.out.println();

      System.out.println("<TESTING STRING \"" + string + "\">");
      var test = automaton.test(string);
      AutomatonLogger.printAllLogs();
      System.out.println();
      if (test) {
        System.out.println("<STRING ACCEPTED>");
        System.out.println();
        AutomatonLogger.printSuccessLogs();
      } else {
        System.out.println("<STRING DENIED>");
      }
      System.out.println();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
