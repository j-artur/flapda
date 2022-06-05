package main.java.automaton.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import main.java.util.List;

public class ListSerializer<T> extends StdSerializer<List<T>> {

  public ListSerializer() {
    this(null);
  }

  public ListSerializer(Class<List<T>> t) {
    super(t);
  }

  @Override
  public void serialize(
      List<T> list,
      JsonGenerator generator,
      SerializerProvider provider)
      throws IOException, JsonProcessingException {
    generator.writeStartArray();

    for (T item : list)
      generator.writeObject(item);

    generator.writeEndArray();
  }
}