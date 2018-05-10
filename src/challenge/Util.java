package challenge;

import java.util.concurrent.ThreadLocalRandom;
public  class Util {
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static void main(String[] args) {
        System.out.println(randomInt(0, 10000));
    }
}
