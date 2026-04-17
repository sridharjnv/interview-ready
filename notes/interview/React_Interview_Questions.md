# ⚛️ Top 100 React Interview Questions — SDE-1 Frontend / Full-Stack

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. React Fundamentals (Q1–Q15)

1. 🟢 What is React? Why is it called a library and not a framework?
2. 🟢 What is the Virtual DOM? How does it improve performance compared to the real DOM?
3. 🟢 What is JSX? How is it different from HTML? Can you use React without JSX?
4. 🟢 What is the difference between a functional component and a class component?
5. 🟢 What are props in React? How do you pass data from parent to child?
6. 🟢 What is state in React? How is it different from props?
7. 🟢 What is the role of `ReactDOM.render()` (or `createRoot` in React 18)?
8. 🟢 What is the difference between controlled and uncontrolled components?
9. 🟢 What are React fragments? Why would you use `<Fragment>` or `<>` instead of a `<div>`?
10. 🟡 What is the reconciliation algorithm in React? How does React decide what to re-render?
11. 🟡 What is the significance of the `key` prop in lists? What happens if you use the array index as the key?
12. 🟢 What is the difference between `React.createElement()` and JSX?
13. 🟡 What is the React Fiber architecture? How does it differ from the old stack reconciler?
14. 🟡 What are synthetic events in React? How are they different from native DOM events?
15. 🟡 What is the difference between declarative and imperative programming? Why is React declarative?

---

## 2. React Hooks (Q16–Q35)

16. 🟢 What are React Hooks? Why were they introduced in React 16.8?
17. 🟢 What is `useState`? How does it work? What happens when you call the setter function?
18. 🟢 What is `useEffect`? How does it replace lifecycle methods like `componentDidMount`, `componentDidUpdate`, and `componentWillUnmount`?
19. 🟡 What is the dependency array in `useEffect`? What happens when you pass an empty array `[]`, no array, or specific dependencies?
20. 🟡 What are the cleanup functions in `useEffect`? When are they called and why are they important?
21. 🟡 What is `useRef`? How is it different from `useState`? When would you use it?
22. 🟡 What is `useMemo`? How does it optimize performance? When should you NOT use it?
23. 🟡 What is `useCallback`? How does it differ from `useMemo`? When is it useful?
24. 🟡 What is `useContext`? How does it help avoid prop drilling?
25. 🟡 What is `useReducer`? How does it compare to `useState`? When should you prefer it?
26. 🟡 What is `useLayoutEffect`? How is it different from `useEffect`?
27. 🟡 What is `useImperativeHandle`? When would you use it with `forwardRef`?
28. 🟡 What are the rules of Hooks? Why can't you call Hooks inside loops, conditions, or nested functions?
29. 🟡 How do you create a custom Hook? What naming convention must it follow?
30. 🟡 What is a stale closure problem in Hooks? How do you avoid it?
31. 🔴 What is `useId`? How does it help with server-side rendering and accessibility?
32. 🔴 What is `useSyncExternalStore`? When would you use it?
33. 🔴 What is `useTransition`? How does it improve user experience with concurrent features?
34. 🔴 What is `useDeferredValue`? How does it differ from debouncing?
35. 🟡 How do you share stateful logic between components using custom Hooks? Give a real-world example.

---

## 3. Component Patterns & Composition (Q36–Q48)

36. 🟢 What is component composition in React? Why is it preferred over inheritance?
37. 🟡 What are Higher-Order Components (HOCs)? Give an example and explain when you would use them.
38. 🟡 What is the Render Props pattern? How does it compare to HOCs?
39. 🟡 What are compound components? Give an example where this pattern is useful.
40. 🟡 What is the container-presentational pattern? Is it still relevant with Hooks?
41. 🟡 What is `React.memo`? How does it prevent unnecessary re-renders? What are its limitations?
42. 🟡 What is `React.lazy`? How do you implement code splitting with `Suspense`?
43. 🟡 What is `forwardRef`? When and why would you use it?
44. 🟡 What is the children prop? How does `props.children` work for component composition?
45. 🟡 What are portals in React? When would you use `ReactDOM.createPortal`?
46. 🟡 What are Error Boundaries? How do you implement them? Can you use Hooks to create one?
47. 🟡 What are controlled vs uncontrolled components for forms? Which approach is recommended and why?
48. 🔴 What is the Provider pattern? How is it used with React Context for dependency injection?

---

## 4. State Management (Q49–Q62)

