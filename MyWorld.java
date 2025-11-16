import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Mundo inicial que lanza directamente el GameWorld.
 */
public class MyWorld extends World
{
    public MyWorld()
    {    
        // Crea el mundo inicial y cambia inmediatamente al GameWorld
        super(900, 600, 1); 
        Greenfoot.setWorld(new GameWorld());
    }
}
