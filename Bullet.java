import greenfoot.*;

public class Bullet extends Actor {
    protected int damage;
    protected Actor target;
    protected double speed = 6.0;

    public Bullet(int damage, Actor target) {
        this.damage = damage;
        this.target = target;
        setImage(new GreenfootImage(6, 6));
        getImage().setColor(greenfoot.Color.BLACK);
        getImage().fillOval(0, 0, 6, 6);
    }

    public void act() {
        // --- ESTA ES LA LÍNEA CORREGIDA ---
        // Comprueba si el objetivo existe Y si todavía está en el mundo
        if (target == null || target.getWorld() == null) {
            if (getWorld() != null) getWorld().removeObject(this);
            return; // Salir del método si el objetivo murió
        }
        // --- FIN DE LA CORRECCIÓN ---
        
        turnTowards(target.getX(), target.getY());
        move((int) speed);
        
        if (intersects(target)) {
            if (target instanceof Enemy) {
                // --- CAMBIO ---
                // Aplica efectos (como slow) ANTES de hacer daño
                applyEffect((Enemy) target);
                ((Enemy) target).takeDamage(damage);
            }
            getWorld().removeObject(this);
        }
    }

    // --- NUEVO MÉTODO ---
    /**
     * Aplica cualquier efecto especial (como slow) al enemigo.
     * Es llamado solo al impactar. Las balas normales no hacen nada.
     */
    protected void applyEffect(Enemy e) {
        // Sobrescribir en subclases como SlowBullet
    }
}