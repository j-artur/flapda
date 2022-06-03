package main.java.automaton;

import main.java.transition.TransitionDescription;

public record AutomatonState(String state, String input, AutomatonStack stack, TransitionDescription lastTransition,
    int step) {
}
