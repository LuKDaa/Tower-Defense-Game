import greenfoot.*;

/**
 * Un actor que cubre toda la pantalla con un filtro oscuro
 * y muestra una calavera y el texto de GAME OVER en el centro.
 */
public class GameOverOverlay extends Actor {
    
    public GameOverOverlay() {
        GreenfootImage img = new GreenfootImage(GameWorld.WIDTH, GameWorld.HEIGHT);
        img.setColor(new greenfoot.Color(0, 0, 0, 255));
        img.fill();

        GreenfootImage skull = null;
        int skullHeight = 0;
        int skullWidth = 0;

        try {
            skull = new GreenfootImage("skull.png");
            skull.scale(skull.getWidth() * 4 / 5, skull.getHeight() * 4 / 5);

            skullHeight = skull.getHeight();
            skullWidth = skull.getWidth();

            int xSkull = (GameWorld.WIDTH - skullWidth) / 2;
            int ySkull = (GameWorld.HEIGHT - skullHeight) / 2;
            img.drawImage(skull, xSkull, ySkull);
            
        } catch (Exception e) {
            System.out.println("No se encontró skull.png o hubo un error al cargarla.");
        }
        String line1 = "GAME OVER";
        String line2 = "PRESIONA 'R' PARA REINICIAR";

        //Crear la imagen de texto para la linea 1
        GreenfootImage textImage1 = new GreenfootImage(
            line1,                        // Texto
            80,                           // Tamanio de la fuente
            greenfoot.Color.RED,          // Color
            new greenfoot.Color(0,0,0,0)  // Fondo
        );

        // 3. Creamos la imagen de texto para la linea 2 (más chica)
        GreenfootImage textImage2 = new GreenfootImage(
            line2,                        // Texto
            30,                           // Tamanio de la fuente
            greenfoot.Color.RED,          // Color
            new greenfoot.Color(0,0,0,0)  // Fondo
        );
        
        int x1 = (GameWorld.WIDTH - textImage1.getWidth()) / 2;
        // Posición Y: 30 píxeles DEBAJO de la calavera
        int y1 = (GameWorld.HEIGHT / 2) + (skullHeight / 2) - 430; 
        img.drawImage(textImage1, x1, y1);
        int x2 = (GameWorld.WIDTH - textImage2.getWidth()) / 2;
        // Posición Y: 15 píxeles DEBAJO de la línea 1
        int y2 = y1 + textImage1.getHeight() + 310; 
        img.drawImage(textImage2, x2, y2);
        setImage(img);
    }
}