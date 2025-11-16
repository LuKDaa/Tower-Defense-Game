import greenfoot.*;
import java.util.*;

/**
 * Controla la generación de oleadas de enemigos.
 */
public class WaveManager {
    private GameWorld world;
    private List<PathPoint> pathAlto;
    private List<PathPoint> pathBajo;
    private boolean waveActive = false;
    private int spawnCountRemaining = 0;
    private int spawnInterval = 60; // frames entre spawns
    private int spawnCooldown = 1;

    public WaveManager(GameWorld w, List<PathPoint> pathAlto, List<PathPoint> pathBajo) {
        this.world = w;
        this.pathAlto = pathAlto; // Guardar path 1
        this.pathBajo = pathBajo; // Guardar path 2
    }

    /** Inicia una nueva oleada */
    public void startWave(int waveNumber) {
        waveActive = true;

        // Cada 10 oleadas aparece un jefe
        if (waveNumber % 10 == 0) {
            
            List<PathPoint> chosenPath = getChosenPath(); 
            
            int bossHP = (int)(300 * Math.pow(1.20, waveNumber / 10)); 
            int bossReward = 30 + waveNumber;
            
            Boss b = new Boss(chosenPath, bossHP, bossReward);
            world.addObject(b, chosenPath.get(0).x, chosenPath.get(0).y);
            
            waveActive = true;
            return;
        }

        // Cantidad de enemigos base
        spawnCountRemaining = 5 + waveNumber * 2;
        spawnCooldown = 0;
    }

    /** Llamado por Spawner en cada frame */
    public void spawnTick() {
        if (!waveActive) return;

        if (spawnCountRemaining <= 0) {
            waveActive = false;
            return;
        }

        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }

        // Determinar tipo de enemigo según la oleada
        String type = "WEAK";
        if (world.currentWave % 5 == 0 && Math.random() < 0.4) type = "FAST";
        if (world.currentWave % 3 == 0 && Math.random() < 0.25) type = "TANK";

        spawnEnemy(type);
        spawnCountRemaining--;
        spawnCooldown = spawnInterval;
    }

    /**
     * Elige aleatoriamente uno de los dos caminos disponibles.
     */
    private List<PathPoint> getChosenPath() {
        if (Math.random() < 0.5) { // 50% de probabilidad
            return pathAlto;
        } else {
            return pathBajo;
        }
    }

    private void spawnEnemy(String type) {
    int rewardBase = 3 + (int)(world.currentWave / 3);
    double scaleFactor = Math.pow(1.10, world.currentWave - 1); //EN ESTA LINEA ESTA EL CONTROL TOTAL DEL BALANCE (BUFF ENEMIES & MONEY)
    List<PathPoint> chosenPath = getChosenPath();
    
    if ("FAST".equals(type)) {
        int hp = (int)(15 * scaleFactor);
        Enemy e = new FastEnemy(chosenPath, hp, rewardBase);
        world.addObject(e, chosenPath.get(0).x, chosenPath.get(0).y);
        
    } else if ("TANK".equals(type)) {
        int hp = (int)(100 * scaleFactor);
        Enemy e = new Enemy(chosenPath, hp, rewardBase + 2);
        e.setSpeed(0.6);
        world.addObject(e, chosenPath.get(0).x, chosenPath.get(0).y);
        
    } else { // WEAK (Enemigo base)
        int hp = (int)(25 * scaleFactor);
        Enemy e = new Enemy(chosenPath, hp, rewardBase);
        world.addObject(e, chosenPath.get(0).x, chosenPath.get(0).y);
    }
}

    public boolean isWaveActive() {
        return waveActive;
    }
}