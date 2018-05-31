package challenge.chapter2_3;

import challenge.Util;
// 动态规划问题
// 搜索问题
public class P17 {

    /**
     * 最长上升子序列问题(LIS)
     *
     * 有一个长度为n的数列a0, a1, a2, ..., an-1。请求出这个序列中最长的上升子序列的长度。上升子序列指的是对于任意的i<j
     * 满足ai < aj的子序列
     *
     * 1 <= n <= 1000
     * 0 <= ai <= 1000000
     */


    /**
     * 这是一个非常经典的搜索问题(搜索问题常用的解法：暴力搜索(DFS,BFS)，贪心算法，动态规划
     * 经典之所以是经典，因为有值得借鉴的东西，或者有很多问题是经典问题的变形或者转换
     * 如果采用暴力搜索，数列中的每个值都可以选择或者不选择，时间复杂度为O(2^N)
     * 一般这样的问题都可以使用动态规划进行解决
     *
     * 初始想法, 状态定义和状态转移方程如下:
     * dp[i] 表示以a[i] 结尾的最长上升子序列
     * dp[i] = max{1, dp[j]+1|j < i且aj < ai}
     *
     * 这个想法的时间复杂度为O(N^2)
     * @param a 给定的数列
     * @return 最长上升子序列的长度
     */
    private static int LIS1(int[] a){
        // as
        if(a == null) throw new IllegalArgumentException("Invalid input");

        // bs
        if(a.length < 2){
            return a.length;
        }

        // init
        int i, j;
        int[] dp = new int[a.length];
        for(i = 0; i < a.length; i++){
            int c = 1; // 最少为1
            // 遍历是为了寻找比ai小的元素
            for(j = i - 1; j >= 0; j--){
                if(a[j] < a[i] && dp[j] >= c) c = dp[j] + 1;
            }
            dp[i] = c;
        }
        return dp[a.length - 1];
    }



    /**
     * 能否在时间复杂度上做一个更好的优化呢？
     * 时间复杂度排行O(1) < O(logN) < O(N) < O(N*logN) < O(N^2) <....
     * 能否将时间复杂度下降到O(N * logN)，怎么样会产生logN，一般在有序数组的二分搜索中会体现这个时间复杂度
     * 这需要另外一种状态的定义: 使得dp呈现一种递增的序列
     * dp[j] 表示长度为j+1的最长上升子序列的最小末尾元素，如果不存在则为Integer.MAXVALUE(这个值设置取决于ai的范围设定)
     * dp[j] 的状态转移方程为对于每一个ai, 遍历dp，如果j = 0 或者dp[j-1] < aj, 说明dp[j-1]可以往右边增加元素aj
     * 则对dp[j]进行更新， dp[j] = min{dp[j], aj}，否则不更新
     * 最终的结果为不为Integer.MAXVALUE的最大i + 1即为最长上升子序列
     * 如果按照传统dp方式进行，时间复杂度为O(N^2)
     * ai如果对多个dp[j]进行更新，如果这样做的话和上一中方法其实一致
     * 但实际上，我们最关心的还是对所能更新的最右端的位置，实际上ai只需要更新这一个位置即可
     * 但其实dp是一个递增的数列，是不是可以在这做文章呢？(二分查找找到>=a[i]的最小位置进行最远处的更新)
     * 二分查找还是很重要的，特别是lower_bound 和 upper_bound的实现非常重要(在Util中已经实现)
     * 按这样的算法时间复杂度为O(N*logN), GO GO!
     */
    private static int LIS2(int[] a){
        // as
        if(a == null) throw new IllegalArgumentException("Invalid input");

        // bs
        if(a.length < 2){
            return a.length;
        }
        // init
        int i, j;
        int[] dp = new int[a.length]; // 与数组等长度即可

        // 初始化dp
        for(i = 0; i < a.length; i++){
            dp[i] = Integer.MAX_VALUE;
        }

        // main block
        for(i = 0; i < a.length; i++){
            // 找到 >= a[i]的最小位置，即为最右边的更新位置，因为满足dp[j-1] < aj可以更新dp[j]
            int index = Util.lowerBound(dp, a[i]);
            dp[index] = Math.min(dp[index], a[i]);
        }

        return Util.lowerBound(dp, Integer.MAX_VALUE); // 获取最长的长度
    }


    public static void main(String[] args) {
        int[] a = {4, 2, 3, 1, 5};
        System.out.println(LIS1(a));
        System.out.println(LIS2(a));
    }

}
