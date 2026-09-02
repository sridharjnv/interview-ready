# 🎯 DSA & System Design Interview Roadmap — SDE-1 / SDE-2

> **Target**: Amazon, Salesforce, and similar tier companies
> **Current Experience**: ~2 years
> **Goal**: Master DSA patterns + System Design fundamentals

---

## 📊 Progress Snapshot

| # | Pattern | Status | Problems |
|---|---------|--------|----------|
| 1 | HashMap / HashSet | ✅ Done | 10 |
| 2 | Two Pointers | ✅ Done | 10 |
| 3 | Sliding Window | ✅ Done | 10 |
| 4 | Prefix Sum | ✅ Done | 9 |
| 5 | Stack & Monotonic Stack | 🔲 Pending | — |
| 6 | Linked List | 🔲 Pending | — |
| 7 | Binary Search | 🔲 Pending | — |
| 8 | Recursion & Backtracking | 🔲 Pending | — |
| 9 | Trees (DFS + BFS) | 🔲 Pending | — |
| 10 | Binary Search Trees | 🔲 Pending | — |
| 11 | Heap / Priority Queue | 🔲 Pending | — |
| 12 | Graphs | 🔲 Pending | — |
| 13 | Dynamic Programming | 🔲 Pending | — |
| 14 | Greedy | 🔲 Pending | — |
| 15 | Intervals | 🔲 Pending | — |
| 16 | Tries | 🔲 Pending | — |
| 17 | Bit Manipulation | 🔲 Pending | — |
| 18 | Matrix / 2D Array | 🔲 Pending | — |

> **Total Patterns**: 18 · **Completed**: 4 · **Remaining**: 14

---

## 🗓️ Suggested Study Order & Timeline

> [!TIP]
> Follow this exact order. Each phase builds on the previous one. Aim for **2–3 problems per day** on weekdays and **4–5 on weekends**. Revisit weak areas every Sunday.

### Phase 1: Linear Data Structures (Weeks 1–3) — *You are here*

You've already completed the array-based patterns. Now finish the remaining linear structures.

### Phase 2: Divide & Conquer + Trees (Weeks 4–7)

Binary Search → Recursion → Trees → BST → Heaps. This builds your recursive thinking muscle.

### Phase 3: Graphs & Advanced (Weeks 8–11)

Graphs, DP, Greedy, Tries. The hardest and most interview-critical section.

### Phase 4: Revision & Mock Interviews (Weeks 12–14)

Timed practice, company-tagged problems, and mock interviews.

---

## 📝 Pattern-wise Problem Lists

> [!IMPORTANT]
> Problems marked with ⭐ are **Amazon/Salesforce favorites** (frequently asked). Problems marked with 🔑 teach a **key technique** you must internalize.

---

### 5. Stack & Monotonic Stack

**Core Idea**: LIFO processing, maintaining increasing/decreasing order for "next greater/smaller" type questions.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 20. Valid Parentheses | Easy | 🔑 Classic stack usage |
| 2 | 155. Min Stack | Medium | ⭐ Design question |
| 3 | 150. Evaluate Reverse Polish Notation | Medium | |
| 4 | 739. Daily Temperatures | Medium | 🔑 Monotonic stack template |
| 5 | 496. Next Greater Element I | Easy | Monotonic stack + hashmap |
| 6 | 503. Next Greater Element II | Medium | Circular array trick |
| 7 | 71. Simplify Path | Medium | ⭐ Amazon favorite |
| 8 | 394. Decode String | Medium | ⭐ Stack with strings |
| 9 | 84. Largest Rectangle in Histogram | Hard | 🔑 Monotonic stack masterpiece |
| 10 | 853. Car Fleet | Medium | Sorting + stack |

**Key Takeaway**: If a problem asks about "next greater/smaller" or "matching brackets", think stack.

---

### 6. Linked List

**Core Idea**: Pointer manipulation, fast-slow pointer technique, reversal patterns.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 206. Reverse Linked List | Easy | 🔑 Must know iterative + recursive |
| 2 | 21. Merge Two Sorted Lists | Easy | 🔑 Foundation for merge sort |
| 3 | 141. Linked List Cycle | Easy | 🔑 Floyd's algorithm |
| 4 | 142. Linked List Cycle II | Medium | Floyd's extended |
| 5 | 876. Middle of the Linked List | Easy | Fast-slow pointer |
| 6 | 19. Remove Nth Node From End | Medium | ⭐ Two-pointer gap technique |
| 7 | 143. Reorder List | Medium | ⭐ Combines multiple techniques |
| 8 | 138. Copy List with Random Pointer | Medium | ⭐ Amazon classic |
| 9 | 148. Sort List | Medium | Merge sort on linked list |
| 10 | 23. Merge k Sorted Lists | Hard | ⭐ Heap + linked list |

