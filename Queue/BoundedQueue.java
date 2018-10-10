/*
 * Bounded Queue Implementation
 * Author: Tej Patel
 * Version: 1.0
 */

package queue;

import java.util.Arrays;
import java.util.Scanner;

public class BoundedQueue<T> {
	private Object[] queue;
	private int size;
	private int head;
	private int tail;
	private int len;

	public BoundedQueue(int len) {
		this.size = 0;
		this.len = len;
		queue = new Object[this.len];
		this.head = 0;
		this.tail = -1;
	}

	// Adds element at end of queue, returns true if added successfully.
	public boolean offer(T x) {
		if (this.size == this.len) {
			return false;
		}
		this.tail = (this.tail + 1) % this.len;
		this.queue[this.tail] = x;
		this.size++;
		return true;
	}

	// Remove and return first element in queue;
	public T poll() {
		if (this.isEmpty()) {
			return null;
		}
		Object element = this.queue[this.head];
		this.queue[this.head] = null;
		this.head = (this.head + 1) % this.len;
		this.size--;
		return (T) element;
	}

	// Return first element in queue;
	public T peek() {
		if (this.isEmpty()) {
			return null;
		}
		return (T) this.queue[this.head];
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void clear() {
		this.head = 0;
		this.tail = -1;
		this.size = 0;
	}

	// Fills the given array to current contents of queue in order
	public void toArray(T[] a) {
		int index = this.head;
		for (int i = 0; i < Math.min(this.size, a.length); i++) {
			a[i] = (T) this.queue[index];
			index = (index + 1) % this.len;
		}
	}

	public void printQueue() {
		System.out.println(this.size + ": ");
		int index = this.head;
		for (int i = 0; i < this.size; i++) {
			System.out.print(this.queue[index] + " ");
			index = (index + 1) % this.len;
		}
		System.out.println();
	}

    //Driver
	public static void main(String[] cd) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter size of queue - ");
		int size = sc.nextInt();
		BoundedQueue<Integer> bq = new BoundedQueue<>(size);
		whileloop: while (sc.hasNext()) {
			int op = sc.nextInt();
			switch (op) {
			case 1: // offer
				int item = sc.nextInt();
				bq.offer(Integer.valueOf(item));
				bq.printQueue();
				break;
			case 2: // poll
				System.out.println(bq.poll());
				bq.printQueue();
				break;
			case 3: // peek
				System.out.println(bq.peek());
				break;
			case 4: // size
				System.out.println(bq.size());
				break;
			case 5: // isEmpty
				System.out.println(bq.isEmpty());
				break;
			case 6: // clear
				bq.clear();
				bq.printQueue();
				break;
			case 7: // toArray
				Integer[] a = new Integer[bq.size];
				bq.toArray(a);
				System.out.println(Arrays.toString(a));
				break;
			case 8: // terminate
				break whileloop;
			}
		}
	}
}