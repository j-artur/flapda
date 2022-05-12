package main.java;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

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
              .collect(Collectors.toSet());
          transitionMap.put(arguments, resultSet);
        }
      });

      var automaton = new Automaton(config, transitionMap);

      System.out.println(automaton);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
