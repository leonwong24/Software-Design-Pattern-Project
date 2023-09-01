package javaproject.entities.singleton;

// 4a. Thread safe singleton approach
public class PlayerThreadSafeSingleton {

    private static PlayerThreadSafeSingleton instance;

    private PlayerThreadSafeSingleton(){}

    public static synchronized PlayerThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new PlayerThreadSafeSingleton();
        }
        return instance;
    }
}
