package main.java.automaton;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import main.java.automaton.json.AutomatonDeserializer;
import main.java.automaton.json.AutomatonSerializer;
import main.java.exception.IllegalAutomatonConfiguration;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionDescription;
import main.java.transition.TransitionResult;
import main.java.util.Stack;

@JsonSerialize(using = AutomatonSerializer.class)
@JsonDeserialize(using = AutomatonDeserializer.class)
public class Automaton {
  private AutomatonConfig config;
  private Map<TransitionArguments, Set<TransitionResult>> transitionTable;
  private AutomatonLogger logger;

  public AutomatonConfig getConfig() {
    return config;
  }

  public Map<TransitionArguments, Set<TransitionResult>> getTransitionTable() {
    return transitionTable;
  }

  public Automaton(
      AutomatonConfig config,
      Map<TransitionArguments, Set<TransitionResult>> transitionTable,
      AutomatonLogger logger)
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
    this.logger = logger;
    this.logger.setTransitionTable(transitionTable);
  }

  public boolean test(String string) {
    var state = this.config.initialState();
    var stack = new Stack<String>();
    stack.push(this.config.initialStackSymbol());

    var initialAutomatonState = new AutomatonState(state, string, stack, null, 0);
    return this.test(initialAutomatonState);
  }

  private boolean test(AutomatonState automatonState) {
    this.logger.log(automatonState);

    if (this.acceptState(automatonState)) {
      this.logger.logSuccess(automatonState);
      return true;
    }

    if (automatonState.stack().isEmpty())
      return false;

    for (var newState : updateState(automatonState)) {
      if (this.test(newState)) {
        this.logger.logSuccess(automatonState);
        return true;
      }
    }

    return false;
  }

  private Set<AutomatonState> updateState(AutomatonState previousState) {
    var string = previousState.input();
    var stack = previousState.stack().clone();
    var topOfStack = stack.pop();

    Set<AutomatonState> nextStates = new HashSet<>();

    var args = new TransitionArguments(previousState.state(), "", topOfStack);
    var resultSet = this.transitionTable.get(args);
    if (resultSet != null)
      for (var result : resultSet) {
        nextStates.add(new AutomatonState(
            result.state(),
            string,
            stack.clone().pushAll(result.stackBuffer().reverse()),
            new TransitionDescription(args, result),
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
            stack.clone().pushAll(result.stackBuffer().reverse()),
            new TransitionDescription(args, result),
            previousState.step() + 1));
      }
    }

    return nextStates;
  }

  private boolean acceptState(AutomatonState automatonState) {
    if (!automatonState.input().isEmpty())
      return false;

    if ((this.config.acceptingStates().isEmpty() && automatonState.stack().isEmpty())
        || this.config.acceptingStates().contains(automatonState.state()))
      return true;

    return false;
  }

  public AutomatonLogger getLogger() {
    return this.logger;
  }

  @Override
  public String toString() {
    return "Automaton Config: " + this.config.toString()
        + "\nTransition Table:\n"
        + String.join("\n", this.transitionTable.entrySet().stream().map(e -> e.toString()).toList());
  }

  public static String stringifyStack(Stack<String> stack) {
    if (stack.isEmpty())
      return "ε";

    String string = "";
    for (var symbol : stack)
      string += symbol;

    return string;
  }
}
