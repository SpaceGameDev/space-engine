package space.util.conversion.smartOld;

import space.util.conversion.Converter;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import spaceOld.engine.logger.BaseLoggerNormal;
import spaceOld.engine.logger.SubLogger;

public class ConverterSmartTest {
	
	public static void main(String[] args) {
		Logger logger = new BaseLoggerNormal(System.out);
		ConverterSmart<Object> conv = new ConverterSmart<>();
		conv.setResolveSmartLogger(new SubLogger(logger, "Converter", LogLevel.FINEST));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test1", "-------------------------------"});
		conv.putConverter(ObjectFloat.class, ObjectInt.class, new ConverterObjectFloatToObjectInt(), 1000);
		
		//valid
		logger.log(LogLevel.INFO, conv.getConverter(ObjectFloat.class, ObjectInt.class));
		//same (invalid)
		logger.log(LogLevel.INFO, conv.getConverter(ObjectInt.class, ObjectInt.class));
		//invalid
		logger.log(LogLevel.INFO, conv.getConverter(ObjectInt.class, ObjectFloat.class));
		
		//smart
		logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectInt.class));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test2", "-------------------------------"});
		conv.putConverter(ObjectInt.class, ObjectShort.class, new ConverterObjectIntToObjectShort(), 1000);
		
		//invalid, needs 1 hop in between
		logger.log(LogLevel.INFO, conv.getConverter(ObjectFloat.class, ObjectShort.class));
		//valid, should find the hop
		logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectShort.class));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test3", "-------------------------------"});
		conv.putConverter(ObjectFloat.class, ObjectInt0.class, new ConverterObjectFloatToObjectInt0(), 200);
		conv.putConverter(ObjectInt0.class, ObjectInt.class, new ConverterObjectInt0ToObjectInt(), 200);
		
		//valid, should find 1 hop with less weight (1000 -> 400)
		logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectInt.class));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test4", "-------------------------------"});
		
		//valid, should find 2 hop with less weight (2000 -> 1400), but also use a node twice (ObjectInt)
		logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectShort.class));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test5", "-------------------------------"});
		conv.putConverter(ObjectInt0.class, ObjectInt.class, new ConverterObjectInt0ToObjectInt(), 1000);
		
		//should now use the direct hops
		logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectShort.class));
		
		
		
		logger.log(LogLevel.INFO, new String[] {"-------------------------------", "Test6", "-------------------------------"});
		conv.putConverter(ObjectInt0.class, ObjectInt.class, new ConverterObjectInt0ToObjectInt(), 800);
		
		//invalid, two ways with the same weight (1000)
		try {
			logger.log(LogLevel.INFO, conv.getSmart(ObjectFloat.class, ObjectShort.class));
		} catch (IllegalStateException e) {
			logger.log(LogLevel.INFO, "IllegalStateException thrown, same weight on multiple ways detection working!");
		}
	}
	
	//Test1
	public static class ObjectFloat {
		
		public float x;
	}
	
	public static class ObjectInt {
		
		public int x;
	}
	
	public static class ConverterObjectFloatToObjectInt implements Converter.IConverterSimple<ObjectFloat, ObjectInt> {
		
		@Override
		public ObjectInt convertNew(ObjectFloat obj) throws UnsupportedOperationException {
			return convertInstance(obj, new ObjectInt());
		}
		
		@Override
		public <LTO extends ObjectInt> LTO convertInstance(ObjectFloat obj, LTO ret) {
			ret.x = (int) obj.x;
			return ret;
		}
	}
	
	//Test2
	public static class ObjectShort {
		
		public short x;
	}
	
	public static class ConverterObjectIntToObjectShort implements Converter.IConverterSimple<ObjectInt, ObjectShort> {
		
		@Override
		public ObjectShort convertNew(ObjectInt obj) throws UnsupportedOperationException {
			return convertInstance(obj, new ObjectShort());
		}
		
		@Override
		public <LTO extends ObjectShort> LTO convertInstance(ObjectInt obj, LTO ret) {
			ret.x = (short) obj.x;
			return ret;
		}
	}
	
	//Test3
	public static class ObjectInt0 {
		
		public int x;
	}
	
	public static class ConverterObjectFloatToObjectInt0 implements Converter.IConverterSimple<ObjectFloat, ObjectInt0> {
		
		@Override
		public ObjectInt0 convertNew(ObjectFloat obj) throws UnsupportedOperationException {
			return convertInstance(obj, new ObjectInt0());
		}
		
		@Override
		public <LTO extends ObjectInt0> LTO convertInstance(ObjectFloat obj, LTO ret) {
			ret.x = (int) obj.x;
			return ret;
		}
	}
	
	public static class ConverterObjectInt0ToObjectInt implements Converter.IConverterSimple<ObjectInt0, ObjectInt> {
		
		@Override
		public ObjectInt convertNew(ObjectInt0 obj) throws UnsupportedOperationException {
			return convertInstance(obj, new ObjectInt());
		}
		
		@Override
		public <LTO extends ObjectInt> LTO convertInstance(ObjectInt0 obj, LTO ret) {
			ret.x = obj.x;
			return ret;
		}
	}
}
