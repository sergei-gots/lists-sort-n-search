package pro.sky;

import pro.sky.service.SortNSearchBenchmarkService;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello Skypro!");
        System.out.println("Homework 2-16 'Sort-n-Search'");
        SortNSearchBenchmarkService benchmarkService = new SortNSearchBenchmarkService();
        benchmarkService.performBenchmark();
    }
}