49. 🟢 What is lifting state up? When and why should you do it?
50. 🟡 What is the Context API? How do you create and consume a context?
51. 🟡 What are the drawbacks of the Context API? Why does it cause unnecessary re-renders?
52. 🟡 What is prop drilling? What are the different ways to avoid it?
53. 🟡 What is Redux? Explain the core concepts: store, actions, reducers, and dispatch.
54. 🟡 What is Redux Toolkit (RTK)? How does it simplify Redux boilerplate?
55. 🟡 What is the difference between Redux and Context API? When should you choose one over the other?
56. 🟡 What are Redux middleware? Explain `redux-thunk` and `redux-saga`.
57. 🟡 What is `createSlice` in Redux Toolkit? How does it combine actions and reducers?
58. 🟡 What is RTK Query? How does it handle data fetching and caching?
59. 🟡 What is Zustand? How does it compare to Redux for state management?
60. 🟡 What is the difference between local state, global state, and server state?
61. 🔴 What is the flux architecture? How does Redux implement it?
62. 🔴 What are selectors in Redux? What is memoized selector (reselect) and why is it important?

---

## 5. React Router & Navigation (Q63–Q70)

63. 🟢 What is React Router? How does client-side routing differ from server-side routing?
64. 🟢 What are the core components: `BrowserRouter`, `Routes`, `Route`, `Link`, and `NavLink`?
65. 🟡 What is the difference between `BrowserRouter` and `HashRouter`?
66. 🟡 How do you implement dynamic routing with URL parameters using `useParams`?
67. 🟡 What are nested routes in React Router v6? How do you use `<Outlet>`?
68. 🟡 How do you implement programmatic navigation using `useNavigate`?
69. 🟡 How do you implement protected/private routes? Give an authentication example.
70. 🟡 What are loaders and actions in React Router v6.4+? How do they change the data fetching pattern?

---

## 6. Performance Optimization (Q71–Q82)

71. 🟡 What causes unnecessary re-renders in React? How do you identify them?
72. 🟡 What is `React.memo`? When does it help and when is it overkill?
73. 🟡 How do `useMemo` and `useCallback` help prevent re-renders? What is referential equality?
74. 🟡 What is code splitting? How do `React.lazy` and `Suspense` enable it?
75. 🟡 What is tree shaking? How does it reduce bundle size?
76. 🟡 What is the React Profiler? How do you use it to identify performance bottlenecks?
77. 🟡 What is windowing/virtualization? How do libraries like `react-window` or `react-virtuoso` help with long lists?
78. 🟡 What is debouncing and throttling? How do you implement them in React for input events?
79. 🔴 What is the difference between `React.memo`, `useMemo`, and `useCallback`? When should you use each?
80. 🔴 What are React Compiler (React Forget) and auto-memoization? How will they change performance optimization?
81. 🟡 How do you optimize images and assets in a React application?
82. 🔴 What is bundle analysis? How do tools like `webpack-bundle-analyzer` help optimize your app?

---

## 7. Side Effects, Data Fetching & Async (Q83–Q92)

83. 🟢 How do you fetch data in React using `useEffect` and `fetch`/`axios`?
84. 🟡 What are race conditions in data fetching? How do you handle them with cleanup functions or `AbortController`?
85. 🟡 What is React Query (TanStack Query)? How does it simplify data fetching, caching, and synchronization?
86. 🟡 What are the concepts of `staleTime`, `cacheTime`, and background refetching in React Query?
87. 🟡 What is SWR? How does the `stale-while-revalidate` strategy work?
88. 🟡 What is optimistic UI update? How do you implement it with React Query or Redux?
89. 🟡 How do you handle loading, error, and success states when fetching data?
90. 🟡 What is the `Suspense` component for data fetching? How does it work with React 18 concurrent features?
91. 🟡 What is `React.startTransition`? How does it differ from `useTransition`?
92. 🔴 What is the `use` Hook (React 19)? How does it change how we handle promises and context?

---

## 8. Testing in React (Q93–Q100)

93. 🟢 What are the common tools for testing React apps? Explain Jest and React Testing Library.
94. 🟡 What is the difference between unit testing, integration testing, and end-to-end testing in React?
95. 🟡 What is the testing philosophy of React Testing Library? Why does it focus on user behavior over implementation details?
96. 🟡 How do you test a component that makes API calls? How do you mock `fetch` or `axios`?
97. 🟡 What are the common queries in React Testing Library — `getBy`, `queryBy`, `findBy`? When do you use each?
98. 🟡 How do you test custom Hooks? What is `renderHook` from `@testing-library/react`?
99. 🟡 What is snapshot testing? What are its pros and cons?
100. 🟡 How do you test user interactions (click, type, submit) using `fireEvent` and `userEvent`?

---

> 💡 **Tip for candidates:** For SDE-1 frontend and full-stack roles, focus heavily on **React Hooks**, **State Management (Context API + Redux basics)**, **Component Lifecycle & Re-renders**, and **Performance Optimization**. Be ready to write small code snippets on the whiteboard for Hooks and common patterns. Understanding **React Query / data fetching** is increasingly expected in modern interviews.
