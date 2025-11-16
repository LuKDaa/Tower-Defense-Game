import greenfoot.*;

/**
 * Un Actor que muestra un texto y desaparece después de un tiempo.
 */
public class TemporaryText extends Actor {
    
    private int lifetime = 120; // 120 frames = 2 segundos a 60fps

    public TemporaryText(String text) {
        // Dibuja el texto
        GreenfootImage img = new GreenfootImage(text.length() * 10 + 20, 30);
        
        img.setColor(new greenfoot.Color(255, 255, 100)); // Un color amarillo pálido
        
        img.drawString(text, 10, 20);
        setImage(img);
    }

    public void act() {
        lifetime--; // Contar hacia atrás
        if (lifetime <= 0) {
            if (getWorld() != null) { // chequear que no haya sido eliminado
                getWorld().removeObject(this); // Autodestruirse
            }
        }
    }
}