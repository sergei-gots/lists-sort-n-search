package pro.sky.util;

public interface SortingAndSearchingInt {
    void loadTestData(int[] array);
    void sortBubble();
    void sortSelection();
    void sortInsertion();

    boolean containsLinear(int element);
    boolean containsBinary(int element);

    int linearIndexOf(int element);
    int binaryIndexOf(int element);

}
