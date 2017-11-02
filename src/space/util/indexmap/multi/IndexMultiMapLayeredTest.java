package space.util.indexmap.multi;

public class IndexMultiMapLayeredTest {
	
	public static void main(String[] args) {
		IndexMultiMapLayeredImpl<Integer> table = new IndexMultiMapLayeredImpl<>();
		
		//put
		table.put(new int[] {0, 0}, 50);
		table.put(new int[] {0, 1}, 60);
		table.put(new int[] {0, 2}, 70);
		table.put(new int[] {1, 0}, 80);
		
		//get
		System.out.println(table.get(new int[] {0, 0}));
		System.out.println(table.get(new int[] {0, 1}));
		System.out.println(table.get(new int[] {0, 2}));
		System.out.println(table.get(new int[] {1, 0}));
		
		System.out.println();
		System.out.println("Get too short");
		System.out.println(table.get(new int[] {0}));
		System.out.println();
		System.out.println("Get too long");
		System.out.println(table.get(new int[] {0, 0, 50}));
		
		System.out.println();
		System.out.println("put override");
		System.out.println(table.get(new int[] {0, 2}));
		table.put(new int[] {0, 2}, 100);
		System.out.println(table.get(new int[] {0, 2}));
		
		System.out.println();
		System.out.println("put too short");
		table.put(new int[] {0}, 777);
		System.out.println(table.get(new int[] {0, 0}));
		
		System.out.println();
		System.out.println("put too long");
		table.put(new int[] {0, 0, 1}, 999);
		System.out.println(table.get(new int[] {0, 0}));
		System.out.println(table.get(new int[] {0, 1}));
		System.out.println(table.get(new int[] {0, 2}));
		System.out.println(table.get(new int[] {1, 0}));
		System.out.println();
		System.out.println(table.get(new int[] {0, 0, 0}));
		System.out.println(table.get(new int[] {0, 0, 1}));
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println(table);
		
		System.out.println();
	}
}
