package main.java.automaton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.exception.IllegalAutomatonConfiguration;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;
import main.java.util.LinkedList;
import main.java.util.List;

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
    String csv = Files.readString(Path.of(path));
    Map<TransitionArguments, Set<TransitionResult>> transitionTable = new HashMap<TransitionArguments, Set<TransitionResult>>();

    String[] csvLines = csv.split("\\r?\\n");

    for (String line : csvLines) {
      var segments = line.split(",", -1);

      String currentState = segments[0];
      String input = segments[1];
      String topOfStack = segments[2];
      String nextState = segments[3];
      List<String> stackBuffer = new LinkedList<>();

      if (!segments[4].isEmpty())
        for (var symbol : segments[4].split(""))
          stackBuffer.addLast(symbol);

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
    }

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