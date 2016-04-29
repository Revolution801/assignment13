/**
 * This class represents a HashTable that uses separate chaining to deal with collisions
 * 
 * @author Kent Allen, Alec Becker
 */
package assignment13;

import java.util.Collection;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class ChainingHashTable<T> implements Set<T> {
	
	protected LinkedList<T>[] storage;
	protected int size, capacity;
	protected final int MAXLOAD = 10;
	
	/**
	 * Constructor for ChainingHashTable
	 * 
	 * @param capacity
	 *            - the initial capacity of the hash table
	 * @param functor
	 *            - the functor to be used with the hash table
	 */
	public ChainingHashTable(int capacity) {
		this.capacity = capacity;
		// Initialize all LinkedLists to not be null
		storage = (LinkedList<T>[]) new LinkedList[capacity];
		for(int i = 0; i < capacity; i++) {
			storage[i] = new LinkedList<T>();
		}
		size = 0;
	}
	
	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item
	 *            - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually inserted); otherwise, returns
	 *         false
	 */
	@Override
	public boolean add(T item) {
		if(item == null) {
			return false;
		}
		
		int index = item.hashCode() % capacity;
		if(!contains(item)) {
			if((double) (size + 1) / capacity >= MAXLOAD) {
				reHash();
			}
			storage[index].add(item);
			size++;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items
	 *            - the collection of items whose presence is ensured in this
	 *            set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if any item in the input collection was actually inserted);
	 *         otherwise, returns false
	 */
	@Override
	public boolean addAll(Collection<? extends T> items) {
		boolean hasChanged = false;
		
		for(T element : items) {
			if(add(element)) {
				hasChanged = true;
			}
		}
		return hasChanged;
	}
	
	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	@Override
	public void clear() {
		size = 0;
		capacity = 10;
		storage = (LinkedList<T>[]) new LinkedList[0];
	}
	
	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item
	 *            - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 */
	@Override
	public boolean contains(T item) {
		if(item == null) {
			return false;
		}
		
		int index = item.hashCode() % capacity;
		
		for(T element : storage[index]) {
			if(item.equals(element)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines if for each item in the specified collection, there is an item
	 * in this set that is equal to it.
	 * 
	 * @param items
	 *            - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an
	 *         item in this set that is equal to it; otherwise, returns false
	 */
	@Override
	public boolean containsAll(Collection<? extends T> items) {
		for(T element : items) {
			if(!contains(element)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if this set contains no items.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the number of items in this set.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns the number of collisions in the set.
	 * 
	 * @return
	 */
	public int getCollisionCount() {
		int collisionCount = 0;
		for(int idx = 0; idx < storage.length; idx++) {
			if(storage[idx].size() > 1) {
				collisionCount += storage[idx].size() - 1;
			}
		}
		return collisionCount;
	}
	
	/**
	 * ReHashes the hash table to double the capacity.
	 */
	public void reHash() {
		size = 0;
		capacity *= 2;
		LinkedList<T>[] temp = storage;
		storage = (LinkedList<T>[]) new LinkedList[capacity];
		for(int i = 0; i < capacity; i++) {
			storage[i] = new LinkedList<T>();
		}
		for(LinkedList<T> element : temp) {
			addAll(element);
		}
	}
}
