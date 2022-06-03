package main.java.automaton;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import main.java.transition.TransitionArguments;
import main.java.transition.TransitionResult;

public class AutomatonLogger {
  private Deque<AutomatonState> successLogs = new ArrayDeque<>();
  private Deque<AutomatonState> allLogs = new ArrayDeque<>();
  private Map<TransitionArguments, Set<TransitionResult>> transitionTable;
  private PrintStream stream;

  public AutomatonLogger(PrintStream stream) {
    this.stream = stream;
  }

  public void logSuccess(AutomatonState automatonState) {
    this.successLogs.push(automatonState);
  }

  public void log(AutomatonState automatonState) {
    this.allLogs.add(automatonState);
  }

  public void printSuccessLogs() {
    if (this.transitionTable.isEmpty())
      return;

    if (this.successLogs.isEmpty())
      return;

    var start = this.successLogs.pop();
    this.stream.println("> Start");
    this.stream.println("("
        + start.state() + ", "
        + "\"" + start.input() + "\"" + ", "
        + start.stack() + ")");

    while (!this.successLogs.isEmpty()) {
      var log = this.successLogs.pop();
      this.stream.println("> " + log.lastTransition().arguments() + " = " + log.lastTransition().result());
      this.stream.println("("
          + log.state() + ", "
          + (log.input().length() > 0 ? "\"" + log.input() + "\"" : "ε") + ", "
          + log.stack() + ")");
    }
    this.stream.println("> Done");
  }

  public void printAllLogs() {
    if (this.transitionTable.isEmpty())
      return;

    if (this.allLogs.isEmpty())
      return;

    var start = this.allLogs.pop();

    var lastStep = start.step();

    this.stream.println("> Start");
    this.stream.println("("
        + start.state() + ", "
        + (start.input().length() > 0 ? "\"" + start.input() + "\"" : "ε") + ", "
        + start.stack() + ")");

    while (!this.allLogs.isEmpty()) {
      var log = this.allLogs.pop();

      if (log.step() > lastStep)
        this.stream.println("> " + log.step() + ". "
            + log.lastTransition().arguments() + " = "
            + this.transitionTable.get(log.lastTransition().arguments()));
      else if (log.step() == lastStep)
        this.stream.println("> No transitions. Continue on " + log.step() + ".");
      else
        this.stream.println("> No transitions. Rollback to " + log.step() + ".");

      lastStep = log.step();
      this.stream.println(log.step() + ". "
          + log.lastTransition().result() + " -> ("
          + log.state() + ", "
          + (log.input().length() > 0 ? "\"" + log.input() + "\"" : "ε") + ", "
          + log.stack() + ")");
    }
    this.stream.println("> No transitions. Finish");
  }

  public void setTransitionTable(Map<TransitionArguments, Set<TransitionResult>> transitionTable) {
    this.transitionTable = transitionTable;
  }

  public PrintStream getStream() {
    return this.stream;
  }
}
