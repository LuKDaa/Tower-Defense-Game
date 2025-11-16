import java.util.*;

public class FastEnemy extends Enemy {
    public FastEnemy(java.util.List<PathPoint> path, int hp, int reward) {
        super(path, hp, reward);
        // --- CAMBIO ---
        setSpeed(2.5); // Usa el nuevo método para establecer la velocidad base
        setImage("fast_enemy.png");
    }
}