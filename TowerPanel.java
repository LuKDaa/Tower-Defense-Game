import greenfoot.*;

public class TowerPanel extends Actor {
    private GameWorld gw;
    private GreenfootImage img;

    public TowerPanel(GameWorld gw) {
        this.gw = gw;
        img = new GreenfootImage(200, 100); // Tamaño original
        setImage(img);
        redraw();
    }

    private void redraw() {
        img.clear();
        img.setColor(greenfoot.Color.LIGHT_GRAY); img.fillRect(0,0,220,200);
        img.setColor(greenfoot.Color.BLACK);
        
        // Los costes ya no están "hardcodeados" en el texto.
        img.drawString("Comprar Torres", 10, 18);
        img.drawString("1 - Basica              ($" + BasicTower.TOWER_COST + ")", 10, 40);
        img.drawString("2 - Ralentizante    ($" + SlowTower.TOWER_COST + ")", 10, 60);
        img.drawString("3 - Sniper              ($" + SniperTower.TOWER_COST + ")", 10, 80);
        img.drawString("Click en el mapa para colocar", 10, 110);
        
        setImage(img);
    }

    public void act() {
        if (Greenfoot.isKeyDown("1")) gw.selectedTower = "Basic";
        if (Greenfoot.isKeyDown("2")) gw.selectedTower = "Slow";
        if (Greenfoot.isKeyDown("3")) gw.selectedTower = "Sniper";
    }
}