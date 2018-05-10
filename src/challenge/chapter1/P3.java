package challenge.chapter1;

import challenge.Util;

import java.util.Arrays;

public class P3 {
    /**
     * 将写有数字的n个纸片放入口袋中，从中选取4次纸片，每次抽完放回
     * 如果这四个数字之和为m,则胜利，否则失败
     * 判断是否存在四个数字之和为m的情况
     *
     */

    /**
     * 初始想法，穷举暴力搜索(时间复杂度为O(N^4)
     * @param n
     * @param m
     * @param arr
     * @return
     */
    private static boolean fourSum1(int n , int m, int[] arr){
        // as
        if(n < 0 || arr == null || arr.length != n){
            throw new IllegalArgumentException("Invalid input");
        }

        // init
        int i, j, k, l;

        // 四重循环暴力搜索
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                for(k = 0; k < n; k++){
                    for(l = 0; l < n; l++){
                        if(arr[i] + arr[j] + arr[k] + arr[l] == m){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 单一职责，只负责二分查找
     */
    private static boolean binarySearch(int[] arr, int key){
        // as
        if(arr == null){
            return false;
        }
        // init
        int start = 0;
        int end = arr.length - 1;
        while(start <= end){
            int mid = start + ((end - start) >> 1);
            if(arr[mid] == key){
                return true;
            }
            else if(arr[mid] < key){
                start = mid + 1;
            }
            else{
                end = mid -1;
            }
        }
        return false;
    }

    /**
     * 如果利用四重循环进行搜索的话，我们来看最后一层搜索，实际上是一个已确定的数的搜索
     * 搜索里面最经典的方法就是二分查找，针对的是有序的数组，可以减少复杂度
     * 数组排序的时间复杂度是O(N*log(N))，所以总的时间复杂度为O(N^3 * log(N))
     * @param n
     * @param m
     * @param arr
     * @return
     */
    private static boolean fourSum2(int n, int m, int[] arr){
        // as
        if(n < 0 || arr == null || arr.length != n){
            throw new IllegalArgumentException("Invalid input");
        }

        // init
        int i, j, k;
        Arrays.sort(arr);
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                for(k = 0; k < n; k++){
                    if(binarySearch(arr, m - arr[i] - arr[j] - arr[k])){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 能否在时间复杂度上做更好的优化呢，优化时间复杂度首先从去掉循环开始
     * 能否去掉倒数第二层循环呢？
     * 利用kc + kd = m - ka - kb
     * 但是不能直接二分查找，不妨先将kc + kd所得的值保存起来，排好序，再利用二分查找即可
     * @param n
     * @param m
     * @param arr
     * @return
     */
    private static boolean fourSum3(int n, int m, int[] arr){
        // as
        if(n < 0 || arr == null || arr.length != n){
            throw new IllegalArgumentException("Invalid input");
        }

        // init
        int i, j, k=0;
        int r = n * n;
        int[] tmp = new int[r];

        // 先缓存两层数组
        // 然后排完序
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                tmp[k++] = arr[i] + arr[j];
            }
        }

        Arrays.sort(tmp);

        // 进行搜索
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                if(binarySearch(tmp, m - arr[i] - arr[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        int n = 4;
//        int m = 9;
//        int[] arr = {1, 3, 5, 7};
        int n = 500;
        int m = 100;
        int [] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = Util.randomInt(1, 100000);
        }
        // 对比下时间
        System.out.println(fourSum1(n, m, arr));
        System.out.println(fourSum2(n, m, arr));
        System.out.println(fourSum3(n, m, arr));
    }
}
