package edaii.simcovid.game;

import edaii.simcovid.app.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Statistics {
    final long notInfected;
    final long infected;
    final long immune;
    final long surrounded;
    final long masked;
    final long dead;
    public Statistics(List<List<Person>> currentIteration) {
        final List<Person> flattenedList = currentIteration.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        this.notInfected = flattenedList.stream().filter(i -> i.getState() == 0).count();
        this.infected = flattenedList.stream().filter(i -> i.getState() == 1).count();
        this.immune = flattenedList.stream().filter(i -> i.getState() == 2).count();
        this.surrounded = flattenedList.stream().filter(i -> i.getState() == 3).count();
        this.masked = flattenedList.stream().filter(i -> i.getState() == 4).count();
        this.dead = flattenedList.stream().filter(i -> i.getState() == 5).count();
    }

    public List<Integer> getStatsAsList(){
        List<Integer> temp = new ArrayList<>();
        temp.add((int)this.getNotInfected());
        temp.add((int)this.getInfected());
        temp.add((int)this.getImmune());
        temp.add((int)this.getSurrounded());
        temp.add((int)this.getMasked());
        temp.add((int)this.getDead());
        return temp;
    }

    public List<String> getStatsAsListOfString(){
        List<String> temp = new ArrayList<>();
        temp.add("Not Infected: " + this.getNotInfected());
        temp.add("Infected: " + this.getInfected());
        temp.add("Immune: " + this.getImmune());
        temp.add("Surrounded: " + this.getSurrounded());
        temp.add("Masked: " + this.getMasked());
        temp.add("Dead: " + this.getDead());
        return temp;
    }


    public boolean eradicated(){
        return this.infected == 0 ? true : false;
    }


    public long getNotInfected() {
        return notInfected;
    }

    public long getInfected() {
        return infected;
    }

    public long getImmune() {
        return immune;
    }

    public long getSurrounded() {
        return surrounded;
    }

    public long getMasked() {
        return masked;
    }

    public long getDead() {
        return dead;
    }
}
