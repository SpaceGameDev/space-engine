package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public class BaseObjectInit {
	
	//constant
	public static final BiFunction<ToStringHelper<?>, Collection<?>, Object> ENTRY_LIST = (api, list) -> api.toString(list.toArray());
	public static final BiFunction<ToStringHelper<?>, Map<?, ?>, Object> ENTRY_MAP = (api, map) -> {
		ToStringHelperTable<?> mapper = api.createMapper(map.getClass().getName(), "->", true);
		int index = 0;
		for (Entry<?, ?> entry : map.entrySet()) {
			mapper.put(new int[] {index, 0}, api.toString(entry.getKey()));
			mapper.put(new int[] {index, 1}, api.toString(entry.getValue()));
			index++;
		}
		return mapper;
	};
	
	//init
	protected static boolean called = false;
	
	public static byte init2() {
		init();
		return 0;
	}
	
	@SuppressWarnings({"unchecked", "RedundantCast"})
	public static void init() {
		synchronized (BaseObjectInit.class) {
			if (called)
				return;
			called = true;
		}
		
		Setable.init();
		Creatable.init();
		Copyable.init();
		ToString.init();
		
		//List
		ToString.manualEntry(ArrayList.class, (BiFunction<ToStringHelper<?>, ? super ArrayList, Object>) ENTRY_LIST);
		ToString.manualEntry(LinkedList.class, (BiFunction<ToStringHelper<?>, ? super LinkedList, Object>) ENTRY_LIST);
		
		//map
		ToString.manualEntry(HashMap.class, (BiFunction<ToStringHelper<?>, ? super HashMap, Object>) ENTRY_MAP);
		ToString.manualEntry(new HashMap<>().keySet().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		ToString.manualEntry(new HashMap<>().values().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		
		ToString.manualEntry(EnumMap.class, (BiFunction<ToStringHelper<?>, ? super EnumMap, Object>) ENTRY_MAP);
		ToString.manualEntry(new EnumMap<>(SomeEnum.class).keySet().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		ToString.manualEntry(new EnumMap<>(SomeEnum.class).values().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
	}
	
	private enum SomeEnum {
	
	}
}
