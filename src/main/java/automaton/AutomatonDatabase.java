package main.java.automaton;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class AutomatonDatabase {
  private static AutomatonDatabase instance;

  private ObjectMapper mapper;
  private TypeFactory factory;
  private MapType mapType;

  private Map<String, Automaton> map;

  public Map<String, Automaton> getMap() {
    return map;
  }

  private AutomatonDatabase() {
    mapper = new ObjectMapper();
    factory = TypeFactory.defaultInstance();
    mapType = factory.constructMapType(HashMap.class, String.class, Automaton.class);

    try {
      File file = new File("src/main/resources/automatons.json");

      if (!file.exists()) {
        file.createNewFile();
      }

      map = mapper.readValue(Files.readString(Path.of(file.getAbsolutePath())), mapType);
    } catch (Exception e) {
      map = new HashMap<String, Automaton>();
    }
  }

  public static synchronized AutomatonDatabase getInstance() {
    if (instance == null)
      instance = new AutomatonDatabase();

    return instance;
  }

  public void add(String name, Automaton automaton) {
    map.put(name, automaton);
  }

  public void flush() {
    try {
      File file = new File("src/main/resources/automatons.json");

      if (!file.exists()) {
        file.createNewFile();
      }

      mapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
    } catch (Exception e) {
      System.out.println(e.getMessage() + "\n");
      e.printStackTrace();
    }
  }
}
