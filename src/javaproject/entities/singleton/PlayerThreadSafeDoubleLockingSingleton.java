package javaproject.entities.singleton;

// 4b. Thread safe double locking singleton approach
public class PlayerThreadSafeDoubleLockingSingleton {
    private static volatile PlayerThreadSafeDoubleLockingSingleton instance  = null;
    private PlayerThreadSafeDoubleLockingSingleton() {}

    public static PlayerThreadSafeDoubleLockingSingleton getInstance()
    {
        if (instance == null)
        {
            // To make thread safe
            synchronized (PlayerThreadSafeDoubleLockingSingleton.class)
            {
                // check again as multiple threads
                // can reach above step
                if (instance==null)
                    instance = new PlayerThreadSafeDoubleLockingSingleton();
            }
        }
        return instance;
    }
}
