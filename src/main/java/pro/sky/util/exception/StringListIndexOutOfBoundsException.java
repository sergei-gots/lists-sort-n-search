package pro.sky.util.exception;

public class StringListIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public StringListIndexOutOfBoundsException(int index) {
        super("Attempt to operate an item with index = " +  index + " which points out of bounds.");
    }
}
