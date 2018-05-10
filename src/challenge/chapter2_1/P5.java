package challenge.chapter2_1;

public class P5 {
    /**
     * Lake Counting 搜索计数问题
     * 使用DFS进行搜索，DFS是一种基于栈的搜索方式(递归是隐式栈: 函数栈)
     * 有一个大小为N*M的园子，雨后积起了水。八连通的积水可以认为连接在一起，请计算出园子里总共有多少水洼
     * 八连通指的是下图中相对W的*部分，其实是九宫格一圈的概念
     *  * * *
     *  * w *
     *  * * *
     */


    /**
     * x, y为当前洼地的坐标
     * 深度优先遍历，将遍历到的洼地置为平地
     * @param arr
     * @param x rowNumber
     * @param y colNumber
     */
    private static void dfs(char[][] arr, int x, int y){
        // 将x, y对应的位置置为.
        // 省去重复的判断，以前用HashSet来表示已完成的其实未免多次一举，学习了
        arr[x][y] = '.';

        // 对周围一圈的8个位置实现深度遍历
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int xInc = x + i;
                int yInc = y + j;
                if(xInc >=0 && xInc < arr.length && yInc >=0 && yInc < arr[0].length && arr[xInc][yInc] == 'W'){
                    dfs(arr, xInc, yInc);
                }
            }
        }
    }

    /**
     * 怎么解决类似问题？比较容易想到的就是DFS，利用递归进行深度优先搜索
     * 具体来说，就是遍历每个空格，一旦找到一个水洼，就将当前水洼置为平地，
     * 然后深度遍历九宫格，将洼地最大程度的扩充
     * @param arr 矩阵的内容，平地为.，洼地为
     * @return 洼地数目
     */
    public static int lakeCounting(char[][] arr){
        // as
        if(arr == null){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int row = arr.length;
        int col = arr[0].length;
        int count = 0;
        // 遍历数组
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(arr[i][j] == 'W'){
                    dfs(arr, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        char[][] arr = { {'W', '.', '.', '.', '.', '.', '.', '.', '.', 'W', 'W', '.'},
                         {'.', 'W', 'W', 'W', '.', '.', '.', '.', '.', 'W', 'W', 'W'},
                         {'.', '.', '.', '.', 'W', 'W', '.', '.', '.', 'W', 'W', '.'},
                         {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'W', 'W', '.'}
                        };
        System.out.println(lakeCounting(arr));
    }
}
