package challenge.chapter2_2;

import org.jetbrains.annotations.Contract;

// 贪心算法
public class P7 {
    /**
     * 硬币问题，给定一系列面值的硬币，然后给定对应面值拥有的数目
     * 需要支付A元，问最少需要多少硬币，假设至少存在一种支付方案(因为这个假设，所以可以使用贪心算法，而且硬币间具有整除关系，否则不行)
     * 实际上，要想所用硬币数最小，当然是优先尽可能使用大面值的进行支付，剩余的在接着使用当前最大的面值进行支付
     * 这就是传说中的循环不变式，优先使用当前的最大面值就是本题的贪心算法
     * @param V 面值数组
     * @param C 数量数组
     * @param A 待支付金额
     */
    @Contract("null, _, _ -> fail; !null, null, _ -> fail")
    private static int minCoin(int[] V, int[] C, int A){
        // as
        if(V == null || C == null || V.length != C.length||A < 0){
            throw new IllegalArgumentException("Params Error");
        }

        // init
        int count = 0;
        int size = V.length - 1;
        for(int i = size; i >= 0; i--){
            // 取能使用多少
            int num = Math.min(C[i], A / V[i]); // 能使用多少
            A -= num * V[i]; // 已完成多少
            count += num;
        }
        return count;
    }

    // 一个变种，如果问有多少种凑硬币的方法，那么可以利用动态规划的方式进行计算
    public static void main(String[] args) {
        int[] V = {1, 5, 10, 50, 100, 500};
        int[] C = {3, 2, 1, 3, 0, 2};
        int A = 620;
        System.out.println(minCoin(V, C, A));
    }
}
