package main.java.automaton.json;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import main.java.automaton.Automaton;
import main.java.transition.Transition;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class AutomatonSerializer extends StdSerializer<Automaton> {

  public AutomatonSerializer() {
    this(null);
  }

  public AutomatonSerializer(Class<Automaton> t) {
    super(t);
  }

  @Override
  public void serialize(
      Automaton automaton,
      JsonGenerator generator,
      SerializerProvider provider)
      throws IOException, JsonProcessingException {
    generator.writeStartObject();

    generator.writeObjectField("config", automaton.getConfig());

    Map<TransitionArguments, Set<TransitionResult>> transitionTable = automaton.getTransitionTable();

    Set<Transition> transitions = new HashSet<Transition>();

    transitionTable.forEach((arguments, results) -> transitions.add(new Transition(arguments, results)));

    generator.writeObjectField("transitions", transitions);

    generator.writeEndObject();
  }
}