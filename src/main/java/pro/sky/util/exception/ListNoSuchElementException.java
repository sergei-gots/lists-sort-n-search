package pro.sky.util.exception;

import java.util.NoSuchElementException;

public class ListNoSuchElementException extends NoSuchElementException {
    public ListNoSuchElementException(String item) {
        super("Item \"" + item + "\" is not listed in a StringList instance.");
    }

    public ListNoSuchElementException(int index) {
        super("Item with the index=" + index + " is not listed in a StringList instance.");
    }
}
