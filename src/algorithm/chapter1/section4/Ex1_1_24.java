package algorithm.chapter1.section4;

// 最大公约数的求解，等价于最小公倍数的求解(最小公倍数 * 最大公约数 = a * b)
// 关键在于归约，将原问题归约到base case上面
// 所以，归约在数学上，就是对应的数学归纳法，所以数学归纳法简直无敌般存在
// gcd(a,b) = gcd(b, a mod b)，这就是传说中的欧几里得算法
public class Ex1_1_24 {
    /**
     * 最大公约数, assume a >= b
     * @param a
     * @param b
     * @return greatest common division
     */
    private static int gcd(int a, int b){
        // 注意点
        // 保持A > B，为了后面取余
        int A = a;
        int B = b;
        if(a < b){
            A = b;
            B = a;
        }
        int rem = A % B;
        // base situation，当余数为0，那么必然就是B为最大公约数
        if(rem == 0){
            return B;
        }
        return gcd(A, rem);
    }
    public static void main(String[] args) {
        System.out.println(gcd(7, 14));
    }
}
