package javaproject.entities.strategy;

import javaproject.entities.Creature;

import java.util.Timer;
import java.util.TimerTask;

public class SlowMovementHitBehavior implements BulletHitBehavior{

    private int affectedDuration;
    private float slowMovementSpeed;
    public SlowMovementHitBehavior(int affectedDuration,float slowMovementSpeed){
        this.affectedDuration = affectedDuration;
        this.slowMovementSpeed = slowMovementSpeed;
    }

    @Override
    public void onBulletHit(Creature creature) {
        float originalMovementSpeed = creature.getMovementSpeed();

        creature.setMovementSpeed(originalMovementSpeed*slowMovementSpeed);

        //set back the movement speed after affectedDuration sec delay
        Timer t = new Timer();
        TimerTask restoreSpeed = new TimerTask(){
            int count = 0;
            @Override
            public void run() {
                creature.setMovementSpeed(originalMovementSpeed);
                count++;
                if(count > 1)
                    t.cancel();
                t.purge();
            }
        };
        t.schedule(restoreSpeed,affectedDuration*1000);
    }
}
