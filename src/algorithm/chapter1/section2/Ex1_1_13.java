package algorithm.chapter1.section2;

import javafx.scene.control.Alert;

public class Ex1_1_13 {
    private static void printTranpose(int[][] matrix){
        if(matrix == null || matrix.length < 1 || matrix[0].length < 1){
            throw new IllegalArgumentException("params error");
        }
        int rowNum = matrix .length;
        int colNum = matrix[0].length;
        int row, col;
        for(col = 0; col < colNum; col++){
            for(row = 0; row < rowNum; row++){
                // 空格打印
                if(row > 0){
                    System.out.print(" ");
                }
                System.out.print(matrix[row][col]);

                // 换行
                if(col < colNum - 1 && row == rowNum-1){
                    System.out.print("\n");
                }
            }
        }
    }
    public static void main(String[] args) {
        int[][] matrix = {
                            {1,2,3,4},
                            {1,2,3,5},
                            {1,2,3,6},
                            {1,2,3,7},
                            };
        printTranpose(matrix);

    }
}
