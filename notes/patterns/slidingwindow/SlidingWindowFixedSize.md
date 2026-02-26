# Sliding Window — Fixed Size Pattern

## What Is It?

Sliding Window is a technique where you maintain a **window** (a contiguous subarray/substring) of fixed or variable size and **slide** it across the array, updating the result by **adding the new element** entering the window and **removing the old element** leaving it.

This guide covers the **Fixed-Size** variant — the window size `k` is given in the problem.

```
Array: [2, 1, 5, 1, 3, 2]    k = 3

Window 1: [2, 1, 5]  1, 3, 2     sum = 8
Window 2:  2 [1, 5, 1] 3, 2      sum = 8 - 2 + 1 = 7
Window 3:  2, 1 [5, 1, 3] 2      sum = 7 - 1 + 3 = 9
Window 4:  2, 1, 5 [1, 3, 2]     sum = 9 - 5 + 2 = 6

→ The window SLIDES one step right each time
→ We DON'T recompute the sum from scratch — we just add/remove ONE element
```

### Why Not Just Use Prefix Sum?

| Approach | Time | Works For |
|----------|------|-----------|
| Prefix Sum | O(n) | **Sum** of subarrays only |
| Sliding Window | O(n) | **Sum, max, min, count, distinct, average** — anything you can maintain incrementally |

Prefix Sum is limited to sums. Sliding Window is a **generalized** technique that works for any aggregate you can update in O(1) when adding/removing one element.

---

## When to Use It?

Look for these keywords in the problem:

- **"subarray of size k"** or **"substring of length k"** → guaranteed fixed window
- **"maximum/minimum sum of k consecutive elements"**
- **"average of subarrays of size k"**
- **"contains duplicate within distance k"**
- **"permutation/anagram in a string"** (fixed window = length of pattern)
- **Any problem with a contiguous window of known size**

> [!TIP]
> **How to distinguish Fixed vs Variable Sliding Window:**
> - **Fixed:** window size `k` is **given** — you just slide it
> - **Variable:** you **grow/shrink** the window to find the optimal size (covered in the next pattern)

---

## 🧠 Templates to Memorize

### Template 1: Fixed Window — Maximum/Minimum Sum

The most basic fixed sliding window. Maintain a running sum and slide.

```java
// Find maximum sum of any subarray of size k
// Time: O(n), Space: O(1)

public int maxSumSubarray(int[] arr, int k) {
    // Step 1: Compute the sum of the first window
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }

    int maxSum = windowSum;

    // Step 2: Slide the window — remove leftmost, add rightmost
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i];       // add element entering window
        windowSum -= arr[i - k];   // remove element leaving window
        maxSum = Math.max(maxSum, windowSum);
    }

    return maxSum;
}
```

**Dry Run:**
```
Array: [2, 1, 5, 1, 3, 2]    k = 3

Initial window [0..2]: sum = 2 + 1 + 5 = 8     maxSum = 8

i=3: add arr[3]=1, remove arr[0]=2 → sum = 8 + 1 - 2 = 7    maxSum = 8
i=4: add arr[4]=3, remove arr[1]=1 → sum = 7 + 3 - 1 = 9    maxSum = 9
i=5: add arr[5]=2, remove arr[2]=5 → sum = 9 + 2 - 5 = 6    maxSum = 9

Answer: 9 ✅ (subarray [5, 1, 3])
```

> [!IMPORTANT]
> **The Key Insight:** Each slide is O(1) — you only add 1 element and remove 1 element. This turns what would be O(n×k) brute force into O(n).

---

### Template 2: Fixed Window with HashMap — Anagram / Permutation Detection

When you need to track **character frequencies** in a window of fixed size.

