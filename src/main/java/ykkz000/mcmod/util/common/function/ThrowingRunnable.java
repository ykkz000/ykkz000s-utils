package ykkz000.mcmod.util.common.function;

/**
 * Similar to {@link Runnable} but can throw exceptions.
 *
 * @author ykkz000
 */
@FunctionalInterface
public interface ThrowingRunnable {
    /**
     * Runs the operation.
     *
     * @throws Exception if an exception is thrown
     */
    void run() throws Exception;
}
