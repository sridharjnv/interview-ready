# Java String Methods - Complete Guide

The `String` class in Java is one of the most commonly used classes. Here's a comprehensive overview of all its methods, organized by category.

---

## 1. Length & Empty Check

| Method | Description | Example |
|--------|-------------|---------|
| `length()` | Returns the number of characters | `"Hello".length()` → `5` |
| `isEmpty()` | Returns `true` if length is 0 | `"".isEmpty()` → `true` |
| `isBlank()` | Returns `true` if empty or only whitespace (Java 11+) | `"   ".isBlank()` → `true` |

---

## 2. Character Access

| Method | Description | Example |
|--------|-------------|---------|
| `charAt(int index)` | Returns char at specified index | `"Hello".charAt(1)` → `'e'` |
| `codePointAt(int index)` | Returns Unicode code point at index | `"A".codePointAt(0)` → `65` |
| `codePointBefore(int index)` | Returns code point before index | `"AB".codePointBefore(1)` → `65` |
| `codePointCount(int begin, int end)` | Returns number of code points in range | `"Hello".codePointCount(0, 5)` → `5` |
| `toCharArray()` | Converts string to char array | `"Hi".toCharArray()` → `['H', 'i']` |

---

## 3. Comparison Methods

| Method | Description | Example |
|--------|-------------|---------|
| `equals(Object obj)` | Compares content for equality | `"abc".equals("abc")` → `true` |
| `equalsIgnoreCase(String str)` | Case-insensitive equality | `"ABC".equalsIgnoreCase("abc")` → `true` |
| `compareTo(String str)` | Lexicographic comparison | `"a".compareTo("b")` → `-1` |
| `compareToIgnoreCase(String str)` | Case-insensitive comparison | `"A".compareToIgnoreCase("a")` → `0` |
| `contentEquals(CharSequence cs)` | Compares with CharSequence | `"abc".contentEquals("abc")` → `true` |

---

## 4. Search Methods

| Method | Description | Example |
|--------|-------------|---------|
| `indexOf(String str)` | First occurrence index (-1 if not found) | `"Hello".indexOf("l")` → `2` |
| `indexOf(String str, int fromIndex)` | Search from specific index | `"Hello".indexOf("l", 3)` → `3` |
| `lastIndexOf(String str)` | Last occurrence index | `"Hello".lastIndexOf("l")` → `3` |
| `contains(CharSequence s)` | Checks if substring exists | `"Hello".contains("ell")` → `true` |
| `startsWith(String prefix)` | Checks prefix | `"Hello".startsWith("He")` → `true` |
| `endsWith(String suffix)` | Checks suffix | `"Hello".endsWith("lo")` → `true` |
| `matches(String regex)` | Matches entire string against regex | `"123".matches("\\d+")` → `true` |

---

## 5. Substring & Extraction

| Method | Description | Example |
|--------|-------------|---------|
| `substring(int beginIndex)` | From index to end | `"Hello".substring(2)` → `"llo"` |
| `substring(int begin, int end)` | From begin to end (exclusive) | `"Hello".substring(1, 4)` → `"ell"` |
| `subSequence(int begin, int end)` | Returns CharSequence | `"Hello".subSequence(0, 2)` → `"He"` |

---

## 6. Modification Methods (Return new String)

| Method | Description | Example |
|--------|-------------|---------|
| `concat(String str)` | Concatenates strings | `"Hello".concat(" World")` → `"Hello World"` |
| `replace(char old, char new)` | Replaces all occurrences of char | `"Hello".replace('l', 'w')` → `"Hewwo"` |
| `replace(CharSequence target, CharSequence replacement)` | Replaces all occurrences of sequence | `"aaa".replace("a", "b")` → `"bbb"` |
| `replaceAll(String regex, String replacement)` | Regex-based replacement | `"a1b2".replaceAll("\\d", "X")` → `"aXbX"` |
| `replaceFirst(String regex, String replacement)` | Replaces first regex match | `"aaa".replaceFirst("a", "b")` → `"baa"` |
| `toUpperCase()` | Converts to uppercase | `"hello".toUpperCase()` → `"HELLO"` |
| `toLowerCase()` | Converts to lowercase | `"HELLO".toLowerCase()` → `"hello"` |
| `trim()` | Removes leading/trailing whitespace | `"  Hi  ".trim()` → `"Hi"` |
| `strip()` | Unicode-aware trim (Java 11+) | `"  Hi  ".strip()` → `"Hi"` |
| `stripLeading()` | Removes leading whitespace (Java 11+) | `"  Hi".stripLeading()` → `"Hi"` |
| `stripTrailing()` | Removes trailing whitespace (Java 11+) | `"Hi  ".stripTrailing()` → `"Hi"` |

