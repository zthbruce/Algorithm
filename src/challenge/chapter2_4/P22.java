package challenge.chapter2_4;

// 优先队列问题



import java.util.*;

public class P22 {

    /**
     * Fence Pair问题
     * 要将一块很长的木板切割成N块，准备切成的木板的长度为L1, L2, ... LN, 未切割前的木板长度恰好为切割后的长度之和
     * 每次切断木板时，需要的开销为这块木板的长度。例如长度为21的模板要切成长度为5， 8， 8的三块模板。第一次切成13，8
     * 时，开销为21。第二次开销为13，合计开销为21+13=34。
     * 求出按照目标要求将木板切割完最小的开销是多少？
     * 1 <= N <= 20000
     * 0 <= Li <= 50000
     *
     * 和2.2中的问题P11一样，只不过Huffman编码树的算法，每次需要当前最小的两块木板进行合并
     * 在2.2中使用遍历的方式搜索长度最小的两个木板，每轮的时间复杂度为O(N), 总时间复杂度为O(N^2)
     * 假如获取最小木板利用优先队列的方式进行，那么时间复杂度会降为logN，总时间复杂度为O(N*logN)
     * 因为优先队列的弹出和加入时间复杂度均为logN, 每做一次合并需要三次堆化操作
     * 对于N规模较大时，使用优先队列的优势会很大
     */
    private static int fencePair(int[] arr){
        // params check
        if(arr == null || arr.length < 1)
            throw new IllegalArgumentException("Invalid Input");

        // init
        // 优先队列，每次会得到最小的值，类似于最小堆的概念，不需要自己重新造轮子
        Queue<Integer> priorityQueue = new PriorityQueue<>();
        int cost = 0;

        // main block
        // 将木板元素加入优先队列
        for(int item: arr){
            priorityQueue.offer(item);
        }

        // 假如还存在两个以上的元素，则弹出相加
        // 合并的和就是切割的花费
        while(priorityQueue.size() > 1){
            int mergeItem = priorityQueue.poll() + priorityQueue.poll();
            cost += mergeItem;
            priorityQueue.offer(mergeItem);
        }

        return cost;
    }


    // 单元测试
    public static void main(String[] args) {
        int[] arr = {8, 5, 8, 10, 12, 15, 18};
//        int[] arr = {8, 5, 8};
        System.out.println(fencePair(arr));
    }

}
