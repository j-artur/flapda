package main.java.util;

public class Deque<T> {
  DoubleLinkedList<T> list;

  /**
   * Creates a new empty Double-ended Queue
   */
  public Deque() {
    this.list = new DoubleLinkedList<>();
  }

  /**
   * Pushes an item to the last position of the queue
   * 
   * @param item item to be pushed onto the queue
   * @return the queue itself
   */
  public Deque<T> push(T item) {
    this.list.addLast(item);
    return this;
  }

  /**
   * Removes the last item of the queue and returns it.
   * 
   * @return the item on the end of the queue.
   */
  public T pop() {
    return this.list.removeLast();
  }

  /**
   * Peeks at the tail of the queue. Doesn't remove the item.
   * 
   * @return the item on the end of the queue.
   */
  public T tail() {
    return this.list.peekLast();
  }

  /**
   * Shiftes an item to the first position of the queue
   * 
   * @param item item to be shifted into the queue
   * @return the queue itself
   */
  public Deque<T> shift(T item) {
    this.list.addFirst(item);
    return this;
  }

  /**
   * Removes the first item of the queue and returns it.
   * 
   * @return the item on the start of the queue.
   */
  public T poll() {
    return this.list.removeFirst();
  }

  /**
   * Peeks at the head of the queue. Doesn't remove the item.
   * 
   * @return the item on the start of the queue.
   */
  public T head() {
    return this.list.peekFirst();
  }

  /**
   * Returns the size of the queue (how many items are queued)
   * 
   * @return the size of the queue
   */
  public int size() {
    return this.list.size();
  }

  /**
   * Returns a boolean representing if the queue is empty.
   * 
   * @return true if the queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.list.isEmpty();
  }

  /**
   * Returns a new queue with the items in reversed order.
   * 
   * Does NOT mutate the queue itself
   * 
   * @return a new reverted queue
   */
  public Deque<T> reverse() {
    Deque<T> reverse = new Deque<>();
    reverse.list = this.list.reverse();
    return reverse;
  }
}
