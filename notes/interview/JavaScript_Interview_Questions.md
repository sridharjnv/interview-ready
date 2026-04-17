# 🟨 Top 100 JavaScript Interview Questions — SDE-1 Full-Stack / Frontend / Backend

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. Language Fundamentals & Syntax (Q1–Q15)

1. 🟢 What are the different data types in JavaScript? How do you check the type of a variable using `typeof`?
2. 🟢 What is the difference between `var`, `let`, and `const`? When should you use each?
3. 🟢 What is hoisting in JavaScript? How does it behave differently for `var`, `let`, `const`, and function declarations?
4. 🟢 What is the Temporal Dead Zone (TDZ)? How is it related to `let` and `const`?
5. 🟢 What is the difference between `==` (loose equality) and `===` (strict equality)? What is type coercion?
6. 🟢 What are template literals? What are tagged template literals and when are they useful?
7. 🟢 What is destructuring assignment? How does it work with arrays and objects?
8. 🟢 What is the spread operator (`...`)? How does it differ from the rest parameter?
9. 🟢 What are `null` and `undefined`? How are they different? What does `typeof null` return and why?
10. 🟡 What is the difference between pass-by-value and pass-by-reference in JavaScript?
11. 🟡 What are symbols in JavaScript? What are well-known symbols like `Symbol.iterator`?
12. 🟡 What is optional chaining (`?.`) and nullish coalescing (`??`)? How do they differ from `&&` and `||`?
13. 🟡 What is the difference between a statement and an expression in JavaScript?
14. 🟡 What are labeled statements in JavaScript? How do `break` and `continue` work with labels?
15. 🟢 What is short-circuit evaluation? How do `&&`, `||`, and `??` leverage it?

---

## 2. Functions & Scope (Q16–Q30)

16. 🟢 What is the difference between function declarations and function expressions? What is a named function expression?
17. 🟢 What are arrow functions? How do they differ from regular functions (especially regarding `this`, `arguments`, and `new`)?
18. 🟢 What is a callback function? Give an example from both synchronous and asynchronous contexts.
19. 🟡 What are closures? How do they work? Give a practical example (e.g., data privacy, counter, module pattern).
20. 🟡 What is lexical scoping? How does JavaScript resolve variable lookups through the scope chain?
21. 🟡 What are Immediately Invoked Function Expressions (IIFEs)? Why were they used before ES6 modules?
22. 🟡 What are higher-order functions? Give examples using `map`, `filter`, `reduce`, and custom implementations.
23. 🟡 What is function currying? How do you implement it? What are its practical use cases?
24. 🟡 What is function composition? How do you compose multiple functions together?
25. 🟡 What are default parameters? How do they interact with `undefined` vs passing no argument?
26. 🟡 What are generator functions (`function*`)? How do `yield` and `next()` work? What are practical use cases?
27. 🟡 What is memoization? How do you implement a generic memoize function?
28. 🟡 What is the difference between pure and impure functions? Why do pure functions matter in functional programming?
29. 🔴 What is tail call optimization (TCO)? Does JavaScript support it? How do you convert recursive functions to be tail-recursive?
30. 🟡 What is the `arguments` object? How does it differ from rest parameters? Why is it not available in arrow functions?

---

## 3. `this` Keyword, `call`, `apply`, `bind` (Q31–Q40)

31. 🟢 What is the `this` keyword in JavaScript? How is its value determined?
32. 🟡 What are the four rules for `this` binding? (Default, Implicit, Explicit, `new` binding — and their precedence)
33. 🟡 What is `call()`? How does it differ from `apply()`? Write a polyfill for `call`.
34. 🟡 What is `bind()`? How does it differ from `call` and `apply`? Write a polyfill for `bind`.
35. 🟡 What is the value of `this` inside an arrow function? Why is it called "lexical `this`"?
36. 🟡 What happens to `this` when you extract a method from an object and call it standalone? How do you fix it?
37. 🟡 What is `this` inside a constructor function vs. a class? What does the `new` keyword do step by step?
38. 🟡 How does `this` behave inside event handlers in the DOM? How do you preserve `this` in React class components?
39. 🔴 What is explicit binding with `call`/`apply`/`bind`? Can you override a hard-bound function's `this`?
40. 🟡 What is `this` inside `setTimeout` and `setInterval`? How do you ensure the correct `this` context?

