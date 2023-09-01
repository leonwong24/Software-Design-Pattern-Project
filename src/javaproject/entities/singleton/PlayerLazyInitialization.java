package javaproject.entities.singleton;

// 3. Lazy Initialization approach example
public class PlayerLazyInitialization {

    private static PlayerLazyInitialization instance;

    private PlayerLazyInitialization(){}

    public static PlayerLazyInitialization getInstance() {
        if (instance == null) {
            instance = new PlayerLazyInitialization();
        }
        return instance;
    }
}