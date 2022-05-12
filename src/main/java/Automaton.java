package main.java;

import java.util.Map;
import java.util.Set;

import main.java.exceptions.IllegalAutomatonConfiguration;

public class Automaton {
  private AutomatonConfig config;
  private Map<TransitionArguments, Set<TransitionResult>> transitionMap;

  public Automaton(AutomatonConfig config, Map<TransitionArguments, Set<TransitionResult>> transitionMap)
      throws IllegalAutomatonConfiguration {

    for (var symbol : config.inputAlphabet())
      if (symbol.length() != 1)
        throw new IllegalAutomatonConfiguration("Input Alphabet must be 1 character long each symbol", config);

    for (var symbol : config.stackAlphabet())
      if (symbol.length() != 1)
        throw new IllegalAutomatonConfiguration("Stack Alphabet symbols must be 1 character long each", config);

    if (!config.states().contains(config.initialState()))
      throw new IllegalAutomatonConfiguration("Initial State is not present in List of States", config);

    if (!config.stackAlphabet().contains(config.initialStackSymbol()))
      throw new IllegalAutomatonConfiguration("Initial Stack Symbol is not present in Stack Alphabet", config);

    for (var state : config.acceptingStates())
      if (!config.states().contains(state))
        throw new IllegalAutomatonConfiguration(
            "Accepting State " + state + " is not present in List of States", config);

    for (var transition : transitionMap.entrySet()) {
      if (!config.states().contains(transition.getKey().state()))
        throw new IllegalAutomatonConfiguration(
            "State " + transition.getKey().state() + " is not present in List of States", transition);

      if (!transition.getKey().input().isEmpty() && !config.inputAlphabet().contains(transition.getKey().input()))
        throw new IllegalAutomatonConfiguration(
            "Symbol " + transition.getKey().input() + " is not present in Input Alphabet", transition);

      if (!config.stackAlphabet().contains(transition.getKey().topOfStack()))
        throw new IllegalAutomatonConfiguration(
            "Symbol " + transition.getKey().topOfStack() + " is not present in Stack Alphabet", transition);

      for (var result : transition.getValue()) {
        if (!config.states().contains(result.state()))
          throw new IllegalAutomatonConfiguration(
              "State " + result.state() + " is not present in List of States", transition);

        if (!result.stackBuffer().isEmpty())
          for (var symbol : result.stackBuffer())
            if (!config.stackAlphabet().contains(symbol))
              throw new IllegalAutomatonConfiguration(
                  "Symbol " + symbol + " is not present in Stack Alphabet", transition);

      }
    }

    this.config = config;
    this.transitionMap = transitionMap;
  }

  @Override
  public String toString() {
    return "config: " + this.config.toString()
        + "\ntransition function:\n"
        + String.join("\n", this.transitionMap.entrySet().stream().map(e -> e.toString()).toList());
  }

  // private TransitionResult transition(String state, String input, String
  // topOfStack) {
  // var arguments = new TransitionArguments(state, input, topOfStack);

  // return this.transitionMap.get(arguments);
  // }
}
