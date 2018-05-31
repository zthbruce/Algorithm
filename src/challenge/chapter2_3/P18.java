package challenge.chapter2_3;

// 动态规划问题
// 计数问题

// 动态规划问题除了最优搜索问题, 还可以解决计数问题
// 比如之前解决的从背包中找出和为K的组合数等等


public class P18 {

    /**
     * 划分数
     * 有n个无区别的物品，将他们划分成不超过m组，求出划分方法数模M的余数
     * 1 <= m <= n <= 1000
     * 2 <= M <= 10000
     *
     * 这是组合数学中非常常见的一个问题，起类似求 x1 + x2 + x3 + ... xk = n
     * k <= m 解的组合
     * 这样的划分称为n的m划分，是一个无序的(不能排列的)的组合数，比如n=4，m = 3时 1+1+2 和 1+2+1是属于同一种情况
     * 这也是一种搜索问题，搜索是否将每个物品选择为一组使得最终划分不超过m组的划分数，这种搜索时间复杂度为2^n
     * 我们尝试使用动态规划的方法进行改善
     *
     * 状态定义：
     * dp[i][j]=j的i划分数
     * 状态转移要注意，一种简单的想法是：先取出k个，然后再将剩余j-k个划分为i-1种，但是这种递推方式明显是重复计算的
     * 上面举得例子就是这样，另一种方式是将其抽象成一个方程x1 + x2 + .... xm = n
     * 我们考虑n的一个m划分，即满足sum(xi) = n，分为两种情况
     * 1. xi > 0 对于所有i成立，那么{xi - 1 | 1 <= i <= m}就会对应一个n-m的m划分
     * 2. 存在xi = 0， 那么对应的划分是n的m-1划分
     * 没问题，上述两种情况不会导致重复计算
     * 状态转移方程：
     * dp[i][j] = dp[i-1][j] + dp[i][j-i]
     *
     * 状态转移方程一旦确定
     *
     *
     */


    // 划分数函数主题
    private static int partNum(int n , int m, int M){
        // 边界检验
        if(n < m) throw new IllegalArgumentException("n should not smaller than m");

        // init
        int[][] dp = new int[m+1][n+1];

        // 计数问题，初始化非常重要, 0的0划分为1这是初始化
        dp[0][0] = 1;
        // main block
        for(int i = 1; i <= m; i++){
            for(int j = 0; j <= n; j++){
                // 有点像背包问题
                if(j - i >= 0){ // 注意此处的判断
                    dp[i][j] = (dp[i-1][j] + dp[i][j-i]) % M;
                }
                else dp[i][j] = dp[i-1][j]; // 使用上一个进行替换即可
            }
        }
        return dp[m][n];
    }

    /**
     * 上述解法的时间复杂度为O(n*m)，空间复杂度为二维数组
     * 可以将空间复杂上进行优化，将二维数组优化为1维数组dp
     * @param n
     * @param m
     * @param M
     * @return
     */
    private static int partNum2(int n, int m, int M){
        // 边界检验
        if(n < m) throw new IllegalArgumentException("n should not smaller than m");


        // init
        int[] dp = new int[n+1];
        dp[0] = 1; // 注意0的划分数恒为1，本质上因为0的0划分数为1，这个初始化可以对k的k划分为1产生效果

        // main block
        for(int i = 1; i <= m; i++){
            for(int j = i; j <=n; j++){
                // 注意这个地方，只需要更新待更新的即可
                dp[j] = dp[j] + dp[j-i];
            }
        }

        return dp[n];
    }


    public static void main(String[] args) {
        System.out.println(partNum(10, 3, 10000));
        System.out.println(partNum2(10, 3, 10000));
    }
}