---

## 4. Prototypes & Object-Oriented JS (Q41–Q55)

41. 🟢 What is prototypal inheritance in JavaScript? How does it differ from classical (class-based) inheritance?
42. 🟢 What is the prototype chain? How does JavaScript look up properties on objects?
43. 🟢 What are constructor functions? How do you create objects using `new`?
44. 🟡 What is `Object.create()`? How does it set up the prototype chain?
45. 🟡 What is the difference between `__proto__` (dunder proto) and `prototype`? How are they related?
46. 🟡 What are ES6 classes? Are they "real" classes or syntactic sugar over prototypes?
47. 🟡 What is the `extends` keyword? How does `super()` work in subclass constructors and methods?
48. 🟡 What are static methods and properties in JavaScript classes? When would you use them?
49. 🟡 What are getters and setters? How do you define them using `get` and `set` in classes and object literals?
50. 🟡 What is `Object.keys()` vs `Object.values()` vs `Object.entries()`? How do they handle inherited properties?
51. 🟡 What is `Object.assign()`? How does it perform shallow copying? What are the alternatives for deep cloning?
52. 🟡 What is `Object.freeze()` vs `Object.seal()`? What is the difference between shallow and deep freeze?
53. 🟡 What are property descriptors? How do `Object.defineProperty()` and `Object.defineProperties()` work?
54. 🔴 What are Proxies in JavaScript? How do `get`, `set`, and `apply` traps work? Give a real-world use case.
55. 🔴 What is Reflect API? How does it work with Proxies?

---

## 5. Asynchronous JavaScript (Q56–Q72)

56. 🟢 What is the difference between synchronous and asynchronous code in JavaScript?
57. 🟢 What is the Event Loop? Explain the Call Stack, Web APIs, Callback Queue, and Microtask Queue.
58. 🟢 What are callbacks? What is callback hell? How do you solve it?
59. 🟢 What are Promises? What are the three states of a Promise? How do you create and consume them?
60. 🟡 What is the difference between `.then()/.catch()/.finally()` chaining and `async/await`?
61. 🟡 What is `async/await`? How does it make asynchronous code look synchronous? What does `await` actually do?
62. 🟡 How do you handle errors in async/await? What is the difference between `try/catch` and `.catch()` on a promise?
63. 🟡 What is `Promise.all()`? When does it reject? How is it different from `Promise.allSettled()`?
64. 🟡 What is `Promise.race()` vs `Promise.any()`? When would you use each?
65. 🟡 What is the Microtask Queue vs the Macrotask (Callback) Queue? What is the execution priority?
66. 🟡 How does `setTimeout(fn, 0)` work? Why doesn't it execute immediately?
67. 🟡 What is `queueMicrotask()`? How does it compare to `Promise.resolve().then()`?
68. 🟡 How do you implement a retry mechanism with exponential backoff using Promises?
69. 🟡 How do you run async operations sequentially vs. in parallel? What is the `for await...of` loop?
70. 🔴 Write a polyfill for `Promise.all()`. What edge cases should you handle?
71. 🔴 What are Web Workers? How do they achieve true parallelism in JavaScript?
72. 🔴 What is `AbortController`? How do you cancel fetch requests and other async operations?

---

## 6. Arrays, Strings & Built-in Methods (Q73–Q82)

