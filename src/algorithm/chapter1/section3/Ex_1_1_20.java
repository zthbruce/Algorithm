package algorithm.chapter1.section3;

public class Ex_1_1_20 {
    /**
     * 编写一个递归的静态方法计算 ln(N!) 的值
     * 利用log(a * b) = log(a) + log(b)
     * 利用规约的方法：log(N!) = log(N * (N-1) * (N-2) * (N-3) * ...log(1)) = log(N) + log((N-1) * (N-2) * (N-3) * ...log(1))
     * ln(N!) = ln((N-1)!) + ln(N)
     * @param N
     * @return {@code ln(N!)}
     */
    private static double logarithmic(int N){
        // as
        if(N < 1){
            throw new IllegalArgumentException("Params Error");
        }
        // bs
        if(N==1){
            return 0;
        }
        // 规约公式
        return logarithmic(N-1) + Math.log(N);
    }

    public static void main(String[] args) {
        System.out.println(logarithmic(10));
    }
}