---

## 7. Split & Join

| Method | Description | Example |
|--------|-------------|---------|
| `split(String regex)` | Splits by regex | `"a,b,c".split(",")` → `["a", "b", "c"]` |
| `split(String regex, int limit)` | Splits with limit | `"a,b,c".split(",", 2)` → `["a", "b,c"]` |
| `join(CharSequence delimiter, CharSequence... elements)` | Static - joins elements | `String.join("-", "a", "b")` → `"a-b"` |

---

## 8. Formatting & Conversion

| Method | Description | Example |
|--------|-------------|---------|
| `format(String format, Object... args)` | Static - formats string | `String.format("Hi %s", "John")` → `"Hi John"` |
| `formatted(Object... args)` | Instance format (Java 15+) | `"Hi %s".formatted("John")` → `"Hi John"` |
| `valueOf(...)` | Static - converts to String | `String.valueOf(123)` → `"123"` |
| `toString()` | Returns the string itself | `"Hello".toString()` → `"Hello"` |

---

## 9. Java 11+ Modern Methods

| Method | Description | Example |
|--------|-------------|---------|
| `repeat(int count)` | Repeats string n times | `"ab".repeat(3)` → `"ababab"` |
| `lines()` | Returns Stream of lines | `"a\nb".lines()` → Stream |
| `indent(int n)` | Adjusts indentation (Java 12+) | Adds/removes spaces |
| `transform(Function f)` | Applies function (Java 12+) | `"hi".transform(String::toUpperCase)` |
| `translateEscapes()` | Translates escape sequences (Java 15+) | `"\\n".translateEscapes()` → newline |

---

## 10. Byte & Encoding

| Method | Description | Example |
|--------|-------------|---------|
| `getBytes()` | Returns byte array (default charset) | `"Hi".getBytes()` |
| `getBytes(String charset)` | Returns bytes in specified charset | `"Hi".getBytes("UTF-8")` |
| `getBytes(Charset charset)` | Returns bytes using Charset object | `"Hi".getBytes(StandardCharsets.UTF_8)` |

---

## 11. Interning & Hash

| Method | Description | Example |
|--------|-------------|---------|
| `intern()` | Returns canonical representation from string pool | `new String("Hi").intern()` |
| `hashCode()` | Returns hash code | `"Hello".hashCode()` → `69609650` |

---

## 12. Region Matching

| Method | Description |
|--------|-------------|
| `regionMatches(int toffset, String other, int ooffset, int len)` | Tests if regions are equal |
| `regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)` | Case-insensitive region match |

---

## 💡 Key Points to Remember

1. **Strings are immutable** - All "modification" methods return a NEW string
2. **String Pool** - String literals are stored in a pool for memory efficiency
3. **Use `equals()` not `==`** - For content comparison
4. **StringBuilder/StringBuffer** - Use for frequent modifications (mutable)

---

## Common Interview Patterns

```java
// Reverse a string
new StringBuilder(str).reverse().toString();

// Check palindrome
str.equals(new StringBuilder(str).reverse().toString());

// Remove duplicates (preserve order)
str.chars().distinct()
   .mapToObj(c -> String.valueOf((char)c))
   .collect(Collectors.joining());

// Count character occurrences
str.chars().filter(c -> c == 'a').count();

// Convert to Title Case
Arrays.stream(str.split(" "))
      .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
      .collect(Collectors.joining(" "));
```

---

## StringBuilder vs StringBuffer

| Feature | StringBuilder | StringBuffer |
|---------|---------------|--------------|
| Thread Safety | Not thread-safe | Thread-safe (synchronized) |
| Performance | Faster | Slower |
| Use Case | Single-threaded | Multi-threaded |

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" World");
sb.reverse();
sb.insert(0, "Start: ");
sb.delete(0, 7);
String result = sb.toString();
```
