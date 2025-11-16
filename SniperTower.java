public class SniperTower extends Tower {
    public static final int TOWER_COST = 120;
    public SniperTower() {
        setImage("tower_sniper.png");
        cost = TOWER_COST;
        range = 220; damage = 30; rate = 90; upgradeCost = 120;
    }
}