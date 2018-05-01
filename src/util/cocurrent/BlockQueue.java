package util.cocurrent;


import java.util.Queue;

/**
 * blockQueue interface
 * extends from queue in collection package
 * @param <E> (generic) the type of element held in this collection
 */
public interface BlockQueue<E> extends Queue<E>{
    /**
     *
     * @param e the element to add
     * @return {@code true} upon success
     */
    boolean add(E e);
}



