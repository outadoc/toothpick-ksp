package toothpick;

/**
 * Provides instances of a given type.
 * This indirection layer to accessing instances
 * is the key of DI in toothpick. It is the indirection layer that answers, in Uncle's Bob meaning,
 * to accessing DI managed instances.
 * @param <T> the type of the instances provided by this provider.
 */
public interface Provider<T> {
  T get();
}