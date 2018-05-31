package challenge.chapter2_3;

// 动态规划
public class P14 {
    /**
     * 完全背包问题
     * 0-1背包问题是完全背包问题的扩展，在状态转移时，不再只取一次，可以选择多次
     * 有n种重量和价值分别为wi, vi的物品。从这些物品中挑选总重量不超过W的物品，求出挑选物品的最大价值
     * 每个物品可以任意挑选任意件
     *
     */


    /**
     * 动态规划状态dp[i][j] 表示选到第i件产品为止，重量不超过j的最大价值
     * 由于可以挑任意件，所以状态转移方程就变化了
     * 动态规划的状态转移方程：dp[i+1][j] = max(dp[i][j-k*w[i]] + k*v[i]),
     * k满足约束  0 <= k * w[i] <= j
     *
     * 时间复杂度为O(N*W*W)，有没有更好的解法呢？
     * 关键问题，在于在于能否优化最里层循环
     *
     * @param w
     * @param v
     * @param W
     * @return
     */
    private static int maxValue1(int[] w, int[] v, int W){
        // as
        if(w == null || v == null || w.length != v.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int[] dp = new int[W+1]; // 状态转移方程，dp[0]永远为1
        int size = w.length;
        for(int i = 0; i < size; i++){
            for(int j = W; j >= 1; j--){
                int k = 1;
                // 取全局最大
                while(k * w[i] <= j){
                    dp[j] = Math.max(dp[j], dp[j - k * w[i]] + k * v[i]);
                    k++;
                }
            }
        }
        return dp[W];
    }

    /**
     * 优化版本，优化里层循环是时间复杂度降为O(N*W)
     * 以目前的经验来看，优化的方向有三个：只是思考，不一定能做到，😝
     * (1) 时间复杂度(减少循环层数，可通过使用数据结构或者简化算法实现)
     * (2) 空间复杂度(数据结构复用)
     * (3) 代码复杂度(模块分离，do not repeat yourself)
     *
     * 对于本题来说，如果要优化时间复杂度，最关键的地方是去掉最里层的循环
     * 我们来看一个问题：dp[i+1][j]的计算中选取k个w[i] 和 dp[i+1][j-w[i]]选取k-1个w[i]的情况一模一样
     * 所以dp[i+1][j]的递推中k>=1的部分计算,在dp[i+1][j-w[i]]中已经完成，简直厉害
     * dp[i+1][j] = max(dp[i][j-k*w[i]] + k*v[i] | k >= 0)
     * => dp[i+1][j] = max(dp[i][j], max(dp[i][j-k * w[i]] + k * v[i] | k >= 1))
     * => dp[i+1][j] = max(dp[i][j], max(dp[i][j-w[i] - k * w[i]] + k * v[i] | k >=0) + v[i])
     * => dp[i+1][j] = max(dp[i][j], dp[i+1][j-w[i]] + v[i])
     * 666!
     *
     */
    private static int maxValue2(int[] w, int[] v, int W) {
        // as
        if (w == null || v == null || w.length != v.length) {
            throw new IllegalArgumentException("Invalid Input");
        }

        int[][] dp = new int[w.length+1][W+1]; // 默认都是为0
        for(int i = 0; i < w.length; i++){
            for(int j = w[i]; j <= W; j++){
                // 小trick
                dp[i+1][j] = Math.max(dp[i][j], dp[i+1][j-w[i]] + v[i]);

            }
        }

        return dp[w.length][W];
    }

    /**
     *
     * 能否和0-1背包问题一样，将二维数组转化为一维数组，减少空间复杂度呢？先观察下转移方程
     * dp[i+1][j] = max(dp[i][j], dp[i+1][j-w[i]] + v[i])
     * 存在本层的元素要小心一点，更新本层的元素会不会产生影响，恰好此处不会，所以可以使用一维数组
     * 存在i+1层的元素，肯定不能使用从后往前的方式，因为这样取不到本层的j较小的情况
     * 所以还是从前往后进行更新，而且j可以从w[i]开始
     *
     * 学习到的写法确实厉害
     *
     */

    private static int maxValue3(int[] w, int[] v, int W) {
        // as
        if (w == null || v == null || w.length != v.length) {
            throw new IllegalArgumentException("Invalid Input");
        }
        // init
        int[] dp = new int[W+1];
        for(int i = 0; i < w.length; i++){
            // 该写法学习了, 因为如果当前的点重量已经超出j，不需要更新
            for(int j = w[i]; j <= W; j++){
                dp[j] = Math.max(dp[j], dp[j-w[i]] + v[i]);
            }
        }
        return dp[W];
    }


    public static void main(String[] args) {
        int[] w = {3, 4, 2};
        int[] v = {4, 5, 3};
        int W = 7;
        System.out.println(maxValue1(w, v, W));
        System.out.println(maxValue2(w, v, W));
        System.out.println(maxValue3(w, v, W));
    }
}
