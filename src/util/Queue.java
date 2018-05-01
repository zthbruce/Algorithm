package util;

import java.util.*;

/**
 * 队列接口
 */
public interface Queue<E> extends Collection<E> {

    /**
     *
     * @param e element to add
     * @return {@code true}
     * @throws IllegalStateException if the element cannot be added at this time due to the restriction
     * queue should not contains null
     *
     */
    boolean add(E e);

    /**
     *
     * @param e element to add
     * @return {@code true} if element is add to queue, else {@code false}
     * if queue is full, it will return false and will not throw Exception
     */
    boolean offer(E e);


    /**
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E remove();


    /**
     *
     * @return the head of this queue
     * return null if this queue is empty
     */
    E poll();



}
