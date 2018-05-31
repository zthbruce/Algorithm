package challenge.chapter2_3;

// 动态规划
// 0-1背包问题
public class P15 {
    /**
     * 0-1背包问题
     * 假如限制条件中:
     * 1 <= n <= 100
     * 1 <= wi < 10^7
     * 1 <= v <= 100
     * 1 <= W <= 10^9
     *
     * 0-1背包问题的算法时间复杂度为O(N*W),W很大时，性能就达到了瓶颈
     * 因为W会达到非常大，相比之下，价值数组会比较小
     * 此时如果仍然使用老算法O(N*W)的时间复杂度，恐怕就会达到性能瓶颈，
     * 这时就需要修改算法, 仍然采用动态规划算法, 动态规划的状态定义和状态转移方程
     * 由于价值较小，不如将X轴采用价值来定义，而不是重量
     * 状态定义: dp[i][j]表示到第i个物品，达到价值之和=j的最小重量
     * 状态转移方程: dp[i+1][j] = min{dp[i][j], dp[i][j-v[i]] + w[i]}
     *
     */
    private static int maxValue(int[] w, int[] v,  int W){
        // as
        if(w == null || v == null || w.length != v.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int i, j;

        // get sum value
        int sum = 0;
        for(i = 0; i < v.length; i++) sum += v[i];

        int[] dp = new int[sum+1]; // 获取动态规划数组

        // 初始化
        // 此处的至关重要，因为当j=v[i]时，w[i]必然是当前最小的，但也得兼顾
        dp[0] = 0;
        for(i = 1; i < dp.length; i++) dp[i] = Integer.MAX_VALUE;

        // main block
        for(i = 0; i < w.length; i++){
            for(j = sum; j >= v[i]; j--){
                // 有一个小技巧，零dp[0] = 0, 而不是INf, 这样当相等时，正好可以使用此处w[i]
                if(dp[j- v[i]] != Integer.MAX_VALUE)
                    dp[j] = Math.min(dp[j], dp[j - v[i]] + w[i]);
            }
        }

        // dp是非递减的么? 从结果来看并不是，因为状态的定义必须是凑整的，没有包含关系，
        // 如果是包含关系那么是非递减的，如P12的经典背包问题解决方式，dp[i][j]表示重量不超过j的最大价值

        j = 0;
        // 如果 j < sum + 1 并且 dp[j] <= W
        // 注意最终的情况是刚好不满足情况不满足情况了，所以得使用j-1
        // 记住
        while(j < sum+1 && dp[j] <= W) j++;
        return j-1;
    }


    public static void main(String[] args) {
        int W = 5;
        int[] v = {3, 2, 4, 2, 6, 1, 2};
        int[] w = {2, 1, 3, 2, 3, 4, 5};
        System.out.println(maxValue(w, v, W));
    }
}
