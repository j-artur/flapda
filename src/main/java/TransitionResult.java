package main.java;

import java.util.List;

public record TransitionResult(
    String state,
    List<String> stackBuffer) {
}
