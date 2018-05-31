package challenge.chapter2_1;

public class P4 {
    /**
     * 有穷搜索
     * 给定一个数组，判断是否可以从中挑选出若干个数，使他们的和恰好为k
     * 这是一个搜索问题，如果采用递归的想法，进行穷举，时间复杂度为O(2^n)
     *
     */

    // i 初始化为n
    private static boolean dfs(int i, int remain, int[] arr){
        // base situation
        if(remain == 0){
            return true;
        }else if(i == 0){
            // 全部元素均已完成
            return false;
        }
        // 选或者不选
        return dfs(i - 1, remain, arr) || dfs(i - 1, remain - arr[i - 1], arr);
    }

    /**
     * 采用递归进行深度优先搜索
     * 每一个都可以选择或者是不选择
     * @param arr
     * @param k
     * @return
     */
    private static boolean sum(int[] arr, int k){
        if(arr == null){
            throw new IllegalArgumentException("Invalid Input");
        }
        int size = arr.length;
        return dfs(size, k, arr);
    }


    /**
     * 可以采用动态规划的思想进行优化，将已经完成的数目进行保存
     * 状态转移方程为dp[i][j] = dp[i-1][j - arr[i]]，如果上一个为true
     * 可以简化为tmp[j], j = 0 ~ N
     */
    private static boolean sum1(int[] arr, int k){
        // as
        if(arr == null){
            throw new IllegalArgumentException("Invalid Input");
        }
        // init
        int i, j;
        int size = arr.length;

        // 申明动态规划数组
        // k = 0 是默认为true
        boolean[] dp = new boolean[k+1];
        for(i = 0; i <= k; i++){
            dp[i] = i == 0; // == 的优先级较高
        }

        // main block
        for(i = 0; i < size; i++){
            // 从后往前遍历，以免覆盖
            for(j = k; j >= 1; j--){
                // 注意这个if的重要性(如果有元素超出限制)
                if(j >= arr[i]){
                    dp[j] = dp[j - arr[i]];
                }
            }
            // 如果dp[k]已经存在，即可进行设置
            if(dp[k]){
                return true;
            }
        }
        return false;
    }


    /**
     * 扩展问题，统计一下和为k的组合数，类似于0-1背包问题
     * 利用动态规划进行搜索
     * 状态转移方程为dp[i][j] = dp[i-1][j] + dp[i-1][j - arr[i]] // 即选与不选是两种情况
     * 注意dp[~][0] = 1，可以理解为不选即可满足为0，即为一种情况
     * @param arr
     * @param k
     * @return
     */
    private static int sumCount(int[] arr, int k){
        // as
        // as
        if(arr == null){
            throw new IllegalArgumentException("Invalid Input");
        }
        // init
        int i, j;
        int size = arr.length;

        // 声明动态规划数组
        // k = 0，默认是1
        int[] tmp = new int[k+1];
        for(i = 0; i <= k; i++){
            tmp[i] = i == 0 ? 1 : 0;
        }

        // 进行状态转移方程
        for(i = 0; i < size; i++){
            for(j = k; j > 0; j--){
                // 状态转移方程
                if(arr[i] <= j){
                    tmp[j] = tmp[j] + tmp[j - arr[i]];
                }
            }
        }
        return tmp[k];
    }

    public static void main(String[] args) {
        int k = 7;
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(sum(arr, k));
        System.out.println(sumCount(arr, k));
//        System.out.println(sum1(arr, k));
    }
}
