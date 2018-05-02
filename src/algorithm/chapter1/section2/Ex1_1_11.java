package algorithm.chapter1.section2;

public class Ex1_1_11 {
    // 打印二维数组
    private static void print(boolean[][] matrix){
        // as
        if(matrix == null || matrix.length < 1 || matrix[0].length < 1){
            throw new IllegalArgumentException("Invalid matrix");
        }
        // init
        int row, col;
        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        String SPACE = " ";
        String COMMA = ",";
        String ASTERISK = "*";

        for(row = 0; row < rowNum; row++){
            for(col = 0; col < colNum; col++){
                System.out.println((row + 1) + COMMA + (col + 1) + SPACE +  (matrix[row][col]? ASTERISK : SPACE));
            }
        }
    }

    public static void main(String[] args) {
        boolean[][] m = {{true, true, true, false}, {false, true, true, false}};
        print(m);
    }
}
