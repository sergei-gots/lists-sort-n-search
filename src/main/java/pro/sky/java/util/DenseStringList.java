package pro.sky.java.util;

import pro.sky.java.util.exception.*;

import java.util.stream.IntStream;

/** @implNote StringList with all the valued items placed at the head of,
 * i.e. in case the item is to be removed all the rest content will be shifted
 * one position to the head.
 *
 * **/
public class DenseStringList implements StringList {
    private int capacity;
    private boolean expandable = false;
    private final static int CAPACITY_INCREASE_MULTIPLICITY = 2;
    private final static int DEFAULT_CAPACITY = 10;
    private int count;
    private String[] items;

    public DenseStringList() {
        this(DEFAULT_CAPACITY);
    }

    public DenseStringList(DenseStringList source) {
        this.expandable = source.expandable;
        this.capacity = source.capacity;
        this.count = source.count;
        items = new String[capacity];
        IntStream.range(0, count).forEach(i -> items[i] = source.items[i]);

    }

    public DenseStringList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity should be a natural number");
        }
        this.capacity = capacity;
        this.items = new String[capacity];
        this.count = 0;
    }

    public DenseStringList(int capacity, boolean expandable) {
        this(capacity);
        this.expandable = expandable;
    }

    public int getCapacity() {
        return capacity;
    }
    public void makeExpandable() {
        expandable = true;
    }

    public void disableExpandable() { expandable = false; }

    public boolean isExpandable() {
        return expandable;
    }

    @Override
    public String add(String item) {
        validateCapacity();
        items[count++] = item;
        return item;
    }

    /** @throws pro.sky.java.util.exception.DenseStringListAttemptToTouchEmptyAreaException
     * if the index points to an area outside the valued data
     **/
    @Override
    public String add(int index, String item) {
        if (index < 0 || index > count) {
            throw new DenseStringListAttemptToTouchEmptyAreaException(index);
        }
        validateCapacity();
        for(int i = count; i > index; i--) {
            items[i] = items[i-1];
        }
        items[index] = item;
        count++;
        return item;
    }

    private void validateCapacity() {
        if (count < capacity) {
            return;
        }
        if(!expandable) {
            throw new StringListIsFullException();
        }
        if(capacity < 1) {
            capacity = 1;
            items = new String[capacity];
            return;
        }
        capacity *= CAPACITY_INCREASE_MULTIPLICITY;
        String[] commodiousList  = new String[capacity];
        System.arraycopy(items, 0, commodiousList, 0, items.length);
        items = commodiousList;
    }

    /** @throws pro.sky.java.util.exception.DenseStringListAttemptToTouchEmptyAreaException
     * if the index points to an area outside the valued data
     **/
    @Override
    public String set(int index, String item) {
        if (index < 0 || index > count) {
            throw new DenseStringListAttemptToTouchEmptyAreaException(index);
        }
        if (index == capacity) {
            validateCapacity();
        }
        items[index] = item;
        if(index == count) {
            count++;
        }
        return item;
    }

     @Override
    public String remove(String item) {
        int index = indexOf(item);
        if(index < 0) {
            throw new StringListNoSuchElementException(item);
        }
        return remove(index);
    }

    @Override
    public String remove(int index) {
        if(index < 0 || index >= capacity) {
            throw new StringListOutOfBoundsException();
        }
        if(index >= count) {
            throw new StringListNoSuchElementException(index);
        }
        String item = items[index];
        for(int i = index; i < count - 1; i++) {
            items[i] = items[i+1];
        }
        count--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) >= 0;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < count; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = count-1; i >= 0; i--) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        if(index < 0 || index >= capacity) {
            throw new StringListOutOfBoundsException();
        }
        if(index >= count) {
            throw new StringListNoSuchElementException(index);
        }
        return items[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if(otherList == null) {
            throw new StringListNullPointerException();
        }
        if(count != otherList.size()) {
            return false;
        }
        for(int i = 0; i < count; i++) {
            if(!items[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void clear() {
        count = 0;
    }

    @Override
    public String[] toArray() {
        String[] array = new String[count];
        System.arraycopy(items, 0, array, 0, count);
        return array;
    }

    public String[] toArray(int index) {
        if(index > count) {
            throw new DenseStringListAttemptToTouchEmptyAreaException(index);
        }
        if(index == count) {
            return new String[0];
        }
        String[] array = new String[count-index];
        System.arraycopy(items, index, array, 0, count-index);
        return array;
    }
}
