/**
 * @author Tej Patel
 */
package heap;

import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
	T[] pq;
	Comparator<T> comp;
	int size;
	int len;

	// Constructor for building an empty priority queue using natural ordering
	// of T
	public BinaryHeap(T[] q) {
		// Use a lambda expression to create comparator from compareTo
		this(q, (T a, T b) -> a.compareTo(b));
	}

	// Constructor for building an empty priority queue with custom comparator
	public BinaryHeap(T[] q, Comparator<T> c) {
		size = -1;
		len = q.length;
		pq = q;
		comp = c;
	}

	// Add element in priority queue (throws exception if queue is full)
	public void add(T x) throws Exception {
		if (isFull()) {
			throw new Exception("Priority Queue is Full");
		}
		size++;
		pq[size] = x;
		percolateUp(size);
	}

	// Add element in priority queue (does not throw exception if queue is full,
	// returns false)
	public boolean offer(T x) throws Exception {
		if (isFull()) {
			return false;
		}
		add(x);
		return true;
	}

	// Remove element in priority queue (throws exception if queue is empty)
	public T remove() throws Exception {
		if (isEmpty()) {
			throw new Exception("Priority Queue is empty");
		}
		T value = pq[0];
		pq[0] = pq[size];
		size--;
		percolateDown(0);
		return value;
	}

	// Remove element in priority queue (does not throw exception if queue is
	// empty, returns null)
	public T poll() throws Exception {
		if (isEmpty()) {
			return null;
		}
		return remove();
	}

	public T peek() {
		if (isEmpty()) {
			return null;
		}
		return pq[0];
	}

	// pq[i] may violate heap order with parent
	void percolateUp(int i) {
		T curVal = pq[i];
		while (i > 0 && comp.compare(curVal, pq[parent(i)]) < 0) {
			move(i, pq[parent(i)]);
			i = parent(i);
		}
		move(i, curVal);
	}

	// pq[i] may violate heap order with children
	void percolateDown(int i) {
		T curVal = pq[i];
		int dominant = leftChild(i);
		while (dominant <= size) {
			if ((dominant + 1) <= size && comp.compare(pq[dominant], pq[dominant + 1]) > 0) {
				dominant++;
			}
			if (comp.compare(curVal, pq[dominant]) < 0) {
				break;
			}
			move(i, pq[dominant]);
			i = dominant;
			dominant = leftChild(i);
		}
		move(i, curVal);
	}

	boolean isEmpty() {
		return size == -1;
	}

	boolean isFull() {
		return size == len - 1;
	}

	// Assign x to pq[i]. Indexed heap will override this method
	void move(int i, T x) {
		pq[i] = x;
	}

	int parent(int i) {
		return (i - 1) / 2;
	}

	int leftChild(int i) {
		return 2 * i + 1;
	}

	public void printPriorityQueue() {
		System.out.print((size + 1) + ": ");
		for (int i = 0; i <= size; i++) {
			System.out.print(pq[i] + " ");
		}
		System.out.println();
	}

    //Driver
	public static void main(String[] cd) throws Exception {
		Integer[] pq = new Integer[5];
		BinaryHeap<Integer> bh = new BinaryHeap<>(pq);// , (Integer a, Integer
														// b) ->
														// b.compareTo(a));
		Scanner sc = new Scanner(System.in);
		whileloop: while (sc.hasNext()) {
			int insertVal;
			switch (sc.nextInt()) {
			case 1: // Add
				insertVal = sc.nextInt();
				bh.add(insertVal);
				bh.printPriorityQueue();
				break;
			case 2: // Offer
				insertVal = sc.nextInt();
				bh.offer(insertVal);
				bh.printPriorityQueue();
				break;
			case 3: // Remove
				System.out.println(bh.remove());
				bh.printPriorityQueue();
				break;
			case 4: // Poll
				System.out.println(bh.poll());
				bh.printPriorityQueue();
				break;
			case 5: // Peek
				System.out.println(bh.peek());
				break;
			default:
				break whileloop;
			}
		}
	}
}