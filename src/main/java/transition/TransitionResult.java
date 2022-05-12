package main.java.transition;

import java.util.List;

public record TransitionResult(String state, List<String> stackBuffer) {
  @Override
  public String toString() {
    return "(" + state + "," + (stackBuffer.isEmpty() ? "ε" : String.join("", stackBuffer)) + ")";
  }
}
