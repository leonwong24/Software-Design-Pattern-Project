package javaproject.entities.strategy;

import javaproject.entities.Creature;

public interface BulletHitBehavior {
    void onBulletHit(Creature creature);
}
