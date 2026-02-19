package ykkz000.mcmod.util.common;

import org.jspecify.annotations.NonNull;
import ykkz000.mcmod.util.common.function.*;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utilities for functional interfaces.
 *
 * @author ykkz000
 */
public final class ThrowingUtils {
    private ThrowingUtils() {
    }

    /**
     * Returns a {@link Function} that delegates to the given {@link ThrowingFunction}, wrapping any checked exception thrown by the delegate in an unchecked {@link RuntimeException}.
     *
     * @param function the throwing function to be wrapped
     * @param <T>      the type of the input to the function
     * @param <R>      the type of the result of the function
     * @return a {@link Function} that applies the given function, rethrowing any checked exception as unchecked
     */
    public static <T, R> Function<T, R> uncheckFunction(@NonNull ThrowingFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Returns a {@link BiFunction} that delegates to the given {@link ThrowingBiFunction}, wrapping any checked exception thrown by the delegate in an unchecked {@link RuntimeException}.
     *
     * @param function the throwing function to be wrapped
     * @param <T>      the type of the first argument
     * @param <U>      the type of the second argument
     * @param <R>      the type of the result
     * @return a {@link BiFunction} that applies the given function, rethrowing any checked exception as unchecked
     */
    public static <T, U, R> BiFunction<T, U, R> uncheckBiFunction(@NonNull ThrowingBiFunction<T, U, R> function) {
        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Returns a {@link Consumer} that delegates to the given {@link ThrowingConsumer}, wrapping any checked exception thrown by the delegate in an unchecked {@link RuntimeException}.
     *
     * @param consumer the throwing consumer to be wrapped
     * @param <T>      the type of the input to the operation
     * @return a {@link Consumer} that applies the given consumer, rethrowing any checked exception as unchecked
     */
    public static <T> Consumer<T> uncheckConsumer(@NonNull ThrowingConsumer<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Returns a {@link Supplier} that delegates to the given {@link ThrowingSupplier}, wrapping any checked exception thrown by the delegate in an unchecked {@link RuntimeException}.
     *
     * @param supplier the throwing supplier to be wrapped
     * @param <T>      the type of results supplied by this supplier
     * @return a {@link Supplier} that applies the given supplier, rethrowing any checked exception as unchecked
     */
    public static <T> Supplier<T> uncheckSupplier(@NonNull ThrowingSupplier<T> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Returns a {@link Runnable} that delegates to the given {@link ThrowingRunnable}, wrapping any checked exception thrown by the delegate in an unchecked {@link RuntimeException}.
     *
     * @param runnable the throwing runnable to be wrapped
     * @return a {@link Runnable} that applies the given runnable, rethrowing any checked exception as unchecked
     */
    public static Runnable uncheckRunnable(@NonNull ThrowingRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Executes the given supplier and returns its result, rethrowing any checked exception thrown by the supplier without declaring it in the method signature.
     *
     * @param supplier the supplier to be executed
     * @param <T>      the type of results supplied
     * @return the result produced by the supplier
     * @throws Exception any checked exception thrown by the supplier (not declared in the method signature)
     */
    public static <T> T sneakyThrow(@NonNull ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            return sneakyThrow0(t);
        }
    }

    /**
     * Executes the given runnable, rethrowing any checked exception thrown by the runnable without declaring it in the method signature.
     *
     * @param runnable the runnable to be executed
     * @throws Exception any checked exception thrown by the runnable (not declared in the method signature)
     */
    public static void sneakyThrow(@NonNull ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            sneakyThrow0(t);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable, R> R sneakyThrow0(Throwable t) throws E {
        throw (E) t;
    }

}
