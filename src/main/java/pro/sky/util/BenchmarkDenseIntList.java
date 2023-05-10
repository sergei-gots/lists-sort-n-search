package pro.sky.util;

import java.util.Random;

public class BenchmarkDenseIntList extends DenseIntList {

    public final static int TEST_DATA_SIZE = 100_000;
    private final static Random random = new Random();

    public BenchmarkDenseIntList() {
        super(100_000);
        generateNewTestData();
    }

    private void generateNewTestData() {
        clear();
        for (int i = 0; i < TEST_DATA_SIZE ; i++) {
            add(random.nextInt());
        }
    }
}
