public class BasicTower extends Tower {
    public static final int TOWER_COST = 60;
    
    public BasicTower() {
        setImage("tower_basic.png"); //
        cost = TOWER_COST;
        range = 100; damage = 10; rate = 30; upgradeCost = 60; //
    }
}