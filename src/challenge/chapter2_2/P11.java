package challenge.chapter2_2;

// 贪心算法
// Fence Repair
public class P11 {
    /**
     * 修理栅栏问题
     * 要将一块很长的木板切割成N块，准备切成的木板的长度为L1, L2, ... LN, 未切割前的木板长度恰好为切割后的长度之和
     * 每次切断木板时，需要的开销为这块木板的长度。例如长度为21的模板要切成长度为5， 8， 8的三块模板。第一次切成13，8
     * 时，开销为21。第二次开销为13，合计开销为21+13=34。
     * 求出按照目标要求将木板切割完最小的开销是多少？
     * 1 <= N <= 20000
     * 0 <= Li <= 50000
     * 这问题的初始想法，当然是优先把最大的那个木板块先切出来，然后剩余的木板块开销自然会小咯，yes,go go!
     * 但是该解法是错误的，这种想法思维固化为每一次切割必须产生结果中木板的一块，实际上并不需要，
     * 比如最终木板长度为5， 8， 8， 10， 第一次切割可以使13， 18， 也可以是21， 10，当然前者会比较小
     */



//    private static int minCost1(int[] arr){
//        // as
//        if(arr == null || arr.length < 1){
//            throw new IllegalArgumentException("Invalid Input");
//        }
//        int sum = 0;
//        int cost = 0;
//        int i;
//        for(i = 0; i < arr.length; i++) sum += arr[i];
//        // main  block
//        Arrays.sort(arr); // 进行排序
//        // 减去最大
//        //
//        for(i = arr.length - 1; i >= 1; i--){
//            System.out.println(sum);
//            cost += sum;
//            sum -= arr[i];
//        }
//        return cost;
//    }

    // 数组两者交换
    private static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    /**
     * 为了验证上述想法是否正确，正规的想法可以从二叉树的角度来思考，如果将木板想象成叶子节点
     * 然后对应的父节点是两个子节点之和，这不就是本题中的切割么，简直神奇，切割模型可以联想到二叉树
     * 更具体来说，其实是霍夫曼树，霍夫曼树的叶子节点是字符，然后父节点是频数之和
     * 其实总开销 = 叶子节点的木板长度 * 叶子节点的深度 (根节点为0)
     * 那么算法设计上，可以利用木板合并的方式，直到木板数目为1
     * 每次找出最小的两块木板进行合并为算法关键所在，厉害厉害
     * @param arr 长度数组
     * @return 最小花费
     */
    private static int minCost2(int[] arr){
        // as
        if(arr == null || arr.length < 1){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int size = arr.length;
        int cost = 0;
        // main block
        // 循环结束条件为当前只有一块木板，不需要再进行合并，要写循环，先确定循环条件
        while(size > 1){
            // 寻找最小的两个数的通俗算法，设置两个值，遍历进行更新
            // 另一种方式是进行堆的维护，最大K算法
            // 前两个必须确定大小关系，找到最小的两个值，除了用循环的方式还可以用维护最大堆的方式进行交换
            // 可以降低时间复杂度，利用维护最大堆的方式?
            int min1 = 0; // 初始化最小为第一块
            int min2 = 1; // 初始化次小为第二块
            if(arr[min1] > arr[min2]) swap(arr, min1, min2);
            for(int i = 2; i < size; i++){
                if(arr[i] < arr[min1]){
                    min2 = min1; // 次小
                    min1 = i;    // 最小
                }else if(arr[i] < arr[min2]){
                    min2 = i;
                }
            }
            // 接下来的操作非常重要
            // 将合并的数代替原先数组min1,min2的某一位，将另一位换到最后，然后即可
            int t = arr[min1] + arr[min2];
            System.out.println(size + "," + t);
            cost += t; // 开销增加t
            arr[min1] = t;
            swap(arr, min2, size - 1); //与最后一个元素交换, 即使最后一个元素是min1
            --size; // 长度减小
        }
        return cost;
    }



    public static void main(String[] args) {
        int[] arr = {8, 5, 8, 10, 12, 15, 18};
//        System.out.println(minCost1(arr)); // 这种想法是错误的
        System.out.println(minCost2(arr));
    }
}