**Key Takeaway**: Master reversal (iterative), fast-slow pointer, and dummy node technique.

---

### 7. Binary Search

**Core Idea**: Eliminate half the search space each step. Not just for sorted arrays — think "search space reduction".

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 704. Binary Search | Easy | 🔑 Template: `left <= right` |
| 2 | 35. Search Insert Position | Easy | Lower bound template |
| 3 | 74. Search a 2D Matrix | Medium | |
| 4 | 153. Find Minimum in Rotated Sorted Array | Medium | 🔑 Rotated array pattern |
| 5 | 33. Search in Rotated Sorted Array | Medium | ⭐ Very frequently asked |
| 6 | 162. Find Peak Element | Medium | 🔑 BS on answer space |
| 7 | 875. Koko Eating Bananas | Medium | ⭐ BS on answer space |
| 8 | 34. Find First and Last Position | Medium | ⭐ Lower/upper bound |
| 9 | 540. Single Element in a Sorted Array | Medium | |
| 10 | 4. Median of Two Sorted Arrays | Hard | Ultimate BS problem |

**Key Takeaway**: Learn 3 templates — standard, lower-bound, upper-bound. Recognize when a problem has **monotonic property** → binary search on answer.

---

### 8. Recursion & Backtracking

**Core Idea**: Explore all possibilities, prune invalid paths. Foundation for trees, graphs, and DP.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 78. Subsets | Medium | 🔑 Backtracking template |
| 2 | 46. Permutations | Medium | 🔑 Permutation template |
| 3 | 77. Combinations | Medium | Combination template |
| 4 | 39. Combination Sum | Medium | ⭐ Unbounded choices |
| 5 | 40. Combination Sum II | Medium | Handling duplicates |
| 6 | 90. Subsets II | Medium | Subsets with duplicates |
| 7 | 17. Letter Combinations of a Phone Number | Medium | ⭐ |
| 8 | 79. Word Search | Medium | ⭐ Grid backtracking |
| 9 | 131. Palindrome Partitioning | Medium | |
| 10 | 51. N-Queens | Hard | Classic backtracking |

**Key Takeaway**: Master the backtracking template: `choose → explore → unchoose`. Learn when to sort input to handle duplicates.

---

### 9. Trees — BFS & DFS

**Core Idea**: Tree traversals (pre/in/post/level), recursive thinking, BFS with queue.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 104. Maximum Depth of Binary Tree | Easy | 🔑 Basic DFS |
| 2 | 226. Invert Binary Tree | Easy | 🔑 |
| 3 | 100. Same Tree | Easy | |
| 4 | 572. Subtree of Another Tree | Easy | |
| 5 | 102. Binary Tree Level Order Traversal | Medium | 🔑 BFS template |
| 6 | 199. Binary Tree Right Side View | Medium | ⭐ |
| 7 | 236. Lowest Common Ancestor | Medium | ⭐🔑 Amazon top question |
| 8 | 105. Construct Tree from Preorder & Inorder | Medium | 🔑 |
| 9 | 543. Diameter of Binary Tree | Easy | ⭐ Tricky edge case |
| 10 | 124. Binary Tree Maximum Path Sum | Hard | ⭐ Post-order thinking |
| 11 | 297. Serialize and Deserialize Binary Tree | Hard | ⭐ Amazon classic |

**Key Takeaway**: Every tree problem is either **top-down** (pass info down via params) or **bottom-up** (return info up via return value). Learn to identify which.

---

### 10. Binary Search Trees (BST)

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 700. Search in a BST | Easy | |
| 2 | 701. Insert into a BST | Medium | |
| 3 | 450. Delete Node in a BST | Medium | 🔑 All cases |
| 4 | 98. Validate BST | Medium | ⭐🔑 Range-based validation |
| 5 | 230. Kth Smallest Element in BST | Medium | ⭐ Inorder traversal |
| 6 | 235. LCA of a BST | Medium | Use BST property |

