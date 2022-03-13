package Algo_Templates;

/***
 *  This is a template of Binary Indexed tree, also known as Fenwick tree, 树状数组
 *  Check leetcode 307 1649 for the usage
 *  1 constructor
 *  2 APIs
 */

public class BinaryIndexedTree {

    int[] BIT;

    public BinaryIndexedTree(int[] nums) {
        int N = nums.length;
        this.BIT = new int[N + 1];
        for (int idx = 0; idx < N; idx++) {
            this.update(idx, nums[idx]);
        }
    }

    public void update(int idx, int val) {
        for (++idx; idx < this.BIT.length; idx += (idx & (-idx))) {
            this.BIT[idx] += val;
        }
    }

    public int sum(int idx) {
        int res = 0;
        for (++idx; idx > 0; idx -= (idx & (-idx))) {
            res += this.BIT[idx];
        }
        return res;
    }

}
