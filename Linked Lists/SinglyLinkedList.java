/** @author Tej Patel
 */

package linkedlists;

import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

	/** Class Entry holds a single node of the list */
	static class Entry<E> {
		E element;
		Entry<E> next;

		Entry(E x, Entry<E> nxt) {
			element = x;
			next = nxt;
		}
	}

	// Dummy header is used. tail stores reference of tail element of list
	Entry<T> head, tail;
	int size;

	public SinglyLinkedList() {
		head = new Entry<>(null, null);
		tail = head;
		size = 0;
	}

	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	protected class SLLIterator implements Iterator<T> {
		Entry<T> cursor, prev;
		boolean ready; // is item ready to be removed?

		SLLIterator() {
			cursor = head;
			prev = null;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}

		public T next() {
			prev = cursor;
			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next())
		// Remove can be called only if next has been called and the element has
		// not been removed
		public void remove() {
			if (!ready) {
				throw new NoSuchElementException();
			}
			prev.next = cursor.next;
			// Handle case when tail of a list is deleted
			if (cursor == tail) {
				tail = prev;
			}
			cursor = prev;
			ready = false; // Calling remove again without calling next will
							// result in exception thrown
			size--;
		}
	} // end of class SLLIterator

	// add first
	public void addFirst(T item) {
		Entry<T> node = new Entry<>(item, head.next);
		head.next = node;
		size++;
	}

	// remove first
	public T removeFirst() {
		if (head.next == null) {
			throw new NoSuchElementException();
		}
		Entry<T> next = head.next;
		head.next = next.next;
		size--;
		return next.element;
	}

	// remove a particular element
	public int remove(T x) {
		Entry<T> cursor = head.next;
		Entry<T> prev = head;
		int index = 0;
		while (cursor != null) {
			if (cursor.element == x) {
				prev.next = cursor.next;
				size--;
				return index;
			}
			index++;
			prev = cursor;
			cursor = cursor.next;
		}
		throw new NoSuchElementException();
	}

	// Return element at given index
	public T get(int index) {
		if (head.next == null || index >= size) {
			throw new NoSuchElementException();
		}
		int ind = 0;
		Entry<T> cursor = head.next;
		while (cursor != null) {
			if (ind == index) {
				break;
			}
			cursor = cursor.next;
			ind++;
		}
		return cursor.element;
	}

	// set element at given index to element
	public void set(int index, T element) {
		if (head.next == null || index >= size) {
			throw new NoSuchElementException();
		}
		int ind = 0;
		Entry<T> cursor = head.next;
		while (cursor != null) {
			if (ind == index) {
				break;
			}
			cursor = cursor.next;
			ind++;
		}
		cursor.element = element;
	}

	// add new element at given index
	public void add(int index, T element) {
		if (index > size) {
			throw new NoSuchElementException();
		}
		int ind = 0;
		Entry<T> cursor = head;
		while (cursor != null) {
			if (ind == index) {
				Entry<T> node = new Entry<>(element, cursor.next);
				cursor.next = node;
				size++;
				break;
			}
			cursor = cursor.next;
			ind++;
		}
	}

	// remove element at particular index
	public T remove(int index) {
		if (head.next == null || index >= size) {
			throw new NoSuchElementException();
		}
		int ind = 0;
		Entry<T> cursor = head.next;
		Entry<T> prev = head;
		T returnVal = null;
		while (cursor != null) {
			if (ind == index) {
				returnVal = cursor.element;
				prev.next = cursor.next;
				size--;
				break;
			}
			prev = cursor;
			cursor = cursor.next;
			ind++;
		}
		return returnVal;
	}

	// Add new elements to the end of the list
	public void add(T x) {
		add(new Entry<>(x, null));
	}

	public void add(Entry<T> ent) {
		tail.next = ent;
		tail = tail.next;
		size++;
	}

	public void printList() {
		System.out.print(this.size + ": ");
		for (T item : this) {
			System.out.print(item + " ");
		}

		System.out.println();
	}

	// Rearrange the elements of the list by linking the elements at even index
	// followed by the elements at odd index. Implemented by rearranging
	// pointers
	// of existing elements without allocating any new elements.
	public void unzip() {
		if (size < 3) { // Too few elements. No change.
			return;
		}

		Entry<T> tail0 = head.next;
		Entry<T> head1 = tail0.next;
		Entry<T> tail1 = head1;
		Entry<T> c = tail1.next;
		int state = 0;

		// Invariant: tail0 is the tail of the chain of elements with even
		// index.
		// tail1 is the tail of odd index chain.
		// c is current element to be processed.
		// state indicates the state of the finite state machine
		// state = i indicates that the current element is added after taili
		// (i=0,1).
		while (c != null) {
			if (state == 0) {
				tail0.next = c;
				tail0 = c;
				c = c.next;
			} else {
				tail1.next = c;
				tail1 = c;
				c = c.next;
			}
			state = 1 - state;
		}
		tail0.next = head1;
		tail1.next = null;
		// Update the tail of the list
		tail = tail1;
    }
    
    // Driver
	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(Integer.valueOf(i));
		}
		lst.printList();

		Iterator<Integer> it = lst.iterator();
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1: // Move to next element and print it
				if (it.hasNext()) {
					System.out.println(it.next());
				} else {
					break whileloop;
				}
				break;
			case 2: // Remove element
				it.remove();
				lst.printList();
				break;
			case 3: // Add first
				int elem = in.nextInt();
				lst.addFirst(elem);
				lst.printList();
				break;
			case 4: // Remove first
				System.out.println(lst.removeFirst());
				lst.printList();
				break;
			case 5: // Remove x
				elem = in.nextInt();
				System.out.println(lst.remove(Integer.valueOf(elem)));
				lst.printList();
				break;
			case 6: // get index
				int index = in.nextInt();
				System.out.println(lst.get(index));
				break;
			case 7: // set index
				index = in.nextInt();
				elem = in.nextInt();
				lst.set(index, elem);
				lst.printList();
				break;
			case 8: // add index
				index = in.nextInt();
				elem = in.nextInt();
				lst.add(index, elem);
				lst.printList();
				break;
			case 9: // remove index
				index = in.nextInt();
				lst.remove(index);
				lst.printList();
				break;
			default: // Exit loop
				break whileloop;
			}
		}
		lst.printList();
		lst.unzip();
		lst.printList();
	}
}