//package util;
//
//
//import java.util.*;
//import java.util.Collection;
//
///**
// * LinkedList是链表的实现
// * 是非常常用的一种数据结构，此处自己实现一遍
// * <E>是泛型的意思
// * LinkedList是双向链表，存在first和last指针
// * 同样为非线程安全
// *
// */
//public class LinkedList<E> extends AbstractSequentialList<E>
//    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
//{
//    // 数组大小
//    transient int size = 0; // 不需要进行序列化
//
//    // 申明私有类
//    // 双向链表的基础
//    private static class Node<E> {
//        E item; // 元素
//        Node<E> next;
//        Node<E> prev;
//
//        // 构造该节点的方式
//        private Node(Node<E> prev,  E e, Node<E> next){
//            this.prev = prev;
//            this.item = e;
//            this.next = next;
//        }
//    }
//
//
//
//    /**
//     * 头指针
//     * 恒满足: (first == null && last == null ) ||
//     *          first.prev == null && first.item != null
//     */
//    transient Node<E> first;
//
//
//    /**
//     * 尾指针
//     * 恒满足: (first == null && last == null) ||
//     *          (last.next != null && last.item != null)
//     */
//    transient Node<E> last;
//
//
//    /**
//     * constructor
//     * 空列表构造器
//     */
//    public LinkedList(){}
//
//
//    /**
//     * 构造器
//     * 先清空，然后再加入
//     * @param c 传入特定集合形成一个列表
//     * @throws NullPointerException 如果这个c is null
//     */
//    public LinkedList(Collection<? extends E> c){
//        this();
//        addAll(c);
//    }
//
//
//    /**
//     * 将元素e设置为链表的首位元素
//     * 生成一个新结点，新结点的next为当前的first，
//     * 当前first的prev为新结点
//     * 设新结点为first
//     * @param e
//     */
//    private void linkFirst(E e){
//        final Node<E> f = first; // 暂做保存
//        final Node<E> newNode = new Node<>(null, e, f);
//        first = newNode;
//        // 取属性时一定要记住判断是否可能为null
//        if(f != null){
//            f.prev = first;
//        }
//        else{
//            last = newNode;
//        }
//
//        // 修改次数等指标修改
//        size++;
//        modCount++;
//    }
//
//
//    /**
//     * 把e放在尾巴上
//     * 只要将last.next = 当前节点
//     * 当前节点.prev = last即可
//     * @param e
//     */
//    void linkLast(E e){
//        final Node<E> l = last;
//        final Node<E> newNode = new Node<>(last, e, null);
//        last = newNode;
//        if(l != null){
//            // 这句话是在l不为null的情况下才好使
//            l.next = newNode;
//        }
//        else{
//            first = newNode;
//        }
//        size++;
//        modCount++;
//    }
//
//    /**
//     * 将元素插入到节点succ之前
//     * @param e 元素节点
//     * @param succ 后驱节点, 原则上必须是不为null的节点
//     */
//    void linkedBefore(E e, Node<E> succ){
//        // 假设succ不为空
//        assert succ != null : "node should not be null"; // 如果在上游控制，那么此处可以进行注释
//        final Node<E> pred = succ.prev; // 前驱节点，先保存着
//        final Node<E> newNode = new Node<>(pred, e, succ);
//        if(pred != null){
//            pred.next = newNode;
//        }else{
//            first = newNode;
//        }
//        succ.prev = newNode;
//
//        size++;
//        modCount++;
//    }
//
//    /**
//     * 将头结点从链表中删除
//     * @param f first Node
//     * @return first的值
//     */
//    private E unlinkFirst(Node<E> f){
//        assert f == first && f != null: "not invalid call";
//        final E e = f.item; // 使用item作为数值，got it
//        final Node<E> next = f.next; // 下一个节点
//        f.item = null;
//        f.next = null; // 将其设置为null，有助于GC过程
//
//        first = next; // 将下一个节点设置为
//        if(next == null){
//            last = null;
//        }else{
//            next.prev = null; // 首节点
//        }
//
//        size--;
//        modCount++;
//        return e;
//    }
//
//
//    /**
//     * 将尾节点从链表中删去
//     * @param l last Node
//     * @return 最后节点的值
//     */
//    private E unlinkLast(Node<E> l){
//        // as
//        assert l == last && l != null : "not invalid call";
//
//        final E e = l.item;
//        final Node<E> pred = l.prev;
//        l.prev = null;
//        l.item = null; // help GC
//        last = pred;
//        if(pred != null)
//            pred.next = null;
//        else
//            first = null; // 如果pred为null说明当前为first
//
//        size--;
//        modCount++;
//        return e;
//
//    }
//
//    /**
//     * 将节点x从链表
//     * @param x
//     * @return
//     */
//    E unlink(Node<E> x){
//        assert x != null : "x should not be null";
//        final E e = x.item;
//        final Node<E> pred = x.prev; //
//        final Node<E> succ = x.next;
//
//        x.prev = null; // 能不能这么用？
//        x.item = null; // 指向的指针发生变化
//        x.next = null; // help GC
//
//        // 头结点可能发生变化
//        if(pred != null)
//            pred.next = succ;
//
//        else
//            first = succ; // 头结点发生变化
//
//        // 尾节点可能发生变化
//        if(succ != null)
//            succ.prev = pred;
//        else
//            last = pred;
//
//        size--;
//        modCount++;
//        return e;
//    }
//
//
//    // 类似于队列
//    // 查询功能
//    /**
//     * 查询链表的首元素
//     * @return first element
//     */
//    public  E getFirst(){
//        final Node<E> f = first;  // 先获取first元素，以防止途中发生改变，导致后面发生错误
//        if(f == null)
//            throw new NoSuchElementException();
//        return f.item;
//
//        // @? 为什么不这样写？？？
//        // 这和多线程有关系，如果在读取中发生了删除头结点的操作，那么就会出现幻读
//        // 如果在f==null判断完成之后，first成了null，那么f.item就会出错了
//        // 学习到了!
////        if(first == null)
////            throw new NoSuchElementException();
////        return first.item;
//
//    }
//
//
//    /**
//     * 查询链表的尾元素
//     * @return last element
//     */
//    public E getLast(){
//        final Node<E> l = last;
//        if(l == null)
//            throw new NoSuchElementException();
//        return l.item;
//    }
//
//
//    // 删除功能
//    /**
//     * 删除头结点
//     * @return first ele
//     */
//    public E reomveFirst(){
//        // 先获取首位置
//        final Node<E> f = first;
//        if(f == null)
//            throw new NoSuchElementException();
//        return unlinkFirst(f); // 将头结点删除
//    }
//
//
//    /**
//     * 删除尾节点
//     * @return last ele
//     */
//    public E removeLast(){
//        final Node<E> l = last;
//        if(l == null)
//            throw new NoSuchElementException();
//        return unlinkLast(l);
//    }
//
//
//    // 增加功能
//
//    /**
//     * 增加队首元素
//     * @param e
//     */
//    public void addFirst(E e){
//        linkFirst(e);
//    }
//
//    /**
//     * 增加队尾元素
//     * @param e
//     */
//    public void addLast(E e){
//        linkLast(e);
//    }
//
//
//    /**
//     * 获取索引的第一个位置
//     * @param o
//     * @return
//     */
//    public int indexOf(Object o){
//        int index = 0;
//        if(o == null){
//            // 遍历链表
//            for(Node<E> x = first; x != null; x = x.next){
//                if(x.item == null)
//                    return index;
//                index++; // 增加索引
//            }
//        }else{
//            // 遍历链表
//            for(Node<E> x = first; x != null; x = x.next){
//                // 此处便是多态的体现，o的方法是子类的方法
//                if(o.equals(x.item))
//                    return index;
//                index++;
//
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * 是否包含
//     * 可以包含null的操作
//     * @param o
//     * @return
//     */
//    public boolean contains(Object o){
//        return indexOf(o) != -1;
//    }
//
//
//    /**
//     *
//     * return the number of element of list
//     * @return
//     */
//    public int size() {return size;}
//
//
//
//    /**
//     * 超过边界的提示消息
//     * @param index 位置索引
//     * @return 提示消息
//     */
//    private String outOfBoundsMsg(int index){
//        return "Index: "+index+", Size: "+size;
//    }
//
//
//    /**
//     * 查数据的边界异常检验
//     * @param index 位置索引
//     * @return
//     */
//    private boolean isElementIndex(int index){
//        return index >= 0 && index < size;
//    }
//
//    /**
//     * 该位置是否可插入，之所以要单独成一个特有的函数，是为了和isElementIndex相区别
//     * 对应查数据时，采用isElementIndex
//     * @param index
//     * @return
//     */
//    private boolean isPositionIndex(int index){
//        return index >= 0 && index <= size;
//    }
//
//
//    /**
//     * 位置检验
//     * @param index
//     */
//    private void checkPositionIndex(int index){
//        if(!isPositionIndex(index)){
//            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
//        }
//    }
//
//    /**
//     * 获取对应索引下的所在位置
//     * 对于链表来说，查找的速率是O(N)
//     * 为了最大限度的提高查找效率，可以充分利用双向链表的特点
//     * 如果在前一半的位置，则从头开始查；如果在后一半的位置，那么从尾部开始查找
//     * @param index
//     * @return index位置的node (0表示第一个)
//     */
//    Node<E> node(int index){
//        if(index < (size >> 1)){
//            Node<E> x = first;
//            for(int i = 0; i < index; i++)
//                x = x.next; // 使用后向节点
//            return x;
//        }else{
//            Node<E> x = last;
//            // 可以使用实例法
//            for(int i = size - 1; i > index; i--){
//                x = x.prev; // 使用前向节点
//            }
//            return x;
//        }
//    }
//
//
//    /**
//     * 默认将集合添加至列表的尾部
//     * @param c 集合元素
//     * @return
//     */
//    public boolean addAll(Collection<? extends E> c){
//        return addAll(size, c);
//    }
//
//    /**
//     * 在index的位置上，增加一个集合的数据
//     * @param index 位置
//     * @param c 集合数据
//     * @return {@code true} 如果成功加入
//     * @throws NullPointerException if the specified
//     */
//    public boolean addAll(int index, Collection<? extends E> c){
//        // 边界检验
//        checkPositionIndex(index);
//
//        // 这里会抛出异常，如果c为null
//        Object[] a = c.toArray();
//
//        int numNew = a.length;
//        // 如果长度为0，那么没发生改变
//        if(numNew == 0){
//            return false;
//        }
//
//        // pred表示前驱节点
//        Node<E> pred, succ;
//        // 如果是放在了
//        if(index == size){
//            succ = null; // 后驱节点
//            pred = last; // 前驱节点为尾节点
//        }else{
//            succ = node(index); // 最后用于放在插入的后面
//            pred = succ.prev;
//        }
//
//        // 遍历collection节点
//        // 添加当前节点的prev，和上一个节点的next
//        for(Object o : a){
//            E e = (E) o; // 利用父类进行转换
//            // 转换为Node接上
//            Node<E> newNode = new Node<>(pred, e, null);
//            // 注意头结点发生的变化
//            // 如果前向节点为null，说明是第一个节点，那么first就会发生变化
//            if(pred == null)
//                first = newNode;
//            else
//                pred.next = newNode;
//            pred = newNode; // 将当前节点变为上一个节点
//        }
//
//        // 为添加的最后一个节点
//        // 如果后驱节点为null,那么index为尾节点
//        // 此时尾节点last会发生变化
//        if(succ == null){
//            last = pred;
//        }else{
//            pred.next = succ;
//            succ.prev = pred; // 如果succ为空，是什么情况，就是没有后驱节点了
//        }
//
//        // 状态量发生变化
//        size += numNew;
//        modCount++;
//        return true;
//    }
//
//
//
//
//
//
//
//
//
//
//}
//
