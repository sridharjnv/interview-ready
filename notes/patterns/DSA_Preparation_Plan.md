# DSA Interview Preparation — Master Plan & Prefix Sum

## 📋 Your Plan: 10 Problems Per Pattern — Feedback

### ✅ What's Good About Your Plan
- **Pattern-based learning** is the #1 recommended approach — you're on the right track
- **10 problems per pattern** gives enough repetition to internalize the pattern
- **Sequential mastery** (finish one pattern before moving to the next) builds solid foundations

### ⚠️ Suggested Improvements

> [!IMPORTANT]
> **Quality > Quantity.** Don't just solve 10 problems — solve them in **3 tiers**: Easy → Medium → Hard. This builds progressive mastery.

| Aspect | Your Plan | Recommended |
|--------|-----------|-------------|
| Problems per pattern | 10 flat | **3 Easy + 5 Medium + 2 Hard** |
| Time per problem | No limit | **25 min attempt → see solution → re-solve next day** |
| Revision | Not mentioned | **Re-solve 2-3 problems from previous pattern weekly** |
| Template | Memorize one | ✅ **Correct! Always start with a template** |
| Tracking | Not mentioned | **Track time, approach used, and mistakes** |

### 🗺️ Recommended Pattern Order

Here's a battle-tested order that builds on previous patterns:

| # | Pattern | Why This Order |
|---|---------|---------------|
| 1 | **Prefix Sum** | Foundation for range queries |
| 2 | **Two Pointers** | Core technique, endless applications |
| 3 | **Sliding Window** | Builds on Two Pointers |
| 4 | **Fast & Slow Pointers** | Linked list mastery |
| 5 | **Binary Search** | Fundamental, tricky edge cases |
| 6 | **Stack / Monotonic Stack** | Key for next-greater/smaller problems |
| 7 | **HashMap / Counting** | Frequency-based problems (very common) |
| 8 | **BFS / DFS (Trees)** | Tree traversals |
| 9 | **BFS / DFS (Graphs)** | Graph problems |
| 10 | **Backtracking** | Combinations, permutations, subsets |
| 11 | **Dynamic Programming** | The boss level |
| 12 | **Greedy** | Interval and scheduling problems |
| 13 | **Heap / Priority Queue** | Top-K, merge-K problems |
| 14 | **Trie** | String search problems |
| 15 | **Union Find** | Connected components |

> [!TIP]
> **For SDE-1 → SDE-2 switch at 1 YOE**, focus heavily on patterns 1–11. Patterns 12–15 are good-to-have but less frequently asked.

---

## 🔥 Pattern 1: Prefix Sum

### What Is It?
Prefix Sum is a technique where you precompute cumulative sums of an array so that any **range sum query** can be answered in **O(1)** time after **O(n)** preprocessing.

### When to Use It?
- Anytime you see **"sum of subarray"** or **"range sum"**
- When you need to compute sums of multiple subarrays efficiently
- When you see **"contiguous subarray with sum = k"**
- When the problem involves **frequency counts over ranges**
- When brute force involves nested loops summing subarrays

### 🧠 Template to Memorize

```java
// ============================================
// TEMPLATE 1: Basic 1D Prefix Sum
// ============================================
// Build prefix sum array where prefix[i] = sum of arr[0..i-1]
int[] prefix = new int[n + 1]; // size n+1, prefix[0] = 0
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}
// Query: sum of arr[l..r] (inclusive) = prefix[r + 1] - prefix[l]
int rangeSum = prefix[r + 1] - prefix[l];


// ============================================
// TEMPLATE 2: Prefix Sum + HashMap (Subarray Sum = K)
// ============================================
// Count subarrays whose sum equals k
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1); // empty prefix has sum 0
int currentSum = 0;
int count = 0;

for (int num : arr) {
    currentSum += num;
    // If (currentSum - k) was seen before, those are valid subarrays
    count += prefixCount.getOrDefault(currentSum - k, 0);
    prefixCount.merge(currentSum, 1, Integer::sum);
}
return count;


// ============================================
// TEMPLATE 3: 2D Prefix Sum
// ============================================
// Build 2D prefix sum for matrix queries
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

### Key Insight Diagram

```
Array:     [2,  4,  1,  3,  5]
Index:      0   1   2   3   4

