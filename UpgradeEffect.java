import greenfoot.*;

/**
 * Un efecto visual simple que flota hacia arriba y se desvanece.
 */
public class UpgradeEffect extends Actor {
    
    private int lifetime = 30; // 30 frames (medio segundo)
    private int initialLifetime = 30;

    public UpgradeEffect() {
        setImage("mejora_fx.png");
    }

    public void act() {
        lifetime--;
        
        if (lifetime <= 0) {
            getWorld().removeObject(this);
        } else {
            // Mover hacia arriba
            setLocation(getX(), getY() - 1);
            
            // Desvanecer (fade out)
            int transparency = (int) (255.0 * lifetime / initialLifetime);
            getImage().setTransparency(transparency);
        }
    }
}