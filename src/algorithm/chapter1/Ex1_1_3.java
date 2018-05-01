package algorithm.chapter1;

import java.util.Scanner;
public class Ex1_1_3 {
    public static void main(String[] args){
        // as
        if(args == null){
            throw new NullPointerException("Params Can Not be NULL");
        }
        // init
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();
        int n2 = sc.nextInt();
        int n3 = sc.nextInt();

        // return
        System.out.println((n1 == n2)  && (n2 == n3));
    }
}
