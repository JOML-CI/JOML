package java.text;

public abstract class NumberFormat extends Format {
    public final String format(double number) {
        return Double.toString(number);
    }
    public final String format(long number) {
        return Long.toString(number);
    }
}
