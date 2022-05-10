package main.java;

import java.util.List;

public record Transition(
    String currState,
    String stackTop,
    String input,
    String nextState,
    List<String> pushToStack) {
}
