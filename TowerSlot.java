import greenfoot.*;
// NO se importa java.awt.Color

/**
 * Representa un "slot" o espacio predefinido en el mapa donde se puede 
 * construir una torre.
 */
public class TowerSlot extends Actor {
    private boolean isUnlocked = false;
    private boolean isOccupied = false;

    private GreenfootImage imgHidden;
    private GreenfootImage imgAvailable;

    /**
     * Constructor CORREGIDO.
     * Ya no recibe x,y ni intenta llamar a setLocation (lo cual era ilegal).
     * GameWorld se encargará de la posición.
     */
    public TowerSlot() {
        // --- INICIO DE LA CORRECCIÓN (para Greenfoot 3.9.0) ---
        imgAvailable = new GreenfootImage(40, 40);
        greenfoot.Color lightGreen = new greenfoot.Color(144, 238, 144);
        imgAvailable.setColor(lightGreen);
        imgAvailable.fillOval(0, 0, 40, 40);
        imgAvailable.setTransparency(150);
        // --- FIN DE LA CORRECCIÓN ---

        imgHidden = new GreenfootImage(40, 40);

        // Se eliminó la línea setLocation(x, y) de aquí.
        
        updateImage(); // Empezar oculto por defecto
    }

    /**
     * Lógica principal: Comprobar si se ha hecho clic en este slot.
     */
    public void act() {
        if (isUnlocked && !isOccupied && Greenfoot.mouseClicked(this)) {
            GameWorld gw = getWorldOfType(GameWorld.class);
            gw.buildTowerAt(this); 
        }
    }

    /**
     * Actualiza la imagen del slot según su estado.
     */
    private void updateImage() {
        if (isOccupied) {
            setImage(imgHidden);
        } else if (isUnlocked) {
            setImage(imgAvailable);
        } else {
            setImage(imgHidden);
        }
    }

    // --- Métodos llamados por GameWorld ---

    public void unlock() {
        isUnlocked = true;
        updateImage();
    }

    public void occupy() {
        isOccupied = true;
        updateImage();
    }
}