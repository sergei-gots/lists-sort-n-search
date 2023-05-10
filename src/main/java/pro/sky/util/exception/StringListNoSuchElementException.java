package pro.sky.util.exception;

import java.util.NoSuchElementException;

public class StringListNoSuchElementException extends NoSuchElementException {
    public StringListNoSuchElementException(String item) {
        super("Item \"" + item + "\" is not listed in a StringList instance.");
    }

    public StringListNoSuchElementException(int index) {
        super("Item with the index=" + index + " is not listed in a StringList instance.");
    }
}
