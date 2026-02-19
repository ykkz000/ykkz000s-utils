package ykkz000.mcmod.util.common.function;

/**
 * Similar to {@link java.util.function.Consumer} but can throw exceptions.
 *
 * @param <T> - the type of the input to the operation
 * @author ykkz000
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t - the input argument
     * @throws Exception if an exception is thrown
     */
    void accept(T t) throws Exception;
}
