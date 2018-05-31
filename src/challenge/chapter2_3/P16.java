package challenge.chapter2_3;

// 动态规划问题
// 搜索问题
public class P16 {
    /**
     * 多重部分和问题
     * 有n种不同大小的数字ai, 每种个mi个。判断是否可以从这些数字之中选出若干使他们的和恰好为K
     * 1 <= n <= 100
     * 1 <= ai, mi <= 100000
     * 1 <= K <= 100000
     */

    /**
     * 这也是动态规划的经典问题之一了，选一个子集使得和恰好为某个值，因为子集总量为2^n，暴力搜索不行
     * 不再是0-1问题，而且无限背包或者有限数背包类似问题，可以使用循环的方式进行转移
     * 这个问题可以推广到动态规划的计数问题，即满足和恰好为K的方式有多少种？这类问题为计数问题，下一节再说
     * 动态规划的两要素，状态定义和状态转移方程
     * dp[i+1][j] 表示前i个元素是否存在组合使得和为j
     * dp[i+1][j] = {存在 dp[i][j-k*ai]为true | 0 <= k <= mi}
     *
     * 时间复杂度为O(K*Sum(m(i)))
     */

    // 初始写法，利用二维数组的的原始形式
    private static boolean sumExist1(int[] a, int[] m, int K){
        // as
        if(a == null || m == null || a.length != m.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int i, j, k;
        boolean[][] dp = new boolean[a.length+1][K+1]; // 默认都是false
        // 当j为0时，全部为true
        for(i = 0; i <= a.length; i++){
            dp[i][0] = true;
        }

        // main block
        // 第一层循环只需要遍历0 ~ a.length - 1
        for(i = 0; i < a.length; i++){
            for(j = a[i]; j <= K; j++){
                for(k = 0; k * a[i] <= j && k <= m[i]; k++){
                    // 一旦存在，就退出
                    if(dp[i][j - k * a[i]]){
                        dp[i+1][j] = true;
                        break;
                    }
                }
            }
        }

        return dp[a.length][K]; // 返回最后一个值
    }

    /**
     * 上面的代码的时间复杂度上是可优化的，这个和之前的无限背包问题类似
     * 但是有一点值得注意：有限背包问题，一定要确定是否还存在物品可选，这是需要注意的！！(如果要用以前的值，一定要记录剩余多少可选方可)
     * 观察状态转移方程
     * dp[i+1][j] = {dp[i][j-k*a[i]]存在true| 0 <= k <= m[i]}
     * => dp[i+1][j] = {dp[i][j] || dp[i][j-k*a[i]] | 1 <= k <= m[i] }
     * => dp[i+1][j] = {dp[i][j] || dp[i][j-a[i] - k* a[i]] | 0 <= k <= m[i] - 1}
     * => dp[i+1][j] = dp[i][j] || dp[i+1][j-a[i]]
     * 注意上述证明很迷惑人，一不小心就中招了
     * 关键在于{dp[i][j-a[i] - k* a[i]] | 0 <= k <= m[i] - 1} != dp[i+1][j-a[i]]
     * 所以另一种做法，是dp[i][j]不仅记录true/false，而且记录剩余还能使用多少个a[i], 即可解决这个问题，否则逻辑是不对的
     * 所以，遇到有数目限制的背包问题时，如果要简化最里层的循环，可以采用这样的做法
     * dp[i+1][j] =  m[i] when dp[i][j] >= 0  // 表明上一层已经实现了
     *               -1 when a[i] > j || dp[i + 1][j - a[i]] <= 0 // 表明a[i]不能用或者已经用完了
     *               dp[i+1][j - a[i]] - 1 其余情况
     * 所以代码可做如下优化
     */
    private static boolean sumExist2(int[] a, int[] m, int K){
        // as
        if(a == null || m == null || a.length != m.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int i, j;
        int[][] dp = new int[a.length+1][K+1]; // 默认都是0
        // 当j为0时，对于每个i，都无条件满足，所以应该初始化为m[i]

        // 对于 i=0, j > 0 时，全部都初始化为-1
        // 让j=0时，设置为0，为了之后判断方便， >= 0 说明能够完成
        for(j = 1; j <= K; j++) dp[0][j] = -1;
        dp[0][0] = 0; // 仅仅初始化第一个即可，dp[...][0]可以不初始化

        // main block
        for(i = 0; i < a.length; i++){
            for(j = 0; j <= K; j++){
                if(dp[i][j] >= 0) dp[i+1][j] = m[i];
                else if(j < a[i] || dp[i+1][j-a[i]] <= 0) dp[i+1][j] = -1;
                else dp[i+1][j] = dp[i+1][j-a[i]] - 1; // 数目减少
            }
            // @DEBUG
//            for(k = 0; k <= K; k++){
//                System.out.print(dp[i+1][k] + " ");
//            }
//            System.out.println();
        }
        return dp[a.length][K] >= 0; // 返回最后一个值是否>=0
    }


    /**
     * 上述代码的空间复杂度为N*K, 可利用数组复用，将数组降为1维数组
     * 观察状态转移方程，可知dp[i+1][j] 由 dp[i][j] 和dp[i+1][j-a[i]]决定
     * 所以遍历必须从前往后进行，能否进行数组复用取决于对dp[i+1][j]的更新不影响j较大的情况下的后续更新
     * 可以利用二维矩阵图想象是否会对后面造成影响即可
     * 优化代码如下: 
     */

    private static boolean sumExist3(int[] a, int[] m, int K){
        if(a ==null || m == null || a.length != m.length)
            throw new IllegalArgumentException("Invalid Input");

        // init
        int i, j;
        int[] dp = new int[K+1];
        dp[0] = 0; 
        for(i = 1; i <= K; i++){
            dp[i] = -1;
        }

        // main block
        for(i = 0; i < a.length; i++){
            for(j = 0; j <= K; j++){
                if(dp[j] >= 0) dp[j] = m[i];
                else if(j < a[i] || dp[j-a[i]] <= 0) dp[j] = -1;
                else dp[j] = dp[j-a[i]] - 1;
            }
        }
        return dp[K] >= 0;

    }


    // 主程序
    public static void main(String[] args) {
        int[] a = {3, 5, 8};
        int[] m = {3, 2, 2};
        int K = 17;
        System.out.println(sumExist1(a, m, K));
        System.out.println(sumExist2(a, m, K));
        System.out.println(sumExist3(a, m, K));
    }
}