```java
// Check if string s2 contains any permutation of s1
// Window size = s1.length(), slide over s2
// Time: O(n), Space: O(1) — at most 26 chars

public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;

    int k = s1.length(); // fixed window size

    // Step 1: Build frequency map of the pattern
    int[] patternFreq = new int[26];
    int[] windowFreq = new int[26];

    for (int i = 0; i < k; i++) {
        patternFreq[s1.charAt(i) - 'a']++;
        windowFreq[s2.charAt(i) - 'a']++;
    }

    // Step 2: Check first window, then slide
    if (Arrays.equals(patternFreq, windowFreq)) return true;

    for (int i = k; i < s2.length(); i++) {
        windowFreq[s2.charAt(i) - 'a']++;       // add new char
        windowFreq[s2.charAt(i - k) - 'a']--;   // remove old char

        if (Arrays.equals(patternFreq, windowFreq)) return true;
    }

    return false;
}
```

**Optimization — Avoid comparing arrays every time:**

```java
// Instead of Arrays.equals on every slide, track a "matches" counter
// A "match" = a character whose frequency in window == frequency in pattern
// When matches == 26, all characters match → it's a permutation

public boolean checkInclusionOptimized(String s1, String s2) {
    if (s1.length() > s2.length()) return false;

    int k = s1.length();
    int[] patternFreq = new int[26];
    int[] windowFreq = new int[26];

    for (int i = 0; i < k; i++) {
        patternFreq[s1.charAt(i) - 'a']++;
        windowFreq[s2.charAt(i) - 'a']++;
    }

    int matches = 0;
    for (int i = 0; i < 26; i++) {
        if (patternFreq[i] == windowFreq[i]) matches++;
    }

    for (int i = k; i < s2.length(); i++) {
        if (matches == 26) return true;

        // Add new character
        int addIdx = s2.charAt(i) - 'a';
        windowFreq[addIdx]++;
        if (windowFreq[addIdx] == patternFreq[addIdx]) matches++;
        else if (windowFreq[addIdx] == patternFreq[addIdx] + 1) matches--;

        // Remove old character
        int remIdx = s2.charAt(i - k) - 'a';
        windowFreq[remIdx]--;
        if (windowFreq[remIdx] == patternFreq[remIdx]) matches++;
        else if (windowFreq[remIdx] == patternFreq[remIdx] - 1) matches--;
    }

    return matches == 26;
}
```

> [!NOTE]
> The `matches` counter tracks how many of the 26 letters have the same frequency in both maps. When `matches == 26`, the window is a permutation of the pattern.

---

### Template 3: Fixed Window with Deque — Sliding Window Maximum

When you need the **maximum (or minimum)** element in each window position. Uses a **monotonic deque**.

```java
// Find the max element in every window of size k
// Time: O(n), Space: O(k)

public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];

    // Deque stores INDICES, front = index of current max
    // Invariant: values at deque indices are in DECREASING order
    Deque<Integer> deque = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        // Remove indices that are out of the current window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }

        // Remove indices of elements SMALLER than current (they'll never be max)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }

        deque.offerLast(i);

        // Once we've processed at least k elements, record the max
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }

    return result;
}
```

**Dry Run:**
```
Array: [1, 3, -1, -3, 5, 3, 6, 7]    k = 3

i=0: deque=[0]           (val: [1])
i=1: 3>1, remove 0       deque=[1]           (val: [3])
i=2: -1<3, keep          deque=[1,2]         (val: [3,-1])      → max=3
i=3: -3<-1, keep         deque=[1,2,3]       (val: [3,-1,-3])   → max=3
i=4: 5>-3, rm 3; 5>-1, rm 2; 5>3, rm 1
                          deque=[4]           (val: [5])         → max=5
i=5: 3<5, keep           deque=[4,5]         (val: [5,3])       → max=5
i=6: 6>3, rm 5; 6>5, rm 4
                          deque=[6]           (val: [6])         → max=6
i=7: 7>6, rm 6           deque=[7]           (val: [7])         → max=7

Result: [3, 3, 5, 5, 6, 7] ✅
```

**Why a Deque?**
```
Think of it as maintaining "candidates" for maximum:

When a new element comes in that's BIGGER than elements at the back,
those back elements can NEVER be the max of any future window
(because the new element is both bigger AND more recent).

So we remove them. This keeps the deque in decreasing order.
The front is always the current window's max.
```

> [!CAUTION]
> Store **indices** in the deque, not values. You need indices to check whether an element has left the window (`deque.peekFirst() < i - k + 1`).

