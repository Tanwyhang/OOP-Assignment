Java is a versatile, object-oriented programming language used for developing various applications. Below is a comprehensive guide to help you grasp the basics quickly.

---

### **1. Setting Up the Environment**
- **Install Java Development Kit (JDK):** Download and install the latest JDK from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).
- **Install an IDE:** Popular choices include IntelliJ IDEA, Eclipse, or VS Code.
- **Write and Run Code:** Use an IDE or a simple text editor and run your program using the terminal.

---

### **2. Java Basics**
- **Hello World Program**
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

---

### **3. Data Types**
Java is a statically typed language. You must declare a variable with its type.

#### **Primitive Data Types:**
| Type       | Size     | Example     |
|------------|----------|-------------|
| `byte`     | 1 byte   | 127         |
| `short`    | 2 bytes  | 32000       |
| `int`      | 4 bytes  | 1_000_000   |
| `long`     | 8 bytes  | 1L          |
| `float`    | 4 bytes  | 1.23f       |
| `double`   | 8 bytes  | 1.23        |
| `char`     | 2 bytes  | 'A'         |
| `boolean`  | 1 bit    | true/false  |

#### **Non-Primitive Types:**
- `String` (e.g., `"Hello"`)
- Arrays
- Objects

---

### **4. Variables and Constants**
- **Variable:** Stores data.
```java
int age = 25;
```
- **Constant:** Value cannot be changed.
```java
final double PI = 3.14159;
```

---

### **5. Operators**
| Type                | Example        |
|---------------------|----------------|
| Arithmetic          | `+`, `-`, `*`, `/`, `%` |
| Relational          | `==`, `!=`, `>`, `<`, `>=`, `<=` |
| Logical             | `&&`, `||`, `!` |
| Assignment          | `=`, `+=`, `-=`, `*=` |

---

### **6. Control Flow**
#### **If-Else**
```java
if (condition) {
    // code
} else {
    // code
}
```

#### **Switch**
```java
switch (value) {
    case 1: System.out.println("One"); break;
    default: System.out.println("Default");
}
```

#### **Loops**
- **For Loop**
```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```
- **While Loop**
```java
while (condition) {
    // code
}
```
- **Do-While Loop**
```java
do {
    // code
} while (condition);
```

---

### **7. Arrays**
```java
int[] numbers = {1, 2, 3, 4, 5};
System.out.println(numbers[0]);
```

---

### **8. Methods**
```java
public static int add(int a, int b) {
    return a + b;
}
```

---

### **9. Object-Oriented Programming (OOP)**
#### **Class and Object**
```java
class Person {
    String name;
    int age;

    void greet() {
        System.out.println("Hello, my name is " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        p.name = "John";
        p.greet();
    }
}
```

#### **Encapsulation**
- Use `private` fields with public getters and setters.
```java
class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

#### **Inheritance**
```java
class Animal {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks.");
    }
}
```

#### **Polymorphism**
```java
class Shape {
    void draw() {
        System.out.println("Drawing Shape");
    }
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing Circle");
    }
}
```

---

### **10. Exception Handling**
```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero.");
} finally {
    System.out.println("Always executed.");
}
```

---

### **11. Java Collections**
Java provides powerful classes for managing data.
- **List:** `ArrayList`, `LinkedList`
- **Set:** `HashSet`, `TreeSet`
- **Map:** `HashMap`, `TreeMap`

---

### **12. Input/Output**
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);
    }
}
```

---

### **13. Packages**
- Organize your code into reusable modules.
```java
package mypackage;

public class MyClass {
    public void display() {
        System.out.println("Hello from MyClass");
    }
}
```

---

### **14. Build and Run Java Programs**
1. Save your file as `Main.java`.
2. Compile: `javac Main.java`
3. Run: `java Main`

---

### Key Concepts to Explore Next:
1. **Advanced OOP:** Abstraction, Interfaces.
2. **Multithreading:** Threads, synchronization.
3. **Lambdas and Streams:** Java 8 features.
4. **Database Connection:** JDBC.
5. **Frameworks:** Spring, Hibernate.
