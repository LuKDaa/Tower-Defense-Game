public class SlowTower extends Tower {
    public static final int TOWER_COST = 80;
    public SlowTower() {
        setImage("tower_slow.png");
        cost = TOWER_COST;
        range = 90; damage = 4; rate = 40; upgradeCost = 70;
    }
    
    @Override
    protected void shoot(Enemy target) { //
        SlowBullet b = new SlowBullet(damage, target);
        getWorld().addObject(b, getX(), getY());
    }
}