---

## 🔑 Decision Framework

```
Problem says "subarray/substring of size k"?
│
├── Need SUM or AVERAGE of each window?
│   └── Template 1 (running sum, add/remove)
│
├── Need ANAGRAM / PERMUTATION detection?
│   └── Template 2 (frequency array + matches counter)
│
├── Need MAX or MIN of each window?
│   └── Template 3 (monotonic deque)
│
├── Need COUNT of distinct elements in each window?
│   └── HashMap to track freq; distinct = map.size()
│       (add new element; remove old; if freq hits 0, remove key)
│
└── Other aggregate (e.g., contains duplicate)
    └── Use HashSet of size k; add new, remove old on each slide
```

---

## The Anatomy of Every Fixed Sliding Window Solution

Every fixed-size sliding window problem follows this **3-step skeleton**:

```java
// STEP 1: Build the first window [0..k-1]
for (int i = 0; i < k; i++) {
    // add arr[i] to your window state
}
// process/record first window

// STEP 2: Slide from index k to n-1
for (int i = k; i < arr.length; i++) {
    // ADD arr[i]         — element entering the window (right side)
    // REMOVE arr[i - k]  — element leaving the window (left side)
    // UPDATE result
}

// STEP 3: Return result
```

> [!IMPORTANT]
> **Memorize this skeleton.** Every problem uses it. The only thing that changes is *what* you're tracking in the window (sum, frequency map, deque, set, etc.).

---

## Common Mistakes to Avoid

1. **Off-by-one on window boundaries** — element leaving is at `i - k`, not `i - k + 1` (when the loop starts at `i = k`)
2. **Forgetting to process the first window** — the loop starting at `k` misses the first window if you don't explicitly handle it
3. **Using `i - k + 1` vs `i - k`** — depends on whether your loop starts at `k` or `k - 1`. Be consistent.
4. **Not checking `k > arr.length`** — edge case that causes ArrayIndexOutOfBounds
5. **Comparing frequency arrays every iteration** — O(26) per slide is fine for lowercase letters, but use the `matches` counter optimization for better constant factors
6. **Storing values instead of indices in deque** — you need indices to evict out-of-window elements

---

## Sliding Window vs Prefix Sum — When to Use What?

| Scenario | Sliding Window | Prefix Sum |
|----------|---------------|------------|
| Sum of fixed-size subarrays | ✅ Works | ✅ Also works |
| Max/Min of fixed-size subarrays | ✅ With deque | ❌ Can't do |
| Frequency/anagram in fixed window | ✅ Natural fit | ❌ Can't do |
| Sum of ANY arbitrary range [l, r] | ❌ Only consecutive | ✅ O(1) random access |
| Multiple range sum queries | ❌ Must re-slide | ✅ Precompute once |
| **Best for** | **One pass, streaming** | **Multiple random queries** |

> [!TIP]
> If you need **one pass** computing aggregates over consecutive windows → **Sliding Window**. If you need **random access** to any range sum → **Prefix Sum**.

---

## 📝 10 Problems

