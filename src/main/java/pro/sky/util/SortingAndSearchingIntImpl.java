package pro.sky.util;

import java.util.Arrays;

public class SortingAndSearchingIntImpl  implements SortingAndSearchingInt  {
    private int[] array;

    public SortingAndSearchingIntImpl() {
    }

    @Override
    public void loadTestData(int[] array) {
        this.array = array;
    }

    private void swapElements(int indexA, int indexB) {
        int tmp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = tmp;
    }

    @Override
    public void sortBubble() {

        int n = array.length - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (array[j] > array[j + 1]) {
                    swapElements(j, j + 1);
                }
            }
        }
    }

    @Override
    public void sortSelection() {
        for (int i = 0; i < array.length-1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(i, minElementIndex);
        }
    }

    @Override
    public void sortInsertion() {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && array[j - 1] >= temp) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = temp;
        }
    }

    @Override
    public boolean containsLinear(int element) {
        return linearIndexOf(element) != -1;
    }

    @Override
    public int linearIndexOf(int element) {
        for (int i = 0; i< array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsBinary(int element) {
        return binaryIndexOf(element) != -1;
    }
    @Override
    public int binaryIndexOf(int element) {
        int min = 0;
        int max = array.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == array[mid]) {
                return mid;
            }

            if (element < array[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortingAndSearchingIntImpl that = (SortingAndSearchingIntImpl) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
