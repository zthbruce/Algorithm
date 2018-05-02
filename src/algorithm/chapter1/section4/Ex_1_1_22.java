package algorithm.chapter1.section4;

public class Ex_1_1_22 {
    /**
     * 经典二分查找，遍历算法
     * @param arr
     * @param key
     * @return index of key
     */
    private static int binarySearch_traverse(int[] arr, int key){
        // as
        if(arr == null){
            throw new NullPointerException("Params Error");
        }
        // init
        int i = 0;
        int j = arr.length - 1;

        // 首尾指针
        while(i <= j){
            int mid = i + ((j - i) >> 1); // middle index
            if(arr[mid] == key) return mid;
            else if(arr[mid] > key) j = mid -1;
            else i = mid + 1;
        }
        return -1; // if key not in arr
    }

    /**
     * 经典二分查找，递归算法，关键在于如何归约
     *
     */
    private static int binarySearch_recursion(int[] arr, int key){
        if(arr == null){
            throw new NullPointerException("Params Error");
        }
        int start = 0;
        int end = arr.length-1;
        return binarySearch_recursion(arr, key, start, end);
    }


    // 进行搜索
    private static int binarySearch_recursion(int[] arr, int key, int start, int end){
        if(start > end){
            return -1;
        }
        int mid = start + ((end - start ) >> 1);
        if(arr[mid] == key) return mid;
        else if(arr[mid] < key) return binarySearch_recursion(arr, key, mid + 1, end);
        else return binarySearch_recursion(arr, key, start, mid - 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(binarySearch_traverse(arr, 3));
        System.out.println(binarySearch_recursion(arr, 3));
    }
}
