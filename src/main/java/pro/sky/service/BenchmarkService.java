package pro.sky.service;

import pro.sky.util.BenchmarkDenseIntList;
import pro.sky.util.BigDecimalFormatter;
import pro.sky.util.SortingAndSearchingInt;
import pro.sky.util.SortingAndSearchingIntImpl;

import java.util.Random;

public class BenchmarkService {
    //used to get index of value for search
    private final static Random random = new Random();
    //sort-n-search-implementing engine
    SortingAndSearchingInt sortAndSearchEngine = new SortingAndSearchingIntImpl();
    //Instance of ArrayList's replicating class, stores test data for bench tests
    BenchmarkDenseIntList testList = new BenchmarkDenseIntList();

    /** Performs set of benchmark tests for:
            - selection sort;
            - bubble sort;
            - insertion sort;
            and then benchmark tests for search a value within test array data:
            - linear search;
            - binary search 

            with printing results to the console.
    **/
    public  void performBenchmark() {
        //init sort-n-search-engine with test data
        sortAndSearchEngine.loadTestData(testList.toArray());
        //get random value to search from the data
        int valueToSearch = testList.get(random.nextInt(BenchmarkDenseIntList.TEST_DATA_SIZE));

        //benchmark tests for sorting algorithms
        System.out.println("\n*** Sorting ***");
        benchmarkSort(() -> sortAndSearchEngine.sortSelection(), "Selection sort");
        benchmarkSort(() -> sortAndSearchEngine.sortBubble(), "Bubble sort   ");
        benchmarkSort(() -> sortAndSearchEngine.sortInsertion(), "Insertion sort");

        //benchmark tests for search algorithms
        System.out.println("\n*** Search   ***");
        benchmarkSearch((value) -> sortAndSearchEngine.containsLinear(value), valueToSearch, "Linear search");
        benchmarkSearch((value) -> sortAndSearchEngine.containsBinary(value), valueToSearch, "Binary search");

    }

    public void benchmarkSort(CallableSorting callable, String sortName) {
            System.out.print("Sort: " + sortName + "\t\t\t");
            long time0 = System.currentTimeMillis();
            callable.sort();
            long time1 = System.currentTimeMillis();
            System.out.println("time = " + BigDecimalFormatter.format(time1 - time0) +  " ms.");
        }



    public void benchmarkSearch(CallableSearching callable, int value, String searchName) {
        System.out.print("Search: " + searchName + "\t\t\t");
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
