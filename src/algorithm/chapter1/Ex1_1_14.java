package algorithm.chapter1;

public class Ex1_1_14 {
    // 接受一个整型参数 N，返回不大于 log2N 的最大整数
    private static int lg(int n){
        int result = 0; //
        int p = 1;
        while(p < n){
            p *= 2;
            result++;
        }
        return result;
    }

    public static void main(String[] args) {
        int m = 4;
        System.out.println(lg(m));
    }
}
