package main.java.exceptions;

import main.java.AutomatonConfig;

public class IllegalAutomatonConfiguration extends Exception {
  AutomatonConfig automaton;

  public IllegalAutomatonConfiguration(String message, AutomatonConfig automaton) {
    super(message);
    this.automaton = automaton;
  }
}
