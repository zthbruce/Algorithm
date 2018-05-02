package algorithm.chapter1.section3;

public class Ex_1_1_18 {

    /**
     * 类似于快速幂的方法，递归方式实现了a*b
     * @param a
     * @param b
     * @return a*b
     */
    private static int mystery(int a, int b) {
        if (b == 0)     return 0;
        if (b % 2 == 0) return mystery(a+a, b/2);
        return mystery(a+a, b/2) + a; // 为奇数还是偶数，可以采用 b&1的方式进行判断
    }

    /**
     * 类似于快速幂的方法，采用遍历方式实现了a*b
     * @param a
     * @param b
     * @return a*b
     */
    private static int mystery_traverse(int a, int b){
        int result = 0;
        while(b > 0){
            // 利用&的方式判断是奇数还是偶数
            if( (b&1) == 1){
                result += a;
            }
            a = a + a;
            b >>=1; // b/2
        }
        return result;
    }


    /**
     * 实现快速幂算法，递归方式实现a^b
     * @param a
     * @param b
     * @return a^b
     */
    private static int mystery1(int a, int b){
        if(b==0) return 1;
        // 偶数
        if((b&1) == 0) return mystery1(a * a, b >> 1);
        // 奇数
        return mystery1(a*a, b >> 1) * a;
    }

    /**
     * 实现快速幂算法，循环方式实现a^b
     * @param a
     * @param b
     * @return a^b
     */
    private static int mystery1_traverse(int a, int b){
        int result = 1;
        while(b > 0){
            if((b&1) == 1){
                result *= a;
            }
            a *= a;
            b >>= 1;
        }
        return result;
    }

    /**
     * 快速幂和快速乘法的递归和非递归方式实现
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(mystery(2, 25));
        System.out.println(mystery_traverse(2, 25));
        System.out.println(mystery1(2, 3));
        System.out.println(mystery1_traverse(2, 3));
    }
}
