import greenfoot.*;

public class Spawner extends Actor {
    private WaveManager manager;

    public Spawner(WaveManager m) {
        manager = m;
        setImage(new GreenfootImage(1, 1)); // invisible
    }

    public void act() {
        manager.spawnTick();

        // Eliminar solo cuando la wave terminó (no cuando no hay enemigos)
        if (!manager.isWaveActive()) {
            getWorld().removeObject(this);
        }
    }
}
