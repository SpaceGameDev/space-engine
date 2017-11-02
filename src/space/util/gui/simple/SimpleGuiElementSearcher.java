package space.util.gui.simple;

import space.engine.manager.classcollection.ClassCollection;
import space.engine.manager.classcollection.ClassCollectionCondition;
import space.util.gui.GuiElement;

import static space.engine.environmentOld.TheMain.getMain;

public class SimpleGuiElementSearcher<BASEELEMENT extends GuiElement<BASEELEMENT>> implements Runnable {
	
	public SimpleGuiApi<BASEELEMENT> api;
	public Class<? extends SimpleGuiApi> apiClass;
	public Class<BASEELEMENT> elemClass;
	public boolean requireAnnotation;
	
	public SimpleGuiElementSearcher(SimpleGuiApi<BASEELEMENT> api) {
		this(api, api.getBaseElementClass(), true);
	}
	
	public SimpleGuiElementSearcher(SimpleGuiApi<BASEELEMENT> api, boolean requireAnnotation) {
		this(api, api.getBaseElementClass(), requireAnnotation);
	}
	
	public SimpleGuiElementSearcher(SimpleGuiApi<BASEELEMENT> api, Class<BASEELEMENT> elemClass, boolean requireAnnotation) {
		this.api = api;
		apiClass = api.getClass();
		this.elemClass = elemClass;
		this.requireAnnotation = requireAnnotation;
	}
	
	@Override
	public void run() {
		ClassCollectionCondition<BASEELEMENT> ccc = ClassCollection.standardNoAbstract.copy().setAssignable(elemClass);
		if (requireAnnotation)
			ccc.setAnnotations(SimpleGuiElement.class);
		for (Class<? extends BASEELEMENT> clazz : getMain().env.manager.classCollection.get(ccc)) {
			if (requireAnnotation) {
				SimpleGuiElement a = clazz.getAnnotation(SimpleGuiElement.class);
				if (a == null)
					continue;
				if (a.api() != apiClass)
					continue;
			}
			
			addElement(clazz);
		}
	}
	
	private <ELEMENT extends BASEELEMENT> void addElement(Class<ELEMENT> clazz) {
		api.addElement(clazz, () -> {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
