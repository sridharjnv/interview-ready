# 🐍 Top 100 Python Interview Questions — SDE-1 Backend / Full-Stack

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. Python Fundamentals (Q1–Q15)

1. 🟢 What is Python? What are its key features? Why is it called an interpreted language?
2. 🟢 What is the difference between Python 2 and Python 3?
3. 🟢 What are Python's built-in data types? Explain mutable vs immutable types.
4. 🟢 What is the difference between a list, tuple, set, and dictionary?
5. 🟢 What is dynamic typing in Python? How is it different from statically typed languages like Java?
6. 🟢 What are `is` and `==` operators? How do they differ?
7. 🟢 What is `None` in Python? How is it different from `0`, `False`, or an empty string?
8. 🟢 What is the difference between `deepcopy` and `shallow copy`? When would you use each?
9. 🟡 What is the Global Interpreter Lock (GIL)? How does it affect multithreading?
10. 🟡 What is Python's memory management model? How does reference counting and garbage collection work?
11. 🟡 What is string interning in Python? How does Python cache small integers and strings?
12. 🟡 What are `*args` and `**kwargs`? How do you use them in function definitions?
13. 🟢 What is the difference between `input()` and `raw_input()` (Python 2 vs 3)?
14. 🟡 What is the difference between `__str__` and `__repr__`? When is each called?
15. 🟡 What is the `pass`, `continue`, and `break` statement? How does each work in loops?

---

## 2. Data Structures & Built-in Functions (Q16–Q30)

16. 🟢 How does a Python list work internally? What is its time complexity for common operations?
17. 🟢 What is a dictionary in Python? How does it work internally using hash tables?
18. 🟡 What is the difference between `dict.get(key)` and `dict[key]`?
19. 🟡 What are list comprehensions? How do they compare to `map()` and `filter()`?
20. 🟡 What are dictionary comprehensions and set comprehensions? Give examples.
21. 🟡 What is the difference between `append()`, `extend()`, and `insert()` for lists?
22. 🟡 What is `collections.defaultdict`? How is it different from a regular dict?
23. 🟡 What is `collections.Counter`? Give an example use case.
24. 🟡 What is `collections.OrderedDict`? Is it still needed in Python 3.7+?
25. 🟡 What is `collections.deque`? When should you use it over a list?
26. 🟡 What is `namedtuple`? How does it compare to a regular tuple and a dataclass?
27. 🟡 What are `zip()`, `enumerate()`, and `sorted()` functions? Explain with examples.
28. 🟡 What is the difference between `sort()` and `sorted()`?
29. 🟡 What is `heapq` module? How do you implement a min-heap and max-heap in Python?
30. 🟡 What is the `bisect` module? How do you perform binary search in Python?

---

## 3. Object-Oriented Programming (Q31–Q48)

31. 🟢 What are classes and objects in Python? How do you define a class?
32. 🟢 What are the four pillars of OOP in Python? Explain each briefly.
33. 🟢 What is `self` in Python? Why is it explicitly passed to instance methods?
34. 🟢 What is `__init__`? Is it a constructor? How does it differ from `__new__`?
35. 🟡 What is the difference between instance variables, class variables, and static variables?
36. 🟡 What is inheritance in Python? Does Python support multiple inheritance?
37. 🟡 What is the Method Resolution Order (MRO)? How does the C3 linearization algorithm work?
38. 🟡 What is the `super()` function? How does it work with single and multiple inheritance?
39. 🟡 What is the difference between `@staticmethod` and `@classmethod`? When would you use each?
40. 🟡 What is encapsulation in Python? What are single `_` and double `__` underscore name mangling conventions?
41. 🟡 What is polymorphism in Python? How is duck typing related to it?
42. 🟡 What are abstract classes and abstract methods? How do you use the `abc` module?
43. 🟡 What are magic/dunder methods? Explain `__len__`, `__getitem__`, `__eq__`, `__hash__`.
44. 🟡 What is operator overloading in Python? How do `__add__`, `__mul__`, etc. work?
45. 🟡 What are `@property`, `@getter`, and `@setter` decorators? How do they implement managed attributes?
46. 🟡 What are `dataclasses` (Python 3.7+)? How do they reduce boilerplate compared to regular classes?
47. 🟡 What is the difference between composition and inheritance in Python? When should you prefer one over the other?
48. 🔴 What are metaclasses in Python? How does `type` work as a metaclass?

---

## 4. Functions, Closures & Decorators (Q49–Q62)

