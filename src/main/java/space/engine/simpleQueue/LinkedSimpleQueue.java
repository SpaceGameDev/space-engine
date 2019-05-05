package space.engine.simpleQueue;

import org.jetbrains.annotations.Nullable;

public class LinkedSimpleQueue<E> implements SimpleQueue<E> {
	
	private @Nullable Node<E> head;
	private @Nullable Node<E> tail;
	
	@Override
	public void add(E e) {
		Node<E> node = new Node<>(e);
		if (tail != null)
			tail.next = node;
		tail = node;
		
		if (head == null)
			head = node;
	}
	
	@Nullable
	@Override
	public E remove() {
		Node<E> removed = head;
		if (removed == null)
			return null;
		head = removed.next;
		return removed.item;
	}
	
	public static class Node<E> {
		
		private final E item;
		private @Nullable Node<E> next;
		
		public Node(E item) {
			this.item = item;
		}
	}
}
