package main.java;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonLogger;

public class App {
  private static Automaton automaton;

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      var config = AutomatonConfig.readConfigFrom("automaton.json");
      var transitionTable = AutomatonConfig.readTransitionTableFrom("transition.csv");

      automaton = new Automaton(config, transitionTable, new AutomatonLogger(new PrintStream(new File("logs.txt"))));

      System.out.println(automaton);
      automaton.getLogger().getStream().println(automaton);

      System.out.println("\nTestando o autômato descrito acima.\n");

      while (true) {
        System.out.print("Indique a string a ser testada pelo autômato: ");
        String string = scanner.nextLine();

        test(string);

        System.out.println("Pressione Ctrl + C para sair ou...");
      }

    } catch (Exception e) {
      System.out.println("Ocorreu um erro...");
      System.out.println(e.getMessage());
    }
  }

  private static void test(String string) {
    System.out.println("\n<TESTING STRING \"" + string + "\">\n");
    automaton.getLogger().getStream().println("\n<TESTING STRING \"" + string + "\">\n");
    var test = automaton.test(string);
    automaton.getLogger().printAllLogs();
    if (test) {
      System.out.println("<STRING ACCEPTED>\n");
      automaton.getLogger().getStream().println("\n<STRING ACCEPTED>\n");
      automaton.getLogger().getStream().println("\n-- Transition Path --\n");
      automaton.getLogger().printSuccessLogs();
    } else {
      System.out.println("<STRING DENIED>\n");
      automaton.getLogger().getStream().println("\n<STRING DENIED>\n");
    }
    System.out.println("Veja detalhes do resultado no arquivo \"logs.txt\"");
  }
}
