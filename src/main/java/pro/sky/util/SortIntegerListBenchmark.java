package pro.sky.util;

public class SortIntegerListBenchmark extends SortableDenseIntegerLIst {
    private void generateTestData() {}
    public void benchmark() {
        trySort(() -> containsLinear(1), "linear search");
        trySort(() -> containsBinary(1), "binary search");
    }

    public long trySort(CallableSortable callable, String sortName) {
        System.out.println("sortName = " + sortName);
        generateTestData();
        long time0 = System.currentTimeMillis();
        boolean found =  callable.sort();
        long time1 = System.currentTimeMillis();
        System.out.println("time = " + (time1 - time0) + "ms");
        return time1 - time0;
    }

    interface CallableSortable {
        boolean sort();
    }
}
