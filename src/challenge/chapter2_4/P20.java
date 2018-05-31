package challenge.chapter2_4;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.Arrays;
import java.util.NoSuchElementException;

// 数据结构相关



class Heap{
    /**
     * 实现堆这种数据结构
     * 需要满足两种动作，插入和弹出(弹出的是当前最小值)
     * 优先队列就是堆这种数据结构可以实现
     * 堆最重要的就是堆化，即满足子节点>=父节点
     * 从而根节点必然是最小值
     *
     * 堆的底层使用数组作为存储的数据结构，这点类似于完全二叉树的结构
     * 父节点的索引为，那么左子树的节点索引为2i+1，右子树的节点索引为2i+2
     *
     * 堆中最重要的就是进行堆化，堆化就是父节点和子节点进行不断交换，使得整颗树满足堆的特性
     * 可以使用循环的方式，也可以使用递归的方式，核心步骤就是
     *
     */

    transient int[] elementData;

    private int size = 0; // 堆的尺寸大小


    // 常量定义
    private int[] EMPTY_ELEMENT_DATA = {};


    private int DEFAULT_CAPACITY = 10; // 默认尺寸为10

    private int MAX_CAPACITY = Integer.MAX_VALUE - 8; // 学习ArrayList的写法


    // constructor
    public Heap(){
        this.elementData = EMPTY_ELEMENT_DATA;
    }

    // constructor
    public Heap(int initialCapacity){
        // params check
        if(initialCapacity < 0)
            throw new IllegalArgumentException("Invalid Input");
        else if(initialCapacity == 0)
            this.elementData = EMPTY_ELEMENT_DATA;
        else
            this.elementData = new int[initialCapacity];
    }

    /**
     * 超大容量扩容
     * @param minCapacity
     * @return
     */
    private int hugeCapacity(int minCapacity){
        // params check
        if(minCapacity < 0)
            throw new IllegalArgumentException();
        return (minCapacity > MAX_CAPACITY) ?
                Integer.MAX_VALUE
                : MAX_CAPACITY;
    }
    /**
     * 进行扩容
     * 最大的长度为多少
     */
    private void grow(int minCapacity){
        int oldCapacity = elementData.length;
        int newCapacity = Math.max(oldCapacity + (oldCapacity >> 1), DEFAULT_CAPACITY);

        if(newCapacity - MAX_CAPACITY > 0){
            newCapacity = hugeCapacity(minCapacity);
        }

        // 使用Arrays.copyOf进行拷贝
        // 形成新的大数组
        elementData = Arrays.copyOf(elementData, newCapacity);
    }


    /**
     * 保证size满足要求，不会溢出
     * @param size
     */
    private void ensureCapacity(int size){
        // 如果所需size超过了elementData的大小
        // 则需要扩容
        if(size > elementData.length){
            grow(size);
        }
    }


    /**
     * 数组两元素进行交换
     * @param arr
     * @param i
     * @param j
     */
    private void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

//        /**
//         * 将以start开头，end结尾的数组元素作为堆进行堆化
//         * @param arr 数组
//         * @param start 开始索引
//         * @param end 结束索引
//         */
//        private void heapify(int[] arr, int start, int end){
//            // 从end开始，与父节点进行对比
//            int parentIndex = ((end + 1) >> 1) - 1; // 获取父节点
//
//            if(parentIndex < start) return; // 如果父节点超出了研究范围，则直接返回
//
//            // 如果父节点比当前节点小，那么进行交换
//            // 递归一层一层往上比较
//            if(arr[parentIndex] - arr[end] > 0)
//                swap(arr, parentIndex, end);
//                heapify(arr, 0, parentIndex);
//        }

    /**
     * 插入操作
     * 首先将该节点放置在数组最后的位置上
     * 父节点的索引为 (i+1 / 2) - 1，进行比较然后交换即可
     * 然后从end，与父节点进行交换即可，如果交换了，那么直接从
     * @param e 待插入元素
     */
    public void push(int e){
        // 保证容量足够
        ensureCapacity(size+1);

        System.out.println("当前size: " +  size);
        // 增加新元素
        elementData[size] = e;

        // 进行从下到上的堆化
        int index = size;
        // 使用循环的方式进行交换
        while(index > 0){
            // 获取父节点的值
            int parentIndex = (index - 1)  >> 1;

            if(elementData[parentIndex] - e <= 0) break; // 说明已经不需要进行交换

            // 交换后，然后往上层判断
            swap(elementData, parentIndex, index);
            index = parentIndex;
        }
        // 数组大小+1
        size++;
    }


    /**
     * 弹出最上层的元素
     * @return 最上层元素的值
     */
    public int pop(){
        // 判断是否有元素
        if(size <= 0)
            throw new NoSuchElementException("Empty Heap");

        // 将最上层元素和尾部元素交换, 尾部元素就是原size-1，简直完美
        // 并将size--
        swap(elementData, 0, --size);

        // 然后从最上层元素开始进行交换
        // 注意是进行交换，不会破坏子节点的结构
        int index = 0;

        // 然后只需要进行0~size-1的数组进行堆化即可
        // 只需要判断还是非叶子节点即可
        while(((index << 1) + 1) - size < 0){
            int left = (index << 1) + 1;
            int right = left + 1;
            int min = left;
            if(right < size && elementData[min] > elementData[right])
                min = right;
            // 确定儿子中的较小者
            // 如果index的值较大，说明需要交换
            // 否则，则不需要交换
            if(elementData[index] > elementData[min]){
                swap(elementData, index, min);
                index = min;
            }else{
                break;
            }
        }
        return elementData[size]; // 就是size的为尾部元素
    }

    public int size(){
        return size;
    }

}


public class P20 {

    // 测试程序
    public static void main(String[] args) {
        int[] test = {10, 3, 4, 2, 5, 7, 1};
        Heap h = new Heap(0);
        for(int t : test){
            h.push(t);
        }
        //
        while(h.size() > 0){
            System.out.print(h.pop() + " ");
        }
    }

}
