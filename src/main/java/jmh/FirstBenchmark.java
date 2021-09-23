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

    @Param({"32","1024","10000","100000","1000000"})
    public int size;

    public Random random = new Random(42);

    public Map<Integer, Integer> testSet;

    public HashMap<Integer, Integer> testMap;

    @Setup
    public void prepare() {
        Integer index = 100000000;
        testSet = new LinkedHashMap<Integer, Integer>(size);
        testMap = new HashMap<Integer, Integer>(size);
        for (int i = 0; i < size; i++) {
            Integer key = random.nextInt();
            Integer val = random.nextInt();
            testSet.put(key, val);
        }
    }

    @Benchmark
    public void hashMapFirstSetTest() {

        for(Map.Entry<Integer, Integer> entry : testSet.entrySet()) {
            Integer key = entry.getKey();
            Integer val = entry.getValue();

            testMap.put(key, val);
        }
    }

    @Benchmark
    public void hashMapSecondGetTest() {

        for(Integer key : testSet.keySet()) {
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
