package pro.sky.util;

import pro.sky.util.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @implNote IntegerList with all the valued items placed at the head of,
 * i.e. in case the item is to be removed all the rest content will be shifted
 * one position to the head.
 *
 * **/
public class DenseIntegerList implements IntegerList {
    private int capacity;
    private boolean expandable = false;
    private final static double CAPACITY_INCREASE_MULTIPLICITY = 1.5;
    private final static int DEFAULT_CAPACITY = 10;
    int count;
    int[] items;

    public DenseIntegerList() {
        this(DEFAULT_CAPACITY);
    }

    public DenseIntegerList(DenseIntegerList source) {
        this.expandable = source.expandable;
        this.capacity = source.capacity;
        this.count = source.count;
        items = new int[capacity];
        IntStream.range(0, count).forEach(i -> items[i] = source.items[i]);

    }

    public DenseIntegerList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity should be a natural number");
        }
        this.capacity = capacity;
        this.items = new int[capacity];
        this.count = 0;
    }

    public DenseIntegerList(int capacity, boolean expandable) {
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
    public Integer add(Integer item) {
        return add(count, item);
    }

    /** @throws ListIndexOutOfBoundsException
     * if the index points to an area outside the valued data
     **/
    @Override
    public Integer add(int index, Integer item) {
        validateIndexWhileAdd(index);

        for(int i = count; i > index; i--) {
            items[i] = items[i-1];
        }
        items[index] = item;
        count++;
        return item;
    }

    private void validateIndexWhileAdd(int index) {
        if (index < 0 || index > count) {
            throw new ListIndexOutOfBoundsException(index);
        }

        if (count < capacity) {
            return;
        }
        if(!expandable) {
            throw new ListIsFullException();
        }
        if(capacity < 1) {
            capacity = 1;
            items = new int[capacity];
            return;
        }
        capacity = (int)((double)(capacity) * CAPACITY_INCREASE_MULTIPLICITY);
        int[] commodiousList  = new int[capacity];
        System.arraycopy(items, 0, commodiousList, 0, items.length);
        items = commodiousList;
    }

    private void validateIndexIfDoesExist(int index) {
        if (index < 0 || index >= count) {
            throw new ListIndexOutOfBoundsException(index);
        }
    }

    /** @throws ListIndexOutOfBoundsException
     * if the index points to an area outside the valued data
     **/
    @Override
    public Integer set(int index, Integer item) {
        validateIndexWhileAdd(index);

        items[index] = item;
        if(index == count) {
            count++;
        }
        return item;
    }

     @Override
    public Integer remove(Integer item) {
        int index = indexOf(item);
        if(index < 0) {
            throw new ListNoSuchElementException(item);
        }
        return removeByIndex(index);
    }

    @Override
    public Integer removeByIndex(int index) {
        validateIndexIfDoesExist(index);
        int item = items[index];
        count--;
        System.arraycopy(items, index+1, items, index, count-index);
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        return indexOf(item) >= 0;
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < count; i++) {
            if (items[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = count-1; i >= 0; i--) {
            if (items[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndexIfDoesExist(index);
        return items[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if(otherList == null) {
            throw new ListNullPointerException();
        }
        if(count != otherList.size()) {
            return false;
        }
        for(int i = 0; i < count; i++) {
            if(items[i] !=  otherList.get(i)) {
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
    public int[] toArray() {
        int[] array = new int[count];
        System.arraycopy(items, 0, array, 0, count);
        return array;
    }

    public int[] toArray(int index) {
        if(index > count) {
            throw new ListIndexOutOfBoundsException(index);
        }
        if(index == count) {
            return new int[0];
        }
        int[] array = new int[count-index];
        System.arraycopy(items, index, array, 0, count-index);
        return array;
    }

    public List<Integer> toList() {
        return toList(0);
    }

    public List<Integer> toList(int index) {
        int[] array = toArray(index);
        return Arrays.stream(array).boxed().collect(Collectors.toCollection(() -> new ArrayList<>(array.length)));
    }
}
