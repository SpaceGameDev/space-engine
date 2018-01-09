package space.engineOld.manager.classcollection;

import space.engineOld.event.searcher.Requires;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public class ClassCollection {
	
	public static final ClassModifier ModifierPublic = new ClassModifier(Modifier.PUBLIC, Modifier.PRIVATE | Modifier.PROTECTED);
	public static final ClassModifier ModifierProtected = new ClassModifier(Modifier.PROTECTED, Modifier.PUBLIC | Modifier.PRIVATE);
	public static final ClassModifier ModifierPackagePrivate = new ClassModifier(0, Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED);
	public static final ClassModifier ModifierPrivate = new ClassModifier(Modifier.PRIVATE, Modifier.PUBLIC | Modifier.PROTECTED);
	public static final ClassModifier ModifierAbstract = new ClassModifier(Modifier.ABSTRACT, 0);
	public static final ClassModifier ModifierNotAbstract = new ClassModifier(0, Modifier.ABSTRACT);
	public static final ClassModifier ModifierFinal = new ClassModifier(Modifier.FINAL, 0);
	public static final ClassModifier ModifierNotFinal = new ClassModifier(0, Modifier.FINAL);
	public static final ClassModifier ModifierStrict = new ClassModifier(Modifier.STRICT, 0);
	public static final ClassModifier ModifierNotStrict = new ClassModifier(0, Modifier.STRICT);
	
	public static final IClassCollectionCondition<?> standardAll = new ClassCollectionCondition<>().setModifier(ModifierPublic);
	public static final IClassCollectionCondition<?> standardNoAbstract = new ClassCollectionCondition<>().setModifier(ModifierPublic, ModifierNotAbstract);
	public static final IClassCollectionCondition<Runnable> standardRunnable = new ClassCollectionCondition<>().setAssignable(Runnable.class).setModifier(ModifierPublic, ModifierNotAbstract);
	public static final IClassCollectionCondition<Runnable> standardRunnableRequires = new ClassCollectionCondition<>(standardRunnable).setAnnotations(Requires.class);
	
	private static final String fileEndingClass = ".class";
	private static final int fileEndingClassLength = fileEndingClass.length();
	
	public List<Class<?>> all;
	public Map<IClassCollectionCondition, List<Class<?>>> map = new HashMap<>();
	public boolean preBuildStandard = true;
	
	public ClassCollection() {
		clearAll();
	}
	
	//find all classes
	public static List<Class<?>> getAll() throws IOException {
		return getAll(new ArrayList<>(), Thread.currentThread().getContextClassLoader());
	}
	
	public static List<Class<?>> getAll(List<Class<?>> list) throws IOException {
		return getAll(list, Thread.currentThread().getContextClassLoader());
	}
	
	public static List<Class<?>> getAll(List<Class<?>> list, ClassLoader classLoader) throws IOException {
		for (Enumeration<URL> enumeration = classLoader.getResources(""); enumeration.hasMoreElements(); ) {
			URL url = enumeration.nextElement();
			File file = new File(url.getFile());
			
			if (file.isDirectory())
				addDirectory(list, file, classLoader, true);
			if (file.getName().endsWith(".jar"))
				addJar(list, new JarFile(file), classLoader);
		}
		return list;
	}
	
	//find directory
	@SuppressWarnings("SameParameterValue")
	public static void addDirectory(List<Class<?>> list, File file, ClassLoader classLoader, boolean reclusive) {
		addDirectory(list, null, file, classLoader, reclusive);
	}
	
	@SuppressWarnings({"ConstantConditions", "SameParameterValue"})
	private static void addDirectory(List<Class<?>> list, String base, File file, ClassLoader classLoader, boolean reclusive) {
		File[] files = file.listFiles();
		if (files == null)
			return;
		
		for (File f : files) {
			String name = (base == null) ? f.getName() : base + '.' + f.getName();
			if (reclusive && f.isDirectory())
				addDirectory(list, name, f, classLoader, reclusive);
			
			if (f.isFile() && name.endsWith(fileEndingClass)) {
				addFile(list, name.substring(0, name.length() - fileEndingClassLength), classLoader);
			}
		}
	}
	
	//find jar
	public static void addJar(List<Class<?>> list, JarFile file, ClassLoader classLoader) {
		file.stream().forEach(jarEntry -> {
			String f = jarEntry.getName();
			if (f.endsWith(fileEndingClass))
				addFile(list, f, classLoader);
		});
	}
	
	public static void addFile(List<Class<?>> list, String file, ClassLoader classLoader) {
		try {
			Class<?> clazz = classLoader.loadClass(file);
			if (!clazz.isAnnotationPresent(Ignore.class))
				list.add(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	//get
	@SuppressWarnings("unchecked")
	public synchronized <CLS> List<Class<? extends CLS>> get(IClassCollectionCondition<CLS> condition) {
		List<Class<? extends CLS>> l = (List<Class<? extends CLS>>) ((Object) map.get(condition));
		if (l != null)
			return l;
		return make(condition);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <CLS> List<Class<? extends CLS>> make(IClassCollectionCondition<CLS> condition) {
		List<Class<? extends CLS>> l = new ArrayList<>();
		for (Class<?> c : getBestSource(condition))
			if (condition.test(c))
				l.add((Class<? extends CLS>) c);
		
		map.put(condition, (List<Class<?>>) ((Object) l));
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <CLS> List<Class<? extends CLS>> getBestSource(IClassCollectionCondition<CLS> condition) {
		return (List<Class<? extends CLS>>) ((Object) getBestSource0(condition));
	}
	
	private List<Class<?>> getBestSource0(IClassCollectionCondition<?> condition) {
		ClassCollectionCondition<?> ccc = new ClassCollectionCondition<>(condition);
		List<Class<?>> l;
		
		//remove Annotation
		l = map.get(ccc.setAnnotations((Class<? extends Annotation>[]) null));
		if (l != null)
			return l;
		
		//remove assignable
		l = map.get(ccc.setAssignable((Class[]) null));
		if (l != null)
			return l;
		
		//remove modifier -> all
		return all;
	}
	
	public void clearAll() {
		List<Class<?>> l;
		try {
			l = getAll();
		} catch (IOException e) {
			throw new RuntimeException("IOException while searching classes!", e);
		}
		if (l == null)
			throw new RuntimeException();
		
		synchronized (this) {
			all = l;
			map.clear();
			
			if (preBuildStandard) {
				make(standardAll);
				make(standardNoAbstract);
				make(standardRunnable);
				make(standardRunnableRequires);
			}
		}
	}
	
	public synchronized void clearCache() {
		map.clear();
	}
}
