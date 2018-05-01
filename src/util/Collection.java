package util;


/**
 * Collection is one of the most import interface in java
 * extend from Iterable
 * @param <E> is generic type of Iterable
 */
public interface Collection<E> extends Iterable<E> {
    // operations

    /**
     * Returns the number of element in collection
     * If collection contains more than Integer.MAX_VALUE, return Integer.MAX_VALUE
     * @return the number of elements in collection
     */
    int size();


    /**
     *
     * @return
     */
    boolean empty();

}
