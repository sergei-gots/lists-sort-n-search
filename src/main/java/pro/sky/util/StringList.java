package pro.sky.util;

import pro.sky.util.exception.StringListIsFullException;
import pro.sky.util.exception.StringListNoSuchElementException;
import pro.sky.util.exception.StringListNullPointerException;

public interface StringList {

    /** adds an item to the list.
     *
     * @param item
     * @return added item
     */
    String add(String item);

    /** adds an item into a specified position within the list.
     *
     * @throws StringListIsFullException if the given position
     * points outside of the actual number of list items.
     * @param index position within the list to insert the item there
     * @param item an item to be added
     * @return the added item
     */
    String add(int index, String item);

    /** Sets an item to a specified position with replacing an existing one.
     *
     * @throws StringListIsFullException if the given position
     * points outside the actual number of list items.
     * @param index position within the list to insert the item there
     * @param item an item to be added
     * @return the added item
     */
    String set(int index, String item);

    /** Removes the first occurrence of a specified item.
     *
     * @throws StringListNoSuchElementException if there isn't such an item
     * within list.
     * @param item an item to be removed
     * @return the removed item
     */
    String remove(String item);

    /** Removes a specified with a position item.
     *
     * @throws StringListNoSuchElementException if there isn't such an item
     * within list.
     * @param index of an item to be removed
     * @return the removed item
     */
    String remove(int index);

    // Checks if an item is listed in the list.
    // @return true if the item is listed, otherwise - false
    boolean contains(String item);

    // Looks for an index of the first item occurrence within the list
    // @return the index of the specified item or -1 if there isn't such an item within the list
    int indexOf(String item);

    // Looks for an index of the last item occurrence within the list
    // @return the index of the specified item or -1 if there isn't such an item within the list
    int lastIndexOf(String item);

    /** Gets an item specified with a position.
     *
     * @throws StringListNoSuchElementException if there isn't such an item
     * within list.
     * @param index of an item to be got
     * @return the specified item
     */
    String get(int index);

    /** Compares current StringList with another one.
     * @throws StringListNullPointerException if otherList is null.
     * @param otherList - a StringList instance to be compared with
     * @return true if the content of the list are exactly the same, otherwise false
     **/
    boolean equals(StringList otherList);

    /** @return the actual count of items within the list **/
    int size();

    /** @return true if there aren't any items within the list, otherwise - false **/
    boolean isEmpty();

    /** Removes all the items from the list **/
    void clear();

    /** @return a created String array with all the items of the list **/
    String[] toArray();
}