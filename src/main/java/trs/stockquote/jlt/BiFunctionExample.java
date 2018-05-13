package trs.stockquote.jlt;

import java.util.function.BiFunction;

public class BiFunctionExample {

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        int result = calculator.calc(
                ((a, b) -> (a / b)),
                3, 5);
        System.out.println(result);
    }
}

class Calculator {
    public Integer calc(BiFunction<Integer, Integer, Integer> bi, Integer i1, Integer i2) {

        return bi.apply(i1,i2);
    }
}
