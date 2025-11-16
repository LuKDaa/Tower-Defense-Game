// Modificaciones para el ARCHIVO: Tower.java

import greenfoot.*;

public abstract class Tower extends Actor {
    protected int level = 1; 
    protected int range = 100; 
    protected int damage = 8; //
    protected int rate = 30; 
    protected int cooldown = 0; 
    protected int cost = 60; 
    protected int upgradeCost = 60; 

    // --- LÍNEA NUEVA A AÑADIR ---
    // Referencia a su propio botón de mejora
    private UpgradeButton myButton = null; 

    public void act() { 
        if (cooldown > 0) cooldown--;
        if (cooldown <= 0) {
            Enemy e = findTarget();
            if (e != null) {
                shoot(e);
                cooldown = rate;
            }
        }
    }

    protected Enemy findTarget() { 
        java.util.List<Enemy> list = getObjectsInRange(range, Enemy.class);
        if (!list.isEmpty()) return list.get(0);
        return null;
    }

    protected void shoot(Enemy target) { 
        Bullet b = new Bullet(damage, target);
        getWorld().addObject(b, getX(), getY());
    }

    // --- MÉTODO 'upgrade' MODIFICADO ---
    /**
     * Intenta comprar una mejora para esta torre.
     * Esta es la NUEVA lógica de mejora (1.5x daño).
     */
    public boolean upgrade() { 
        GameWorld gw = getWorldOfType(GameWorld.class);
        if (gw.spendMoney(upgradeCost)) {
            level++; 
            
            // --- TU LÓGICA DE MEJORA ---
            // "1.5 mas potente" -> Aplicado al daño.
            // "acumulativo" -> Se logra al multiplicar el valor actual.
            // Usamos (int) para que el daño siga siendo un entero.
            damage = (int) (damage * 1.5); 
            
            // (Opcional) Aumentar el costo de la *siguiente* mejora
            // Sube 20% + 10 para que el costo siempre aumente.
            upgradeCost = (int) (upgradeCost * 1.2) + 10; 
            
            // (La lógica antigua de "damage += 4; range += 15;" se reemplaza)
            
            return true; // Mejora comprada con éxito
        }
        return false; // No había dinero
    }
    
    public int getCost(){ 
        return cost; 
    }
    
    public int getUpgradeCost(){ return upgradeCost; } 

    
    // --- INICIO: TRES (3) NUEVOS MÉTODOS PARA AÑADIR AL FINAL DE LA CLASE ---

    /**
     * Muestra el botón de mejora sobre la torre.
     * Es llamado por GameWorld cuando empieza el tiempo de descanso.
     */
    public void showUpgradeButton() {
        // Solo crea un botón nuevo si no existe o fue eliminado
        if (myButton == null || myButton.getWorld() == null) {
            myButton = new UpgradeButton(this, upgradeCost); //Esto muesta el valor de la mejora            
            // Coloca el botón 30 píxeles arriba de la torre
            getWorld().addObject(myButton, getX(), getY() - 30); 
        }
    }

    /**
     * Oculta y elimina el botón de mejora de esta torre.
     * Es llamado por GameWorld cuando termina el tiempo de descanso.
     */
    public void hideUpgradeButton() {
        if (myButton != null && myButton.getWorld() != null) {
            getWorld().removeObject(myButton);
            myButton = null; // Limpia la referencia
        }
    }

    /**
     * Este método es llamado por el UpgradeButton cuando se le hace clic.
     */
    public void performUpgrade() {
        if (upgrade()) {
            Greenfoot.playSound("mejora.mp3");
            getWorld().addObject(new UpgradeEffect(), getX(), getY());
        } else {
            // No hay dinero.
            GameWorld gw = getWorldOfType(GameWorld.class);
            if (gw != null) {
                // --- ¡ESTA ES LA LÍNEA CORRECTA! ---
                // Añade el *Actor* que se autodestruye.
                gw.addObject(new TemporaryText("No hay dinero"), getX(), getY() - 50);
            }
        }
    }
}