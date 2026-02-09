# Consistent Hashing: Interview Guide (SDE-1/2)

Since you mentioned you know nothing about it, let's start with the **Why** and the **How** before we get to the interview simulation.

---

## 1. The Core Concept (Zero to Hero)

### The Problem: Simple Hashing
Imagine you have 3 cache servers ($S_0, S_1, S_2$) and you want to store a user's data. 
A simple way is: `server_index = hash(user_id) % 3`.
*   User 101 hashes to 101 % 3 = **Server 2**.
*   User 102 hashes to 102 % 3 = **Server 0**.

**The Disaster:** If Server 1 crashes, you now have only 2 servers. 
Now the formula is `hash(user_id) % 2`.
*   User 101 now goes to 101 % 2 = **Server 1** (Wait, it used to be Server 2!).
*   User 102 now goes to 102 % 2 = **Server 0** (Stayed the same by luck).

In a large system, adding or removing **one** server causes almost **all** keys to move to different servers. This creates a "Cache Miss Storm" that can crash your database.

### The Solution: Consistent Hashing
Instead of a simple modulo, imagine a **Hash Ring** (a circle from 0 to $2^{32}-1$).
1.  **Place Servers on the Ring:** Hash the server IDs and place them on the circle.
2.  **Place Data on the Ring:** Hash the `user_id` and place it on the circle.
3.  **The Rule:** To find the server for a user, move **clockwise** on the ring until you hit the first server.

**Why this is better:** If a server is removed, only the users that were mapped to *that specific server* need to move to the next one. Everyone else stays exactly where they were.

---

## 2. The Interview Question
> *"We are scaling our distributed cache to 50 nodes. How should we distribute keys across these nodes so that if we add a 51st node, we don't have to reshuffle all our data? Explain Consistent Hashing."*

---

## 3. Analyzing Answers

### **The Weak Answer (Junior/Surface Level)**
> "Consistent hashing is a way to map headers or keys to servers using a ring. You put servers on the ring and keys on the ring. When you need a key, you go clockwise. It's better because when a server leaves, you don't lose all your data, only some of it. It's used in things like DynamoDB or Memcached."

#### **Interviewer’s Evaluation:**
*   **Missing 'The Why':** Doesn't explain *why* the standard `% N` approach fails (the reshuffling problem).
*   **Vague Mechanics:** Mentions a "ring" but doesn't explain how nodes are distributed or how we find the key.
*   **Doesn't Mention Hotspots:** Doesn't address what happens if one server gets 80% of the traffic because of where it sits on the ring.

---

### **The Strong Answer (SDE-1 "Rising Star")**
> "The goal is to minimize data migration when the cluster size changes. In standard hashing, adding a node changes the 'N' in `hash % N`, causing a massive reshuffle. 
>
> In **Consistent Hashing**, we map both servers and keys onto a logical **Hash Ring** (0 to $2^{n}-1$). A key is assigned to the first server found moving clockwise. 
> 
> **Key Benefits:**
> 1. **Minimal Disruption:** Only $1/N$ of the keys need to be moved when a node is added or removed.
> 2. **Virtual Nodes:** A common issue is 'non-uniform distribution' (one server might own a huge chunk of the ring). We solve this by using **Virtual Nodes** (or 'Vnodes'). We map each physical server to multiple points on the ring (e.g., Server A-1, Server A-2). This ensures that if Server A fails, its load is distributed across *many* other servers, not just its one neighbor.
>
> I've seen this used in Load Balancers to ensure 'Sticky Sessions' remain relatively stable even during scaling events."

#### **Interviewer’s Evaluation:**
*   **Quantifiable Benefits:** Mentions exactly how much data moves ($1/N$).
*   **Advanced Depth:** Mentions **Virtual Nodes**, which is the "senior" part of this pattern. It shows they understand real-world edge cases (hotspots).
*   **Problem-Solution mindset:** Clearly identifies the "Modulo N" problem first.

---

## 4. Comparison Table

| Feature | Weak Answer | Strong Answer |
| :--- | :--- | :--- |
| **Problem Statement** | "Modulo is bad." | "Modulo causes $O(N)$ reshuffling; we need $O(1/N)$." |
| **The Ring** | "It's a circle." | "A logical hash space where both keys and nodes coexist." |
| **Load Balancing** | Ignored. | **Virtual Nodes** to prevent 'hotspots' or 'islands' of data. |
| **Impact of failure** | "Only some move." | "Only the immediate neighbor on the ring is affected (minimized blast radius)." |

---

## 5. Follow-up Questions to Prepare For
1.  **"How do Virtual Nodes help with heterogeneous servers (e.g., one server has 2x the RAM of others)?"** (Hint: Give the powerful server more virtual nodes!)
2.  **"What is the time complexity of finding a server for a key on the ring?"** (Hint: $O(\log N)$ using a binary search or Treap of server positions).
3.  **"How does this differ from Rendezvous Hashing?"** (A more niche alternative you can mention for extra credit).
