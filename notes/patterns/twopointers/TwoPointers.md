# Two Pointers Pattern

## What Is It?

Two Pointers is a technique where you use **two index variables** (pointers) that move through a data structure — typically an array or string — in a coordinated way to solve problems in **O(n)** time instead of **O(n²)**.

The key idea: instead of checking every possible pair `(i, j)` with nested loops, you use **constraints** (like sorted order, or a target condition) to **skip unnecessary pairs** by intelligently moving one pointer or the other.

### Types of Two Pointer Patterns

```
1. OPPOSITE DIRECTION (converging)      2. SAME DIRECTION (fast & slow)
   ┌─────────────────────┐                ┌─────────────────────┐
   │  L →           ← R  │                │  S → ... F →        │
   │     converge to      │                │  both move right     │
   │       middle         │                │  at different speeds  │
   └─────────────────────┘                └─────────────────────┘

3. TWO ARRAYS (merge-style)             4. PARTITION / REMOVE
   Array1:  [p1 →          ]              ┌─────────────────────┐
   Array2:  [p2 →          ]              │  W →     R →        │
   Both move forward                      │  write   read       │
                                          │  (slow)  (fast)     │
                                          └─────────────────────┘
```

---

## When to Use It?

- **Sorted array** + "find pair with sum/difference = target" → **opposite-direction** pointers
- **"Remove duplicates"** or **"remove element in-place"** → **read/write** pointers
- **"Merge two sorted arrays"** → **two-array** pointers
- **"Is palindrome?"** → **opposite-direction** pointers
- **"Container with most water"** or **"trapping rain water"** → **opposite-direction** with greedy shrink
- **String/array problems** where brute force is O(n²) nested loops checking pairs
- **"Partition array"** around a condition → **read/write** pointers

> [!TIP]
> **Rule of thumb:** If a problem involves a **sorted array** and **pairs/triplets**, think Two Pointers first. If it involves **removing/partitioning in-place**, think read/write pointers.

---

## 🧠 Templates to Memorize

### Template 1: Opposite Direction — Pair Sum in Sorted Array

**Use when:** Array is sorted, find two elements satisfying a condition (sum, difference, etc.)

```java
// Find pair in sorted array with sum = target
// Time: O(n), Space: O(1)

int left = 0, right = arr.length - 1;

while (left < right) {
    int sum = arr[left] + arr[right];

    if (sum == target) {
        // Found! Process result
        return new int[]{left, right};
    } else if (sum < target) {
        left++;   // need bigger sum → move left pointer right
    } else {
        right--;  // need smaller sum → move right pointer left
    }
}
return new int[]{-1, -1}; // no pair found
```

**Why does this work?**
```
Sorted: [1, 3, 5, 7, 9, 11]   target = 12

Step 1: L=0(1), R=5(11) → sum=12 → Found! ✅

But say target = 10:
Step 1: L=0(1), R=5(11) → sum=12 > 10 → R-- (11 is too big for ANY left element)
Step 2: L=0(1), R=4(9)  → sum=10 → Found! ✅

Key insight: When sum > target and we move R left, we're not missing any pairs.
Because arr[R] + arr[any index > L] will ALSO be > target (array is sorted).
So arr[R] cannot be part of the answer → safe to eliminate it.
```

> [!IMPORTANT]
> This only works on **sorted arrays**. If the array isn't sorted, either sort it first (O(n log n)) or use a HashMap approach instead.

---

### Template 2: Same Direction — Remove Duplicates / Remove Element (Read-Write)

**Use when:** Modify array in-place, removing elements or deduplicating.

```java
// Remove duplicates from sorted array in-place
// write = position to place next unique element
// read = scans through the array
// Time: O(n), Space: O(1)

int write = 1; // first element is always unique

for (int read = 1; read < arr.length; read++) {
    if (arr[read] != arr[write - 1]) {
        arr[write] = arr[read];
        write++;
    }
}
return write; // new length of array without duplicates
```

**Dry Run:**
```
Array: [1, 1, 2, 2, 3]

read=1: arr[1]=1, arr[write-1]=arr[0]=1 → duplicate, skip
        [1, 1, 2, 2, 3]   write=1
         W  R

read=2: arr[2]=2, arr[write-1]=arr[0]=1 → unique! arr[1]=2, write++
        [1, 2, 2, 2, 3]   write=2
            W     R

read=3: arr[3]=2, arr[write-1]=arr[1]=2 → duplicate, skip
        [1, 2, 2, 2, 3]   write=2
            W        R

read=4: arr[4]=3, arr[write-1]=arr[1]=2 → unique! arr[2]=3, write++
        [1, 2, 3, 2, 3]   write=3
               W        R

Result: first 3 elements = [1, 2, 3] ✅
```

