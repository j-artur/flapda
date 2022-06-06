package main.java.util;

import java.util.Iterator;

/**
 * A generic last-in-first-out (LIFO) stack of objects of the same
 * type/interface.
 */
public class Stack<T> implements Iterable<T> {
  private class Node {
    T data;
    Node next;

    Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  private int size = 0;
  private Node top = null;

  /**
   * Creates a new empty Stack
   */
  public Stack() {
  }

  /**
   * Pushes an item to the stack and returns the stack itself.
   * 
   * @param item item to be pushed onto the stack.
   * @return the stack itself.
   */
  public Stack<T> push(T item) {
    Node next = this.top;

    this.top = new Node(item);
    this.top.next = next;

    this.size++;

    return this;
  }

  /**
   * Pops the last item of the stack and returns it.
   * 
   * @return the item on top of the stack.
   */
  public T pop() {
    if (this.isEmpty())
      return null;

    Node node = this.top;

    this.top = node.next;
    this.size--;

    return node.data;
  }

  /**
   * Peeks at the top of the stack. Doesn't remove the item.
   * 
   * @return the item on top of the stack.
   */
  public T peek() {
    if (this.isEmpty())
      return null;

    return this.top.data;
  }

  /**
   * Returns the size of the stack (how many items are stacked)
   * 
   * @return the size of the stack
   */
  public int size() {
    return this.size;
  }

  /**
   * Returns a boolean representing if the stack is empty.
   * 
   * @return true if the stack is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Pushes a collection of items to the stack and returns itself.
   * 
   * @param items to be pushed onto the stack.
   * @return the stack itself.
   */
  public Stack<T> pushAll(Iterable<T> items) {
    if (items != null)
      for (var item : items)
        this.push(item);

    return this;
  }

  /**
   * Creates and returns a clone of the stack
   * 
   * @return A clone of the stack
   */
  public Stack<T> clone() {
    Stack<T> items = new Stack<>();
    Node item = this.top;

    while (item != null) {
      items.push(item.data);
      item = item.next;
    }

    Stack<T> clone = new Stack<>();

    while (!items.isEmpty()) {
      clone.push(items.pop());
    }

    return clone;
  }

  public Iterator<T> iterator() {
    return new Iterator<T>() {
      Node curr = top;

      public boolean hasNext() {
        return this.curr != null;
      }

      @Override
      public T next() {
        Node curr = this.curr;
        this.curr = curr.next;
        return curr.data;
      }
    };
  }

  @Override
  public String toString() {
    String string = "Stack;size=" + this.size()
        + ";top=" + (this.top != null ? this.top.data : null)
        + ";[";
    Node node = this.top;
    while (node != null) {
      string += node.data + (node.next != null ? ", " : "");
      node = node.next;
    }
    string += "]";
    return string;
  }
}
