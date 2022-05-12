package main.java.exceptions;

import java.util.Map.Entry;
import java.util.Set;

import main.java.AutomatonConfig;
import main.java.TransitionArguments;
import main.java.TransitionResult;

public class IllegalAutomatonConfiguration extends Exception {
  public IllegalAutomatonConfiguration(String message, AutomatonConfig config) {
    super(message + "\n\tat " + config.toString());
  }

  public IllegalAutomatonConfiguration(String message, Entry<TransitionArguments, Set<TransitionResult>> transition) {
    super(message + "\n\tat " + transition.toString());
  }
}
