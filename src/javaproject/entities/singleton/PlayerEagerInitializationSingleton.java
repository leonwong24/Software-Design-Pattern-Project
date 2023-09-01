package javaproject.entities.singleton;

// 1. Eager Initialization example
public class PlayerEagerInitializationSingleton {
    private static final PlayerEagerInitializationSingleton instance = new PlayerEagerInitializationSingleton();

    // private constructor to avoid client applications using the constructor
    private PlayerEagerInitializationSingleton(){}

    public static PlayerEagerInitializationSingleton getInstance() {
        return instance;
    }
}
