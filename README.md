[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/6khmQbkb)
# RAIK 184H LINKED LIST LAB


## Objectives

1.  Implement a linked list in Java.
2.  Write an implementation of the List interface.


## Overview

For this lab we provide a partial implementation of the List interface that uses a linked list to store the elements.  We left three of the methods incomplete; your job is to fill them in.  We provide JUnit tests you can use to check your work.

If you are not familiar with a linked list, you might want to read [the Wikipedia page about it](https://en.wikipedia.org/wiki/Linked_list), but we'll also start with a brief introduction.


## Linked data structures

A data structure is "linked" if it is made up of objects, often called "nodes", that contain references to other nodes.  In a linked *list*, each node contains a reference to the next node in the list.  Other linked structures include trees and graphs, in which nodes can contain references to more than one other node.

A linked list is used to store a sequence of elements, so each node contains a reference to an element, or sometimes to a collection of elements.  The element part of the node is sometimes called "cargo", so you can think of nodes as rail cars, where each car contains cargo and the cars are connected together.

Let's see what this looks like in code.  Here's a class definition for a simple Node:

```java
public class ListNode {

	public Object cargo;
	public ListNode next;

	public ListNode() {
		this.cargo = null;
		this.next = null;
	}

	public ListNode(Object cargo) {
		this.cargo = cargo;
		this.next = null;
	}

	public ListNode(Object cargo, ListNode next) {
		this.cargo = cargo;
		this.next = next;
	}

	public String toString() {
		return "ListNode(" + cargo.toString() + ")";
	}
}
```

The ListNode object has two instance variables: `cargo` is a reference to some kind of Object, and `next` is a reference to the next node in the list.  In the last node in the list, by convention, `next` is `null`.

ListNode provides several constructors, allowing you to provide values for `cargo` and `next`, or initialize them to the default value, `null`.

You can think of each ListNode as a list with a single element, but more generally, a list can contain any number of nodes.  There are several ways to make a new list.  A simple way is to create a set of ListNode objects, like this:

```java
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
```

And then link them up, like this:

```java
		node1.next = node2;
		node2.next = node3;
		node3.next = null;
```

Alternatively, you can create a Node and link it at the same time.  For example, if you want to add a new Node at the beginning of a list, you can do it like this:

```java
		ListNode node0 = new ListNode(0, node1);
```

After this sequence of instructions, we have four Nodes containing the Integers 0, 1, 2, and 3 as cargo, linked up in increasing order.  In the code that has 3 as `cargo`, the `next` field is `null`.

The following diagram shows these variables and the objects they refer to:

![linked list](https://curriculum-content.s3.amazonaws.com/javacs/cs-implementing-a-linkedlist-lab/linked_list_diagram1.png)

## Project folder structure

In the source folder, you'll find the source files you need for this lab:

*  `MyLinkedList.java` contains a partial implementation of the `List` interface using a linked list to store the elements.
*  `MyLinkedListTest.java` contains JUnit tests for `MyLinkedList`.

Run `MyArrayList.java`, which contains a few simple tests.  Or, run `MyLinkedListTest.java` for more tests.

When you run the tests, several of them should fail.  If you examine the source code, you'll find three `TODO` comments indicating which methods you will fill in.


## `MyLinkedList code`

Before you start filling in the missing methods, let's walk through some of the code.  Here are the instance variables and the constructor for `MyLinkedList`:

```java
public class MyLinkedList<E> implements List<E> {

	private int size;            // keeps track of the number of elements
	private Node head;           // reference to the first node

	public MyLinkedList() {
		head = null;
		size = 0;
	}
}
```

As the comments indicate, `size` keeps track of how many elements are in `MyLinkedList`; `head` is a reference to the first Node in the list, or `null` if the list is empty.

Storing the number of elements is not necessary, and in general it is risky to keep redundant information, because if it's not updated correctly, it creates opportunities for error.  It also takes a little bit of extra space.

But if we store `size` explicitly, we can implement the `size` method in constant time; otherwise, we would have to traverse the list and count the elements, which requires linear time.

Because we store `size` explicitly, we have to update it each time we add or remove an element, so that slows down those methods a little, but it doesn't change their order of growth, so it's probably worth it.

The constructor sets `head` to `null`, which indicates an empty list, and sets `size` to 0.

This class uses the type parameter `E` for the type of the elements.  If you are not familiar with type parameters, you might want to read [this tutorial](https://docs.oracle.com/javase/tutorial/java/generics/types.html).

The type parameter also appears in the definition of `Node`, which is nested inside `MyLinkedList`:

```java
	private class Node {
		public E cargo;
		public Node next;

		public Node(E cargo, Node next) {
			this.cargo = cargo;
			this.next = next;
		}
	}
```

Other than that, `Node` is similar to `ListNode` above.


## Adding nodes

At this point you should have a general idea of how this implementation of linked lists works.
But before you get started on the exercises, we'll look at one more method.  Here's my implementation of `add`:

```java
	public boolean add(E element) {
		if (head == null) {
			head = new Node(element);
		} else {
			Node node = head;
			// loop until the last node
			for ( ; node.next != null; node = node.next) {}
			node.next = new Node(element);
		}
		size++;
		return true;
	}
```
This example demonstrates two patterns you'll need for your solutions:

1.  For many methods, we have to handle the first element of the list as a special case.  In this example, if we are adding the first element of a list, we have to modify `head`.  Otherwise, we traverse the list, find the end, and add the new node.

2.  This method shows how to use a `for` loop to traverse the nodes in a list.  In your solutions, you will probably write several variations on this loop.  Notice that we have to declare `node` before the loop so we can access it after the loop.


## Instructions

Now it's your turn:

*    Fill in the body of `indexOf`.  As usual, you should [read the documentation](https://docs.oracle.com/javase/7/docs/api/java/util/List.html#indexOf(java.lang.Object)) so you know what it's supposed to do.  In particular, notice how it is supposed to handle `null`.

    To make things a little easier, I've provided a helper method called `equals` that compares an element from the array to a target value and checks whether they are equal — and it handles `null` correctly.  This method is private because it is used inside this class but it is not part of the `List` interface.

    When you are done, run `ant test` again; `testIndexOf` should pass now, as well as the other tests that depend on it.

*   Next, you should fill in the two-parameter version of `add`, which takes an index and stores the new value at the given index.  Again, [read the documentation](https://docs.oracle.com/javase/7/docs/api/java/util/List.html#add(int,%20E)), write an implementation, and run the tests for confirmation.


*  Last one: fill in both `remove` methods.  One takes the element that should be removed and returns a `boolean`; the other takes the index of the element that should be removed and returns the element.  When you get these working, all tests should pass.


*  Once you have your implementation working (meaning all unit tests are passing), push it to GitHub!



# A note on garbage collection

In ArrayLists, the array grows if necessary, but it never shrinks.  The array never gets garbage collected, and the elements don't get garbage collected until the list itself is destroyed.

One advantage of the linked list implementation is that it shrinks when elements are removed, and the unused Nodes can get garbage collected immediately.

Here is my implementation of the `clear` method:

```java
	public void clear() {
		head = null;
		size = 0;
	}
```

When we set `head` to `null`, we remove a reference to the first `Node`.  If there are no other references to that `Node` (and there shouldn't be), it will get garbage collected.  At that point, the reference to the second `Node` is removed, so it gets garbage collected, too.  This process continues until all `Nodes` are collected.

So how should we classify `clear`?  The method itself contains two constant time operations, so it sure looks like it's constant time.  But when you invoke it, you make the garbage collector do work that's proportional to the number of elements.  So there's an argument that we should consider it linear!

This is a subtle example of what is sometimes called a "performance bug": a program that is correct in the sense that it does the right thing, but it doesn't belong to the order of growth we expected.  In languages like Java that do a lot of work, like garbage collection, behind the scenes, this kind of error can be hard to find.

## Resources

[Linked list Wikipedia page](https://en.wikipedia.org/wiki/Linked_list)
[Generic types](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
