package main.java.automaton;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AutomatonStack {
  private Stack<String> stack = new Stack<>();

  public AutomatonStack() {
  }

  public AutomatonStack(String base) {
    push(base);
  }

  public AutomatonStack(List<String> items) {
    pushAll(items);
  }

  public AutomatonStack push(String item) {
    if (item != null)
      stack.push(item);
    return this;
  }

  public AutomatonStack pushAll(List<String> buffer) {
    if (buffer != null && !buffer.isEmpty()) {
      var iterator = buffer.listIterator();
      while (iterator.hasNext())
        stack.push(iterator.next());
    }
    return this;
  }

  public AutomatonStack pushReverse(List<String> buffer) {
    if (buffer != null && !buffer.isEmpty()) {
      var iterator = buffer.listIterator(buffer.size());
      while (iterator.hasPrevious())
        stack.push(iterator.previous());
    }
    return this;
  }

  public String pop() {
    return stack.pop();
  }

  public AutomatonStack copy() {
    return new AutomatonStack().pushAll(stack);
  }

  public boolean empty() {
    return stack.empty();
  }

  @Override
  public String toString() {
    if (this.empty())
      return "ε";

    var copy = this.copy();
    Collections.reverse(copy.stack);
    return String.join("", copy.stack);
  }
}
