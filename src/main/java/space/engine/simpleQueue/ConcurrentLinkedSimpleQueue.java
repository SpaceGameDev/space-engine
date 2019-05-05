package space.engine.simpleQueue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentLinkedSimpleQueue<E> implements SimpleQueue<E> {
	
	private AtomicReference<@NotNull Node<E>> headRef;
	private AtomicReference<@NotNull Node<E>> tailRef;
	
	public ConcurrentLinkedSimpleQueue() {
		//noinspection ConstantConditions
		Node<E> starterNode = new Node<>(null);
		headRef = new AtomicReference<>(starterNode);
		tailRef = new AtomicReference<>(starterNode);
	}
	
	@Override
	public void add(E e) {
		Node<E> node = new Node<>(e);
		Node<E> oldTail = tailRef.getAndSet(node);
		oldTail.next = node;
	}
	
	@Nullable
	@Override
	public E remove() {
		Node<E> head, next;
		do {
			head = headRef.get();
			next = head.next;
			if (next == null)
				return null;
		}
		while (!headRef.compareAndSet(head, next));
		return next.item;
	}
	
	public static class Node<E> {
		
		private final E item;
		private volatile @Nullable Node<E> next;
		
		public Node(E item) {
			this.item = item;
		}
	}
}
