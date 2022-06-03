package main.java.automaton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.exception.IllegalAutomatonConfiguration;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public record AutomatonConfig(
    Set<String> states,
    Set<String> inputAlphabet,
    Set<String> stackAlphabet,
    String initialState,
    String initialStackSymbol,
    Set<String> acceptingStates) {
  public static AutomatonConfig readConfigFrom(String path) throws IOException {
    var json = Files.readString(Path.of(path));
    var config = new ObjectMapper().readValue(json, AutomatonConfig.class);
    return config;
  }

  public static Map<TransitionArguments, Set<TransitionResult>> readTransitionTableFrom(String path)
      throws IOException {
    var csv = Files.readString(Path.of(path));
    var transitionTable = new HashMap<TransitionArguments, Set<TransitionResult>>();
    List.of(csv.split("\\r?\\n")).forEach(csvLine -> {
      var line = csvLine.split(",", -1);

      var currentState = line[0];
      var input = line[1];
      var topOfStack = line[2];
      var nextState = line[3];
      var stackBuffer = line[4].isEmpty() ? List.<String>of() : List.of(line[4].split(""));

      var arguments = new TransitionArguments(currentState, input, topOfStack);
      var result = new TransitionResult(nextState, stackBuffer);

      if (!transitionTable.containsKey(arguments)) {
        transitionTable.put(arguments, Set.of(result));
      } else {
        var resultSet = Stream
            .concat(transitionTable.get(arguments).stream(), Stream.of(result))
            .collect(Collectors.toUnmodifiableSet());
        transitionTable.put(arguments, resultSet);
      }
    });

    return transitionTable;
  }

  public void isValidConfig(AutomatonConfig config) throws IllegalAutomatonConfiguration {
  }

  @Override
  public String toString() {
    try {
      var objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
      return objectMapper.writeValueAsString(this);
    } catch (Exception e) {
      return "Error displaying " + this.getClass();
    }
  }
}