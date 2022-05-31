package edaii.simcovid.game;

import edaii.simcovid.app.Person;

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
    public Statistics(List<List<Person>> currentIteration, int rows, int columns) {
        final List<Person> flattenedList = Stream.iterate(0, y -> y < rows, y -> y+1)
                .map(y -> Stream.iterate(0, x -> x < columns, x -> x+1)
                        .map(x -> new Person(x, y, currentIteration.get(y).get(x).getState(), currentIteration.get(y).get(x).getDaysInState())))
                .flatMap(row -> row)
                .collect(Collectors.toList());

        this.notInfected = flattenedList.stream().filter(i -> i.getState() == 0).count();
        this.infected = flattenedList.stream().filter(i -> i.getState() == 1).count();
        this.immune = flattenedList.stream().filter(i -> i.getState() == 2).count();
        this.surrounded = flattenedList.stream().filter(i -> i.getState() == 3).count();
        this.masked = flattenedList.stream().filter(i -> i.getState() == 4).count();
        this.dead = flattenedList.stream().filter(i -> i.getState() == 5).count();
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
