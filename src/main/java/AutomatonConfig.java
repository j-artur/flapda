package main.java;

import java.util.Set;

public record AutomatonConfig(
    Set<String> states,
    Set<String> inputAlphabet,
    Set<String> stackAlphabet,
    String initialState,
    String initialStackSymbol,
    Set<String> acceptingStates) {
}