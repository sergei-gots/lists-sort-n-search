package pro.sky.util.exception;

public class ListIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public ListIndexOutOfBoundsException(int index) {
        super("Attempt to operate an item with index = " +  index + " which points out of bounds.");
    }
}
