package edaii.simcovid.game;

import edaii.simcovid.app.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CovidLogic {

    private final VirusParameters parameters;

    public CovidLogic(VirusParameters parameters) {
        this.parameters = parameters;
    }


    public List<List<Person>> advanceDay(List<List<Person>> population, int rows, int columns) {
        final List<List<Person>> lastIteration = population;
        return Stream.iterate(0, y -> y < rows, y -> y+1)
                .map(y -> Stream.iterate(0, x -> x < columns, x -> x+1)
                        .map(x -> new Person(x, y, population.get(y).get(x).getState(), population.get(y).get(x).getDaysInState()))
                        .map(person -> calcState(person, rows, columns, lastIteration)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
    public Person calcState(Person person, int rows, int columns, List<List<Person>> population){
        final List<Person> neighbours = findNeighbours(person, columns, rows, population);
        final long infectedNeighbours = countInfectedNeighbours(neighbours);
        if(person.getState() == 4){
            if (personGotInfected(neighbours, parameters.maskedTransmissionPercent)) return person.infected();
            if (infectedNeighbours < parameters.surrounding) return person.notInfected();
        }
        else if(person.getState() == 3){
            if (infectedNeighbours >= parameters.surrounding) return person.masked();
            return person.notInfected();
        }
        else if (person.getState() == 2){
            return person.immune();
        }
        else if(person.getState() == 1){
            if (Math.random() <= parameters.mortalityRate * 1.0 / 100) return person.dead();
            if (person.getDaysInState() >= parameters.lifetimeInDays) return person.immune();
            return person.infected();
        }
        else if(person.getState() == 0){
            if (personGotInfected(neighbours, parameters.transmissionPercent)) return person.infected();
            if (infectedNeighbours >= parameters.surrounding) return person.surrounded();
        }
        return person;
    }

    private boolean personGotInfected(List<Person> neighbours, int transmission){
        return neighbours.stream()
                .filter(i -> i.getState() == 1)
                .map(j -> Math.random() <= (transmission * 1.0 / 100))
                .anyMatch(k -> k == true);
    }

    private long countInfectedNeighbours(List<Person> neighbours) {
        return neighbours.stream().filter(i -> i.getState() == 1).count();
    }

    private List<Person> findNeighbours(Person person, int columns, int rows, List<List<Person>> population) {
        ArrayList<Person> list = new ArrayList<>();
        // Esquina superior izquierda
        if(person.getY() == 0 && person.getX() == 0){
            list.add(population.get(0).get(1));
            list.add(population.get(1).get(0));
            list.add(population.get(1).get(1));
        }
        // Esquina superior derecha
        else if(person.getY() == 0 && person.getX() == columns-1){
            list.add(population.get(0).get(columns-2));
            list.add(population.get(1).get(columns-2));
            list.add(population.get(1).get(columns-1));
        }
        // Esquina inferior izquierda
        else if(person.getY() == rows-1 && person.getX() == 0){
            list.add(population.get(rows-2).get(0));
            list.add(population.get(rows-2).get(1));
            list.add(population.get(rows-1).get(1));
        }
        // Esquina inferior derecha
        else if(person.getY() == rows-1 && person.getX() == columns-1){
            list.add(population.get(0).get(rows-2));
            list.add(population.get(1).get(rows-2));
            list.add(population.get(1).get(rows-1));
        }
        // Borde superior
        else if(person.getY() == 0 && (person.getX() > 0 && person.getX() < columns-1)){
            list.add(population.get(0).get(person.getX()-1));
            list.add(population.get(0).get(person.getX()+1));
            list.add(population.get(1).get(person.getX()-1));
            list.add(population.get(1).get(person.getX()));
            list.add(population.get(1).get(person.getX()+1));
        }
        // Borde inferior
        else if(person.getY() == rows-1 && (person.getX() > 0 && person.getX() < columns-1)){
            list.add(population.get(rows-1).get(person.getX()-1));
            list.add(population.get(rows-1).get(person.getX()+1));
            list.add(population.get(rows-2).get(person.getX()-1));
            list.add(population.get(rows-2).get(person.getX()));
            list.add(population.get(rows-2).get(person.getX()+1));
        }
        // Borde izquierdo
        else if((person.getY() > 0 && person.getY() < rows-1) && person.getX() == 0){
            list.add(population.get(person.getY()-1).get(0));
            list.add(population.get(person.getY()-1).get(1));
            list.add(population.get(person.getY()).get(1));
            list.add(population.get(person.getY()+1).get(0));
            list.add(population.get(person.getY()+1).get(1));
        }
        // Borde derecho
        else if((person.getY() > 0 && person.getY() < rows-1) && person.getX() == columns-1){
            list.add(population.get(person.getY()-1).get(columns-1));
            list.add(population.get(person.getY()-1).get(columns-2));
            list.add(population.get(person.getY()).get(columns-2));
            list.add(population.get(person.getY()+1).get(columns-2));
            list.add(population.get(person.getY()+1).get(columns-1));
        }
        // Casillas internas
        else{
            list.add(population.get(person.getY()-1).get(person.getX()-1));
            list.add(population.get(person.getY()-1).get(person.getX()));
            list.add(population.get(person.getY()-1).get(person.getX()+1));
            list.add(population.get(person.getY()).get(person.getX()-1));
            list.add(population.get(person.getY()).get(person.getX()+1));
            list.add(population.get(person.getY()+1).get(person.getX()-1));
            list.add(population.get(person.getY()+1).get(person.getX()));
            list.add(population.get(person.getY()+1).get(person.getX()+1));
        }
        return list;
    }

}
