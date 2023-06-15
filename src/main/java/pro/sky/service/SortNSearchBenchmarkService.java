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
        benchmarkSort(sortAndSearchEngine::sortSelection(), "Selection sort");
        benchmarkSort(sortAndSearchEngine::sortBubble(), "Bubble sort   ");
        benchmarkSort(sortAndSearchEngine::sortInsertion(), "Insertion sort");
        benchmarkSort(sortAndSearchEngine::sortRecursiveQuickSort, "Quick sort (recursive)");

        //benchmark tests for search algorithms
        System.out.println("\n*** Search   ***");
        benchmarkSearch(sortAndSearchEngine::containsLinear(value), valueToSearch, "Linear search");
        benchmarkSearch(sortAndSearchEngine::containsBinary(value), valueToSearch, "Binary search");

    }

    /** Invokes method sort()  of callasbleSortingEngine-instance,
     *  measures and prints time the method sort() takes,
     *  also prints the name of tested sorting algorithm.
     *  @param callableSortingEngine interface implementation (anonymous) with the specified invokation of sorting method.
     *  @param sortName name of sorting algorithm to be printed.
     **/
    public void benchmarkSort(CallableSorting callableSortingEngine, String sortName) {
            //print title with the name of sorting algorithm
            System.out.print("Sort: " + sortName + "\t\t\t");
            //note the time
            long time0 = System.currentTimeMillis();
            //perform sorting
            callableSortingEngine.sort();
            //stop measuring time
            long time1 = System.currentTimeMillis();
            //print elapsed time 
            System.out.println("time = " + BigDecimalFormatter.format(time1 - time0) +  " ms.");
        }

    /** Supposed to be implemented as anonymous class. 
     *  The only method sort is intended to perform sorting.
     **/
    interface CallableSorting {
        void sort();
    }

    /** Invokes method search(value) of callableSearchingEngine-instance,
     *  measures and prints time the method search(value) takes,
     *  also prints the name of tested sorting algorithm.
     *  @param callableSearchingEngine - interface implementation (anonymous) with the specified invokation of search method.
     *  @param value - value to search
     *  @param searchName - name of searching algorithm to be printed.
     *  @throws IllegalArgumentException if the value
     **/
    public void benchmarkSearch(CallableSearching callableSearching, int value, String searchName) {
        //print title with the name of searching algorithm
        System.out.print("Search: " + searchName + "\t\t\t");
        //measure start time
        long time0 = System.currentTimeMillis();
        //perform search
        if(!callableSearching.search(value)) {
            //if the value is not presented in the array of test data 
            //then throw an exception.
            throw new IllegalArgumentException("There isn't such a value=" + value + " within array." );
        }
        //stop measuring time
        long time1 = System.currentTimeMillis();
        //print elapsed time
        System.out.println("time = " + BigDecimalFormatter.format(time1 - time0)  + " ms.");
    }

    /** Supposed to be implemented as anonymous class. 
     *  The only method search is intended to perform search.
     **/
    interface CallableSearching {
        /** 
         * @param value - the value to search
         * @return true if the value is found. otherwise, false. 
         **/
        boolean search(int value);
    }
}
