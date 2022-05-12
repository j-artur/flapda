package main.java;

import java.io.File;
import java.io.PrintStream;

import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonLogger;

public class App {
  private static Automaton automaton;

  public static void main(String[] args) {
    try {
      var config = AutomatonConfig.readConfigFrom("automaton.json");
      var transitionTable = AutomatonConfig.readTransitionTableFrom("transition.csv");

      automaton = new Automaton(config, transitionTable, new AutomatonLogger(new PrintStream(new File("logs.txt"))));

      var string = "10aa01";

      test(string);
    } catch (Exception e) {
      System.out.println("Ocorreu um erro...");
      System.out.println(e.getMessage());
      System.out.println();
      e.printStackTrace();
    }
  }

  private static void test(String string) {
    automaton.getLogger().getStream().println(automaton);
    automaton.getLogger().getStream().println("\n<TESTING STRING \"" + string + "\">\n");
    var test = automaton.test(string);
    automaton.getLogger().printAllLogs();
    if (test) {
      automaton.getLogger().getStream().println("\n<STRING ACCEPTED>\n");
      automaton.getLogger().getStream().println("\n-- Transition Path --\n");
      automaton.getLogger().printSuccessLogs();
    } else {
      automaton.getLogger().getStream().println("\n<STRING DENIED>");
    }
  }
}
