package fightingLogic;
import fightingComponents.*;
import fightingComponents.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fight {
    private Planet planet;
    private Character villain;
    private List<Character> avengers; //if the user does no choose a team of avengers the list will consist of one element

    public Fight()
    {
        this.avengers = new ArrayList<Character>();
    }
    public Fight(Planet planet, Character villain, List<Character> avengers) {
        this.planet = planet;
        this.avengers = avengers;
        this.villain = villain;
    }
    private void setModifiersBeforeFight(){
        for (Character hero : avengers) {
            hero.setAttack(hero.getAttack() + planet.getHeroModifiers().getAttackModifier());
            hero.setHealth(hero.getHealth() + planet.getHeroModifiers().getHealthModifier());
        }
        villain.setAttack(villain.getAttack() + planet.getVillainModifiers().getAttackModifier());
        villain.setHealth(villain.getHealth() + planet.getVillainModifiers().getHealthModifier());
    }

    private void villainAttacks(Character hero,int attack){
        if(villain.getHealth()>0)
            hero.setHealth(hero.getHealth()-attack);

    }

    private void heroAttacks(Character hero,int attack){
        if(hero.getHealth()>0 && villain.getHealth()>0)
            villain.setHealth(villain.getHealth()-attack);
    }
    private boolean avengersHealthIsPositive(){
        for (Character avenger : avengers)
            if (avenger.getHealth() > 0)
                return true;
        return false;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Character getVillain() {
        return villain;
    }

    public void setVillain(Character villain) {
        this.villain = villain;
    }

    public List<Character> getAvengers() {
        return avengers;
    }

    public void setAvengers(List<Character> avengers) {
        this.avengers = avengers;
    }

    public int addAvenger(Character hero){
        try{
        for (Character avenger:avengers)
            if(hero.getId() == avenger.getId())
                throw(new Exception("Avenger already chosen"));

        this.avengers.add(hero);
        return 1;
        }

        catch(Exception ex){
            System.out.println("Something went wrong" + ex.getMessage());
            return 0;
        }
    }

    public ArrayList<String> getAvengersName(){
        ArrayList<String> names = new ArrayList<String>();
        for (Character hero : avengers
             ) {
            names.add(hero.getName());
        }

        return names;
    }

    public void beginFight() {

        setModifiersBeforeFight();

        Random r = new Random();
        int low = 60;
        int high = 100;

        while (avengersHealthIsPositive() && villain.getHealth() > 0)
            for (Character hero : avengers) {

                int heroPercentage = r.nextInt(high - low) + low;
                int heroAttack = (hero.getAttack() * heroPercentage) / 100;


                int villainPercentage = r.nextInt(high - low) + low;
                int villainAttack = (villain.getAttack() * villainPercentage) / 100;

                villainAttacks(hero,villainAttack);
                System.out.println(villain.getName() + " attacks:" + villainAttack + "    health: " + villain.getHealth() +"     " +
                        hero.getName() +  "     health:" + hero.getHealth());

                heroAttacks(hero, heroAttack);

                System.out.println(hero.getName() + " attacks:" + heroAttack + "     health:" + hero.getHealth() + "      "+
                        villain.getName() + "    health: " + villain.getHealth());


                if (!avengersHealthIsPositive() || villain.getHealth() <= 0)
                    break;
            }
        if(avengersHealthIsPositive())
            System.out.println("Avengers win");

        else
        if(villain.getHealth()>0)
            System.out.println(villain.getName() + " wins...");

    }
}
