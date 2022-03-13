package Algo_Templates;

import java.util.*;

// leet code 218
public class interval_max_segment_tree {
    /*
        interval segment tree
        the leaf node is the minimum interval.
                          [1,9]
                         /    \
                    [1,5]     [6,9]
                      / \       / \
                 [1,3] [4,5] [6,7] [8,9]
    */
    class Node{
        int start, end, maximum;
        Node left, right;

        public Node(int i, int j){
            start = i;
            end = j;
            maximum = 0;
            left = null;
            right = null;
        }
    }

    class SegmentTree{
        Node root;

        public SegmentTree(int[] nums){
            int n = nums.length;
            this.root = buildTree(nums, 0, n - 1);
        }

        private Node buildTree(int[] nums, int i, int j){
            if(i >= j){
                return null;
            }
            if(i + 1 == j){
                return new Node(nums[i], nums[j] - 1);
            }
            Node node = new Node(nums[i], nums[j] - 1);
            int mid = i + (j - i) / 2;
            node.left = buildTree(nums, i, mid);
            node.right = buildTree(nums, mid, j);
            return node;
        }

        public void update(Node root, int i, int j, int val){
            if(i > j){
                return;
            }
            if(root.start == i && root.end == j && root.left == null){
                root.maximum = Math.max(root.maximum, val);
                return;
            }
            int mid = root.left.end;
            if(mid < i){
                update(root.right, i, j, val);
            }else if(j <= mid){
                update(root.left, i, j, val);
            }else{
                update(root.left, i, mid, val);
                update(root.right, mid + 1, j, val);
            }
            root.maximum = Math.max(root.left.maximum, root.right.maximum);
        }

        public int rangeMax(Node root, int i, int j){
            if(i > j){
                return 0;
            }

            if(i <= root.start && root.end <= j){
                return root.maximum;
            }

            int mid = root.left.end;
            if(j <= mid){
                return rangeMax(root.left, i, j);
            }
            if(i > mid){
                return rangeMax(root.right, i, j);
            }
            return Math.min(rangeMax(root.left, i, mid), rangeMax(root.right, mid + 1, j));
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        Set<Integer> set = new HashSet<>();
        for(int[] building: buildings){
            set.add(building[0]);
            set.add(building[1]);
        }
        int[] x = new int[set.size()];
        int idx = 0;
        for(int i : set){
            x[idx++] = i;
        }
        Arrays.sort(x);
        SegmentTree st = new SegmentTree(x);
        for(int[] building: buildings){
            st.update(st.root, building[0], building[1] - 1, building[2]);
        }
        int prev = 0;
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i < x.length - 1; i++){
            int h = st.rangeMax(st.root, x[i], x[i + 1] - 1);
            if(h != prev){
                res.add(Arrays.asList(x[i], h));
                prev = h;
            }
        }
        res.add(Arrays.asList(x[x.length - 1], 0));
        return res;
    }
}
