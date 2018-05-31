package challenge.chapter2_4;

// 二叉搜索树
// 二叉搜索树是一种高效的搜索型数据结构
// 能够以O(log(N))的时间复杂度完成以下操作:
// 1. 插入操作 2. 查询操作 3. 删除操作
// 下面我们实现

// 二叉搜索树
class BinarySearchTree{
    // 定义树节点，二叉树只需要定义left和right
    private class Node{
        int val;
        Node left = null;
        Node right = null;
        Node(int val){
            this.val = val; // 此处价值
        }
    }

    // 二叉树根节点root
    // 初始化为null
    private Node root;

    private int size = 0;

    // 递归插入
    // 最终返回根节点
    private Node insertRecursively(Node p, int item){
        if(p == null)
            return new Node(item); // 如果当前为空
        else if(p.val >= item)
            p.left = insertRecursively(p.left, item);
        else
            p.right = insertRecursively(p.right, item);
        return p;
    }

    /**
     * 插入操作
     * 将item插入到该二叉树中
     * @param item 待插入值
     */
    public void insert(int item){
        // 添加进去后，返回来的还是根节点
        root = insertRecursively(root, item);
        size ++;
    }



    // 递归查找
    private boolean searchRecursively(Node p, int item){
        if(p == null) return false;
        if(p.val == item) return true;
        else if(p.val < item) return searchRecursively(p.right, item);
        else return searchRecursively(p.left, item);
    }

    /**
     * 查找操作
     * 查找是否包含item
     * @param item 待查找值
     *
     */
    public boolean search(int item){
        return searchRecursively(root, item);
    }


    // 递归删除
    // 删除的操作比较复杂，涉及到删除之后节点的移动
    // 返回的是Node,即返回的是
    private Node deleteRecursively(Node p, int item){
        Node r = p; // 返回的是当前的根节点
        if(p == null) return null;
        else if(p.val < item) p.right = deleteRecursively(p.right, item); // 递归右子树
        else if(p.val > item) p.left =  deleteRecursively(p.left, item); // 递归左子树
        // 剩余的情况是 p.val == item的情况
        // 左子树为空，直接将右子树提上去
        // p删除之后，可以将p
        else if(p.left == null)
            r = p.right;
        // 右子树为空，直接将左子树提上去
        else if(p.right == null)
            r = p.left;
        // 如果左儿子没有右儿子，那么返回左儿子
        else if(p.left.right == null)
            r = p.left;
        else if(p.right.left == null)
            r = p.right;
        // 将左子树上最大的节点返回
        else{
            Node q = p.left; // 取左子树
            // 需要取最右节点的父节点,在这种情况下不会出现q.right = null的情况
            while(q.right.right!=null)
                q = q.right;
            r = q.right;
            // 将q的右儿子提升上去
            q.right = null;
            r.left = p.left;
            r.right = p.right;
        }
        // help GC
        p.left = null;
        p.right = null;
        size--;
        return r;
    }

    /**
     * 删除操作
     * 删除操作的逻辑更复杂，关键在于删除某个节点之后的策略如何移动子树，使得整棵树依然满足二叉搜索树的性质
     *
     * 其删除的策略:
     * 1. 如果删除的节点没有左儿子，那么把右儿子提上去
     * 2. 否则如果删除的节点的左儿子没有右儿子，那么把左儿子提上去
     * 3. 如果以上2种情况均不满足，就把左儿子的子孙中的最大节点提到需要删除的节点上
     * 上述3种策略已经够用
     * 实际上，与之对称的策略，也可以加上也可以利用或的方式
     * 4. 如果删除的节点没有右儿子，可以把左儿子提上去
     * 5. 否则如果删除的节点的右儿子没有左儿子，那么把右儿子提上去
     * 6. 如果上述2种情况均不满足，则把右儿子的子孙中的最小节点提到删除的节点上
     * @param item 待删除的值item
     * @return 删除是否成功，如果存在删除则返回true, 否则返回false
     */
    public void delete(int item){
        // 如果返回为null, 则说明没有找到对应的节点
        root = deleteRecursively(root, item);

    }

    public int size(){
        return size;
    }

}


// 单元测试
public class P23 {

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        int[] test = {1,2 ,3 ,4, 5, 6,7};

        // 插入操作
        System.out.println("开始构造二叉搜索树");
        for(int t: test)
            tree.insert(t);
        System.out.println("构造二叉搜索树完成");

        // 查询操作
        System.out.println("开始搜索");
        for(int t : test){
            System.out.println(t + ":" + tree.search(t));
        }


        // 删除操作
        System.out.println("开始删除");
        for(int t: test){
            System.out.println(t + ":" + tree.search(t));
            tree.delete(t);
            System.out.println("size: " + tree.size());
            System.out.println(t + ":" + tree.search(t));
        }
    }

}
