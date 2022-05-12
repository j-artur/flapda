package main.java.automaton;

import main.java.transition.Transition;

public record AutomatonState(String state, String input, AutomatonStack stack, Transition lastTransition, int step) {
}
