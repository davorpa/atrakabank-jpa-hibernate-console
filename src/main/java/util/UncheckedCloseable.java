package util;

/**
 * A {@link FunctionalInterface} extension over {@link Runnable} that can deal
 * with multiple {@link AutoCloseable} instances defering their execution.
 *
 * The principal use could be incorporate it as an exception handler and closing
 * of resources. For example, you can use <tt>Stream.onClose</tt> to
 * register an action that will be performed when the Stream gets closed, but it
 * has to be a {@link Runnable} which can not throw checked exceptions.
 * Similarly the <tt>tryAdvance</tt> method is not allowed to throw checked exceptions.
 * And since we can’t simply nest <tt>try(…)</tt> blocks here, the program logic
 * of suppression exceptions thrown in <tt>close</tt>, when there is already a
 * pending exception, doesn’t come for free.
 *
 * To help us here, we introduce this new type which can wrap closing operations
 * which may throw checked exceptions and deliver them wrapped in an unchecked
 * exception.
 *
 * By implementing {@link AutoCloseable} itself, it can utilize the <tt>try(…)</tt>
 * construct to chain close operations safely.
 */
@FunctionalInterface
interface UncheckedCloseable
	extends Runnable, AutoCloseable
{
	/**
	 * Implement {@link Runnable} to raise exceptions produced by
	 * {@link AutoCloseable#close()} as generic unchecked exceptions.
	 *
	 * {@inheritDoc}
	 */
	@Override
	default void run()
	{
		try
		{
			close();
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}


	/**
	 * Helper to wrap the first interact with this functional interface.
	 *
	 * @param c {@link AutoCloseable} to wrap, never <tt>null</tt>
	 * @return the {@link #close()} of wrapped object casted this functional interface.
	 */
	static UncheckedCloseable wrap(
			final AutoCloseable c)
	{
		return c::close;
	}

	/**
	 * <tt>Nest</tt> is in charge of wrap any exception produced by any
	 * {@link AutoCloseable} object into the chain initialized by
	 * {@link #wrap(AutoCloseable)} helper.
	 *
	 * @param c {@link AutoCloseable} to wrap, never <tt>null</tt>
	 * @return the {@link #close()} of wrapped object casted this functional interface.
	 */
	default UncheckedCloseable nest(
			final AutoCloseable c)
	{
		return () -> {
			try (UncheckedCloseable c1 = this)
			{
				c.close();
			}
		};
	}

}
