package challenge.chapter2_2;

import java.util.*;

public class P8 {

    /**
     * 区间问题
     * 有n项工作，每项工作分别在si时间开始，在ti时间结束。对于每项工作，可以选择参加与否
     * 如果选择了参与，那么自始至终都必须全程参与。参与工作的时间段不能重叠，开始时间和结束瞬间也不能重叠
     * 如何参与尽可能多的工作，最多能参与几项工作
     * 1 <= N <= 100000
     * 1 <= si <= ti <= 10^9 (在int范围内)
     */

    // 自定义可以使用比较器的类
    // Comparable是类需要实现的接口，而Comparator则是传入的比较器
    private static class Pair implements Comparable<Pair>{
        int s;
        int t;
        // Constructor
        public Pair(int s, int t){
            this.s = s;
            this.t = t;
        }

        // 用于进行比较
        public int compareTo(Pair p) {
            return t < p.t?-1:t < p.t?0:1; // -1比较小, 顺序排序，1比较大，逆序排序
        }
    }


    // 另一种自定义comparator的方式，适用于自定义comparator的情况

    /**
     * 最原始的想法，暴力搜索型
     * 实际上对于每一个工作，找出与其不重叠的所有工作进行技术
     * 这样的时间复杂度是O(N)
     * @param s 开始时间数组
     * @param t 结束时间数组
     * @return 所能参与的最大工作数
     */
    private static int  maxWork1(int[] s, int[] t){
        // as
        if(s == null || t == null || s.length != t.length){
            throw new IllegalArgumentException("Invalid Input");
        }
        // init
        int size = s.length;
        int maxCount = 0;
        int i, j;
        // 以某个开头的，进行不断扩展
        for(i = 0; i < size; i++){
            int count = 1; // 初始化为1
            int end = t[i];
            for(j = i + 1;  j < size; j++){
                // 不重叠，所以可以参与该项工作
                // 然后更新尾巴
                if(end < s[j]){
                    count++;
                    end = t[j];
                }

            }
            maxCount = Math.max(count, maxCount); // 取较大者
        }
        return maxCount;
    }

    /**
     * 如果不适用暴力搜索的方式(DFS和BFS原则上都是暴力搜索，也叫穷竭搜索，是最基础和最容易想到的一种解法)
     * 想要优化穷竭搜索，可以尝试使用贪心算法和动态规划算法，贪心算法
     * 贪心算法虽然巧妙，但是最重要的就是确定算法的贪心原则，即怎么贪心？
     * 贪心算法往往从现实出发，怎么样能获取更多的工作？当然是希望当前能选的工作中，结束时间最早的，如此这般
     * 便可以继续前往下一个工作，寻找下一个工作时，同样选取当前可选工作中结束时间最早的
     * 时间复杂度为O(N+N*log(N)) =>O(N*log(N))
     * @param s 开始时间数组
     * @param t 结束时间数组
     * @return 最大工作数目
     */
    private static int maxWork2(int[] s, int[] t){
        // as
        if(s == null || t == null || s.length != t.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int size = s.length;
        int count = 0;
        Pair[] pairs = new Pair[size];
        int i;
        int end = 0; // 初始化，使得第一个元素必然满足情况

        // 使用size
        for(i = 0; i < size; i++){
            pairs[i] = new Pair(s[i], t[i]);
        }

        // 按照t进行排序，贪心算法经常需要对数组进行排序
        Arrays.sort(pairs);

        // main block
        // 实际上就是优先选择结束时间最早的，不妨按照结束时间进行排序

        for(i = 0; i < size; i++){
            Pair ele = pairs[i];
            // 不会重叠
            if(end < ele.s){
                count++;
                end = ele.t; // 更新结束时间
            }
        }
        return count;
    }


    public static void main(String[] args) {
        int[] s = {1, 2, 4, 6, 8, 12};
        int[] t = {3, 5, 7, 9, 10, 14};
        System.out.println(maxWork1(s, t));
        System.out.println(maxWork2(s, t));
    }
}
