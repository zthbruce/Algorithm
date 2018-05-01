package algorithm.chapter1;

public class Ex1_1_8 {
    // 考察字符型和整型的相互转换转换
    // char 在java中为2个byte
    // b的Ascii码为98
    public static void main(String[] args) {
        System.out.println('b');
        System.out.println('b' + 'c');
        System.out.println((char)('a' + 4));
    }
}
