package challenge.chapter2_2;

// 贪心算法
// Saruman's Army
public class P10 {
    /**
     * Saruman's Army
     * 直线上有N个点，点i的位置是Xi。从这N个中选若干个，给他们加上标记，
     * 对每一个点，其距离为R以内的区域里必须有带有标记的点(自身若是已标记，可认为与其距离为0的地方有一个带有标记的点)
     * 满足这个条件的情况下，希望能为尽可能少的点条件标记。请问至少要有多少点被加上标记？
     * 如果按照暴力搜索的方式，那么有2^N中状态，时间复杂度为O(2^N)
     *
     * 试想，怎么获取最少标记点的标记方式呢？我们当然希望选取的点能覆盖更多的剩余点，这是归约结论的一种方式
     * 要想覆盖的点越多，越往中心越可能覆盖更多的点，所以希望标记的点尽量往右走，但是当然得包括当前的点
     * 所以贪心的原则：勇往直前，直到遮不住当前最左边的节点为止
     *
     */

    private static int minPoints(int[] arr, int R){
        // as
        if(arr == null || R < 0){
            throw new IllegalArgumentException("Invalid input");
        }

        int point = 0;

        // init
        // 利用指针移动法
        int i = 0;
        int size = arr.length;
        while(i < size){
            int j = i + 1;
            // 循环结束条件
            // 要么超出，要么与left的距离超过R，这时都可以结束
            while(j < size && arr[j] - arr[i] <= R) ++j;

            int k = j; // k - 1 = j - 1为标记点，然后让k往前走

            while(k < size && arr[k] - arr[j - 1] <= R) ++k;

            i = k; // 重新赋值，找到了超出范围的点即可
            ++point; // 标记j这个点
        }
        return point;
    }



    public static void main(String[] args) {
        int R = 10;
        int[] arr = {1, 7, 15, 20, 30, 50};
        System.out.println(minPoints(arr, R));
    }



}