> [!NOTE]
> The "write" pointer always points to where the **next valid element** should go. Everything before `write` is the "processed" portion.

---

### Template 3: Opposite Direction — Container With Most Water / Two Sum Variants

**Use when:** Maximize/minimize something by choosing two boundaries, with a greedy shrink strategy.

```java
// Container With Most Water pattern
// Greedy: always move the pointer with the SHORTER height
// Time: O(n), Space: O(1)

int left = 0, right = height.length - 1;
int maxArea = 0;

while (left < right) {
    int width = right - left;
    int h = Math.min(height[left], height[right]);
    maxArea = Math.max(maxArea, width * h);

    // Greedy: move the shorter side — it's the bottleneck
    if (height[left] < height[right]) {
        left++;
    } else {
        right--;
    }
}
return maxArea;
```

**Why move the shorter side?**
```
Heights: [1, 8, 6, 2, 5, 4, 8, 3, 7]

L=0(h=1), R=8(h=7): area = min(1,7) × 8 = 8
  → L is shorter. Moving R won't help because height is capped at 1.
  → Moving L gives us a CHANCE at a taller left wall.

L=1(h=8), R=8(h=7): area = min(8,7) × 7 = 49
  → R is shorter. Move R? No, move the shorter one (R).

...and so on. We never miss the optimal because:
The shorter wall CANNOT produce a larger area with ANY other wall
(width only decreases, height is capped by the shorter wall).
```

---

### Template 4: Three Pointers — 3Sum Pattern

**Use when:** Find triplets satisfying a condition. Fix one element, then use Template 1 on the rest.

```java
// 3Sum: find all unique triplets with sum = 0
// Fix arr[i], then use two pointers on arr[i+1..n-1]
// Time: O(n²), Space: O(1) excluding output

Arrays.sort(arr); // MUST sort first

List<List<Integer>> result = new ArrayList<>();

for (int i = 0; i < arr.length - 2; i++) {
    // Skip duplicate values of i
    if (i > 0 && arr[i] == arr[i - 1]) continue;

    int left = i + 1, right = arr.length - 1;
    int target = -arr[i]; // looking for two numbers summing to -arr[i]

    while (left < right) {
        int sum = arr[left] + arr[right];

        if (sum == target) {
            result.add(Arrays.asList(arr[i], arr[left], arr[right]));
            // Skip duplicates for left and right
            while (left < right && arr[left] == arr[left + 1]) left++;
            while (left < right && arr[right] == arr[right - 1]) right--;
            left++;
            right--;
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
}
return result;
```

> [!CAUTION]
> **Handling duplicates in 3Sum is the #1 source of bugs.** You must skip duplicates at BOTH levels — the outer loop (`i`) and the inner pointers (`left`, `right`). Missing either one will produce duplicate triplets.

---

## 🔑 Decision Framework

Use this flowchart when you see a Two Pointer problem:

```
Is the array SORTED (or can you sort it)?
├── YES → Do you need pairs/triplets with a sum/target?
│         ├── YES, pairs → Template 1 (opposite direction)
│         └── YES, triplets → Template 4 (fix one + two pointers)
│
├── YES → Do you need to remove/deduplicate in-place?
│         └── YES → Template 2 (read/write pointers)
│
├── YES → Is it a "maximize area/distance" problem?
│         └── YES → Template 3 (greedy shrink)
│
└── NO  → Can you use two pointers WITHOUT sorting?
          ├── Palindrome check → opposite direction on string
          ├── Merge two lists → two-array pointers
          └── Partition → read/write pointers
```

---

## Common Mistakes to Avoid

1. **Forgetting to sort** — opposite-direction Two Pointers on an unsorted array gives wrong results
2. **Using `left <= right` instead of `left < right`** — for pair-finding you need two distinct elements, so use strict `<`
3. **Not handling duplicates** — especially in 3Sum; always skip duplicate values after finding a match
4. **Off-by-one in read/write** — `write` starts at 0 or 1 depending on the problem. Think carefully about what "write position" means
5. **Infinite loops** — make sure at least one pointer moves in every iteration
6. **Modifying the array when you shouldn't** — some problems expect the original array untouched; read the problem carefully

---

## Complexity Analysis

| Variant | Time | Space | When |
|---------|------|-------|------|
| Opposite Direction | O(n) | O(1) | Pair sum, palindrome, container |
| Read/Write | O(n) | O(1) | Remove duplicates, partition |
| 3Sum (fix + 2ptr) | O(n²) | O(1) | Triplet problems |
| Two Arrays | O(n + m) | O(1)* | Merge, intersection |

