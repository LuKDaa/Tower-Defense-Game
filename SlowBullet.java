import greenfoot.*;

public class SlowBullet extends Bullet {
    
    public SlowBullet(int damage, Actor target) {
        super(damage, target);
        getImage().setColor(greenfoot.Color.BLUE);
        getImage().fillOval(0,0,8,8);
    }

    // --- CAMBIO RADICAL ---
    // Se elimina el método act() que estaba aquí.
    // La lógica de "super.act()" y aplicar slow en cada frame era incorrecta.
    // Ahora, solo sobrescribimos el nuevo método de Bullet.

    /**
     * Este método se llama DESDE Bullet.act() SÓLO AL IMPACTAR
     */
    @Override
    protected void applyEffect(Enemy e) {
        e.applySlow(120); // Aplica slow por 120 frames (2 segundos)
    }
}