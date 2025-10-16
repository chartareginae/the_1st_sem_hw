package hw_6;

public class Calculator<T extends Number> {
    public double sum(T a, T b) {
        return b.doubleValue() + a.doubleValue();
    }

    public double substract(T a, T b){
        return a.doubleValue() - b.doubleValue();
    }

    public double multiply(T a, T b){
        return a.doubleValue() * b.doubleValue();
    }

    public double divide(T a, T b){
        return a.doubleValue() / b.doubleValue();
    }

    public static void main(String[] args) {
        final Calculator<Integer> intCalc = new Calculator<>();
        final double result = intCalc.sum(5, 3); // 8.0

        final Calculator<Double> doubleCalc = new Calculator<>();
        final double div = doubleCalc.divide(10.0, 4.0); // 2.5
    }
}