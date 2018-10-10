/*
 * Author:Tej Patel
 */

package linkedlists;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;

		Entry(E x, Entry<E> next, Entry<E> prev) {
			super(x, next);
			this.prev = prev;
		}
	}

	DoublyLinkedList() {
		head = new Entry<>(null, null, null);
		tail = head;
	}

	public DLLIterator dllIterator() {
		return new DLLIterator();
	}

	class DLLIterator extends SinglyLinkedList<T>.SLLIterator {
		public boolean hasPrev() {
			return ((Entry<T>) cursor).prev != null;
		}

		public void remove() {
			super.remove();
			if (cursor.next != null) {
				((Entry<T>) cursor.next).prev = (Entry<T>) prev;
			}
		}

		public T prev() {
			cursor = ((Entry<T>) cursor).prev;
			prev = ((Entry<T>) cursor).prev;
			if (cursor != head) {
				ready = true;
			} else {
				ready = false;
			}
			return cursor.element;
		}

		public void add(T x) {
			Entry<T> nxt = (Entry<T>) cursor.next;
			cursor.next = new Entry<T>(x, null, (Entry<T>) cursor);
			cursor = cursor.next;
			cursor.next = nxt;
			if (nxt != null) {
				nxt.prev = (Entry<T>) cursor;
            }
            ready = false;
			size++;
		}
	}

	public void add(T x) {
		super.add(new Entry<T>(x, null, (Entry<T>) tail));
	}

    //Driver
	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(Integer.valueOf(i));
		}
		lst.printList();
		DoublyLinkedList<Integer>.DLLIterator it = lst.dllIterator();
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

			case 2: // Move to previous element and print it
				if (it.hasPrev()) {
					System.out.println(it.prev());
				} else {
					break whileloop;
				}
				break;

			case 3: // Remove element
				it.remove();
				lst.printList();
				break;

			case 4: // Add element
				int item = in.nextInt();
				it.add(item);
				lst.printList();
				break;

			default: // Exit loop
				break whileloop;
			}
		}
		lst.printList();
		lst.printList();
	}
}