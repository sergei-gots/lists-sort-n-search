package pro.sky;

import pro.sky.service.BenchmarkService;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello Skypro!");
        System.out.println("Homework 2-15 'Sort-n-Search'");
        BenchmarkService benchmarkService = new BenchmarkService();
        benchmarkService.performBenchmark();
    }
}