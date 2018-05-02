package algorithm.chapter1.section3;

import java.util.Arrays;

// 递归练习
public class Ex_1_1_15 {
//    编写一个静态方法 histogram()，接受一个整型数组 a[] 和一个整数 M 为参数并返回一个大小为 M 的数组，
//    其中第 i 个元素的值为整数 i 在参数数组中出现的次数。如果 a[] 中的值均在 0 到 M-1之间，返回数组中所有元素之和应该和 a.length 相等
    private static int[] histogram(int[] a, int M){
        // as
        if(a == null || a.length < 1){
            throw new IllegalArgumentException("Illegal Params");
        }
        // init
        int[] result = new int[M];
        int i;

        // main block
        for(i = 0; i < a.length; i++){
            int ele = a[i];
            if(ele >= 0 && ele <= M-1){
                result[ele]++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 2, 3, 4, 5, 9, 9, 3, 5};
        int M = 10;
        System.out.println(Arrays.toString(histogram(arr, M)));
    }
}
