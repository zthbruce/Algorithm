package challenge.chapter2_4;

// 优先队列问题

import java.util.Comparator;
import java.util.PriorityQueue;

public class P21 {
    /**
     * 你需要驾驶一辆卡车行驶L单位距离。最开始时，卡车上有P单位的汽油。
     * 卡车每开1单位距离需要消耗1单位的汽油。如果在途中车上的汽油耗尽，卡车无法继续前行
     * 因而无法到达终点。途中一共有N个加油站。第i个加油站在距离起点Ai单位距离的地方
     * 最多可以给卡车加Bi单位汽油。假设卡车的燃料箱的容量是无限大的，无论加多少油都没限制
     * 问卡车是否能达到终点？如果可以，最少需要加多少次油？如果可以到达终点，输出最少的加油次数，否则输出-1
     * 1 <= N <= 10000
     * 1 <= L <= 1000000, 1 <= P <= 1000000
     * 1 <= Ai < L, 1 <= Bi <= 100
     */


    /**
     * 这是一个搜索型问题，最少的加油次数
     * 如果每个加油站都加油，还不能到达，那么必然是不能到达终点
     *
     * 搜索问题最重要的是策略:
     * 分析一下这个问题，实际上每经过一个加油站，即获得了一次加油的机会
     * 而什么时候加油呢? 当然是没油的时候加油
     *
     * 分析了这个问题后，可以得到搜索的策略，这就是程序化思维:
     * 搜索的策略为:
     * 1. 如果要成功到达目的地，必须在没油的时候补上油
     *
     * 2. 没油的时候选择哪个加油站补油呢，如果当前加油队列为空，则返回-1, 否则取当前加油队列中加油量最大的那个加油站
     *
     * 优先队列这种数据结构可以完美的解决这个问题，可以每次都取出最大的加油站即可
     *
     */
    private static int expedition(int L, int P, int[] A, int[] B){
        // param check
        if(L < 1 || P < 1 || A == null || B == null || A.length != B.length)
            throw new IllegalArgumentException("Invalid Input");

        // base case
        // 如果L比P小，这是基本情况，必然返回0
        if(L < P) return 0;

        // init
        int count = 0; // 加油次数统计

        // 比较器
        // 定义比较器，从大到小排序
        // 这种写法, 非常棒, lambda表达式
        Comparator<Integer> cmp = (e1, e2) -> e2 - e1;
        // 优先队列
        PriorityQueue<Integer> queue = new PriorityQueue<>(cmp);
        // main block
        for(int i = 0; i < A.length; i++){
            // 如果到不了这个加油站，说明需要加油
            while( P < A[i]){
                if(queue.isEmpty())
                    return -1;
                 P += queue.poll(); // 进行加油(最大者加油)
                count++;
            }
            queue.offer(B[i]); // 如果能达到该加油站，就获取了该加油站的加油机会
        }

        // 最终看能不能到达
        return P >= L? count : -1;
    }

    public static void main(String[] args) {
        int N = 4;
        int L = 25;
        int P = 10;
        int[] A = {10, 14, 20, 21};
        int[] B = {10, 5, 2, 4};
        System.out.println(expedition(L, P, A, B));
    }
}