Prefix:  [0, 2,  6,  7, 10, 15]
Index:    0  1   2   3   4   5

Sum(1..3) = prefix[4] - prefix[1] = 10 - 2 = 8
            (4 + 1 + 3 = 8) ✅
```

### Common Mistakes to Avoid
1. **Off-by-one errors** — prefix array is size `n+1`, not `n`
2. **Forgetting `prefixCount.put(0, 1)`** in the HashMap variant — this accounts for subarrays starting at index 0
3. **Integer overflow** — use `long` if values can be large
4. **Modular arithmetic** — if problem says "return answer mod 10^9+7", apply mod during prefix computation

---

## 📝 10 Problems — Prefix Sum Pattern

Ordered by difficulty tier: **Easy → Medium → Hard**

### Easy (3 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 1 | Running Sum of 1D Array | [LC 1480](https://leetcode.com/problems/running-sum-of-1d-array/) | Basic prefix sum construction |
| 2 | Find Pivot Index | [LC 724](https://leetcode.com/problems/find-pivot-index/) | Left sum vs right sum using prefix |
| 3 | Range Sum Query - Immutable | [LC 303](https://leetcode.com/problems/range-sum-query-immutable/) | Classic prefix sum range query |

### Medium (5 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 4 | Subarray Sum Equals K | [LC 560](https://leetcode.com/problems/subarray-sum-equals-k/) | Prefix Sum + HashMap (Template 2) |
| 5 | Continuous Subarray Sum | [LC 523](https://leetcode.com/problems/continuous-subarray-sum/) | Prefix sum with modular arithmetic |
| 6 | Product of Array Except Self | [LC 238](https://leetcode.com/problems/product-of-array-except-self/) | Prefix product + suffix product |
| 7 | Subarray Sums Divisible by K | [LC 974](https://leetcode.com/problems/subarray-sums-divisible-by-k/) | Prefix sum + mod + HashMap |
| 8 | Contiguous Array | [LC 525](https://leetcode.com/problems/contiguous-array/) | Prefix sum trick (convert 0→-1) |

### Hard (2 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 9 | Range Sum Query 2D - Immutable | [LC 304](https://leetcode.com/problems/range-sum-query-2d-immutable/) | 2D prefix sum (Template 3) |
| 10 | Number of Ways to Split Array | [LC 2270](https://leetcode.com/problems/number-of-ways-to-split-array-into-three-parts/) | Prefix sum + conditions |

---

## 🎯 How to Solve Each Problem

**For each problem, follow this process:**
1. **Read the problem** — identify that it's a Prefix Sum problem
2. **Pick the right template** (1, 2, or 3)
3. **Try solving within 25 minutes**
4. **If stuck** — look at the template, try again for 10 more minutes
5. **If still stuck** — study the solution, understand it, then re-solve from scratch
6. **Next day** — re-solve problems you struggled with (spaced repetition)

## Verification Plan

### Progress Tracking
- Solutions will be written in `/Users/sridhar/Documents/java/src/` under a `dsa/prefixsum/` package
- Each solution file will be a standalone Java class with `main()` method for local testing
- We will discuss each solution after you attempt it

### How We Continue
After finishing 10 Prefix Sum problems → move to Pattern 2 (Two Pointers) with its own template and 10 problems.

---

## 🔥 Pattern 2: Two Pointers

> **Full guide:** [TwoPointers.md](file:///Users/sridhar/Documents/java/notes/patterns/twopointers/TwoPointers.md)

### What Is It?
Two Pointers uses two index variables moving through a data structure in a coordinated way to solve problems in **O(n)** instead of **O(n²)**.

### When to Use It?
- **Sorted array** + pairs/triplets with target sum
- **Remove duplicates** or elements in-place
- **Palindrome** checks
- **Container/trapping water** problems
- Any problem where brute force uses nested loops over pairs

### 🧠 Templates
1. **Opposite Direction** — pair sum in sorted array (`left++` / `right--`)
2. **Read/Write (same direction)** — remove duplicates, partition in-place
3. **Greedy Shrink** — container with most water, maximize/minimize by shrinking boundaries
4. **3Sum** — fix one element, two-pointer on the rest

### 📝 10 Problems — Two Pointers Pattern

#### Easy (3 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 1 | Valid Palindrome | [LC 125](https://leetcode.com/problems/valid-palindrome/) | Opposite direction, skip non-alphanumeric |
| 2 | Two Sum II - Input Array Is Sorted | [LC 167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Classic opposite direction (Template 1) |
| 3 | Remove Duplicates from Sorted Array | [LC 26](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Read/Write pointers (Template 2) |

#### Medium (5 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 4 | 3Sum | [LC 15](https://leetcode.com/problems/3sum/) | Fix + Two Pointers + duplicate handling |
| 5 | Container With Most Water | [LC 11](https://leetcode.com/problems/container-with-most-water/) | Greedy shrink (Template 3) |
| 6 | Sort Colors (Dutch National Flag) | [LC 75](https://leetcode.com/problems/sort-colors/) | Three-way partition |
| 7 | Boats to Save People | [LC 881](https://leetcode.com/problems/boats-to-save-people/) | Greedily pair heaviest with lightest |
| 8 | Two Sum IV - Input is a BST | [LC 653](https://leetcode.com/problems/two-sum-iv-input-is-a-bst/) | Inorder traversal + two pointers |

#### Hard (2 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 9 | Trapping Rain Water | [LC 42](https://leetcode.com/problems/trapping-rain-water/) | Opposite direction + max-height tracking |
| 10 | 4Sum | [LC 18](https://leetcode.com/problems/4sum/) | Nested fix + Two Pointers + dedup |

---

## 🔥 Pattern 3: Sliding Window — Fixed Size

> **Full guide:** [SlidingWindowFixedSize.md](file:///Users/sridhar/Documents/java/notes/patterns/slidingwindow/SlidingWindowFixedSize.md)

### What Is It?
Maintain a window of fixed size `k` and slide it across the array, updating the result by adding the new element and removing the old one — all in **O(n)** time.

### When to Use It?
- **"Subarray of size k"** — max sum, average, count
- **"Anagram/permutation in string"** — window = pattern length
- **"Contains duplicate within distance k"**
- Any contiguous window of **known, fixed** size

### 🧠 Templates
1. **Running Sum** — add new, remove old, track max/min
2. **Frequency Array + Matches** — anagram/permutation detection
3. **Monotonic Deque** — sliding window maximum/minimum

### 📝 10 Problems — Sliding Window Fixed Size

#### Easy (3 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 1 | Maximum Average Subarray I | [LC 643](https://leetcode.com/problems/maximum-average-subarray-i/) | Basic fixed window sum |
| 2 | Contains Duplicate II | [LC 219](https://leetcode.com/problems/contains-duplicate-ii/) | Fixed window with HashSet |
| 3 | Sub-arrays of Size K with Avg ≥ Threshold | [LC 1343](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/) | Fixed window sum + condition |

#### Medium (5 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 4 | Permutation in String | [LC 567](https://leetcode.com/problems/permutation-in-string/) | Frequency array + matches counter |
| 5 | Find All Anagrams in a String | [LC 438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Collect all anagram positions |
| 6 | Maximum Vowels in Substring of Given Length | [LC 1456](https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/) | Fixed window with vowel counter |
| 7 | K Radius Subarray Averages | [LC 2090](https://leetcode.com/problems/k-radius-subarray-averages/) | Centered window of size 2k+1 |
| 8 | Maximum Points from Cards | [LC 1423](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/) | Reverse: minimize middle window of n-k |

#### Hard (2 problems)

| # | Problem | LeetCode Link | Key Concept |
|---|---------|---------------|-------------|
| 9 | Sliding Window Maximum | [LC 239](https://leetcode.com/problems/sliding-window-maximum/) | Monotonic deque |
| 10 | Substring with Concatenation of All Words | [LC 30](https://leetcode.com/problems/substring-with-concatenation-of-all-words/) | Word-level frequency map |

