package main.java.exception;

import java.util.Map.Entry;
import java.util.Set;

import main.java.automaton.AutomatonConfig;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class IllegalAutomatonConfiguration extends Exception {
  public IllegalAutomatonConfiguration(String message, AutomatonConfig config) {
    super(message + " at " + config.toString());
  }

  public IllegalAutomatonConfiguration(String message, Entry<TransitionArguments, Set<TransitionResult>> transition) {
    super(message + "\nat " + transition.toString() + " (on CSV file)");
  }
}
