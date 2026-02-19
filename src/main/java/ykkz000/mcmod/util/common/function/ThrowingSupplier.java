package ykkz000.mcmod.util.common.function;

/**
 * Similar to {@link java.util.function.Supplier} but can throw exceptions.
 *
 * @param <T> the type of results supplied by this supplier
 * @author ykkz000
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    /**
     * Gets a result.
     * @return a result
     * @throws Exception if an exception is thrown
     */
    T get() throws Exception;
}
