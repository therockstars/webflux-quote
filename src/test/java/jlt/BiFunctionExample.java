package jlt;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

public class BiFunctionExample {

    @Test
    public void testBiFunction() {

        BiFunction<Integer, Integer, Integer> bfAdd = (x, y) -> x + y;
        BiFunction<Integer, Integer, Integer> bfMinus = (x, y) -> x - y;
        BiFunction<Integer, Integer, Integer> bfMultiply = (x, y) -> x * y;
        BiFunction<Integer, Integer, Integer> bfDivide = (x, y) -> x / y;

        Function<Integer, Integer> func = n->n*n;

        System.out.println(bfMinus.andThen(func).apply(5,2));

    }
}
