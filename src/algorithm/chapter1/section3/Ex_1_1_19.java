package algorithm.chapter1.section3;

import java.util.Arrays;
public class Ex_1_1_19 {
    /**
     * 求斐波那契数列，递归写法
     * 递归写法效率不高，会重复计算很多
     * @param N
     * @return Fibonacci(N)
     */
    private static int fibonacci(int N){
        // base situation
        if(N == 0){
            return 1;
        }
        if(N == 1){
            return 1;
        }
        return fibonacci(N-1) + fibonacci(N-2);
    }


    // 遍历方式实现斐波那契数列
    private static int fibonacci_traverse(int N){
        // as
        if(N < 0){
            throw new IllegalArgumentException("N is too small");
        }
        // bs
        if(N == 0){
            return 1;
        }
        if(N == 1){
            return 1;
        }
        // init
        int x = 1;
        int y = 1;
        // 循环结束条件
        while(N > 1){
            y = y + x;
            x = y - x;
            N--;
        }
        return y;
    }


    /**
     * 利用快速幂算法实现菲波那切数列
     * [y,   [1, 1]   [y
     *              *
     *  x] = [1, 0]    x]
     *  每一次递归只需要将矩阵进行乘法运算，即需要进行N-1次矩阵乘法运算，只要实现矩阵的快速幂乘法即可
     * @param N
     * @return Fibonacci(N)
     */
    private static int quick_fibonacci(int N){
        // as
        if(N < 0){
            throw new IllegalArgumentException("N is too small");
        }
        // bs
        if(N == 0){
            return 1;
        }
        if(N == 1){
            return 1;
        }

        int[][] m = {{1, 1}, {1, 0}};
        int[][] base = {{1}, {1}};
        // 矩阵快速幂乘法，进行N-1次
        int[][] mp = quickMatrixPower(m, N-1);
        return matrixMultipy(mp, base)[0][0];
    }

    private static int[][] quickMatrixPower(int[][] m, int N){
        // as
        if( N < 1){
            throw new IllegalArgumentException("N is too small");
        }
        // bs
        if( N == 1){
            return m;
        }
        // init matrix is I
        int[][] result = new int[m.length][m.length];
        for(int i = 0; i < m.length; i++){
            result[i][i] = 1;
        }

        while(N > 0){
            if((N&1)==1) result = matrixMultipy(result, m);
            m = matrixMultipy(m, m);
            N >>= 1;
        }
        return result;
    }


    /**
     * 矩阵相乘算法
     * @param m1
     * @param m2
     * @return m1 * m2
     */
    private static int[][] matrixMultipy(int[][] m1, int[][] m2){
        // as
        if(m1 == null || m2 == null){
            throw new NullPointerException("matrix can not be null");
        }
        // init
        int rowNum1 = m1.length;
        int colNum1 = m1[0].length;
        int rowNum2 = m2.length;
        int colNum2 = m2[0].length;
        int i, j, k, s;
        // as
        if(colNum1 != rowNum2){
            throw new IllegalArgumentException("The column length of matrix1 must equal the row length of matrix");
        }

        // main block
        // 最终是个rowNum1 * colNum2的矩阵
        int[][] result = new int[rowNum1][colNum2];
        for(i = 0; i < rowNum1; i++){
            for(j = 0; j < colNum2; j++){
                s = 0;
                for(k = 0; k < colNum1; k++){
                    s += m1[i][k] * m2[k][j];
                }
                result[i][j] = s;
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(fibonacci(100000)); // stack_overflow
        System.out.println(fibonacci_traverse(100000));
        System.out.println(quick_fibonacci(100000));
    }
}
