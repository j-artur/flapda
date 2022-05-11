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
    var objectMapper = new ObjectMapper();

    try {
      var json = Files.readString(Path.of("automaton.json"));

      var config = objectMapper.readValue(json, AutomatonConfig.class);

      var csv = Files.readString(Path.of("transition.csv"));

      var transitions = List.of(csv.split("\n")).stream().map(line -> {
        var lineItems = line.split(",");
        return new Transition(
            lineItems[0],
            lineItems[1],
            lineItems[2],
            lineItems[3],
            List.of(lineItems[4].split("")));
      }).toList();

      var transitionMap = new HashMap<TransitionArguments, Set<TransitionResult>>();

      transitions.forEach(t -> {
        var arguments = new TransitionArguments(t.currState(), t.input(), t.stackTop());
        var result = new TransitionResult(t.nextState(), t.pushToStack());

        if (transitionMap.containsKey(arguments)) {
          transitionMap.put(
              arguments,
              Stream
                  .concat(transitionMap.get(arguments).stream(), Stream.of(result))
                  .collect(Collectors.toUnmodifiableSet()));
        } else {
          transitionMap.put(arguments, Set.of(result));
        }
      });

      var automaton = new Automaton(config, transitionMap);

      System.out.println(automaton);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
