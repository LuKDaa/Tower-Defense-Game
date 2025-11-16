import greenfoot.*;
import java.util.*;

public class Enemy extends Actor {
    protected java.util.List<PathPoint> path;
    protected int index = 1;
    
    private double preciseX;
    private double preciseY;
    
    protected double baseSpeed; // Velocidad original
    public double speed;        // Velocidad actual (modificada por slow)
    protected int slowTimer = 0; // Temporizador para el efecto slow

    protected int hp;
    protected int reward;

    public Enemy(java.util.List<PathPoint> path, int hp, int reward) {
        this.path = path; this.hp = hp; this.reward = reward;
        setImage("enemy.png");

        this.preciseX = path.get(0).x;
        this.preciseY = path.get(0).y;

        setLocation((int)preciseX, (int)preciseY);
        turnTowards(path.get(index).x, path.get(index).y);
        
        this.baseSpeed = 1.2; 
        this.speed = this.baseSpeed;
    }

    public void act() {
        moveAlongPath();
    }

    protected void moveAlongPath() {
        if (slowTimer > 0) {
            speed = baseSpeed * 0.5; // Aplica un 50% de slow
            slowTimer--;
        } else {
            speed = baseSpeed; // Restaura la velocidad normal
        }

        if (index >= path.size()) return;
        PathPoint p = path.get(index);

        double dx = p.x - preciseX;
        double dy = p.y - preciseY;
        double dist = Math.hypot(dx, dy);
        
        if (dist < speed) {
            preciseX = p.x;
            preciseY = p.y;
            setLocation((int)preciseX, (int)preciseY);

            index++;
            if (index >= path.size()) { reachedGoal(); }
            else turnTowards(path.get(index).x, path.get(index).y);
        } else {
        preciseX += (dx / dist) * speed;
        preciseY += (dy / dist) * speed;
        
        setLocation((int)preciseX, (int)preciseY);
        }
    }

    protected void reachedGoal() {
        getWorldOfType(GameWorld.class).damagePlayer(1);
        getWorld().removeObject(this);
    }

    public void takeDamage(int d) {
        hp -= d;
        if (hp <= 0) die();
    }

    protected void die() {
        getWorldOfType(GameWorld.class).addMoney(reward);
        getWorld().removeObject(this);
    }
    
    /**
     * Aplica un efecto de slow durante un número determinado de frames.
     */
    public void applySlow(int duration) {
        // Previene que un slow más corto sobreescriba uno más largo
        if (duration > this.slowTimer) {
            this.slowTimer = duration;
        }
    }
    
    /**
     * Establece la velocidad base de este enemigo.
     */
    public void setSpeed(double newSpeed) {
        this.baseSpeed = newSpeed;
        this.speed = newSpeed;
    }
}