49. 🟢 What are first-class functions in Python? What does it mean for functions to be "objects"?
50. 🟢 What is the difference between a function and a method?
51. 🟡 What are lambda functions? When should and shouldn't you use them?
52. 🟡 What are closures in Python? How does a nested function capture variables from the enclosing scope?
53. 🟡 What is the `nonlocal` keyword? How does it differ from `global`?
54. 🟡 What are decorators in Python? How do you write a simple decorator?
55. 🟡 How do you write a decorator that accepts arguments?
56. 🟡 What is `functools.wraps`? Why is it important when writing decorators?
57. 🟡 What is `functools.lru_cache`? How does it implement memoization?
58. 🟡 What are `map()`, `filter()`, and `reduce()`? How do they work with lambda functions?
59. 🟡 What is the difference between a generator function and a normal function? What does `yield` do?
60. 🟡 What are generator expressions? How do they differ from list comprehensions in memory usage?
61. 🟡 What is `itertools` module? Explain `chain()`, `product()`, `permutations()`, and `combinations()`.
62. 🔴 What are coroutines in Python? How does `yield` enable cooperative multitasking?

---

## 5. Exception Handling & File I/O (Q63–Q72)

63. 🟢 How does exception handling work in Python? Explain `try`, `except`, `else`, and `finally`.
64. 🟢 What is the difference between `Exception` and `BaseException`?
65. 🟢 What are common built-in exceptions? (`ValueError`, `TypeError`, `KeyError`, `IndexError`, `FileNotFoundError`)
66. 🟡 How do you create custom exceptions in Python? When should you do so?
67. 🟡 What is the `raise` keyword? How do you re-raise exceptions?
68. 🟡 What is exception chaining with `raise ... from ...`?
69. 🟡 What is the `with` statement and context managers? How does `__enter__` and `__exit__` work?
70. 🟡 How do you create a custom context manager using `contextlib.contextmanager`?
71. 🟡 What are the different file modes in Python (`r`, `w`, `a`, `rb`, `wb`)? How do you read and write files?
72. 🟡 What is the difference between `read()`, `readline()`, and `readlines()`?

---

## 6. Concurrency & Parallelism (Q73–Q84)

73. 🟡 What is the difference between concurrency and parallelism?
74. 🟡 What is multithreading in Python? How does the `threading` module work?
75. 🟡 Why can't Python achieve true parallelism with threads? How does the GIL affect CPU-bound tasks?
76. 🟡 What is multiprocessing in Python? How does the `multiprocessing` module differ from `threading`?
77. 🟡 What is `concurrent.futures`? Explain `ThreadPoolExecutor` and `ProcessPoolExecutor`.
78. 🟡 What is `asyncio`? How does `async`/`await` work in Python?
79. 🟡 What is an event loop in `asyncio`? How does it schedule coroutines?
80. 🟡 What is the difference between `asyncio.gather()` and `asyncio.wait()`?
81. 🟡 What are `asyncio.Queue`, `asyncio.Lock`, and `asyncio.Semaphore`?
82. 🟡 When should you use threading vs multiprocessing vs asyncio? Give practical examples.
83. 🔴 What is the `asyncio.run()` function? How do you run async code from synchronous code?
84. 🔴 What are race conditions in Python? How do you use locks, semaphores, and events to handle them?

---

## 7. Modules, Packages & Pythonic Practices (Q85–Q93)

85. 🟢 What is the difference between a module and a package in Python?
86. 🟢 What is `__name__ == "__main__"`? Why is it used?
87. 🟡 What are virtual environments? Why are they important? Explain `venv` vs `virtualenv` vs `conda`.
88. 🟡 What is `pip`? How do you manage dependencies with `requirements.txt` and `pyproject.toml`?
89. 🟡 What is PEP 8? Name some important Python coding conventions.
90. 🟡 What are type hints in Python (PEP 484)? How do `typing.List`, `typing.Dict`, `typing.Optional` work?
91. 🟡 What is the `__slots__` attribute? How does it optimize memory in classes?
92. 🟡 What is monkey patching in Python? When is it useful and when is it dangerous?
93. 🟡 What is the Walrus operator (`:=`) introduced in Python 3.8? Give a practical example.

---

## 8. Testing, Debugging & Advanced Topics (Q94–Q100)

94. 🟡 What is `unittest` module? How do you write test cases using `TestCase`?
95. 🟡 What is `pytest`? How is it different from `unittest`? Why is it preferred?
96. 🟡 What are fixtures in `pytest`? How do `@pytest.fixture` and scopes (`function`, `module`, `session`) work?
97. 🟡 How do you mock dependencies in Python tests? Explain `unittest.mock.patch` and `MagicMock`.
98. 🟡 What is the `pdb` debugger? How do you set breakpoints and step through code?
99. 🔴 What are descriptors in Python? How do `__get__`, `__set__`, and `__delete__` work?
100. 🔴 What is the Python data model? How do special methods (`__init__`, `__call__`, `__iter__`, `__next__`) define object behavior?

---

> 💡 **Tip for candidates:** For SDE-1 backend and full-stack roles, focus heavily on **Data Structures (lists, dicts internals)**, **OOP (MRO, dunder methods, decorators)**, **Generators & Iterators**, and **Concurrency (GIL, asyncio basics)**. Be ready to write clean, Pythonic code on the spot — interviewers love list comprehensions, generators, and decorator questions.
