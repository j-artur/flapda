package main.java.transition;

public record TransitionArguments(String state, String input, String topOfStack) {
  @Override
  public String toString() {
    return "δ(" + state + "," + (input.isEmpty() ? "ε" : input) + "," + topOfStack + ")";
  }
}
