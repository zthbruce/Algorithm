package challenge.chapter1;

import java.util.Arrays;
public class P1 {

    /**
     * 问题介绍:
     * 给定一个边长数组，从中选择三根作为边长，求能组成的三角形的最大周长
     * 输入 arr 边长数组
     * 输出 形成的三角形的最大周长
     */


    /**
     * 单一职责原则：判断是否可以组成三角形
     * 判断是否为三角形这一步值得学习，sum, max, sum-max之间的关系，为什么一开始没想到
     * @param a 边1
     * @param b 边2
     * @param c 边3
     * @return -1: 表示不能组成三角形，否则返回三者之和，这样可以不浪费计算sum这一步
     */
    private static int isTriangle(int a, int b, int c){
        int sum = a + b + c;
        int maxEdge = Math.max(a, Math.max(b, c));
        if(maxEdge < (sum - maxEdge)){
            return sum;
        }
        return -1;
    }

    /**
     * 初始解法，穷举法，从n个数组中选取3根，其数量为 (n * (n-1) * (n-2)) / ( 3 * 2 * 1)
     * 什么时候能组成三角形：最大边 < 较小边1 + 较小边2
     * 时间复杂度O(N^3), 空间复杂度O(1)
     */
    private static int maxTriangle1(int[] arr){
        // as
        if(arr == null || arr.length < 3){
            throw new IllegalArgumentException("Invalid input");
        }
        // init
        int size = arr.length;
        int maxSum = 0;

        // main block
        for(int i = 0; i < size - 2; i++){
            for(int j = i + 1; j < size - 1; j++){
                for(int k = j + 1; k < size; k++){
                    int sum = isTriangle(arr[i], arr[j], arr[k]);
                    if(sum > maxSum){
                        maxSum = sum;
                    }
                }
            }
        }
        return maxSum;
    }


    /**
     * 如果针对排序的数组，可以采用从右往前的方式进行遍历
     * 时间复杂度为O(N), 空间复杂度为O(1)，时间复杂度综合排序后为O(N*log(N))
     * a1, a2, a3, a4, a5, a6, a7， 如果a5, a6, a7能满足条件，那么这个窗口为最大
     * 否则将窗口往前移动一位
     */
    private static int maxTriangle2(int[] arr){
        // as
        if(arr == null || arr.length < 3){
            throw new IllegalArgumentException("Invalid Input");
        }
        // init
        Arrays.sort(arr); // 排序
        int i = arr.length;
        int a = arr[--i];
        int b = arr[--i];
        while(i > 0){
            // 窗口移动
            int c = arr[--i];
            int sum = isTriangle(a, b, c);
            if(sum != -1){
                return sum;
            }
            a = b;
            b = c;
        }
        return 0; // 如果找不到那么直接返回0
    }


    /**
     * 测试模块
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {10, 23, 45, 1, 2, 17,18};
        System.out.println(maxTriangle1(arr));
        System.out.println(maxTriangle2(arr));
    }

}
