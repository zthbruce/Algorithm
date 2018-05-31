package challenge.chapter2_3;


// 动态规划算法
// 动态规划最重要的就是确定两点：
// 状态定义 + 状态转移方程
public class P12 {

    /**
     * 经典问题：背包问题
     * 有n个重量和价值分为为wi, vi的物品。从这些物品中挑选出总重量不超过W的物品
     * 求所有挑选方案中价值总和的最大值
     *
     * 这是经典的0-1背包问题，即每件物品只能选择一次(实际上是无限背包问题的特例，还有一种特例是指定物品的个数)
     * 背包问题是一个经典的搜索问题，一般采用动态规划进行求解，动态规划实际上暴力搜索的一种改进，记录状态信息
     * 为了更好地理解动态规划，我们先利用暴力搜索的方式进行求解
     */


    // 暴力搜索算法

    /**
     * 记录了选到第i物品为止，重量不超过j的最大价值
     * 相当于利用递归的方式进行求解，每一层的搜索都有选择与不选择两个分支
     * 时间复杂度为O(2^N)，在N比较大的时候这种时间复杂度当然不满足要求
     * 实际上，对于每一个i, j，都会对应一个状态，如果将已经搜索过的i, j记录下来，就会避免后面计算
     * 会重复计算i, j这种状态，这就是动态规划，本质上是暴力搜索的改进，记录结果再利用
     */
    private static int violentSearch(int[] w, int[] v, int i, int j){
        // 基础情况
        if(i < 0){
            return 0;
        }
        // 超过重量时，必然不能选，所以最大重量只能看上一个物品
        if(w[i] > j){
            return violentSearch(w, v, i-1, j);
        }else{
            // 如果可以无限取，或者可以取多个，只需要在这加一个循环
            return Math.max(violentSearch(w, v, i-1, j), v[i] + violentSearch(w, v, i-1, j-w[i])); // 取选与不选取的差别
        }
    }


    /**
     * 背包最大value问题
     * 采用暴力搜索算法进行搜索，暴力搜索通常使用递归的方式进行
     * @param w 每件物品的重量
     * @param v 每件物品的价值
     * @param W 背包容纳总重量
     * @return 不超过背包容量情况下的最大价值
     */
    private static int maxValue1(int[] w, int[] v, int W){
        // as
        if(w == null || v == null || w.length != v.length){
            throw new IllegalArgumentException("Invalid Input");
        }
        // 暴力搜索方式
        return violentSearch(w, v, w.length-1, W);
    }


    /**
     * 动态规划方式求解最大背包问题
     * 动态规划方式，也成为记忆式搜索
     * 将以求状态放在数组里面保存，典型的空间换取时间的方法(最多N*W中状态)
     * 可以固定头部，也可以固定尾部
     */
    private static int maxValue2(int[] w, int[] v, int W){
        // as
        if(w == null || v == null || w.length != v.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // 动态规划
        // 其实，暴力搜索里面已经涉及到状态转移方程
        // dp[i+1][j] = max(dp[i][j], dp[i][j-w[i]] + v[i])，这样表述与索引保持一致
        // 其中dp[0][j] = 0，dp[i][0] = 0; 便于逻辑统一
        // 使用二维数组的方式空间占用过大，实际上没必要，只需要观察状态转移方程，
        // 当前层i的状态依赖上一层i-1的状态，所以本质上只需要一个一维数组即可
        // 只是更新时注意从后往前更新，因为从前往后的方式会导致dp[i][j-w[i]]被更新

        // init
        int i, j;
        int[] dp = new int[W+1]; // 会初始化为0的情况
        for(i = 0; i < w.length; i++){
            for(j = W; j >= w[i]; j--){ // 注意循环结束条件，写法很厉害，学习了
                dp[j] = Math.max(dp[j], dp[j-w[i]] + v[i]);
            }
            for(int k = 0; k <= W; k++){
                System.out.print(dp[k] + " ");
            }
            System.out.println();
        }
        return dp[W];


//        // init
//        int[][] dp = new int[w.length+1][W+1]; // 二维数组, 注意
//        int i, j;
//        // main block
//        // 填充这个数组
//        for(i = 0; i < w.length + 1; i++){
//            for(j = 0; j < W+1; j++){
//                // 初始化
//                if(i == 0 || j == 0){
//                    dp[i][j] = 0;
//                }else{
//                    // 如果可以选 则进行判断是否可以选
//                    // 注意索引的细节注意一下
//                    // 选用w[i], v[i]
//                    if(w[i-1] <= j) {
//                        dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w[i-1]] + v[i-1]);
//                    }
//                }
//            }
//        }
//
//        return dp[w.length][W]; // 最终的选择
    }


    public static void main(String[] args) {
        int W = 5;
        int[] v = {3, 2, 4, 2, 6, 1, 2};
        int[] w = {2, 1, 3, 2, 3, 4, 5};
        System.out.println(maxValue1(w, v, W));
        System.out.println(maxValue2(w, v, W));
    }
}
