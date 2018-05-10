package challenge.chapter1;

public class P2 {
    /**
     * 蚂蚁爬行问题
     * n只蚂蚁以1cm/s的速度在长为Lcm的杆子上爬行，遇到蚂蚁时会掉头，直到到达杆子两端
     * 给定一个数组arr, 表示每只蚂蚁的初始位置(距离左端点)，每只蚂蚁的朝向未知(可能往左可能往右)
     * 求蚂蚁爬行最长和最短时间
     * 实际上，一旦所有蚂蚁的方向确定了的话，那么爬行的时长也就确定了，而所有的蚂蚁的总状态数为2^n
     * 如果利用穷举法的话，必然会导致指数爆炸，不妨分析下最长和最短时间发生的场景
     *
     * 对于最短时间来说，我们希望蚂蚁朝着距离较近的那一端爬行，这样蚂蚁便不会发生碰撞，所以结果表达式为
     * Max(Min(x, L -x)), x表示距离左端点的位置
     *
     * 对于最长时间来说，我们来观察下，加入两只蚂蚁发生了碰撞，掉头而走时会发生什么，因为蚂蚁其实是没有区别的
     * 所以本质上，掉头而走和穿越而过的效果是一模一样的，最长时间，当然是选择朝着距离较远的那一端爬行，所以结果表达式为
     * Max(Max(x, L - x))
     *
     * 分析之后的结果，时间复杂度为O(N)
     */


    private static class Tuple{
        int maxTime;
        int minTime;
        Tuple(int maxTime, int minTime){
            this.maxTime = maxTime;
            this.minTime = minTime;
        }
    }

    private static Tuple ant(int n, int[] arr, int L){
        // as
        if(n < 0 || L < 0 || arr == null || n != arr.length){
            throw new IllegalArgumentException("Invalid Input");
        }

        // init
        int i;
        int minTime = 0;
        int maxTime = 0;
        // 最短和最长时间
        for(i = 0; i < n; i++){
            minTime = Math.max(minTime, Math.min(arr[i], L - arr[i]));
            maxTime = Math.max(maxTime, Math.max(arr[i], L- arr[i]));
        }

        // 试试使用Turple类，表示最大最小
        return new Tuple(maxTime, minTime);

    }



    public static void main(String[] args) {
        int L = 10;
        int n = 3;
        int[] arr = {2, 6, 7};
        Tuple result = ant(n, arr, L);
        System.out.println(result.minTime + "," + result.maxTime);

    }
}
