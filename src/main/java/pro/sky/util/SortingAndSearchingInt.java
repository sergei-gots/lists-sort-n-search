package pro.sky.util;


public interface SortingAndSearchingInt {
    void sortBubble();

    void sortSelection();

    void sortInsertion();

    void sortRecursiveQuickSort();

    boolean containsLinear(int element);

    boolean containsBinary(int element);

    int linearIndexOf(int element);

    int binaryIndexOf(int element);

}
