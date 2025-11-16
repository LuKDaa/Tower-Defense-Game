// Reemplaza todo el contenido de UpgradeButton.java con esto:
import greenfoot.*;

/**
 * Un botón que aparece sobre una torre y permite mejorarla.
 * Muestra el costo de la mejora.
 */
public class UpgradeButton extends Actor {
    
    private Tower owner; // La torre que este botón va a mejorar

    /**
     * Crea un nuevo botón de mejora para una torre específica.
     * AHORA TAMBIÉN RECIBE EL COSTO.
     */
    public UpgradeButton(Tower owner, int cost) {
        this.owner = owner;
        
        // --- LÓGICA DE DIBUJO MODIFICADA ---
        
        // El texto que queremos mostrar
        String text = "Mejorar ($" + cost + ")";
        
        // Hacemos el botón más ancho para que quepa el texto (ej. 120px)
        GreenfootImage img = new GreenfootImage(120, 22); 
        img.setColor(greenfoot.Color.YELLOW);
        img.fillRect(0, 0, 120, 22); // Rellena el nuevo tamaño
        
        img.setColor(greenfoot.Color.BLACK);
        img.drawRect(0, 0, 119, 21); // Borde del nuevo tamaño
        
        // Dibuja el nuevo texto
        // (Ajustamos la 'x' a 10 para que se centre un poco mejor)
        img.drawString(text, 10, 16); 
        setImage(img);
    }

    /**
     * Comprueba si el jugador ha hecho clic en este botón.
     * (El método act() no necesita cambios)
     */
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (owner != null && owner.getWorld() != null) {
                owner.performUpgrade(); 
            }
        }
    }
}