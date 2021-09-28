package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class FirstBenchmark {

    @Param({"1000000"})
    private int size;

    private Random random = new Random(42);

    private Map<String, String> testSet;

    private HashMap<String, String> testMap;

    @Setup
    public void prepare() {
        Integer index = 100000000;
        testSet = new LinkedHashMap<String, String>(size);
        testMap = new HashMap<String, String>(size);
        for (int i = 0; i < size; i++) {
            index += 1;
            String key = index.toString();
            String val = "test-context";
            testSet.put(key, val);
        }
    }

    @Benchmark
    public void hashMapFirstSetTest() {

        for(Map.Entry<String, String> entry : testSet.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();

            testMap.put(key, val);
        }
    }

    @Benchmark
    public void hashMapSecondGetTest() {

        for(String key : testSet.keySet()) {
            testSet.get(key);
        }

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FirstBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }
}
