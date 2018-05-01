package algorithm.chapter1;

import org.jetbrains.annotations.NotNull;

// 将整型数转化为二进制的字符串形式并输出
public class Ex1_1_9 {
    // 转化为二进制
    @NotNull
    private static String toBinaryString(int num){
//         solution 1: java std solution
//         return Integer.toBinaryString(num);

//         solution 2: simple solution
//        StringBuilder s = new StringBuilder();
//        while(num > 0){
//            s.append(num % 2);
//            num /= 2;
//        }
//        return s.toString();

//         solution3: bit operation
        StringBuilder s = new StringBuilder();
        int i = 31;
        // use more while, use less for
        while(i >= 0){
            s.append(num >>> i & 1); // 将num进行右移
            i--;
        }
        return s.toString();
    }

    public static void main(String[] args) {
        if(args.length < 1){
            throw new IllegalArgumentException("too many argument to run");
        }

        System.out.println(toBinaryString(Integer.parseInt(args[0])));
        System.out.println(toBinaryString(-1));
    }
}
