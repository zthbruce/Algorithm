package challenge.chapter2_3;


// 动态规划问题
// 搜索问题
public class P19 {

    /**
     * 多重集组合数
     * 有n种物品，第i种物品有ai个。不同种类的物品可以相互区分但相同种类的无法区分;
     * 从这些物品中取出m个，有多少种取法？求方案数数模M的余数
     * 1 <= n <= 1000
     * 1 <= m <= 1000
     * 1 <= ai <= 1000
     * 2 <= M <= 10000
     *
     * 这种问题其实和有限物品背包类似，都是一种物品有限数
     * 一般以物品为单位进行遍历，以免重复性问题的出现
     *
     * 定义状态转移方程：
     * dp[i+1][j]: 从前i中物品中取出j个的组合个数
     * 为了从前种物品中取出j个，可以从前i-1中物品中取出j-k个，再从第i中物品中取出k个
     *
     * 所以状态转移方程为:
     * dp[i+1][j] = sum(dp[i][j-k] | 0 <= k <= min(j, a[i])) 受到个数的限制
     */


    /**
     *
     * 初始解法：
     * @return 多重组合数
     */
    private static int multiCombination(int n, int m, int[] a, int M){
        // params check
        // assert a != null && n == a.length : "invalid input";
        if(a == null|| n != a.length)
            throw new IllegalArgumentException("invalid input");
        // init
        int i , j, k;
        int[][] dp = new int[n+1][m+1];

        // 取出0个永远只有一种做法，就是另其为0
        for(i = 0; i <= m; i++){
            dp[i][0] = 1;
        }

        // main block
        for(i = 0; i < n; i++){
            for(j = 1; j <= m; j++){
                for(k = 0; k <= Math.min(j, a[i]); k++){
                    dp[i+1][j] = (dp[i+1][j]+ dp[i][j-k]) % M;
                }
            }
        }
        return dp[n][m];
    }


    /**
     * 能否在时间复杂度上做优化呢？
     * 三层循环里面的最里层这种优化，我们在之前有限数据集也是做过的，循环内上一层的累加实际上在本层已经有所体现
     * 所以通过调用本层的方式，来进行时间复杂度上的优化
     * 我们来看状态转移方程：
     * dp[i+1][j] = sum{dp[i][j-k] | 0 <= k <= min(a[i], j)}
     * => dp[i+1][j] = sum{dp[i][j-k-1] | 0 <= k <= min(a[i], j-1)} + dp[i][j] - dp[i][j-a[i]-1]
     * 考虑首尾，进行相加减，k = 0时dp[i][j-1]还少dp[i][j]，故补上即可
     * k = min(a[i], j), 如果 j <= a[i]时，首部不会多出来一项，j > a[i]时才会多出来一项dp[i][j-a[i]-1]，故减去即可
     * 所以状态转移方程实际上是：
     * dp[i+1][j] = dp[i+1][j-1] + dp[i][j] - dp[i][j-a[i]-1]
     *
     * 这种方法时间复杂度为O(m*n)
     */

    private static int multiCombination2(int n, int m, int[] a, int M){
        // params check
        //assert a != null && n == a.length : "invalid input";
        if(a == null|| n != a.length)
            throw new IllegalArgumentException("invalid input");
        // init
        int i , j, k;
        int[][] dp = new int[n+1][m+1];

        // 取出0个永远只有一种做法，就是另其为0
        for(i = 0; i <= m; i++){
            dp[i][0] = 1;
        }

        // main block
        for(i = 0; i < n; i++){
            for(j = 1; j <= m; j++){
                if(j >= a[i] + 1)
                    dp[i+1][j] = (dp[i+1][j-1] + dp[i][j] - dp[i][j - a[i] - 1]) % M;
                else
                    dp[i+1][j] = (dp[i+1][j-1] + dp[i][j]) % M;
            }
        }
        return dp[n][m];
    }


    /**
     * 时间复杂度上优化完成后
     * 能否针对空间复杂度做优化呢，将二维数组优化为1维数组？
     * 需要注意的地方是状态转移方程
     * dp[i+1][j] = dp[i+1][j-1] + dp[i][j] - dp[i][j-a[i]-1]
     * 本层需使用上一层的dp[i][j-a[i]-1]，所以不能更新dp[i][j-a[i]-1]，而且需要本层的小j元素，从右往左也不可行
     * 故不能优化空间复杂度
     *
     */


    public static void main(String[] args) {
        int n = 3;
        int m = 3;
        int[] a = {1, 2, 3};
//        int[] a = null;
        int M = 10000;
        System.out.println(multiCombination(n, m, a, M));
        System.out.println(multiCombination2(n, m, a, M));
    }
}
