:warning: No longer maintained, BUT, feel free to **fork** it. :wink:

# Array linked list

An **arbitrary tool**, born out of a question, **"Design a linked list structure only using arrays"**.

> During a **data structures and algorithms** college course, as an assessment.

The idea is to take **indexes as pointers** (nodes) and beside saving each datum, save information about related elements (next / previous) in arrays.

Check the implementation class, [`ArrayLinkedList`](src/main/java/io/github/agcom/arraylinkedlist/ArrayLinkedList.java)


## Usage

First, let's mention that the list doesn't implements **Java standard interfaces** yet!

Here is a small example,

> Pay attention to commented lines for functionalities.

```java
import io.github.agcom.arraylinkedlist.ArrayLinkedList;

public final class QuickExample {

    public static void main(String[] args) {
        var list = new ArrayLinkedList<Integer>(10); // Fixed capacity

        // Insert integers 1 to 3
        int index1, index2, index3;
        index1 = list.insertFirst(1); // First node
        index2 = list.insertAfter(index1, 2); // Link after the first node (1)
        index3 = list.insertAfter(index2, 3); // Link after the second node (2)

        printElements(list); // [1, 2, 3]

        list.remove(index2); // Remove 2

        printElements(list); // [1, 3]

    }

    // Example of traversing the list
    private static void printElements(ArrayLinkedList<?> list) {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');

        int currentIndex = list.isEmpty() ? -1 : list.getFirst(); // Check for emptiness, get first node
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(currentIndex)); // Get a node datum
            if (i != list.size() - 1) sb.append(", ");

            currentIndex = list.getNext(currentIndex); // Get next node
        }

        sb.append(']');
        System.out.println(sb);
    }

}
```

> Note that, the word **node** is used to align with linked list definition; In actual, it refers to an **index**.
