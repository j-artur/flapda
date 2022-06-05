package main.java.automaton;

import main.java.transition.TransitionDescription;
import main.java.util.Stack;

public record AutomatonState(
    String state,
    String input,
    Stack<String> stack,
    TransitionDescription lastTransition,
    int step) {
}