**Key Takeaway**: BST inorder traversal = sorted order. Always think about leveraging the BST property.

---

### 11. Heap / Priority Queue

**Core Idea**: Efficiently find min/max, top-K problems, streaming data.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 703. Kth Largest Element in a Stream | Easy | Min-heap of size K |
| 2 | 215. Kth Largest Element in an Array | Medium | ⭐🔑 Quickselect or heap |
| 3 | 347. Top K Frequent Elements | Medium | ⭐ Heap + hashmap |
| 4 | 973. K Closest Points to Origin | Medium | ⭐ |
| 5 | 295. Find Median from Data Stream | Hard | ⭐🔑 Two-heap technique |
| 6 | 621. Task Scheduler | Medium | ⭐ Greedy + heap |
| 7 | 355. Design Twitter | Medium | OOD + heap |
| 8 | 767. Reorganize String | Medium | |
| 9 | 23. Merge k Sorted Lists | Hard | ⭐ K-way merge |
| 10 | 378. Kth Smallest Element in Sorted Matrix | Medium | |

**Key Takeaway**: See "top K" or "Kth largest/smallest" → think heap. Two-heap pattern is critical for median problems.

---

### 12. Graphs

**Core Idea**: BFS/DFS on adjacency list/matrix, cycle detection, topological sort, Union-Find.

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 200. Number of Islands | Medium | ⭐🔑 BFS/DFS on grid |
| 2 | 733. Flood Fill | Easy | Grid DFS |
| 3 | 695. Max Area of Island | Medium | |
| 4 | 994. Rotting Oranges | Medium | ⭐🔑 Multi-source BFS |
| 5 | 133. Clone Graph | Medium | ⭐ BFS + hashmap |
| 6 | 207. Course Schedule | Medium | ⭐🔑 Topological sort (cycle detection) |
| 7 | 210. Course Schedule II | Medium | ⭐ Topo sort (Kahn's algo) |
| 8 | 417. Pacific Atlantic Water Flow | Medium | Multi-source DFS |
| 9 | 684. Redundant Connection | Medium | 🔑 Union-Find |
| 10 | 127. Word Ladder | Hard | ⭐ BFS shortest path |
| 11 | 743. Network Delay Time | Medium | Dijkstra's algorithm |
| 12 | 323. Number of Connected Components | Medium | Union-Find / DFS |

**Key Takeaway**: Master 4 sub-patterns: grid BFS/DFS, topological sort (Kahn's), Union-Find, shortest path (BFS unweighted / Dijkstra weighted).

---

### 13. Dynamic Programming

> [!CAUTION]
> DP is the most important and hardest pattern. Expect 1–2 DP questions in Amazon/Salesforce interviews. Spend the most time here.

**Approach**: For every DP problem, define: **(1) State**, **(2) Transition**, **(3) Base case**, **(4) Answer location**.

#### 13a. 1D DP

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 70. Climbing Stairs | Easy | 🔑 Fibonacci-style |
| 2 | 198. House Robber | Medium | 🔑 Take/skip pattern |
| 3 | 213. House Robber II | Medium | Circular variation |
| 4 | 322. Coin Change | Medium | ⭐🔑 Unbounded knapsack |
| 5 | 139. Word Break | Medium | ⭐ |
| 6 | 300. Longest Increasing Subsequence | Medium | ⭐🔑 O(n log n) with BS |
| 7 | 152. Maximum Product Subarray | Medium | Track min and max |
| 8 | 91. Decode Ways | Medium | ⭐ |
| 9 | 377. Combination Sum IV | Medium | |
| 10 | 416. Partition Equal Subset Sum | Medium | 🔑 0/1 Knapsack |

#### 13b. 2D DP

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 62. Unique Paths | Medium | 🔑 Grid DP template |
| 2 | 64. Minimum Path Sum | Medium | |
| 3 | 1143. Longest Common Subsequence | Medium | ⭐🔑 Classic 2D DP |
| 4 | 72. Edit Distance | Medium | ⭐🔑 String DP |
| 5 | 518. Coin Change II | Medium | 2D unbounded knapsack |
| 6 | 494. Target Sum | Medium | |
| 7 | 97. Interleaving String | Medium | |
| 8 | 309. Best Time to Buy and Sell Stock with Cooldown | Medium | State machine DP |
| 9 | 312. Burst Balloons | Hard | Interval DP |
| 10 | 10. Regular Expression Matching | Hard | |

**Key Takeaway**: Learn these DP archetypes: **Fibonacci**, **0/1 Knapsack**, **Unbounded Knapsack**, **LCS**, **LIS**, **Grid paths**, **State machine**. Most DP problems map to one of these.

---

### 14. Greedy

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 53. Maximum Subarray | Medium | 🔑 Kadane's algorithm |
| 2 | 55. Jump Game | Medium | ⭐ |
| 3 | 45. Jump Game II | Medium | ⭐ |
| 4 | 134. Gas Station | Medium | ⭐ |
| 5 | 846. Hand of Straights | Medium | |
| 6 | 763. Partition Labels | Medium | ⭐ |
| 7 | 678. Valid Parenthesis String | Medium | |
| 8 | 1899. Merge Triplets to Form Target | Medium | |

**Key Takeaway**: Greedy = making locally optimal choice leads to globally optimal. If you can prove that, use greedy. Otherwise, consider DP.

---

### 15. Intervals

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 252. Meeting Rooms | Easy | Sort by start |
| 2 | 56. Merge Intervals | Medium | ⭐🔑 Classic |
| 3 | 57. Insert Interval | Medium | ⭐ |
| 4 | 435. Non-overlapping Intervals | Medium | 🔑 Greedy by end time |
| 5 | 253. Meeting Rooms II | Medium | ⭐ Min-heap approach |
| 6 | 1288. Remove Covered Intervals | Medium | |

**Key Takeaway**: Always **sort intervals** first (usually by start time). Overlapping = `a.end > b.start`.

---

### 16. Tries

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 208. Implement Trie | Medium | 🔑 Build from scratch |
| 2 | 211. Design Add and Search Words | Medium | ⭐ Trie + DFS |
| 3 | 212. Word Search II | Hard | ⭐ Trie + backtracking |
| 4 | 14. Longest Common Prefix | Easy | Can solve with trie |

**Key Takeaway**: Trie = prefix tree. Use when dealing with prefix-based search, autocomplete, or word dictionaries.

---

### 17. Bit Manipulation

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 136. Single Number | Easy | 🔑 XOR trick |
| 2 | 191. Number of 1 Bits | Easy | Brian Kernighan's |
| 3 | 338. Counting Bits | Easy | |
| 4 | 268. Missing Number | Easy | XOR or math |
| 5 | 371. Sum of Two Integers | Medium | Bit addition |
| 6 | 190. Reverse Bits | Easy | |

**Key Takeaway**: Know XOR properties: `a ^ a = 0`, `a ^ 0 = a`. These come up occasionally but are easy wins.

---

### 18. Matrix / 2D Array

| # | Problem | Difficulty | Notes |
|---|---------|-----------|-------|
| 1 | 48. Rotate Image | Medium | ⭐ Transpose + reverse |
| 2 | 54. Spiral Matrix | Medium | ⭐ |
| 3 | 73. Set Matrix Zeroes | Medium | ⭐ In-place marking |
| 4 | 240. Search a 2D Matrix II | Medium | 🔑 Staircase search |

---

## 🏗️ System Design Roadmap (for SDE-2)

> [!NOTE]
> For SDE-1, system design is usually a **light round** (basic concepts). For SDE-2, expect a **full 45-min system design round**. Start learning system design in parallel with DP (Phase 3).

### Foundational Concepts (Learn First)

| # | Topic | Priority |
|---|-------|----------|
| 1 | Client-Server Architecture, HTTP/HTTPS, REST APIs | 🔴 Must |
| 2 | Database basics: SQL vs NoSQL, when to use which | 🔴 Must |
| 3 | Caching: Redis, Memcached, cache invalidation strategies | 🔴 Must |
| 4 | Load Balancing: Round Robin, Consistent Hashing | 🔴 Must |
| 5 | CAP Theorem, Consistency vs Availability | 🔴 Must |
| 6 | Database Scaling: Sharding, Replication, Partitioning | 🔴 Must |
| 7 | Message Queues: Kafka, RabbitMQ, SQS | 🟡 Important |
| 8 | CDN, DNS, Reverse Proxy | 🟡 Important |
| 9 | Rate Limiting, API Gateway | 🟡 Important |
| 10 | Microservices vs Monolith | 🟡 Important |

### Practice Designs (Pick 8–10)

| # | System | Key Concepts Tested |
|---|--------|-------------------|
| 1 | **URL Shortener (TinyURL)** | Hashing, base62, DB design, caching |
| 2 | **Design Twitter / News Feed** | Fan-out, caching, timelines, pub-sub |
| 3 | **Design Instagram / Photo Sharing** | Blob storage, CDN, feed generation |
| 4 | **Chat System (WhatsApp/Slack)** | WebSockets, message queues, presence |
| 5 | **Design Rate Limiter** | Token bucket, sliding window, Redis |
| 6 | **Notification System** | Push vs Pull, priority queues, templating |
| 7 | **Design Parking Lot** | OOD + low-level design |
| 8 | **Design BookMyShow / Ticket Booking** | Concurrency, distributed locks, seat selection |
| 9 | **Design Search Autocomplete** | Trie, caching, ranking |
| 10 | **Design a Key-Value Store** | Consistent hashing, replication, WAL |

### Recommended Resources

| Resource | Type | Best For |
|----------|------|----------|
| [System Design Primer (GitHub)](https://github.com/donnemartin/system-design-primer) | Free | Comprehensive overview |
| [Designing Data-Intensive Applications (Book)](https://dataintensive.net/) | Book | Deep understanding |
| [ByteByteGo (Alex Xu)](https://bytebytego.com/) | Course | Visual system design |
| Gaurav Sen (YouTube) | Video | Quick concept videos |
| sudoCode (YouTube) | Video | Indian context, clear explanations |

---

## 📅 14-Week Master Plan

| Week | Focus | Daily Target | Notes |
|------|-------|-------------|-------|
| **1** | Stack & Monotonic Stack | 2–3 problems | |
| **2** | Linked List | 2–3 problems | Revise HashMap, Two Pointers |
| **3** | Binary Search | 2–3 problems | |
| **4** | Recursion & Backtracking | 2–3 problems | Take your time here |
| **5** | Trees (DFS + BFS) | 2–3 problems | Start system design basics |
| **6** | BST + Heap | 2–3 problems | |
| **7** | Graphs (Part 1: BFS/DFS) | 2–3 problems | |
| **8** | Graphs (Part 2: Topo Sort, Union-Find) | 2–3 problems | |
| **9** | DP — 1D | 2–3 problems | Hardest phase begins |
| **10** | DP — 2D + Knapsack variants | 2–3 problems | |
| **11** | Greedy + Intervals + Bit Manipulation | 2–3 problems | |
| **12** | Tries + Matrix + Mixed practice | 2–3 problems | Practice system designs |
| **13** | 🔄 Revision: Redo starred ⭐ problems | Timed practice | Do each in ≤25 min |
| **14** | 🎯 Mock Interviews | 2 mocks/day | Use Pramp, Interviewing.io |

---

## 🧠 Interview Day Tips

### DSA Round
1. **Clarify** the problem — ask about edge cases, constraints, input size
2. **Brute force first** — explain it, state its complexity
3. **Optimize** — pattern match to the techniques you've learned
4. **Dry run** your solution on an example before coding
5. **Code cleanly** — use meaningful variable names, modular functions
6. **Test** — walk through edge cases after coding

### System Design Round
1. **Clarify requirements** — functional and non-functional (latency, scale, availability)
2. **Estimate scale** — DAU, QPS, storage
3. **High-level design** — draw components, data flow
4. **Deep dive** — pick 2–3 components to detail
5. **Address trade-offs** — explain why you chose X over Y

---

## 📚 Recommended Practice Platforms

| Platform | Best For |
|----------|----------|
| **LeetCode** | Primary problem practice, company tags |
| **NeetCode.io** | Curated lists, video explanations |
| **Striver's A2Z DSA Sheet** | Structured Indian-context roadmap |
| **Pramp** | Free mock interviews with peers |
| **Interviewing.io** | Anonymous mock interviews |

---

> [!TIP]
> **Golden Rule**: Don't just solve problems — after each problem, write down the **pattern** and **when to use it** in your own words. During interviews, you're pattern-matching, not solving from scratch.

> [!IMPORTANT]
> **Consistency > Intensity**. 2–3 problems daily for 14 weeks (200+ problems) beats 10 problems/day for 3 weeks and burning out.
