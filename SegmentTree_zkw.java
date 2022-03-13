package Algo_Templates;

/**
 * segment tree implemented by 张昆玮(TsingHua)
 */

public class SegmentTree_zkw {
    int[] segTree;
    int N;

    public SegmentTree_zkw(int[] nums) {
        this.N = nums.length;
        this.segTree = new int[2 * N];
        for (int i = N; i < 2 * N; i++) {
            this.segTree[i] = nums[i - N];
        }
        for (int i = N - 1; i > 0; --i) {
            this.segTree[i] = this.segTree[2 * i] + this.segTree[2 * i + 1];
        }
    }

    public void update(int idx, int val) {
        int diff = val - this.segTree[idx + N];
        for (int i = idx + N; i > 0; i >>= 1) {
            this.segTree[i] += diff;
        }
    }

    public int rangeSum(int left, int right) {
        int res = 0;
        for (int i = left + N, j = right + N; i <= j; i >>= 1, j >>= 1) {
            if (i % 2 == 1) {
                res += this.segTree[i++];
            }
            if (j % 2 == 0) {
                res += this.segTree[j--];
            }
        }
        return res;
    }

}
