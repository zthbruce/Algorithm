package util;

import java.util.*;
import java.util.Collection;

/**
 * ArrayList is common used
 * 实现一遍
 * <E>表示泛型
 * AbstractList是对接口List的实现
 * List继承与Collection接口
 * ArrayList底层使用数组实现，但和数组的最大区别在于可扩展
 *
 * 方法主要实现增删改查
 * 该类是线程不安全
 */
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    /**
     * 序列化所需ID
     * 用于序列化版本控制，如果不同版本的类希望兼容，那么versionUID必须相同
     * 如果不希望兼容，那么versionUID设置为不同
     * mysql中的乐观锁也是利用version控制
     */
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 容量，默认初始化为10
     */
    private static final int DEFAULT_CAPACITY = 10;



    /**
     * 底层使用数组实现
     * 为什么使用transient？意义在于不需要在序列化时存储所有元素，因为ArrayList是动态扩展
     * 实际的元素使用往往远小于size，只需要序列化使用的元素即可
     * 使用transient搭配writeObject和readObject使用
     */
    transient Object[] elementData;

    /**
     * 空数组定义
     */
    private static final Object[] EMPTY_ELEMENT_DATA = {};


    /**
     * 默认构造器的空数组定义
     * 是为了满足不同的定义方式有不同的扩展策略
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENT_DATA = {};

    /**
     * ArrayList大小
     */
    private int size;


    /**
     * 构造器
     * @param initialCapacity 初始化大小
     * @throws IllegalArgumentException if the initialCapacity is negative
     */
    public ArrayList(int initialCapacity){
        if(initialCapacity > 0){
            this.elementData = new Object[initialCapacity];
        }else if(initialCapacity == 0){
            this.elementData = EMPTY_ELEMENT_DATA;
        }else{
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }


    /**
     * default constructor
     * 使用默认空数组，以区别ArrayList(0)的方式构造
     */
    public ArrayList(){
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENT_DATA;
    }

    /**
     * 以Collection作为参数的constructor
     * @param c Collection Object
     */
    public ArrayList(Collection<? extends E> c){
        elementData = c.toArray(); // 底层数组
        // size = elementData 写法可以一学
        if((size = elementData.length) > 0){
            // toArray may not return Object[], why? Tell me
            // getClass是对象的方法(反射机制)，而class是类的属性
            if(elementData.getClass() != Object[].class){
                // 知识点Array.copyOf用法，不是完全的深拷贝
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        }else{
            this.elementData = EMPTY_ELEMENT_DATA; // 空数组
        }
    }

    /**
     * 将ArrayList的容量大小缩小至当前size
     */
    public void trimToSize(){
        modCount++; //
        if(size < elementData.length){
            elementData = (size == 0)
                    ? EMPTY_ELEMENT_DATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 为ArrayList扩容，以满足数据插入需求
     * minCapacity参数是为了扩容可操作性更强，可以指定最小的Capacity
     */
    public void ensureCapacity(int minCapacity){
        // 最小扩充量
        // 判断是不是以无参数方式构造的类
        int minExpand = (elementData == DEFAULTCAPACITY_EMPTY_ELEMENT_DATA)
                ? DEFAULT_CAPACITY
                : 0;
        // 如果还没有默认的大，那么就按照内部更新即可
        // 如果比内部大，那么就按照minCapacity
        if(minCapacity > minExpand){
            ensureExplicitCapacity(minCapacity);
        }
    }


    /**
     * 内部扩容
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity){
        if(elementData == DEFAULTCAPACITY_EMPTY_ELEMENT_DATA){
            // 取两者中较大的元素
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }


    /**
     * 扩容函数
     * @param minCapacity
     */
    private void ensureExplicitCapacity(int minCapacity){
        modCount++;
        // a > b 底层是由 a - b > 0决定，所以这是一种优化细节
        if(minCapacity - elementData.length > 0){
            grow(minCapacity);
        }
    }

    /**
     * ArrayList的最大size
     * 设置这个数量主要目的在于预防OOM
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8; // why?



    private void grow(int minCapacity){
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 每次增长当前的一半
        if(newCapacity < minCapacity){
            newCapacity = minCapacity;
        }
        // 如果超出了最大限制
        if(newCapacity > MAX_ARRAY_SIZE){
            newCapacity = hugeCapacity(minCapacity);
        }
    }

    /**
     * 超大级别扩容
     * 如果传入的minCapacity > MAX_ARRAY_SIZE，则取Integer的最大值
     * @param minCapacity 最小容量
     * @return the capacity grow up to
     * 扩容的最大值为Integer.MAX_VALUE
     */
    private int hugeCapacity(int minCapacity){
        if(minCapacity < 0){
            throw new OutOfMemoryError();
        }
        return minCapacity > MAX_ARRAY_SIZE
                ?Integer.MAX_VALUE
                :MAX_ARRAY_SIZE;
    }

    /**
     * return the number of elements in the list
     * @return the number of elements in the list
     */
    public int size(){
        return size;
    }


    /**
     * 判断是否为空
     * @return true if no element in this list
     */
    public boolean isEmpty(){
        return size == 0;
    }


    /**
     * 返回找到的第一个对象的位置
     */
    public int indexOf(Object o){
        if(o == null){
            for(int i = 0; i < size; i++){
                if(elementData[i] == null){
                    return i;
                }
            }
        }else{
            for(int i = 0; i < size; i++){
                // 两个对象是否相同，使用equals进行比较
                if(elementData[i].equals(o)){
                    return i;
                }
            }
        }
        return -1; // if not found
    }

    /**
     * 判断是否包含
     */
    public boolean contains(Object o){
        return indexOf(o) >= 0; // not -1 if founded
    }


    /**
     * 获取出现的最后一个位置
     * 只需从后往前遍历即可
     */
    public int lastIndexOf(Object o){
        if(o == null){
            for(int i = size - 1; i >= 0; i--){
                if(elementData[i] == null){
                    return i;
                }
            }
        }
        else{
            for(int i = size - 1; i >= 0; i--){
                if(elementData[i].equals(o)){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取一份list的浅拷贝，对于base type 来说，就是深拷贝，但是对于对象来说，即为浅拷贝
     * 因为list中的对象只是引用的复制
     * @return
     */
    public Object clone(){
        try{
            // 直接使用clone时只是引用的拷贝
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size); // 拷贝数组元素，这是非常关键
            return v; // 只有一个元素
        }catch(CloneNotSupportedException e){
            throw new InternalError(e); // 系统内部错误
        }
    }


    /**
     * 将其转化为数组, 无数据类型
     * @return
     */
    public Object[] toArray(){
        return Arrays.copyOf(elementData, size);
    }

    /**
     * 将其转化为数组a的类型，注意泛型的写法
     * @param a 类型数组
     * @param <T> 泛型
     * @return T[]
     */
    // 这个方法有问题，a[size] = null 是什么意思？
    // unchecked
    public <T> T[] toArray(T[] a){
        // 如果a的长度比size小，注意新长度确实是size
        if(a.length < size){
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        // why use this code ?
        if(a.length > size)
            a[size] = null;
        return a;
    }


    // position Access Operation
    // unchecked
    E elementData(int index){  return (E) elementData[index];}


    // 边界检查模块
    /**
     * 边界检查
     * 一旦越界则抛出异常
     * @param index 位置索引
     */
    private void rangeCheck(int index){
        if(index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 错误信息，单一职务原则
     * @param index
     * @return
     */
    private String outOfBoundsMsg(int index){
        return "Index: " + index + ", Size: " + size;
    }

    /**
     * 查询index所在位置的值
     * @param index specified position index
     * @return the element of the specified position in the list
     */
    public E get(int index){
        rangeCheck(index); // 边界检查
        return elementData(index);
    }


    /**
     * 改变index所在位置的值
     * Replaces the element at specified position in this list, with specified element
     * @param index
     * @return the oldValue at specified position in this list
     */
    public E set(int index, E element){
        rangeCheck(index); // 边界检查

        E oldValue = elementData(index);
        elementData[index] = element;
        return  oldValue;
    }


    /**
     * 往尾部增加位置
     * @param element
     * @return true if add successfully
     */
    public boolean add(E element){
        ensureCapacityInternal(size + 1);
        elementData[size++] = element;
        return true;
    }


    /**
     * 往特定位置上增加元素，其余元素往后移动
     * @param index
     * @param element
     */
    public void add(int index, E element){
        rangeCheckForAdd(index); // 边界检查

        ensureCapacityInternal(size + 1);
        // 小技巧，将index之后的元素进行移动，可以采用System.arrayCopy的方式进行
        // 注意System.arrayCopy方法拷贝自身时，会先将index~size-index之间的数据拷贝到一个临时数组
        // 然后进行拷贝, 效果等同于以下注释了的代码，注意从右往前进行替换
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
//        for(int i = size; i > index; i++){
//            elementData[i] = elementData[i-1];
//        }
        elementData[index] = element;
        size ++;
    }

    /**
     * 插入的位置如果必须在0~size-1之间取值
     * @param index
     */
    private void rangeCheckForAdd(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }


    /**
     * 删除特定位置的元素，然后移动位置
     * @param index specified position
     * @return oldValue of specified position
     */
    //? 如果index为负数呢/
    public E remove(int index){
        rangeCheck(index);

        modCount++; // 修改次数

        // 如果index为负数，那么此处就会报错，这么一来rangeCheck是不是有点多余，因为这里会报错
        // modCount应该是只要操作了
        E oldValue = elementData(index);

        int numMoved = size - index + 1;
        // if numMoved == 0 表示index = size - 1即最后一个位置
        // 确定需要移动多少位
        if(numMoved > 0){
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        elementData[--size] = null; // 往前移位，可以让GC进行回收
        return oldValue;
    }

    /**
     * 快速删除
     * @param index
     */
    private void fastRemove(int index){
        modCount++;
        int numMoved = size - index + 1;
        if(numMoved > 0){
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    /**
     * 删除出现的第一个元素
     * @param o
     * @return
     */
    public boolean remove(Object o){
        // why not use indexOf?
        // 因为index还得判断一下是不是-1，然后进行删除，从中也可以看出来jdk对细节的要求
        if(o == null){
            for(int index = 0; index < size; index++){
                if(elementData[index] == null){
                    fastRemove(index);
                    return true;
                }
            }
        }else{
            for(int index = 0; index < size; index++){
                if(elementData[index].equals(o)){
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * remove all of the elements from the lists
     */
    public void clear(){
        modCount++;
        for(int i = 0; i < size; i++){
            elementData[i] = null; // 使得引用不再指向对象
        }
        size = 0; //修改size
    }

    /**
     * 将某个集合的所有元素都添加到当前list的最右边
     * @param c
     * @return true if c has element insert into the list
     */
    public boolean addAll(Collection<? extends E> c){
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0; // 如果不为空，则认为是添加
    }

    /**
     * 将集合的所有元素插到某个特定的位置
     * @param index 特定位置索引
     * @param c 集合
     * @return true if add c successfully
     */
    public boolean addAll(int index, Collection<? extends E> c){
        rangeCheckForAdd(index); // 检查是否越界

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);


        int numMoved = size - index;
        if(numMoved > 0)
            // 将原先的index ~ size - 1这部分数据，移动至index + numNew ~ size + numNew - 1，空出numNew个位置
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);

        // 然后将a拷贝至index ~ index + numNew - 1
        System.arraycopy(a, 0, elementData, index, numNew);

        // 更新size
        size += numNew;
        return numNew != 0;
    }

    /**
     * 删除从fromIndex(包含)到 toIndex(不包含)之间的数据
     * @param fromIndex 开始位置
     * @param toIndex 结束位置
     */
    protected void removeRange(int fromIndex, int toIndex){
        // 关键在于将toIndex ~ size - 1之间的数据复制到fromIndex，然后将尾部的数据设为null即可
        int numMoved = size - toIndex; // size - 1 - toIndex + 1 = sie - toIndex
        System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

        int newSize = size - (toIndex - fromIndex);
        for(int i = newSize; i < size; i++){
            elementData[i] = null;
        }
        size = newSize; // 更新size值
        modCount++; // 更新修改次数
    }


    /**
     * 实现了AbstractList的removeAll方法
     * 将c中包含的元素全部删除
     * @param c 集合元素
     * @return true if remove successfully
     */
    public boolean removeAll(Collection<?> c){

        Objects.requireNonNull(c); // 要求不为null
        return batchRemove(c, false);
    }


    /**
     * 将c中包含的元素保留下, 其余删除
     * @param c
     * @return
     */
    public boolean retainAll(Collection<?> c){
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }


    /**
     * 将列表批量删除，具体策略取决于是保留还是删除
     * @param c 集合元素
     * @param complement 如果为true: 表示只保留c中的元素，否则删除c中的元素
     * @return true 如果List经过修改
     */
    public boolean batchRemove(Collection<?> c, boolean complement) {
        // init
        // why use this code?
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false; // 是否修改
        try {
            for (; r < size; r++) {
                // 取决于complement: true or false
                //
                if (c.contains(elementData[r]) == complement) {
                    elementData[w++] = elementData[r++];
                }
            }

        } finally {
            // 如果contains出错，可能会导致r没走完，走完r=size
            // 将剩余的进行拷贝到w的后面
            if (r < size) {
                // 将r~size-1的元素进行拷贝到w之后
                System.arraycopy(elementData, r, elementData, w, size - r);
                w += size - r; // 之后的不进行判断
            }

            // 将剩余的进行赋值，这相当于有了修改
            if (w < size) {
                for (int i = w; i < size; i++) {
                    elementData[i] = null; // lead to GC
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }


    /**
     * 序列化一个对象，将对象转化为流
     * 之前的transient的作用就体现出来了，学习一下别人是怎么序列化的
     * @param s 输出流
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        int expectedModCount = modCount; // 为了预防多线程问题
        s.defaultWriteObject(); // 这句是啥意思？

        // write size info
        // 转化为字节序列
        s.writeInt(size);

        // write out all elements
        // 将
        for(int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }

        // 转化为字节序列
        if(modCount != expectedModCount) {
            throw new ConcurrentModificationException(); // ArrayList非线程安全
        }
    }


    /**
     * 从字节流中获取对象
     * @param s 输入流
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException,  ClassNotFoundException{
        elementData = EMPTY_ELEMENT_DATA;

        s.defaultReadObject(); // read stream to Object

        // Read in capacity
        s.readInt(); // ignored

        if(size > 0){
            // like clone process
            ensureCapacityInternal(size);
            Object[] a = elementData;
            for(int i = 0; i < size; i++){
                a[i] = s.readObject(); // 从字节流中读出对象
            }
        }
    }



































}
