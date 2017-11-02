package space.engine.number;

import space.engine.number.base.INumberBase;
import space.engine.number.exception.NumberException;
import space.util.conversion.IConverterSearchable;
import space.util.conversion.smart.ConverterSmart;
import space.util.conversion.smart.ConverterSmartCaching;
import space.util.conversion.smart.ConverterSmartSameCheck;
import space.util.conversion.smart.ConverterSmartSynchronized;
import space.util.conversion.smart.IConverterSmart;
import spaceOld.engine.logger.SubLogger;
import spaceOld.engine.startup.init.ClassSearcherNoAbstract;
import spaceOld.util.conversion.smart.*;

import static space.engine.environmentOld.TheMain.getMain;

public class NumberConverter {
	
	public static IConverterSmart<INumberBase> conv;
	
	//	@Require(state = {ManagerSide.loadupStateManagerGeneral})
	public static class NumberConverterLoader implements ClassSearcherNoAbstract {
		
		private static <LFROM extends INumberBase, LTO extends INumberBase> void add(IConverterSearchable<LFROM, LTO> c) throws Exception {
			conv.putConverter(c.classFrom(), c.classTo(), c, c.weight());
		}
		
		@Override
		public void run() {
			conv = new ConverterSmartSameCheck<INumberBase>(new ConverterSmartSynchronized<>(new ConverterSmartCaching<>(new ConverterSmart<>()))) {
				@Override
				public <LTO extends INumberBase> LTO convertInstance(INumberBase from, LTO ret) {
					if (from.getClass() == ret.getClass())
						return ret;
					return super.convertInstance(from, ret);
				}
			};
			conv.setDefaultConversionMethod(IConverterSmart.ConverterSmartDefaultConversionMethod.Smart);
			conv.setResolveSmartIgnoreDuplicatePriorities(true);
			conv.setResolveSmartLogger(new SubLogger(getMain().env.manager.baseLogger, "NumberConverter"));
			
			ClassSearcherNoAbstract.super.run();
		}
		
		@Override
		public void init(Class<?> clazz) {
			if (!IConverterSearchable.class.isAssignableFrom(clazz))
				return;
			if (!clazz.isAnnotationPresent(INumberConverter.class))
				return;
			
			try {
				//noinspection unchecked
				add((IConverterSearchable<? extends INumberBase, ? extends INumberBase>) clazz.newInstance());
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new NumberException("Error while loading class " + clazz, e);
			}
		}
	}
}
