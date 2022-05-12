package main.java.automaton;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class AutomatonLogger {
  private static Deque<AutomatonState> successLogs = new ArrayDeque<>();
  private static Deque<AutomatonState> allLogs = new ArrayDeque<>();
  private static Map<TransitionArguments, Set<TransitionResult>> transitionTable;

  public static void logSuccess(AutomatonState automatonState) {
    AutomatonLogger.successLogs.push(automatonState);
  }

  public static void log(AutomatonState automatonState) {
    AutomatonLogger.allLogs.add(automatonState);
  }

  public static void printSuccessLogs() {
    if (AutomatonLogger.transitionTable.isEmpty())
      return;

    if (AutomatonLogger.successLogs.isEmpty())
      return;

    var start = AutomatonLogger.successLogs.pop();
    System.out.println("> Start");
    System.out.println("("
        + start.state() + ", "
        + "\"" + start.input() + "\"" + ", "
        + start.stack() + ")");

    while (!AutomatonLogger.successLogs.isEmpty()) {
      var log = AutomatonLogger.successLogs.pop();
      System.out.println("> " + log.lastTransition().args() + " = " + log.lastTransition().result());
      System.out.println("("
          + log.state() + ", "
          + (log.input().length() > 0 ? "\"" + log.input() + "\"" : "ε") + ", "
          + log.stack() + ")");
    }
    System.out.println("> Finish");
  }

  public static void printAllLogs() {
    if (AutomatonLogger.transitionTable.isEmpty())
      return;

    if (AutomatonLogger.allLogs.isEmpty())
      return;

    var start = AutomatonLogger.allLogs.pop();

    var lastStep = start.step();

    System.out.println("> Start");
    System.out.println("("
        + start.state() + ", "
        + (start.input().length() > 0 ? "\"" + start.input() + "\"" : "ε") + ", "
        + start.stack() + ")");

    while (!AutomatonLogger.allLogs.isEmpty()) {
      var log = AutomatonLogger.allLogs.pop();

      if (log.step() > lastStep)
        System.out.println("> " + log.step() + ". "
            + log.lastTransition().args() + " = "
            + AutomatonLogger.transitionTable.get(log.lastTransition().args()));
      else if (log.step() == lastStep)
        System.out.println("> No transitions. Continue on " + log.step() + ".");
      else
        System.out.println("> No transitions. Rollback to " + log.step() + ".");

      lastStep = log.step();
      System.out.println(log.step() + ". "
          + log.lastTransition().result() + " -> ("
          + log.state() + ", "
          + (log.input().length() > 0 ? "\"" + log.input() + "\"" : "ε") + ", "
          + log.stack() + ")");
    }
    System.out.println("> Finish");
  }

  public static void setTransitionTable(Map<TransitionArguments, Set<TransitionResult>> transitionTable) {
    AutomatonLogger.transitionTable = transitionTable;
  }
}
