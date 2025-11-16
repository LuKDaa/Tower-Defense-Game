import greenfoot.*; 
import java.util.*;

public class GameWorld extends World {
    public static final int WIDTH = 1338;
    public static final int HEIGHT = 542;
    public static final int GOAL_X = 1318;
    private List<PathPoint> pathAlto;
    private List<PathPoint> pathBajo;
    
    public int playerHP = 20;
    public int money = 150;
    public int currentWave = 0;
    
    private boolean isGameOver = false;
    private String gameState = "STARTING";
    private int phaseTimer = 0;
    private WaveManager waveManager;
    private UIOverlay ui;
    public String selectedTower = "Basic";
    
    private List<TowerSlot> allTowerSlots;
    private int unlockedSlotsCount;        

    private int nextWaveTimer = -1; 
    private static final int WAVE_DELAY_FRAMES = 600; 
    private GreenfootSound bgMusic;

    public GameWorld() {
        super(WIDTH, HEIGHT, 1);
        setBackground(new GreenfootImage("mapa.png"));
        createPaths();
        
        bgMusic = new GreenfootSound("musica_fondo.mp3");
        bgMusic.setVolume(50);
        
        ui = new UIOverlay(this);
        addObject(ui, 930, 40);
        
        waveManager = new WaveManager(this, pathAlto, pathBajo);
        
        addObject(new TowerPanel(this), 1100, 60);

        allTowerSlots = new ArrayList<>();
        createTowerSlots(); 
        
        unlockedSlotsCount = 3; 
        unlockSlots();
        
        currentWave = 0;
        ui.updateAll();
        gameState = "STARTING";
    }

    private void createTowerSlots() {
        addSlot(130, 80);
        addSlot(130, 200);
        addSlot(130, 350);
        addSlot(130, 450);
        
        addSlot(260, 170);
        addSlot(265, 275);
        addSlot(360, 320);
        
        addSlot(500, 230);
        addSlot(600, 80);
        addSlot(730, 250);
        
        addSlot(820, 400);
        addSlot(950, 400);
        
        addSlot(950, 250);
        addSlot(1130, 300);
        addSlot(1260, 190);
    }
    
    private void addSlot(int x, int y) {
        TowerSlot slot = new TowerSlot();
        allTowerSlots.add(slot); // Guardar en la lista
        addObject(slot, x, y);   // Añadir al mundo EN ESTE MOMENTO
    }
    
    /**
     * Actualiza el estado 'unlocked' de los slots segun la oleada actual.
     */
    private void unlockSlots() {
        for (int i = 0; i < allTowerSlots.size(); i++) {
            if (i < unlockedSlotsCount) {
                allTowerSlots.get(i).unlock();
            }
        }
    }

    /**
     * Lógica de construccion.
     * Es llamado por un TowerSlot cuando el jugador hace clic en el.
     */
    public void buildTowerAt(TowerSlot slot) {
        Tower towerToBuild = null;
        String type = selectedTower;
        
        if ("Basic".equals(type)) {
            towerToBuild = new BasicTower();
        } else if ("Slow".equals(type)) {
            towerToBuild = new SlowTower();
        } else if ("Sniper".equals(type)) {
            towerToBuild = new SniperTower();
        } else {
            return;
        }

        int cost = towerToBuild.getCost();

        if (spendMoney(cost)) {
            addObject(towerToBuild, slot.getX(), slot.getY());
            slot.occupy();
        } else {
            addObject(new TemporaryText("No hay dinero"), slot.getX(), slot.getY() - 50);
        }
    }
    
    private void createPaths() {
    ArrayList<PathPoint> pathComunFinal = new ArrayList<>();
    pathComunFinal.add(new PathPoint(310, 275));  //Merge Point
    pathComunFinal.add(new PathPoint(545, 275));
    pathComunFinal.add(new PathPoint(545, 125));
    pathComunFinal.add(new PathPoint(772, 125));
    pathComunFinal.add(new PathPoint(772, 445));
    pathComunFinal.add(new PathPoint(1010, 445));
    pathComunFinal.add(new PathPoint(1010, 245));
    pathComunFinal.add(new PathPoint(GOAL_X + 50, 245)); // (Punto de salida)

    pathAlto = new ArrayList<>();
    pathAlto.add(new PathPoint(82, 1)); // Spawn
    pathAlto.add(new PathPoint(82, 125));
    pathAlto.add(new PathPoint(310, 130));
    // Se une directamente al merge point
    pathAlto.addAll(pathComunFinal);

    pathBajo = new ArrayList<>();
    pathBajo.add(new PathPoint(82, 1)); // Spawn
    pathBajo.add(new PathPoint(82, 125));
    pathBajo.add(new PathPoint(82, 410));
    pathBajo.add(new PathPoint(310, 410));
    // Se une directamente al merge point
    pathBajo.addAll(pathComunFinal);
}
    
    public WaveManager getWaveManager() { return waveManager; }
    
