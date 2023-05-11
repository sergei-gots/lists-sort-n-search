package pro.sky.util;

import pro.sky.util.exception.ArrayIsNotSortedException;

import java.util.Arrays;

public class SortingAndSearchingIntImpl implements SortingAndSearchingInt {
    private final IntegerList list;
    private int[] array;

    public SortingAndSearchingIntImpl(IntegerList testData) {
        list = testData;
    }


    private void swapElements(int indexA, int indexB) {
        int tmp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = tmp;
    }

    @Override
    public void sortBubble() {
        resetArray();
        int n = array.length - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (array[j] > array[j + 1]) {
                    swapElements(j, j + 1);
                }
            }
        }
    }

    private void resetArray() {
        this.array = list.toArray();
    }

    @Override
    public void sortSelection() {
        resetArray();
        for (int i = 0; i < array.length - 1; i++) {
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
        resetArray();
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
    public void sortRecursiveQuickSort() {
        resetArray();
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private void swapElements(int[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    @Override
    public boolean containsLinear(int element) {
        return linearIndexOf(element) != -1;
    }

    @Override
    public int linearIndexOf(int element) {
        if (array == null) {
            array = list.toArray();
        }
        for (int i = 0; i < array.length; i++) {
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

        if (array == null) {
            throw new ArrayIsNotSortedException();
        }

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
