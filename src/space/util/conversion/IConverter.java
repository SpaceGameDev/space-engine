package space.util.conversion;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;
import space.util.string.toStringHelperOld.objects.TSHObjects.TSHObjectsInstance;

public interface IConverter<FROM, TO> {
	
	/**
	 * converts FROM to TO, making a new Instance of FROM
	 *
	 * @param from the object to be converted FROM
	 * @return the new Object TO
	 * @throws UnsupportedOperationException                              if type is required
	 * @throws space.util.conversion.exception.InvalidConversionException when the conversion is not allowed
	 */
	TO convertNew(FROM from) throws UnsupportedOperationException;
	
	/**
	 * converts FROM to LTO, using the supplied instance and returning it.
	 *
	 * @param from the object to be converted FROM
	 * @param ret  the supplied instance
	 * @return the supplied instance TO
	 * @throws space.util.conversion.exception.InvalidConversionException when the conversion is not allowed
	 */
	<LTO extends TO> LTO convertInstance(FROM from, LTO ret);
	
	/**
	 * converts FROM to LTO, using creating an instance of supplied class and returning it.
	 * It is not required that the returned instance is an instance of the supplied class.
	 * By default it will call convertNew() and ignore the class argument.
	 *
	 * @throws space.util.conversion.exception.InvalidConversionException when the conversion is not allowed
	 */
	default TO convertType(FROM from, Class<? extends TO> type) {
		return convertNew(from);
	}
	
	default <LTO> IConverter<FROM, LTO> andThen(IConverter<TO, LTO> next) {
		return new IConverterAndThen<>(this, next);
	}
	
	default <LFROM> IConverter<LFROM, TO> before(IConverter<LFROM, FROM> before) {
		return new IConverterBefore<>(before, this);
	}
	
	class IConverterAndThen<FROM, MIDDLE, TO> implements IConverter<FROM, TO>, BaseObject {
		
		static {
			//noinspection unchecked
			BaseObject.initClass(IConverterAndThen.class, d -> new IConverterAndThen(Copyable.copy(d.th), Copyable.copy(d.next)));
		}
		
		public IConverter<FROM, MIDDLE> th;
		public IConverter<MIDDLE, TO> next;
		
		public IConverterAndThen(IConverter<FROM, MIDDLE> th, IConverter<MIDDLE, TO> next) {
			this.th = th;
			this.next = next;
		}
		
		@Override
		public TO convertNew(FROM from) throws UnsupportedOperationException {
			return next.convertNew(th.convertNew(from));
		}
		
		@Override
		public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
			return next.convertInstance(th.convertNew(from), ret);
		}
		
		@Override
		public TO convertType(FROM from, Class<? extends TO> type) {
			return convertNew(from);
		}
		
		@Override
		public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
			TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
			tsh.add("th", this.th);
			tsh.add("next", this.next);
			return tsh;
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	class IConverterBefore<FROM, MIDDLE, TO> implements IConverter<FROM, TO>, BaseObject {
		
		static {
			//noinspection unchecked
			BaseObject.initClass(IConverterBefore.class, d -> new IConverterBefore(Copyable.copy(d.before), Copyable.copy(d.th)));
		}
		
		public IConverter<FROM, MIDDLE> before;
		public IConverter<MIDDLE, TO> th;
		
		public IConverterBefore(IConverter<FROM, MIDDLE> before, IConverter<MIDDLE, TO> th) {
			this.before = before;
			this.th = th;
		}
		
		@Override
		public TO convertNew(FROM from) throws UnsupportedOperationException {
			return th.convertNew(before.convertNew(from));
		}
		
		@Override
		public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
			return th.convertInstance(before.convertNew(from), ret);
		}
		
		@Override
		public TO convertType(FROM from, Class<? extends TO> type) {
			return convertNew(from);
		}
		
		@Override
		public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
			TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
			tsh.add("before", this.before);
			tsh.add("th", this.th);
			return tsh;
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
