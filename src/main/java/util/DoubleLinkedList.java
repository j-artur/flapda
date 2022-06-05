package main.java.util;

import java.util.Iterator;

public class DoubleLinkedList<T> implements List<T> {
  private int size = 0;
  private Node head = null;
  private Node tail = null;

  /**
   * Creates a new empty DoubleLinkedList
   */
  public DoubleLinkedList() {
  }

  /**
   * Gets an item from a position in the list
   * 
   * @param index position to get the item
   * @return the item
   * @throws IndexOutOfBoundsException if there's no item on the specified
   *                                   position
   */
  public T get(int index) throws IndexOutOfBoundsException {
    if (this.isEmpty() || index >= this.size() || index < 0)
      throw new IndexOutOfBoundsException();

    return this.getNode(index).data;
  }

  /**
   * Adds an item to the beginning of the list
   * 
   * @param item item to be added
   * @return the list itself
   */
  public DoubleLinkedList<T> addFirst(T item) {
    Node node = new Node(item);

    if (this.head != null) {
      node.next = this.head;
      this.head.prev = node;
    }

    this.head = node;

    if (this.tail == null) {
      this.tail = node;
    }

    this.size++;

    return this;
  }

  /**
   * Adds an item to the end of the list
   * 
   * @param item item to be added
   * @return the list itself
   */
  public DoubleLinkedList<T> addLast(T item) {
    Node node = new Node(item);

    if (this.tail != null) {
      this.tail.next = node;
      node.prev = this.tail;
    }

    this.tail = node;

    if (this.head == null) {
      this.head = node;
    }

    this.size++;

    return this;
  }

  /**
   * Adds an item to a specified position in the list, if the specifiend position
   * isn't available, adds to the end of the list
   * 
   * @param item  item to be added
   * @param index position to add the item
   * @return the list itself
   */
  public DoubleLinkedList<T> add(T item, int index) {
    if (this.isEmpty() || index <= 0)
      return this.addFirst(item);

    if (index >= this.size())
      return this.addLast(item);

    Node prev = this.getNode(index - 1);
    Node curr = new Node(item);
    Node next = prev.next;

    curr.prev = prev;
    curr.next = next;

    prev.next = curr;
    next.prev = curr;

    this.size++;

    return this;
  }

  /**
   * Peeks at the first item of the list
   * 
   * @return the item
   */
  public T peekFirst() {
    if (this.head == null)
      return null;

    return this.head.data;
  }

  /**
   * Peeks at the last item of the list
   * 
   * @return the item
   */
  public T peekLast() {
    if (this.tail == null)
      return null;

    return this.tail.data;
  }

  /**
   * Removes the first item of the list, if there is one
   * 
   * @return the item removed
   */
  public T removeFirst() {
    if (this.isEmpty())
      return null;

    Node head = this.head;

    this.head = head.next;

    this.size--;

    if (this.isEmpty()) {
      this.tail = null;
    } else {
      this.head.prev = null;
    }

    return head.data;
  }

  /**
   * Removes the last item of the list, if there is one
   * 
   * @return the item removed
   */
  public T removeLast() {
    if (this.isEmpty())
      return null;

    Node tail = this.tail;

    this.tail = tail.prev;

    this.size--;

    if (this.isEmpty()) {
      this.head = null;
    } else {
      this.tail.next = null;
    }

    return tail.data;
  }

  /**
   * Removes an item from a position in the list
   * 
   * @param index position to remove the item
   * @return the removed item
   * @throws IndexOutOfBoundsException if there's no item on the specified
   *                                   position
   */
  public T remove(int index) throws IndexOutOfBoundsException {
    if (this.isEmpty() || index >= this.size() || index < 0)
      throw new IndexOutOfBoundsException();

    if (index == 0) {
      return this.removeFirst();
    }

    Node prev = this.getNode(index - 1);

    Node curr = prev.next;

    Node next = curr.next;

    prev.next = next;
    next.prev = prev;

    this.size--;

    return curr.data;
  }

  /**
   * Returns the size of the list
   * 
   * @return the size of the list
   */
  public int size() {
    return this.size;
  }

  /**
   * Returns a boolean representing if the list is empty.
   * 
   * @return true if the list is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Returns a new list with the items in reversed order.
   * 
   * Does NOT mutate the list itself
   * 
   * @return a new reverted list
   */
  public DoubleLinkedList<T> reverse() {
    DoubleLinkedList<T> reverse = new DoubleLinkedList<>();
    Node node = this.head;

    while (node != null) {
      reverse.addFirst(node.data);
      node = node.next;
    }

    return reverse;
  }

  public Iterator<T> iterator() {
    return new Iterator<T>() {
      Node curr = head;

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

  private Node getNode(int index) throws IndexOutOfBoundsException {
    if (this.isEmpty() || index < 0 || index >= this.size())
      throw new IndexOutOfBoundsException();

    if (index < this.size() / 2) {
      return this.getNodeFromStart(index);
    } else {
      return this.getNodeFromEnd(this.size() - index - 1);
    }
  }

  private Node getNodeFromStart(int index) throws IndexOutOfBoundsException {
    Node node = this.head;

    for (int i = 0; i < index; i++)
      node = node.next;

    return node;
  }

  private Node getNodeFromEnd(int index) throws IndexOutOfBoundsException {
    Node node = this.tail;

    for (int i = 0; i < index; i++)
      node = node.prev;

    return node;
  }

  @Override
  public String toString() {
    String string = "LinkedList;size=" + this.size()
        + ";head=" + (this.head != null ? this.head.data : null)
        + ";tail=" + (this.tail != null ? this.tail.data : null)
        + ";[";
    Node node = this.head;
    while (node != null) {
      string += node.data + (node.next != null ? ", " : "");
      node = node.next;
    }
    string += "]";
    return string;
  }

  private class Node {
    Node prev;
    T data;
    Node next;

    Node(T data) {
      this.prev = null;
      this.data = data;
      this.next = null;
    }
  }
}
