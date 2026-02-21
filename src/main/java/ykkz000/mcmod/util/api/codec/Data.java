package ykkz000.mcmod.util.api.codec;

/**
 * Defines a data that can be converted to immutable.
 *
 * @param <M> the type of mutable (the type itself, must be {@link Data})
 * @param <I> the type of immutable (must be both {@link Immutable} and {@link Record})
 * @author ykkz000
 */
public interface Data<M extends Data<M, I>, I extends Record & Data.Immutable<I, M>> {
    /**
     * Converts to immutable.
     *
     * @return the immutable
     */
    I toImmutable();

    /**
     * Defines an immutable data that can be converted to mutable.
     *
     * @param <I> the type of immutable (the immutable itself, must be both {@link Immutable} and {@link Record})
     * @param <M> the type of mutable (must be {@link Data})
     */
    interface Immutable<I extends Record & Immutable<I, M>, M extends Data<M, I>> {
        /**
         * Converts to mutable.
         *
         * @return the mutable
         */
        M toMutable();
    }
}
