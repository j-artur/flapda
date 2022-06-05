package main.java;

import java.util.Scanner;

import main.java.automaton.Automaton;
import main.java.automaton.AutomatonConfig;
import main.java.automaton.AutomatonDatabase;
import main.java.automaton.AutomatonLogger;
import main.java.util.DoubleLinkedList;
import main.java.util.List;
import main.java.util.Stack;
import main.java.view.View;

public class App {
  private static Automaton automaton;

  public static void main(String[] args) {
    View.launch(View.class);
  }

  public static void main() {
    List<Integer> list = new DoubleLinkedList<>();
    list.addLast(1).addLast(2).addLast(3).addLast(4).addLast(5);

    Stack<Integer> stack = new Stack<>();

    System.out.println(list);
    System.out.println(stack.clone().pushAll(list.reverse()));

    try (Scanner scanner = new Scanner(System.in)) {
      AutomatonDatabase database = AutomatonDatabase.getInstance();

      database.getMap().forEach((key, value) -> {
        System.out.println(key + ":\n" + value + "\n");
      });

      var config = AutomatonConfig.readConfigFrom("automaton.json");
      var transitionTable = AutomatonConfig.readTransitionTableFrom("transition.csv");

      automaton = new Automaton(config, transitionTable,
          AutomatonLogger.getInstance());

      System.out.println(automaton);
      System.out.print("Nome do autômato: ");
      String name = scanner.nextLine();

      database.add(name, automaton);

      database.flush();

      AutomatonLogger.getInstance().getStream().println(automaton);

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
      e.printStackTrace();
    }
  }

  private static void test(String string) {
    System.out.println("\n<TESTING STRING \"" + string + "\">\n");
    AutomatonLogger.getInstance().getStream().println("\n<TESTING STRING \"" +
        string +
        "\">\n");
    var test = automaton.test(string);
    AutomatonLogger.getInstance().printAllLogs();
    if (test) {
      System.out.println("<STRING ACCEPTED>\n");
      AutomatonLogger.getInstance().getStream().println("\n<STRING ACCEPTED>\n");
      AutomatonLogger.getInstance().getStream().println("\n-- Transition Path --\n");
      AutomatonLogger.getInstance().printSuccessLogs();
    } else {
      System.out.println("<STRING DENIED>\n");
      AutomatonLogger.getInstance().getStream().println("\n<STRING DENIED>\n");
    }
    System.out.println("Veja detalhes do resultado no arquivo \"logs.txt\"");
  }
}
