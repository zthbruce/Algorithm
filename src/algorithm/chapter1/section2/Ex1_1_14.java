package algorithm.chapter1.section2;

public class Ex1_1_14 {
    // 接受一个整型参数 N，返回不大于 log2N 的最大整数
    private static int lg(int n){
        int result = -1; //
        int p = 1;
        // 注意最后一步，可能会超出n，所以需要将结果初始化为-1
        while(p <= n){
            p *= 2;
            result++;
        }
        return result;
    }

    public static void main(String[] args) {
        int m = 2;
        System.out.println(lg(m));
    }
}
