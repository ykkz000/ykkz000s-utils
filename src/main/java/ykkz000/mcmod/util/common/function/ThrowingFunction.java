package ykkz000.mcmod.util.common.function;

/**
 * Similar to {@link java.util.function.Function} but can throw exceptions.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @author ykkz000
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an exception is thrown
     */
    R apply(T t) throws Exception;
}
