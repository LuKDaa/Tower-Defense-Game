import greenfoot.*;
import java.awt.Color;

public class UIOverlay extends Actor {
    private GameWorld gw;
    private GreenfootImage img;

    public UIOverlay(GameWorld gw) {
        this.gw = gw;
        img = new GreenfootImage(120, 60);
        setImage(img);
        updateAll();
    }

    public void updateAll() {
        redraw();
        img.drawString("HP: " + gw.playerHP, 10, 18);
        img.drawString("Dinero: $" + gw.money, 10, 36);
        img.drawString("Ola: " + gw.currentWave, 10, 54);
        setImage(img);
    }

    public void updateHP() { updateAll(); }
    public void updateMoney() { updateAll(); }

    public void act() {
        // (El clic manual ha sido eliminado)
    }

    private void redraw() {
        img.clear();
        img.setColor(greenfoot.Color.WHITE);
        img.fillRect(0,0,220,80);
        img.setColor(greenfoot.Color.BLACK);
    }
}