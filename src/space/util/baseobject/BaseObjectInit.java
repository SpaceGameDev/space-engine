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
	public static byte init2() {
		init();
		return 0;
	}
	
	private static boolean called = false;
	
	@SuppressWarnings({"RedundantCast", "unchecked", "MismatchedQueryAndUpdateOfCollection"})
	public static void init() {
		synchronized (BaseObjectInit.class) {
			if (called)
				return;
			called = true;
		}
		
		ToString.init();
		
		//List
		ToString.manualEntry(ArrayList.class, (BiFunction<ToStringHelper<?>, ? super ArrayList, Object>) ENTRY_LIST);
		ToString.manualEntry(LinkedList.class, (BiFunction<ToStringHelper<?>, ? super LinkedList, Object>) ENTRY_LIST);
		
		//map
		HashMap<Object, Object> hashMap = new HashMap<>();
		ToString.manualEntry(HashMap.class, (BiFunction<ToStringHelper<?>, ? super HashMap, Object>) ENTRY_MAP);
		ToString.manualEntry(hashMap.keySet().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		ToString.manualEntry(hashMap.values().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		
		EnumMap<SomeEnum, Object> enumMap = new EnumMap<>(SomeEnum.class);
		ToString.manualEntry(EnumMap.class, (BiFunction<ToStringHelper<?>, ? super EnumMap, Object>) ENTRY_MAP);
		ToString.manualEntry(enumMap.keySet().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
		ToString.manualEntry(enumMap.values().getClass(), (BiFunction<ToStringHelper<?>, ? super Collection, Object>) ENTRY_LIST);
	}
	
	private enum SomeEnum {
	
	}
}
