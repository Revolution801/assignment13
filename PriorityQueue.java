package assignment13;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue to be used in our NetworkGraph class. The queue
 * is implemented as a min heap. The min heap is implemented implicitly as an
 * array.
 * 
 * @author Kent Allen, Alec Becker
 */
public class PriorityQueue {
	
	private int currentSize;
	
	private AirportVertex[] array;
	
	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable) AnyType is
	 * not forced to be Comparable.
	 */
	public PriorityQueue() {
		currentSize = 0;
		array = new AirportVertex[10]; // safe to ignore warning
	}
	
	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return currentSize;
	}
	
	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
	}
	
	/**
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in constant time.)
	 */
	public AirportVertex findMin() throws NoSuchElementException {
		if(currentSize == 0) { // Just checks the item count.
			throw new NoSuchElementException();
		}
		return array[0];
	}
	
	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AirportVertex deleteMin() throws NoSuchElementException {
		// if the heap is empty, throw a NoSuchElementException
		if(currentSize == 0) { // Just checks the item count.
			throw new NoSuchElementException();
		}
		
		// store the minimum item so that it may be returned at the end
		AirportVertex deletedMin = array[0];
		
		swap(0, currentSize - 1);
		
		array[currentSize - 1] = null;
		
		// replace the item at minIndex with the last item in the tree
		
		// update size
		
		int currentIndex = 0;
		currentSize--;
		percolateDown(currentIndex);
		
		// return the minimum item that was stored
		return deletedMin;
	}
	
	/**
	 * Percolates the item down until heap order is restored
	 * 
	 * @param currentIndex
	 *            - Index of the item to be percolated down
	 */
	private void percolateDown(int currentIndex) {
		int leftChildIndex = leftChildIndex(currentIndex);
		int rightChildIndex = rightChildIndex(currentIndex);
		int minIndex;
		
		if(leftChildIndex > currentSize || rightChildIndex > currentSize || (array[leftChildIndex] == null && array[rightChildIndex] == null)) {
			return;
		}
		
		minIndex = childNullCheck(leftChildIndex, rightChildIndex);
		
		// percolate the item at minIndex down the tree until heap order is
		// restored
		while(minIndex < currentSize && compare(array[currentIndex], array[minIndex]) > 0) {
			swap(currentIndex, minIndex);
			currentIndex = minIndex;
			leftChildIndex = leftChildIndex(currentIndex);
			if(leftChildIndex > currentSize - 1) {
				return;
			}
			rightChildIndex = rightChildIndex(currentIndex);
			if(rightChildIndex > currentSize - 1) {
				rightChildIndex = leftChildIndex;
			}
			
			if(array[leftChildIndex] == null && array[rightChildIndex] == null) {
				return;
			}
			
			minIndex = childNullCheck(leftChildIndex, rightChildIndex);
		}
	}
	
	/**
	 * Checks if the children are null and returns the appropriate index.
	 * 
	 * @param leftChildIndex
	 * @param rightChildIndex
	 * @return - The index of the smaller child
	 */
	private int childNullCheck(int leftChildIndex, int rightChildIndex) {
		int minIndex = 0;
		if(array[leftChildIndex] != null && array[rightChildIndex] != null) {
			minIndex = findMinOfChildren(leftChildIndex, rightChildIndex);
		} else if(array[leftChildIndex] == null && array[rightChildIndex] != null) {
			minIndex = rightChildIndex;
		} else if(array[leftChildIndex] != null && array[rightChildIndex] == null) {
			minIndex = leftChildIndex;
		}
		return minIndex;
	}
	
	/**
	 * Returns the smaller of the two children. If one child is null the other
	 * is the minimum.
	 * 
	 * @param leftChildIndex
	 *            - Index of the right child
	 * @param rightChildIndex
	 *            - Index of the left child
	 * @return - The index of the minimum child
	 */
	private int findMinOfChildren(int leftChildIndex, int rightChildIndex) {
		int minIndex;
		
		if(compare(array[leftChildIndex], array[rightChildIndex]) < 0) {
			minIndex = leftChildIndex;
		} else {
			minIndex = rightChildIndex;
		}
		return minIndex;
	}
	
	/**
	 * Adds an item to this priority queue.
	 * 
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * @param x
	 *            -- the item to be inserted
	 */
	public void add(AirportVertex x) {
		// if the array is full, double its capacity
		if(currentSize == array.length) {
			resizeArray();
		}
		
		// add the new item to the next available node in the tree, so that
		// complete tree structure is maintained
		array[currentSize] = x;
		// update size
		int currentIndex = currentSize;
		currentSize++;
		
		// percolate the new item up the levels of the tree until heap order is
		// restored
		percolateUp(currentIndex);
	}
	
	/**
	 * Doubles the size of the backing array
	 */
	private void resizeArray() {
		AirportVertex[] newList = new AirportVertex[currentSize * 2];
		
		// Copy items from the old array to the new array.
		for(int i = 0; i < array.length; i++) {
			newList[i] = array[i];
		}
		// Copy the new array reference.
		array = newList;
	}
	
	/**
	 * Percolates the newly added item up until heap order is restored
	 * 
	 * @param currentIndex
	 *            - Index of the newly added item
	 */
	private void percolateUp(int currentIndex) {
		int parentIndex = findParent(currentIndex);
		while(compare(array[currentIndex], array[parentIndex]) < 0) {
			swap(currentIndex, parentIndex);
			currentIndex = parentIndex;
			parentIndex = findParent(currentIndex);
		}
	}
	
	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try(PrintWriter out = new PrintWriter(filename)) {
			out.println("digraph Heap {\n\tnode [shape=record]\n");
			
			for(int i = 0; i < currentSize; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + array[i] + "|<f2> \"]");
				if(((i * 2) + 1) < currentSize)
					out.println("\tnode" + i + ":f0 -> node" + ((i * 2) + 1) + ":f1");
				if(((i * 2) + 2) < currentSize)
					out.println("\tnode" + i + ":f2 -> node" + ((i * 2) + 2) + ":f1");
			}
			out.println("}");
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by
	 * the user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	private int compare(AirportVertex lhs, AirportVertex rhs) {
		
		return lhs.compareTo(rhs);
	}
	
	// LEAVE IN for grading purposes
	public Object[] toArray() {
		Object[] ret = new Object[currentSize];
		for(int i = 0; i < currentSize; i++) {
			ret[i] = array[i];
		}
		return ret;
	}
	
	/**
	 * helper method that returns the index of the parent when child's index is
	 * entered
	 * 
	 * * @param child - the index where the child data is located at.
	 */
	private int findParent(int child) {
		return (child - 1) / 2;
	}
	
	/**
	 * helper method that returns the index of the left child when a parent's
	 * index is entered
	 * 
	 * * @param parent - the index where the child data is located at.
	 */
	private int leftChildIndex(int parent) {
		return (parent * 2) + 1;
	}
	
	/**
	 * helper method that returns the index of the right child when a parent's
	 * index is entered
	 * 
	 * * @param parent - the index where the child data is located at.
	 */
	private int rightChildIndex(int parent) {
		return (parent * 2) + 2;
	}
	
	/**
	 * Swaps two items in the backed array
	 * 
	 * @param idx1
	 *            - Index of the first item
	 * @param idx2
	 *            - Index of the second item
	 */
	private void swap(int idx1, int idx2) {
		AirportVertex temp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = temp;
	}
	
	/**
	 * Returns the index of the object if it is in the queue, -1 otherwise
	 * 
	 * @param obj
	 * @return
	 */
	public int indexOf(AirportVertex obj) {
		for(int i = 0; i < array.length; i++) {
			if(array[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes the specified object from the queue if it exists
	 * 
	 * @param index
	 * @throws NoSuchElementException
	 */
	public void remove(int index) throws NoSuchElementException {
		if(currentSize == 0) { // Just checks the item count.
			throw new NoSuchElementException();
		}
		
		swap(index, currentSize - 1);
		
		array[currentSize - 1] = null;
		
		// replace the item at minIndex with the last item in the tree
		
		// update size
		
		int currentIndex = index;
		currentSize--;
		percolateDown(currentIndex);
	}
	
	/**
	 * Returns the item if it is in the queue, null if it does not exist in the
	 * queue.
	 * 
	 * @param airport
	 * @return
	 */
	public AirportVertex get(AirportVertex airport) {
		for(int idx = 0; idx < currentSize; idx++) {
			if(array[idx].equals(airport)) {
				return array[idx];
			}
		}
		return null;
	}
}