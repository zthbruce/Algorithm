package challenge.chapter2_3;

// 动态规划问题
// 最长公共子序列
public class P13 {

    /**
     * 最长公共子序列问题
     * 给定两个字符串 s1s2...sn和t1t2...tn。求出这两个字符串最长的公共子序列的长度
     * 还有一个等价问题，就将两个字符串变成相同，最少需要删除多少个字符？
     *
     *
     * s存在s.length个取值，t存在t.length个取值，对于每个都可以选择选或者不选，这种2^N的问题
     * 经常可以使用动态规划来进行记忆搜索
     *
     * 动态规划中，最重要的是两点
     * 1. 就是状态的定义：dp[i][j]表示什么意思？这
     * 2. 状态转移方程是怎么样的？
     * 只要想清楚这俩问题，就解决了问题
     *
     * 状态定义：dp[i][j] 表示什么？s1...si 和 t1....tj之间的最长公共子串长度
     * 状态转移方程：dp[i+1][j+1] = si == tj? dp[i][j]+1 : max(dp[i][j+1], dp[i+1][j])
     * 注意此处既要更新本层的元素会影响同一列的取值，所以不能使用数组复用来减少空间复杂度
     * 状态转移方程中值得注意的是：si == tj时，本来应该是max(dp[i][j-1]+1, dp[i][j+1], dp[i+1][j])
     * 但往深了一想即可知：dp[i][j+1] <= dp[i][j]+1, dp[i+1][j] <= dp[i][j] + 1
     * 所以代码已经确定
     */

    private static int LCS(String s, String t){
        if(s == null || t == null){
            throw new IllegalArgumentException("Invalid Exception");
        }

        int i, j;
        int sLen = s.length();
        int tLen = t.length();
        int[][] dp = new int[sLen+1][tLen+1];
        for(i = 0; i < sLen; i++){
            for(j = 0; j < tLen; j++){
                    dp[i+1][j+1] = s.charAt(i) == t.charAt(j)
                            ? dp[i][j] + 1
                            : Math.max(dp[i][j+1], dp[i+1][j]);
            }

        }
        return dp[sLen][tLen]; // 最终结果
    }


    public static void main(String[] args) {
        String s = "abcd";
        String t = "becd";
        System.out.println(LCS(s, t));
    }

}
