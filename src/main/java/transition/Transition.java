package main.java.transition;

import java.util.Set;

public record Transition(TransitionArguments arguments, Set<TransitionResult> results) {
}