    public void damagePlayer(int amount) {
        playerHP -= amount;
        ui.updateAll();
    }
    public void addMoney(int amount) {
        money += amount;
        ui.updateAll();
    }
    public boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            ui.updateAll();
            return true;
        }
        return false;
    }

    /**
     * Este metodo es llamado por Greenfoot CADA VEZ que
     * se presiona el boton "Run" (o "Ejecutar").
     */
    public void started() {
        // Inicia (o reanuda) la musica en bucle
        bgMusic.playLoop(); 
        
        if (gameState.equals("STARTING")){
        gameState = "COUNTDOWN";
        phaseTimer = 180; //3 segundos = 180 frames
    }
    }

    /**
     * Este metodo es llamado por Greenfoot CADA VEZ que
     * se presiona el boton "Pause" (o "Pausa").
     */
    public void stopped() {
        // Pausa la musica
        bgMusic.pause();
    }
    
    public void act() {
        // 1. LOGICA DE GAME OVER (Prioridad)
        if (playerHP <= 0) {
            if (!isGameOver) {
                isGameOver = true; 
                if (bgMusic.isPlaying()) {
                    bgMusic.stop();
                    Greenfoot.playSound("game_over.mp3");
                }
                //Remueve objetos del mundo
                addObject(new GameOverOverlay(), WIDTH / 2, HEIGHT / 2);
                removeObjects(getObjects(Tower.class));
                removeObjects(getObjects(Enemy.class));
                removeObjects(getObjects(Bullet.class));
                removeObjects(getObjects(UpgradeButton.class));
                removeObjects(getObjects(UpgradeEffect.class));
                
            }
            if (Greenfoot.isKeyDown("r")) {
                Greenfoot.stop(); 
                Greenfoot.setWorld(new GameWorld());
            }
            return; 
        }
        // FASE 1: "El juego comienza en x tiempo"
        if (gameState.equals("COUNTDOWN")) {
            phaseTimer--;
            int seconds = (phaseTimer / 60) + 1; // Calcula segundos restantes
            showText("El juego comienza en: " + seconds, WIDTH / 2, HEIGHT / 2);
            
            if (phaseTimer <= 0) {
                showText("¡Empieza el juego!", WIDTH / 2, HEIGHT / 2);
                gameState = "GRACE_PERIOD"; // Pasar a la siguiente fase
                phaseTimer = 180; // Reiniciar timer para 3 seg de gracia
            }
        } 
        
        // FASE 2: "¡Prepara tus defensas!..." (Período de gracia)
        else if (gameState.equals("GRACE_PERIOD")) {
            phaseTimer--;
            int seconds = (phaseTimer / 60) + 1;
            // Actualizar el texto del estado anterior
            showText("¡Prepara tus defensas! (" + seconds + ")", WIDTH / 2, HEIGHT / 2);
            
            if (phaseTimer <= 0) {
                showText("", WIDTH / 2, HEIGHT / 2); // Limpiar mensaje central
                gameState = "PLAYING"; // ¡El juego empieza AHORA!
                
                // --- ¡AQUÍ ES DONDE REALMENTE COMIENZA LA ACCIÓN! ---
                nextWave(); // Llama a la Oleada 1 por PRIMERA VEZ
            }
        }
        
        // FASE 3: El juego está en curso (esta es tu lógica de oleadas anterior)
        else if (gameState.equals("PLAYING")) {
            
            // Lógica de pausa entre oleadas
            if (nextWaveTimer > 0) {
                nextWaveTimer--;
                int secondsLeft = (nextWaveTimer / 60) + 1;
                showText("Siguiente oleada en: " + secondsLeft, WIDTH / 2, HEIGHT / 2 + 50);

                if (nextWaveTimer == 0) {
                    showText("", WIDTH / 2, HEIGHT / 2 + 50);
                    hideAllUpgradeButtons();
                    nextWave();
                    nextWaveTimer = -1;
                }
            }
            // Lógica de espera a que termine la oleada
            else if (nextWaveTimer == -1) {
                boolean enemiesGone = getObjects(Enemy.class).isEmpty();
                boolean bossesGone = getObjects(Boss.class).isEmpty();
                boolean waveInactive = !waveManager.isWaveActive();
                
                if (enemiesGone && bossesGone && waveInactive && currentWave > 0) {
                    nextWaveTimer = WAVE_DELAY_FRAMES;
                    showAllUpgradeButtons();
                }
            }
        }
        
        // Si gameState es "STARTING", no hace nada y espera a que started() lo cambie.
    }

    public void nextWave() {
        currentWave++;
        ui.updateAll();
        
        if (unlockedSlotsCount < allTowerSlots.size()) {
            unlockedSlotsCount++;
        }
        unlockSlots(); 
        
        waveManager.startWave(currentWave);
        
        if (getObjects(Spawner.class).isEmpty()) {
            addObject(new Spawner(waveManager), 10, 10);
        }
    }
    public void showAllUpgradeButtons() {
        for (Tower t : getObjects(Tower.class)) {
            t.showUpgradeButton();
        }
    }

    public void hideAllUpgradeButtons() {
        for (Tower t : getObjects(Tower.class)) {
            t.hideUpgradeButton();
        }
}
}