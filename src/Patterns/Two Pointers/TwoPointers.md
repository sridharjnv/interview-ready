# 🎯 Two Pointers Pattern - Complete Guide

---

## 1️⃣ What is Two Pointers?

Two Pointers is a technique where you use **two index variables** to traverse a data structure (usually an array or string) simultaneously. Instead of using nested loops (O(n²)), you achieve O(n) by moving pointers strategically.

```
Array: [1, 2, 3, 4, 5, 6, 7]
        ↑                 ↑
       left             right
```

---

## 2️⃣ Types of Two Pointer Techniques

### **Type A: Opposite Direction (Start & End)**
```
[1, 2, 3, 4, 5, 6, 7]
 ↑                 ↑
 L →           ← R

Pointers start at opposite ends and move toward each other
```
**Use cases**: Pair sum, Palindrome check, Container with most water

---

### **Type B: Same Direction (Slow & Fast)**
```
[1, 2, 3, 4, 5, 6, 7]
 ↑  ↑
slow fast →

Both pointers move in same direction at different speeds
```
**Use cases**: Remove duplicates, Linked list cycle, Partition array

---

### **Type C: Two Arrays**
```
Array1: [1, 3, 5, 7]      Array2: [2, 4, 6, 8]
         ↑                         ↑
         i                         j
```
**Use cases**: Merge sorted arrays, Intersection of arrays

---

## 3️⃣ How to Identify Two Pointer Problems?

> 🔍 **Ask yourself these questions:**

| Signal | Example |
|--------|---------|
| Array/String is **sorted** or can be sorted | "Find pair with sum X" |
| Need to find **pair/triplet** with certain property | "Two Sum in sorted array" |
| Need to compare elements from **both ends** | "Is palindrome?" |
| Need to **partition** or **rearrange** in-place | "Move zeros to end" |
| Keywords: "in-place", "O(1) space", "without extra space" | "Remove duplicates" |
| Need to find **subarray** with certain property | Sometimes overlaps with Sliding Window |

---

## 4️⃣ Generic Templates

### **Template A: Opposite Direction**
```java
public void oppositePointers(int[] arr) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        // Process current state
        
        if (/* condition to move left */) {
            left++;
        } else if (/* condition to move right */) {
            right--;
        } else {
            // Found answer or process both
            left++;
            right--;
        }
    }
}
```

### **Template B: Same Direction (Slow-Fast)**
```java
public void slowFastPointers(int[] arr) {
    int slow = 0;
    
    for (int fast = 0; fast < arr.length; fast++) {
        if (/* condition to keep element */) {
            arr[slow] = arr[fast];
            slow++;
        }
    }
    // 'slow' is now the new length or partition point
}
```

---

## 5️⃣ Problem Walkthrough: Two Sum II (Sorted Array)

> Given a **sorted** array, find two numbers that add up to target. Return their indices.

```
Input: nums = [2, 7, 11, 15], target = 9
Output: [0, 1]  (because 2 + 7 = 9)
```

### **Step 1: Identify the Pattern**
- ✅ Array is sorted
- ✅ Need to find a pair
- ✅ Sum-based condition
- → **Use Opposite Direction Two Pointers**

### **Step 2: Logic**
```
[2, 7, 11, 15]  target = 9
 ↑          ↑
 L          R

sum = 2 + 15 = 17 > 9  → Too big! Move R left

[2, 7, 11, 15]  target = 9
 ↑      ↑
 L      R

sum = 2 + 11 = 13 > 9  → Still too big! Move R left

[2, 7, 11, 15]  target = 9
 ↑  ↑
 L  R

sum = 2 + 7 = 9 = target  → Found it! ✓
```

### **Step 3: Code**
```java
public int[] twoSum(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;    // Need bigger sum
        } else {
            right--;   // Need smaller sum
        }
    }
    
    return new int[]{-1, -1};  // Not found
}
```

### **Complexity**
- **Time**: O(n) - each pointer moves at most n times
- **Space**: O(1) - only two variables

---

## 6️⃣ Problem Walkthrough: Remove Duplicates from Sorted Array

> Given sorted array, remove duplicates **in-place**. Return new length.

```
Input: [1, 1, 2, 2, 2, 3]
Output: 3, array becomes [1, 2, 3, _, _, _]
```

### **Identify the Pattern**
- ✅ In-place modification
- ✅ Array is sorted (duplicates are adjacent)
- → **Use Same Direction (Slow-Fast) Two Pointers**

### **Logic**
```
[1, 1, 2, 2, 2, 3]
 ↑  ↑
 S  F
nums[slow] == nums[fast]? Yes → just move fast

[1, 1, 2, 2, 2, 3]
 ↑     ↑
 S     F
nums[slow] == nums[fast]? No! → slow++, copy nums[fast] to nums[slow]

[1, 2, 2, 2, 2, 3]
    ↑  ↑
    S  F
...continue...
```

### **Code**
```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int slow = 0;
    
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    
    return slow + 1;  // Length is index + 1
}
```

---

## 7️⃣ Common Mistakes to Avoid

| Mistake | Fix |
|---------|-----|
| Using `left <= right` when it should be `left < right` | Think: do pointers need to meet or cross? |
| Forgetting to handle empty array | Always check edge cases first |
| Moving wrong pointer | Draw it out! Which pointer reduces the gap toward goal? |
| Off-by-one errors in return value | Is it index or length? |

---

## 8️⃣ Practice Problems

### 🟢 **Easy (Start Here)**
| # | Problem | Focus |
|---|---------|-------|
| 1 | Two Sum II - Sorted | Opposite pointers |
| 2 | Valid Palindrome | Opposite pointers |
| 3 | Remove Duplicates from Sorted Array | Slow-fast |
| 4 | Move Zeroes | Slow-fast |
| 5 | Squares of a Sorted Array | Opposite pointers |
| 6 | Merge Sorted Array | Two arrays |

### 🟡 **Medium (Build Confidence)**
| # | Problem | Focus |
|---|---------|-------|
| 7 | 3Sum | Opposite + iteration |
| 8 | Container With Most Water | Opposite pointers |
| 9 | Sort Colors (Dutch National Flag) | Three pointers |
| 10 | Trapping Rain Water | Opposite pointers |
| 11 | 4Sum | Nested two pointers |
| 12 | Remove Duplicates II | Slow-fast |

### 🔴 **Hard (Master Level)**
| # | Problem | Focus |
|---|---------|-------|
| 13 | Trapping Rain Water | Opposite with tracking |
| 14 | Minimum Window Substring | Two pointers + hash |

---

## 📋 Practice Plan

```
Day 1: Solve problems 1-3 (Easy)
Day 2: Solve problems 4-6 (Easy)
Day 3: Attempt problem 7 (3Sum - classic!)
Day 4: Solve problems 8-9
Day 5: Attempt problem 10 (Trapping Rain Water)
Day 6: Revise all + solve any remaining
```

---

## ✅ Checklist Before Moving to Next Pattern

- [ ] Can identify Two Pointer problems quickly
- [ ] Know when to use Opposite vs Same direction
- [ ] Solved at least 6-8 problems independently
- [ ] Can explain 3Sum approach in an interview
