package main.java.automaton.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonLogger;
import main.java.exception.IllegalAutomatonConfiguration;
import main.java.transition.Transition;
import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class AutomatonDeserializer extends StdDeserializer<Automaton> {

  public AutomatonDeserializer() {
    this(null);
  }

  public AutomatonDeserializer(Class<Automaton> t) {
    super(t);
  }

  @Override
  public Automaton deserialize(JsonParser parser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    JsonNode node = parser.readValueAsTree();

    AutomatonConfig config = new ObjectMapper().readValue(node.get("config").toString(), AutomatonConfig.class);

    Set<Transition> transitions = new ObjectMapper().readValue(
        node.get("transitions").toString(),
        TypeFactory.defaultInstance().constructCollectionType(Set.class, Transition.class));

    Map<TransitionArguments, Set<TransitionResult>> transitionTable = new HashMap<TransitionArguments, Set<TransitionResult>>();

    transitions.forEach(transition -> transitionTable.put(transition.arguments(), transition.results()));

    try {
      Automaton automaton = new Automaton(config, transitionTable, AutomatonLogger.getInstance());
      return automaton;
    } catch (IllegalAutomatonConfiguration e) {
      throw new IOException(e.getMessage());
    }
  }
}