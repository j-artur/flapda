package main.java.util;

public interface List<T> extends Iterable<T> {
  /**
   * Gets an item from a position in the list
   * 
   * @param index position to get the item
   * @return the item
   * @throws IndexOutOfBoundsException if there's no item on the specified
   *                                   position
   */
  public T get(int index) throws IndexOutOfBoundsException;

  /**
   * Adds an item to the beginning of the list
   * 
   * @param item item to be added
   * @return the list itself
   */
  public List<T> addFirst(T item);

  /**
   * Adds an item to the end of the list
   * 
   * @param item item to be added
   * @return the list itself
   */
  public List<T> addLast(T item);

  /**
   * Adds an item to a specified position in the list, if the specifiend position
   * isn't available, adds to the end of the list
   * 
   * @param item  item to be added
   * @param index position to add the item
   * @return the list itself
   */
  public List<T> add(T item, int index);

  /**
   * Peeks at the first item of the list
   * 
   * @return the item
   */
  public T peekFirst();

  /**
   * Peeks at the last item of the list
   * 
   * @return the item
   */
  public T peekLast();

  /**
   * Removes the first item of the list, if there is one
   * 
   * @return the item removed
   */
  public T removeFirst();

  /**
   * Removes the last item of the list, if there is one
   * 
   * @return the item removed
   */
  public T removeLast();

  /**
   * Removes an item from a position in the list
   * 
   * @param index position to remove the item
   * @return the removed item
   * @throws IndexOutOfBoundsException if there's no item on the specified
   *                                   position
   */
  public T remove(int index) throws IndexOutOfBoundsException;

  /**
   * Returns the size of the list
   * 
   * @return the size of the list
   */
  public int size();

  /**
   * Returns a boolean representing if the list is empty.
   * 
   * @return true if the list is empty, false otherwise.
   */
  public boolean isEmpty();

  /**
   * Returns a new list with the items in reversed order.
   * 
   * Does NOT mutate the list itself
   * 
   * @return a new reverted list
   */
  public List<T> reverse();
}
