package main.java;

public record TransitionArguments(
    String state,
    String input,
    String topOfStack) {
}
