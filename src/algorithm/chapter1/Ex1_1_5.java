package algorithm.chapter1;

public class Ex1_1_5 {
    /**
     * 两者是否位于0，1之间
     * @param x
     * @param y
     * @return {@code true} if x in [0, 1] and y in [0, 1]
     */
    private boolean between0And1(double x, double y){
        return (x >= 0 && x <= 1) && (y >= 0 && y <= 1);
    }

    public static void main(String[] args)  {
        // as
        if(args == null){
            throw new NullPointerException("Param Error");
        }
        // init
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        System.out.println(x == y);
    }
}