### Easy (3 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 1 | Maximum Average Subarray I | [LC 643](https://leetcode.com/problems/maximum-average-subarray-i/) | Basic fixed window sum (Template 1) |
| 2 | Contains Duplicate II | [LC 219](https://leetcode.com/problems/contains-duplicate-ii/) | Fixed window with HashSet |
| 3 | Number of Sub-arrays of Size K with Avg ≥ Threshold | [LC 1343](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/) | Fixed window sum + condition |

### Medium (5 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 4 | Permutation in String | [LC 567](https://leetcode.com/problems/permutation-in-string/) | Frequency array + matches (Template 2) |
| 5 | Find All Anagrams in a String | [LC 438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Template 2 — collect ALL positions |
| 6 | Maximum Number of Vowels in a Substring of Given Length | [LC 1456](https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/) | Fixed window with vowel counter |
| 7 | K Radius Subarray Averages | [LC 2090](https://leetcode.com/problems/k-radius-subarray-averages/) | Fixed window of size 2k+1, centered |
| 8 | Maximum Points You Can Obtain from Cards | [LC 1423](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/) | Reverse thinking — minimize window of n-k |

### Hard (2 problems)

| # | Problem | Link | Key Concept |
|---|---------|------|-------------|
| 9 | Sliding Window Maximum | [LC 239](https://leetcode.com/problems/sliding-window-maximum/) | Monotonic deque (Template 3) |
| 10 | Substring with Concatenation of All Words | [LC 30](https://leetcode.com/problems/substring-with-concatenation-of-all-words/) | Fixed window + word-level frequency map |

---

## 🧩 Problem-Solving Hints (read after attempting!)

<details>
<summary>Problem 1: Maximum Average Subarray I — Hint</summary>

Exact Template 1. Compute sum of first `k` elements, then slide. Track max sum. Divide by `k` at the end for average. Use `double` for the result.
</details>

<details>
<summary>Problem 2: Contains Duplicate II — Hint</summary>

Maintain a `HashSet` of size at most `k`. For each new element: if it's already in the set → return true. Add it. If the set exceeds size `k`, remove `nums[i - k]`. This is a fixed window of size `k+1` actually (indices within distance `k` means window of `k+1`).
</details>

<details>
<summary>Problem 3: Sub-arrays with Avg ≥ Threshold — Hint</summary>

Template 1. Instead of checking `sum/k >= threshold`, check `sum >= threshold * k` to avoid floating point.
</details>

<details>
<summary>Problem 4: Permutation in String — Hint</summary>

Template 2. Window size = `s1.length()`. Use frequency array of size 26. Use the `matches` counter for O(1) per-slide comparison.
</details>

<details>
<summary>Problem 5: Find All Anagrams — Hint</summary>

Same as Problem 4, but instead of returning `true` on first match, collect the start index `i - k + 1` into a list every time `matches == 26`.
</details>

<details>
<summary>Problem 6: Maximum Vowels — Hint</summary>

Template 1 variant. Instead of sum, maintain a `vowelCount`. When adding a char, check if it's a vowel (aeiou) and increment. When removing, check and decrement. Track max.
</details>

<details>
<summary>Problem 7: K Radius Subarray Averages — Hint</summary>

Window size = `2*k + 1`. For index `i`, the window is `[i-k, i+k]`. Slide a window of that size. Result[i] = windowSum / (2*k+1). Indices where `i < k` or `i >= n-k` get value `-1`. Use `long` for sum to avoid overflow.
</details>

<details>
<summary>Problem 8: Maximum Points from Cards — Hint</summary>

Tricky! You pick `k` cards from left and/or right ends. Instead of choosing ends, think: you're leaving `n - k` consecutive cards in the middle. Minimize the sum of those middle cards using a fixed window of size `n - k`. Answer = totalSum - minWindowSum.
</details>

<details>
<summary>Problem 9: Sliding Window Maximum — Hint</summary>

Template 3. Use a `Deque<Integer>` storing indices. Maintain decreasing order of values. Remove from front if out of window. Remove from back if smaller than current. Front is always the max.
</details>

<details>
<summary>Problem 10: Substring with Concatenation of All Words — Hint</summary>

All words have the same length `w`. Window size = `numWords × w`. Slide by 1 character at a time (or optimize: slide by `w` at a time with `w` different starting offsets). Use a `HashMap<String, Integer>` to track word frequencies in the current window.
</details>

---

## ✅ Progress Tracker

- [ ] Problem 1 — Maximum Average Subarray I
- [ ] Problem 2 — Contains Duplicate II
- [ ] Problem 3 — Sub-arrays of Size K with Avg ≥ Threshold
- [ ] Problem 4 — Permutation in String
- [ ] Problem 5 — Find All Anagrams in a String
- [ ] Problem 6 — Maximum Number of Vowels in Substring
- [ ] Problem 7 — K Radius Subarray Averages
- [ ] Problem 8 — Maximum Points from Cards
- [ ] Problem 9 — Sliding Window Maximum
- [ ] Problem 10 — Substring with Concatenation of All Words
