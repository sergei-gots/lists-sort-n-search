package pro.sky.java.util.exception;

public class DenseStringListAttemptToTouchEmptyAreaException extends IllegalArgumentException {
    public DenseStringListAttemptToTouchEmptyAreaException(int index) {
        super("Index=" + index + "points to an area left behind the valued one");
    }
}
