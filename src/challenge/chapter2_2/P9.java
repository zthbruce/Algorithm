package challenge.chapter2_2;

// 贪心算法
// 字典序这种问题很适合使用
public class P9 {
    /**
     * 字典序最小问题
     * 跟字典序相关的问题，经常可以使用贪心算法，
     * 因为字典序具有贪心的性质，只要当前的字符存在大小关系，字典序的大小关系就已经确定
     * 给定字符串S, 构造相同长度的字符串T。起初，T是一个空串，随后反复进行下列任意操作
     * 从S的头部删除一个字符，加到T的尾部
     * 从S的尾部删除一个字符，加到T的尾部
     * 构造字典序最小的字符串
     * @param s 原字符串
     * @return 生成的字典序最小字符串
     */
    private static String bestCowLine(String s){
        // as
        if(s == null){
            throw new IllegalArgumentException("Invalid input");
        }
        // bs
        int size = s.length();
        if(size <= 1){
            return s;
        }

        // init
        // 首尾指针
        int i = 0;
        int j = size - 1;
        int k, l;
        StringBuilder t = new StringBuilder();

        // 贪心算法，取当前比较中较小的值
        // 这种写法，感觉很臃肿，能不能有更好的代码呢？
        // 实际上，其逻辑为
        // 如果left < right, 取left
        // 如果left > right, 取right
        // 如果相等，则进入下一次循环，继续判断这就是循环不变式
        // 所以通过两个循环进行判断
        while(i <= j){
            k = i;
            l = j;
            boolean left = false; // 初始化为false
            // 循环的结束条件为k = l, 其实当k = l时，说明了取left或者right都随意
            while(k < l){
                if(s.charAt(k) < s.charAt(l)){
                    // 取左侧
                    left = true;
                    break;
                }else if(s.charAt(k) > s.charAt(l)){
                    break;
                }
                // 如果相等，继续下一轮比较即可
                k++;
                l--;
            }
            if(left){
                t.append(s.charAt(i++));
            }else{
                t.append(s.charAt(j--));
            }
        }
        return t.toString();


        // original 写法，写的好丑陋
//        while(i <= j){
//            // 如果
//            char left = s.charAt(i);
//            char right = s.charAt(j);
//            // 字符是可以直接比较的
//            if(left < right){
//                t.append(left);
//                i++;
//            }else if(left > right){
//                t.append(right);
//                j--;
//            }else{
//                // 如果两者相等, 是否取哪个都不会有影响呢？
//                // 当然会有影响，我们希望越小的值越早出来
//                if(i == j){
//                    t.append(left); // 如果只剩一个最后一个字符，直接添加即可
//                    i++;
//                }else{
////                    char ele = left; // 首先初始化为ele
//                    boolean isLeft = false;
//                    int k = i+1;
//                    int l = j-1;
//                    while(k < l){
//                        if(s.charAt(k) < s.charAt(l)){
//                            isLeft = true;
////                            ele = left;
//                            break;
//                        }else if(s.charAt(k) > s.charAt(l)){
////                            isLeft = false;
////                            ele = right;
//                            break;
//                        }
//                    }
//                    if(isLeft){
//                        t.append(left);
//                        i++;
//                    }else{
//                        t.append(right);
//                        j--;
//                    }
//                }
//            }
//
//        }
//        return t.toString(); // 返回内容


    }
    public static void main(String[] args) {
        String s = "ACDBCB";
        System.out.println(bestCowLine(s));
    }
}