73. 🟢 What are the most commonly used array methods? Explain `map`, `filter`, `reduce`, `find`, `some`, `every`, `forEach`.
74. 🟢 What is the difference between `map` and `forEach`? When should you use one over the other?
75. 🟢 How do `push`, `pop`, `shift`, `unshift`, `splice`, and `slice` work? Which mutate and which don't?
76. 🟡 How does `Array.reduce()` work? Implement `map`, `filter`, and `flat` using `reduce`.
77. 🟡 What is `Array.from()`? How do you convert array-like objects (e.g., `NodeList`, `arguments`) into arrays?
78. 🟡 What are `flat()` and `flatMap()`? How do you flatten deeply nested arrays?
79. 🟡 What are common string methods? Explain `slice`, `substring`, `includes`, `startsWith`, `replace`, `split`, `trim`, `padStart`.
80. 🟡 What is `Array.isArray()`? Why is it more reliable than `typeof` or `instanceof` for checking arrays?
81. 🟡 What is the difference between `sort()` with and without a comparator? Why does `[10, 2, 1].sort()` return `[1, 10, 2]`?
82. 🟡 What are `Set` and `Map`? How are they different from plain objects and arrays? What about `WeakSet` and `WeakMap`?

---

## 7. DOM Manipulation & Browser APIs (Q83–Q90)

83. 🟢 What is the DOM? What is the difference between the DOM tree and the HTML source?
84. 🟢 What are the different ways to select elements: `getElementById`, `querySelector`, `querySelectorAll`, `getElementsByClassName`?
85. 🟢 How do you add, remove, and modify DOM elements? Explain `createElement`, `appendChild`, `remove`, `innerHTML` vs `textContent`.
86. 🟡 What is event delegation? How does it leverage event bubbling? Why is it a performance best practice?
87. 🟡 What is the difference between event bubbling and event capturing? What does `stopPropagation()` do?
88. 🟡 What is the difference between `addEventListener` and inline event handlers (`onclick`)? How do you remove event listeners?
89. 🟡 What is `localStorage` vs `sessionStorage` vs cookies? What are the size limits, expiration, and security implications?
90. 🟡 What is the `fetch` API? How does it differ from `XMLHttpRequest`? How do you handle response headers, status codes, and errors?

---

## 8. ES6+ Features & Modern JavaScript (Q91–Q95)

91. 🟢 What are ES6 modules? What is the difference between `import/export` and `require/module.exports` (CommonJS)?
92. 🟡 What are dynamic imports (`import()`)? How do they enable code splitting and lazy loading?
93. 🟡 What are iterators and iterables? How does the `Symbol.iterator` protocol work? How do you make a custom iterable?
94. 🟡 What are `for...in` and `for...of` loops? What is the difference and when should you use each?
95. 🟡 What are `WeakRef` and `FinalizationRegistry`? How does JavaScript garbage collection work?

---

## 9. Error Handling, Security & Miscellaneous (Q96–Q100)

96. 🟢 How does `try/catch/finally` work in JavaScript? What happens if you return inside both `try` and `finally`?
97. 🟡 What are the different types of errors in JavaScript? Explain `TypeError`, `ReferenceError`, `SyntaxError`, `RangeError`.
98. 🟡 What is debouncing vs throttling? Implement both from scratch. What are the practical use cases?
99. 🟡 What is `structuredClone()`? How does it differ from `JSON.parse(JSON.stringify())` for deep cloning?
100. 🟡 What is Cross-Site Scripting (XSS) and Cross-Site Request Forgery (CSRF)? How do you prevent them in JavaScript applications?

---

> 💡 **Tip for candidates:** For SDE-1 full-stack and frontend roles, focus heavily on **Closures & Scope**, **Promises & async/await**, **the Event Loop**, **`this` keyword & binding rules**, and **Prototypal Inheritance**. Be prepared to write polyfills for `call`, `bind`, `Promise.all`, `debounce`, and `throttle` on a whiteboard. Understanding **ES6+ features** (destructuring, spread, modules) is considered table stakes in modern interviews.