*\*Output space may be O(n+m) but auxiliary space is O(1)*

---

## 📝 10 Problems

### Easy (3 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 1 | Valid Palindrome | [LC 125](https://leetcode.com/problems/valid-palindrome/) | Opposite direction, skip non-alphanumeric |
| 2 | Two Sum II - Input Array Is Sorted | [LC 167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Classic opposite direction (Template 1) |
| 3 | Remove Duplicates from Sorted Array | [LC 26](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Read/Write pointers (Template 2) |

### Medium (5 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 4 | 3Sum | [LC 15](https://leetcode.com/problems/3sum/) | Fix + Two Pointers + duplicate handling (Template 4) |
| 5 | Container With Most Water | [LC 11](https://leetcode.com/problems/container-with-most-water/) | Greedy shrink (Template 3) |
| 6 | Sort Colors (Dutch National Flag) | [LC 75](https://leetcode.com/problems/sort-colors/) | Three-way partition with two pointers |
| 7 | Boats to Save People | [LC 881](https://leetcode.com/problems/boats-to-save-people/) | Greedily pair heaviest with lightest |
| 8 | Two Sum IV - Input is a BST | [LC 653](https://leetcode.com/problems/two-sum-iv-input-is-a-bst/) | Inorder traversal + two pointers |

### Hard (2 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 9 | Trapping Rain Water | [LC 42](https://leetcode.com/problems/trapping-rain-water/) | Opposite direction with max-height tracking |
| 10 | 4Sum | [LC 18](https://leetcode.com/problems/4sum/) | Nested fix + Two Pointers + dedup |

---

## 🧩 Problem-Solving Hints (read after attempting!)

<details>
<summary>Problem 1: Valid Palindrome — Hint</summary>

Use `left = 0, right = s.length() - 1`. Skip characters that are not letters/digits using `Character.isLetterOrDigit()`. Compare lowercase versions. Classic opposite-direction.
</details>

<details>
<summary>Problem 2: Two Sum II — Hint</summary>

Exact Template 1. Array is already sorted. Move left if sum < target, move right if sum > target.
</details>

<details>
<summary>Problem 3: Remove Duplicates — Hint</summary>

Exact Template 2. The `write` pointer starts at 1 (first element is always kept).
</details>

<details>
<summary>Problem 4: 3Sum — Hint</summary>

Sort first. Fix `arr[i]`, then use Two Pointers on `[i+1, n-1]` to find pairs summing to `-arr[i]`. Skip duplicates at every level. Template 4.
</details>

<details>
<summary>Problem 5: Container With Most Water — Hint</summary>

Template 3. Area = min(h[L], h[R]) × (R - L). Always move the shorter side.
</details>

<details>
<summary>Problem 6: Sort Colors — Hint</summary>

Three pointers: `low`, `mid`, `high`. Move all 0s before `low`, all 2s after `high`. `mid` scans through. Swap `arr[mid]` with `arr[low]` if 0, swap with `arr[high]` if 2, just advance `mid` if 1.
</details>

<details>
<summary>Problem 7: Boats to Save People — Hint</summary>

Sort the array. Try pairing the lightest person (`left`) with the heaviest (`right`). If they fit together, move both pointers. If not, the heaviest goes alone (move `right` only). Always need one boat per iteration.
</details>

<details>
<summary>Problem 8: Two Sum IV (BST) — Hint</summary>

Do an inorder traversal to get a sorted array, then apply Template 1. Alternatively, use two iterators (one forward inorder, one reverse inorder) for O(h) space.
</details>

<details>
<summary>Problem 9: Trapping Rain Water — Hint</summary>

Two pointers from both ends. Track `leftMax` and `rightMax`. Water at any position = `min(leftMax, rightMax) - height[i]`. Process the side with the smaller max first (similar greedy logic to Container With Most Water).
</details>

<details>
<summary>Problem 10: 4Sum — Hint</summary>

Two nested loops (fix `arr[i]` and `arr[j]`), then Two Pointers on `[j+1, n-1]`. Skip duplicates at every level. Time: O(n³).
</details>

---

## ✅ Progress Tracker

- [ ] Problem 1 — Valid Palindrome
- [ ] Problem 2 — Two Sum II - Input Array Is Sorted
- [ ] Problem 3 — Remove Duplicates from Sorted Array
- [ ] Problem 4 — 3Sum
- [ ] Problem 5 — Container With Most Water
- [ ] Problem 6 — Sort Colors (Dutch National Flag)
- [ ] Problem 7 — Boats to Save People
- [ ] Problem 8 — Two Sum IV - Input is a BST
- [ ] Problem 9 — Trapping Rain Water
- [ ] Problem 10 — 4Sum
