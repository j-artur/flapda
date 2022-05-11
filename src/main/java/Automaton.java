package main.java;

import main.java.exceptions.IllegalAutomatonConfiguration;

public class Automaton {
  // private final Set<String> states;
  // private final Set<String> inputAlphabet;
  // private final Set<String> stackAlphabet;
  // private final String initialState;
  // private final String initialStackSymbol;
  // private final Set<String> acceptingStates;

  public Automaton(AutomatonConfig config) throws IllegalAutomatonConfiguration {

    for (var symbol : config.inputAlphabet()) {
      if (symbol.length() != 1) {
        throw new IllegalAutomatonConfiguration("Input Alphabet must be 1 character long each symbol", config);
      }
    }

    for (var symbol : config.stackAlphabet()) {
      if (symbol.length() != 1) {
        throw new IllegalAutomatonConfiguration("Stack Alphabet must be 1 character long each symbol", config);
      }
    }

    if (!config.states().contains(config.initialState())) {
      throw new IllegalAutomatonConfiguration("Initial State is not present in List of States", config);
    }

    if (!config.stackAlphabet().contains(config.initialStackSymbol())) {
      throw new IllegalAutomatonConfiguration("Initial Stack Symbol is not present in Stack Alphabet", config);
    }

    for (var state : config.acceptingStates()) {
      if (!config.states().contains(state)) {
        throw new IllegalAutomatonConfiguration("Accepting State " + state + " is not present in List of States",
            config);
      }
    }

    // this.states = config.states();
    // this.inputAlphabet = config.inputAlphabet();
    // this.stackAlphabet = config.stackAlphabet();
    // this.initialState = config.initialState();
    // this.initialStackSymbol = config.initialStackSymbol();
    // this.acceptingStates = config.acceptingStates();
  }

}
