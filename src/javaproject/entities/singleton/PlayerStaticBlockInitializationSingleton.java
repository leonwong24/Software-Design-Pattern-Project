package javaproject.entities.singleton;

// 2. Static Block example
public class PlayerStaticBlockInitializationSingleton {
    public static PlayerStaticBlockInitializationSingleton instance; //can be private or public

    // private constructor
    private PlayerStaticBlockInitializationSingleton(){

    }

    static{
        try{ //optional
            instance = new PlayerStaticBlockInitializationSingleton();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    public static PlayerStaticBlockInitializationSingleton getInstance() { //optional. implement when instance property is private
        return instance;
    }


}
