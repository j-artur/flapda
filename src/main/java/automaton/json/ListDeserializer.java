package main.java.automaton.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import main.java.util.LinkedList;
import main.java.util.List;

public class ListDeserializer<T> extends StdDeserializer<List<T>> {

  public ListDeserializer() {
    this(null);
  }

  public ListDeserializer(Class<List<T>> t) {
    super(t);
  }

  @Override
  public List<T> deserialize(JsonParser parser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    java.util.List<T> values = parser.readValueAs(new TypeReference<java.util.List<T>>() {
    });

    List<T> list = new LinkedList<>();

    for (T item : values)
      list.addLast(item);

    return list;
  }
}