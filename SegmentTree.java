package Algo_Templates;

public class SegmentTree {
    class Node{
        int start, end, sum;
        Node left, right;
        public Node(int i, int j, int sum){
            this.start = i;
            this.end = j;
            this.sum = sum;
            this.left = null;
            this.right = null;
        }
    }

    Node root;
    int[] nums;

    public SegmentTree(int[] nums){
        this.nums = nums;
        this.root = buildTree(nums, 0, nums.length - 1);
    }

    public Node buildTree(int[] nums, int start, int end){
        if(start > end){
            return null;
        }
        if(start == end){
            return new Node(start, end, nums[start]);
        }
        int mid = start + (end - start) / 2;
        Node root = new Node(start, end, 0);
        root.left = buildTree(nums, start, mid);
        root.right = buildTree(nums, mid + 1, end);
        root.sum = root.left.sum + root.right.sum;
        return root;
    }

    public void update(Node node, int idx, int val){
        if(node.start == node.end){
            node.sum = val;
            return;
        }
        int mid = node.start + (node.end - node.start) / 2;
        if(idx <= mid){
            update(node.left, idx, val);
        }else{
            update(node.right, idx, val);
        }
        node.sum = node.left.sum + node.right.sum;
    }

    public int rangeSum(Node node, int start, int end){
        if(start > end){
            return 0;
        }
        if(start == node.start && end == node.end){
            return node.sum;
        }
        int mid = node.start + (node.end - node.start) / 2;
        if(end <= mid){
            return rangeSum(node.left, start, end);
        }else if(start > mid){
            return rangeSum(node.right, start, end);
        }else{
            return rangeSum(node.left, start, mid) + rangeSum(node.right, mid + 1, end);
        }
    }

}
