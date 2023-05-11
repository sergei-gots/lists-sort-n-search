package pro.sky.service;

import pro.sky.util.BenchmarkDenseIntList;
import pro.sky.util.BigDecimalFormatter;
import pro.sky.util.SortingAndSearchingInt;
import pro.sky.util.SortingAndSearchingIntImpl;

import java.util.Random;

public class SortNSearchBenchmarkService {
    private final static Random random = new Random();

    private final BenchmarkDenseIntList testList = new BenchmarkDenseIntList();

    private final SortingAndSearchingInt sortAndSearchEngine = new SortingAndSearchingIntImpl(testList);


    public  void performBenchmark() {
        int valueToSearch = testList.get(random.nextInt(BenchmarkDenseIntList.TEST_DATA_SIZE));
        System.out.println("\n*** Sorting ***");
        benchmarkSort(sortAndSearchEngine::sortSelection, "Selection sort");
        benchmarkSort(sortAndSearchEngine::sortBubble, "Bubble sort   ");
        benchmarkSort(sortAndSearchEngine::sortInsertion, "Insertion sort");
        benchmarkSort(sortAndSearchEngine::sortRecursiveQuickSort, "Quick sort (recursive)");
        System.out.println("\n*** Search   ***");
        benchmarkSearch(sortAndSearchEngine::containsLinear, valueToSearch, "Linear search");
        benchmarkSearch(sortAndSearchEngine::containsBinary, valueToSearch, "Binary search");

    }

    public void benchmarkSort(CallableSorting callable, String sortName) {
            System.out.print("Sort: " + sortName + "\t\t\t\t");
            long time0 = System.currentTimeMillis();
            callable.sort();
            long time1 = System.currentTimeMillis();
            System.out.println("time = " + BigDecimalFormatter.format(time1 - time0) +  " ms.");
        }



    public void benchmarkSearch(CallableSearching callable, int value, String searchName) {
        System.out.print("Search: " + searchName + "\t\t\t\t");
        long time0 = System.currentTimeMillis();
        if(!callable.search(value)) {
            throw new IllegalArgumentException("There isn't such a value=" + value + " within array." );
        }
        long time1 = System.currentTimeMillis();
        System.out.println("time = " + BigDecimalFormatter.format(time1 - time0)  + " ms.");
    }

    interface CallableSorting {
        void sort();
    }

    interface CallableSearching {
        boolean search(int value);
    }
}
