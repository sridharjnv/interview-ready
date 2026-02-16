# Prefix Sum Pattern

## What Is It?
Prefix Sum is a technique where you precompute cumulative sums of an array so that any **range sum query** can be answered in **O(1)** time after **O(n)** preprocessing.

## When to Use It?
- Anytime you see **"sum of subarray"** or **"range sum"**
- When you need to compute sums of multiple subarrays efficiently
- When you see **"contiguous subarray with sum = k"**
- When the problem involves **frequency counts over ranges**
- When brute force involves nested loops summing subarrays

---

## 🧠 Templates to Memorize

### Template 1: Basic 1D Prefix Sum

```java
// Build: prefix[i] = sum of arr[0..i-1], prefix[0] = 0
// Query: sum(l..r) = prefix[r+1] - prefix[l]

int[] prefix = new int[n + 1]; // size n+1, prefix[0] = 0
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}
int rangeSum = prefix[r + 1] - prefix[l];
```

**Dry Run:**
```
Array:     [2,  4,  1,  3,  5]
Index:      0   1   2   3   4

Prefix:  [0, 2,  6,  7, 10, 15]
Index:    0  1   2   3   4   5

Sum(1..3) = prefix[4] - prefix[1] = 10 - 2 = 8  (4+1+3 = 8) ✅
```

> [!IMPORTANT]
> Always use `n+1` sized prefix array with `prefix[0] = 0` — it eliminates off-by-one edge cases.

---

### Template 2: Prefix Sum + HashMap (Subarray Sum = K)

```java
// Count subarrays whose sum equals k
// Key insight: if prefix[j] - prefix[i] = k, subarray (i..j) sums to k

Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1); // CRITICAL: empty prefix sum = 0
int currentSum = 0;
int count = 0;

for (int num : arr) {
    currentSum += num;
    count += prefixCount.getOrDefault(currentSum - k, 0);
    prefixCount.merge(currentSum, 1, Integer::sum);
}
return count;
```

> [!CAUTION]
> Never forget `prefixCount.put(0, 1)` — it accounts for subarrays starting at index 0.

---

### Template 3: 2D Prefix Sum

```java
// Build 2D prefix sum using inclusion-exclusion principle
int[][] prefix = new int[m + 1][n + 1];
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        prefix[i][j] = matrix[i-1][j-1]
                      + prefix[i-1][j]
                      + prefix[i][j-1]
                      - prefix[i-1][j-1];
    }
}

// Query: sum of submatrix (r1,c1) to (r2,c2)
int subMatrixSum = prefix[r2+1][c2+1]
                 - prefix[r1][c2+1]
                 - prefix[r2+1][c1]
                 + prefix[r1][c1];
```

---

## Common Mistakes to Avoid
1. **Off-by-one errors** — prefix array is size `n+1`, not `n`
2. **Forgetting `prefixCount.put(0, 1)`** in the HashMap variant
3. **Integer overflow** — use `long` if values can be large
4. **Modular arithmetic** — apply mod during prefix computation if needed

---

## 📝 10 Problems

### Easy (3 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 1 | Running Sum of 1D Array | [LC 1480](https://leetcode.com/problems/running-sum-of-1d-array/) | Basic prefix sum construction |
| 2 | Find Pivot Index | [LC 724](https://leetcode.com/problems/find-pivot-index/) | Left sum vs right sum |
| 3 | Range Sum Query - Immutable | [LC 303](https://leetcode.com/problems/range-sum-query-immutable/) | Classic range query (Template 1) |

### Medium (5 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 4 | Subarray Sum Equals K | [LC 560](https://leetcode.com/problems/subarray-sum-equals-k/) | Prefix Sum + HashMap (Template 2) |
| 5 | Continuous Subarray Sum | [LC 523](https://leetcode.com/problems/continuous-subarray-sum/) | Prefix sum + modular arithmetic |
| 6 | Product of Array Except Self | [LC 238](https://leetcode.com/problems/product-of-array-except-self/) | Prefix product + suffix product |
| 7 | Subarray Sums Divisible by K | [LC 974](https://leetcode.com/problems/subarray-sums-divisible-by-k/) | Prefix sum + mod + HashMap |
| 8 | Contiguous Array | [LC 525](https://leetcode.com/problems/contiguous-array/) | Convert 0→-1, then prefix sum |

### Hard (2 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 9 | Range Sum Query 2D - Immutable | [LC 304](https://leetcode.com/problems/range-sum-query-2d-immutable/) | 2D prefix sum (Template 3) |
| 10 | Number of Ways to Split Array | [LC 2270](https://leetcode.com/problems/number-of-ways-to-split-array-into-three-parts/) | Prefix sum + conditions |

---

## ✅ Progress Tracker

- [x] Problem 1 — Running Sum of 1D Array
- [x] Problem 2 — Find Pivot Index
- [x] Problem 3 — Range Sum Query - Immutable
- [ ] Problem 4 — Subarray Sum Equals K
- [ ] Problem 5 — Continuous Subarray Sum
- [ ] Problem 6 — Product of Array Except Self
- [ ] Problem 7 — Subarray Sums Divisible by K
- [ ] Problem 8 — Contiguous Array
- [ ] Problem 9 — Range Sum Query 2D - Immutable
- [ ] Problem 10 — Number of Ways to Split Array
