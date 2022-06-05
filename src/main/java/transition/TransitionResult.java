package main.java.transition;

import main.java.util.List;

public record TransitionResult(String state, List<String> stackBuffer) {
  @Override
  public String toString() {
    String string = "(" + state + ",";
    if (stackBuffer.isEmpty())
      string += "ε";
    else
      for (var symbol : stackBuffer)
        string += symbol;
    string += ")";
    return string;
  }
}
