package main.java.automaton;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.exception.IllegalAutomatonConfiguration;
import main.java.transition.Transition;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class Automaton {
  private AutomatonConfig config;
  private Map<TransitionArguments, Set<TransitionResult>> transitionTable;

  public Automaton(AutomatonConfig config, Map<TransitionArguments, Set<TransitionResult>> transitionTable)
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

    for (var transition : transitionTable.entrySet()) {
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
    this.transitionTable = transitionTable;
    AutomatonLogger.setTransitionTable(transitionTable);
  }

  public boolean test(String string) {
    var state = this.config.initialState();
    var stack = new AutomatonStack();
    stack.push(this.config.initialStackSymbol());

    var initialAutomatonState = new AutomatonState(state, string, stack, null, 0);
    return this.test(initialAutomatonState);
  }

  private boolean test(AutomatonState automatonState) {
    AutomatonLogger.log(automatonState);

    if (this.acceptState(automatonState)) {
      AutomatonLogger.logSuccess(automatonState);
      return true;
    }

    if (automatonState.stack().empty())
      return false;

    for (var newState : updateState(automatonState)) {
      if (this.test(newState)) {
        AutomatonLogger.logSuccess(automatonState);
        return true;
      }
    }

    return false;
  }

  private Set<AutomatonState> updateState(AutomatonState previousState) {
    var string = previousState.input();
    var stack = previousState.stack().copy();
    var topOfStack = stack.pop();

    Set<AutomatonState> nextStates = new HashSet<>();

    var args = new TransitionArguments(previousState.state(), "", topOfStack);
    var resultSet = this.transitionTable.get(args);
    if (resultSet != null)
      for (var result : resultSet) {
        nextStates.add(new AutomatonState(
            result.state(),
            string,
            stack.copy().pushReverse(result.stackBuffer()),
            new Transition(args, result),
            previousState.step() + 1));
      }

    if (string.isEmpty())
      return nextStates;

    args = new TransitionArguments(previousState.state(), String.valueOf(string.charAt(0)), topOfStack);
    resultSet = this.transitionTable.get(args);
    if (resultSet != null) {
      for (var result : resultSet) {
        nextStates.add(new AutomatonState(
            result.state(),
            string.substring(1),
            stack.copy().pushReverse(result.stackBuffer()),
            new Transition(args, result),
            previousState.step() + 1));
      }
    }

    return nextStates;
  }

  private boolean acceptState(AutomatonState automatonState) {
    if (!automatonState.input().isEmpty())
      return false;

    if ((this.config.acceptingStates().isEmpty() && automatonState.stack().empty())
        || this.config.acceptingStates().contains(automatonState.state()))
      return true;

    return false;
  }

  @Override
  public String toString() {
    return "config: " + this.config.toString()
        + "\nTransition Table:\n"
        + String.join("\n", this.transitionTable.entrySet().stream().map(e -> e.toString()).toList());
  }
}
