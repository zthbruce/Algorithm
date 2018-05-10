package challenge.chapter2_1;

import java.util.*;

public class P6 {
    /**
     * 宽度优先搜索(Breadth-First Search)，另一种常用的搜索方式
     * 宽度优先搜索使用队列作为数据结构，如二叉树的层次遍历就是使用宽度优先遍历，利用先进先出的特点
     * 实现了由近到远的搜索
     */

    // x, y pair
    private static class Pair{
        int x;
        int y;
        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    // do not repeat yourself
    private static void updateQueue(int x, int y, char[][] arr, Queue<Pair> queue){
        // range check
        //
        // 注意遍历的条件
        if(x < arr.length && x >= 0 && y < arr[0].length && y >= 0 && arr[x][y] != '#'){
            queue.offer(new Pair(x, y));
            arr[x][y] = '#'; // 一旦遍历过，就将其的状态标记，状态位的标记注意，否则会生成死循环
        }
    }

    /**
     *
     * @param arr 迷宫矩阵
     *            迷宫矩阵内的元素包括：'#','.','S','G'分别表示墙壁,通道,起点,终点
     * 每一步可以从上下左右四个方向进行移动，这四个位置是距离最近的位置
     * 广度优先遍历的状态控制很重要，一旦访问过，状态就会发生改变，否则会导致重复访问，这点要注意
     * 深度优先遍历的状态管理更加简单
     * @param sx 起点x
     * @param sy 起点y
     * @param gx 终点x
     * @param gy 终点y
     * @return 从起点到终点所需的最小步数
     *
     * 使用BFS的方式可以很方便的求出最短路径，每一次加入一批节点，就可以计算一次距离
     */
     private static int minPath(char[][] arr, int sx, int sy, int gx, int gy){
        // as
        if(arr == null){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
//        int minPath = Integer.MAX_VALUE;
        Pair start = new Pair(sx, sy);
//        Pair end = new Pair(gx, gy);
        int res = 0; // 初始步数为0
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(start);
        // 循环结束条件为空
        while(!queue.isEmpty()){
            int size = queue.size(); // 获取数组的长度
            // 遍历数组
            for(int i = 0; i < size; i++){
                Pair p = queue.poll(); // 出队
                // 第一次见到终点即可返回
                if(p.x == gx && p.y == gy){
                    return res;
                }
                // up
                updateQueue(p.x, p.y+1, arr, queue);
                // down
                updateQueue(p.x, p.y-1, arr, queue);
                // left
                updateQueue(p.x - 1, p.y, arr, queue);
                // right
                updateQueue(p.x + 1, p.y, arr, queue);
            }
            res++; // 层数加1，注意四个方向都进行完了，然后层数增加，每增加一层
        }

        return -1; // 表示找不到同路
    }


    public static void main(String[] args) {
        char[][] m = {{'#', 'S', '#', '#'},
                        {'.', '.', 'G', '#'}
                        };
        System.out.println(minPath(m, 0, 1, 1, 2));
    }
}
