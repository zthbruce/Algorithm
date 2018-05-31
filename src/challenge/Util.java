package challenge;

import java.util.concurrent.ThreadLocalRandom;
public  class Util {
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


    /**
     * 找到arr[i] >= key的最小位置(即为下边界)
     * @param arr 递增数组
     * @param key key值
     * @return >= key的最小位置索引
     */
    public static int lowerBound(int[] arr, int key){
        // as
        if(arr ==  null){
            throw new IllegalArgumentException("Invalid input");
        }
        // init
        int start = 0;
        int end = arr.length - 1;

        // main block
        while(start <= end){
            int mid = start + ((end - start) >> 1);
            if(arr[mid] < key) start = mid + 1; // 如果小于，则start必然在mid+1
            else end = mid - 1; // 即使相等，start不动，让end往左移动，这样最后start会在>=key的位置上
        }
        return start; // start才是真正的下界所在
    }

    /**
     * 找到arr[i] <= key的最大位置(取其上界)
     * @param arr 数组
     * @param key key
     * @return
     */
    public static int upperBound(int[] arr, int key){
        // as
        if(arr ==  null){
            throw new IllegalArgumentException("Invalid input");
        }
        // init
        int start = 0;
        int end = arr.length - 1;
        // main block
        while(start <= end){
            int mid = start + ((end - start) >> 1);
            if(arr[mid] > key) end = mid - 1;
            else start = mid + 1; // 即使相等，start也要往后，end不动
        }
        return end;
    }


    public static void main(String[] args) {
//        System.out.println(randomInt(0, 10000));
        int[] arr = {1, 3, 3, 3, 5, 7, 9};
        System.out.println(lowerBound(arr, 3));
        System.out.println(upperBound(arr, 3));
        int ele = 3;
        System.out.println(ele + "的个数为：" + (upperBound(arr, 3) - lowerBound(arr, 3)  + 1));
    }
}
