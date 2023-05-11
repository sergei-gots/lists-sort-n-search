package pro.sky.util;

import pro.sky.util.exception.ListIsFullException;
import pro.sky.util.exception.ListNoSuchElementException;
import pro.sky.util.exception.ListNullPointerException;

public interface IntegerList {

    /** adds an item to the list.
     *
     * @param item item to be added
     * @return added item
     */
    Integer add(Integer item);

    /** adds an item into a specified position within the list.
     *
     * @throws ListIsFullException if the given position
     * points outside the actual number of list items.
     * @param index position within the list to insert the item there
     * @param item an item to be added
     * @return the added item
     */
    Integer add(int index, Integer item);

    /** Sets an item to a specified position with replacing an existing one.
     *
     * @throws ListIsFullException if the given position
     * points outside the actual number of list items.
     * @param index position within the list to insert the item there
     * @param item an item to be added
     * @return the added item
     */
    Integer set(int index, Integer item);

    /** Removes the first occurrence of a specified item.
     *
     * @throws ListNoSuchElementException if there isn't such an item
     * within list.
     * @param item an item to be removed
     * @return the removed item
     */
    Integer remove(Integer item);

    /** Removes a specified with a position item.
     *
     * @throws ListNoSuchElementException if there isn't such an item
     * within list.
     * @param index of an item to be removed
     * @return the removed item
     */
    Integer removeByIndex(int index);

    // Checks if an item is listed in the list.
    // @return true if the item is listed, otherwise - false
    boolean contains(Integer item);

    // Looks for an index of the first item occurrence within the list
    // @return the index of the specified item or -1 if there isn't such an item within the list
    int indexOf(Integer item);

    // Looks for an index of the last item occurrence within the list
    // @return the index of the specified item or -1 if there isn't such an item within the list
    int lastIndexOf(Integer item);

    /** Gets an item specified with a position.
     *
     * @throws ListNoSuchElementException if there isn't such an item
     * within list.
     * @param index of an item to be got
     * @return the specified item
     */
    Integer get(int index);

    /** Compares current IntegerList with another one.
     * @throws ListNullPointerException if otherList is null.
     * @param otherList - a IntegerList instance to be compared with
     * @return true if the content of the list are exactly the same, otherwise false
     **/
    boolean equals(IntegerList otherList);

    /** @return the actual count of items within the list **/
    int size();

    /** @return true if there aren't any items within the list, otherwise - false **/
    boolean isEmpty();

    /** Removes all the items from the list **/
    void clear();

    /** @return a created Integer array with all the items of the list **/
    int[] toArray();
}