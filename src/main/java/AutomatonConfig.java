package main.java;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public record AutomatonConfig(
    Set<String> states,
    Set<String> inputAlphabet,
    Set<String> stackAlphabet,
    String initialState,
    String initialStackSymbol,
    Set<String> acceptingStates) {
  @Override
  public String toString() {
    try {
      var objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
      return objectMapper.writeValueAsString(this);
    } catch (Exception e) {
      return "Error displaying " + this.getClass();
    }
  }
}