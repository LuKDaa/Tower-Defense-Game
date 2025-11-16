import java.util.*;
import greenfoot.*;

public class Boss extends Enemy {
    public Boss(java.util.List<PathPoint> path, int hp, int reward) {
        super(path, hp, reward);
        // --- CAMBIO ---
        setSpeed(0.7); // Usa el nuevo método para establecer la velocidad base
        setImage("boss.png");
    }

    @Override
    protected void reachedGoal() {
        getWorldOfType(GameWorld.class).damagePlayer(5);
        getWorld().removeObject(this);
    }

    @Override
    public void takeDamage(int d) {
        super.takeDamage(d);
        // visual cue
        if (getImage() != null) getImage().scale(getImage().getWidth(), getImage().getHeight());
    }
}