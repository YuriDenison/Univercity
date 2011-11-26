/*
 * IncorrectParameters.java
 *
 * Created on 3 Октябрь 2004 г., 19:54
 */

/**
 *
 * @author  Стаценко Владимир
 */
public class IncorrectParametersException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>IncorrectParameters</code> without detail message.
     */
    public IncorrectParametersException() {
        this("IncorrectParameters");
    }
    
    
    /**
     * Constructs an instance of <code>IncorrectParameters</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IncorrectParametersException(String msg) {
        super(msg);
    }
}
