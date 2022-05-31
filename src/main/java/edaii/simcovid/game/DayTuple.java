package edaii.simcovid.game;

import edaii.simcovid.app.Person;

import java.util.List;

public class DayTuple {
    final List<List<Person>> current;
    final Statistics stats;
    public DayTuple(List<List<Person>> currentIteration, Statistics dayStats) {
        this.current = currentIteration;
        this.stats = dayStats;
    }

    public List<List<Person>> getCurrent() {
        return current;
    }

    public Statistics getStats() {
        return stats;
    }
}
