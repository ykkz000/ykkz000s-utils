package ykkz000.mcmod.util.common.function;

/**
 * Similar to {@link java.util.function.BiFunction} but can throw exceptions.
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 * @param <R> the type of the result
 * @author ykkz000
 */
public interface ThrowingBiFunction<T, U, R> {
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws Exception if an exception is thrown
     */
    R apply(T t, U u) throws Exception;
}
