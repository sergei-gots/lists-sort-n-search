package pro.sky.util;

public class SortableDenseIntegerLIst  extends DenseIntegerList
                                       implements Sortable,
                                                Searchable  {
    private void swapElements(int indexA, int indexB) {
        int tmp = get(indexA);
        set(indexA, get(indexB));
        set(indexB, tmp);
    }

    @Override
    public void sortBubble() {

        int n = size() - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (get(j) > get(j + 1)) {
                    swapElements(j, j + 1);
                }
            }
        }
    }

    @Override
    public void sortSelection() {
        for (int i = 0; i < size()-1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < size(); j++) {
                if (get(j) < get(minElementIndex)) {
                    minElementIndex = j;
                }
            }
            swapElements(i, minElementIndex);
        }
    }

    @Override
    public void sortInsertion() {
        for (int i = 1; i <size(); i++) {
            int temp = get(i);
            int j = i;
            int value = get(j - 1);
            while (j > 0 && value >= temp) {
                set(j, value);
                j--;
            }
            set(j, temp);
        }
    }

    @Override
    public boolean containsLinear(int element) {
        for (int i = 0; i< count; i++) {
            if (items[i] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsBinary(int element) {
        int min = 0;
        int max = count - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == items[mid]) {
                return true;
            }

            if (element < items[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}
