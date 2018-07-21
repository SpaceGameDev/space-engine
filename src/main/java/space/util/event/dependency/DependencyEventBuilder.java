package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.Cache;
import space.util.baseobject.ToString;
import space.util.delegate.list.ModificationAwareList;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DependencyEventBuilder<FUNCTION> implements DependencyEventCreator<FUNCTION>, Cache {
	
	public static boolean TOSTRING_HIDE_CACHE_VALUES = true;
	
	public List<DependencyEventEntry<FUNCTION>> list = new ModificationAwareList<>(new ArrayList<>(), this::clearCache);
	
	//hooks
	@Override
	public synchronized void addHook(@NotNull DependencyEventEntry<FUNCTION> task) {
		list.add(task);
	}
	
	@Override
	public synchronized boolean removeHook(@NotNull DependencyEventEntry<FUNCTION> task) {
		return list.remove(task);
	}
	
	//clear
	@Override
	public abstract void clearCache();
	
	//Build
	protected static class Build<FUNCTION> implements ToString {
		
		public List<Node<FUNCTION>> allNodes = new ArrayList<>();
		public List<Node<FUNCTION>> firstNodes = new ArrayList<>();
		
		/**
		 * @param list                      List of {@link DependencyEventEntry}
		 * @param optimizeExecutionPriority whether to resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
		 */
		public Build(List<DependencyEventEntry<FUNCTION>> list, boolean optimizeExecutionPriority) {
			//adding
			list.forEach(entry -> allNodes.add(new Node<>(entry)));
			
			//adding Dependencies
			for (int i = 0; i < allNodes.size(); i++) {
				Node node = allNodes.get(i);
				for (int k = i + 1; k < allNodes.size(); k++) {
					Node<FUNCTION> test = allNodes.get(k);
					int comp = DependencyEventEntry.COMPARATOR.compare(node.entry, test.entry);
					if (comp < 0)
						node.addDependencyAfter(test);
					else if (comp > 0)
						test.addDependencyAfter(node);
				}
			}
			
			for (Node<FUNCTION> node : allNodes) {
				//firstNodes
				if (node.depCnt == 0) {
					firstNodes.add(node);
					node.depCnt = 1;
				}
				
				//optimizeExecutionPriority
				if (optimizeExecutionPriority)
					node.next.sort((o1, o2) -> o2.next.size() - o1.next.size());
			}
		}
		
		@NotNull
		@Override
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("allNodes", this.allNodes);
			tsh.add("firstNodes", this.firstNodes);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	/**
	 * one {@link Node} exists for each FUNCTION
	 */
	protected static class Node<FUNCTION> implements ToString {
		
		public final DependencyEventEntry<FUNCTION> entry;
		public int depCnt;
		public final List<Node> next = new ArrayList<>();
		
		public Node(DependencyEventEntry<FUNCTION> entry) {
			this.entry = entry;
		}
		
		protected void addDependencyAfter(Node node) {
			next.add(node);
			node.depCnt++;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Node))
				return false;
			Node node = (Node) o;
			return Objects.equals(entry, node.entry);
		}
		
		@Override
		public int hashCode() {
			return entry.hashCode();
		}
		
		@Override
		@NotNull
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("entry", this.entry);
			tsh.add("depCnt", this.depCnt);
			tsh.add("next", this.next);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
