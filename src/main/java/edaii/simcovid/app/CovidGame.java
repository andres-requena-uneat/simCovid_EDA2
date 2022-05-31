package edaii.simcovid.app;

import edaii.simcovid.game.CovidLogic;
import edaii.simcovid.game.DayTuple;
import edaii.simcovid.game.VirusParameters;
import edaii.simcovid.ui.CovidGameWindow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CovidGame {
    public static final int ROWS = 30;
    public static final int COLUMNS = 30;
    public static final int MSECONDS_PER_DAY = 750;
    public static final int VIRUS_TRANSMISSION_PERCENT = 20;
    public static final int VIRUS_TIMELIFE_DAYS = 14;
    private static final int VIRUS_MORTALITY_RATE = 1;
    private static final int SURROUNDING_INFECTED = 3;
    private static final int MASKED_TRANSMISSION_PERCENT = 1;

    public static void main(String[] args) throws InterruptedException {
        final CovidGameWindow game = new CovidGameWindow();
        game.setRowsAndColumns(ROWS, COLUMNS);
        final VirusParameters virusParameters = new VirusParameters(
                VIRUS_TRANSMISSION_PERCENT,
                VIRUS_TIMELIFE_DAYS,
                VIRUS_MORTALITY_RATE,
                SURROUNDING_INFECTED,
                MASKED_TRANSMISSION_PERCENT
        );
        // Inicialización de logica de juego, población, ...
        final CovidLogic covidLogic = new CovidLogic(virusParameters);
        List<List<Person>> population = initializePopulation(ROWS, COLUMNS);
        while (true) {
            // Actualización del estado de la población
            population = covidLogic.advanceDay(population, ROWS, COLUMNS);
            // Representación del estado de la población como una lista
            final List<Integer> cellStates = population
                    .stream()
                    .flatMap(row -> row.stream())
                    .map(it -> it.getState())
                    .collect(Collectors.toUnmodifiableList());
            // Asignación de estado y pintado en la ventana
            game.setCellStates(cellStates);
            // Paso del tiempo.
            Thread.sleep(MSECONDS_PER_DAY);
        }
    }
    // Genera una lista de listas de personas donde el infectado estará en el centro.
    private static List<List<Person>> initializePopulation(int rows, int columns)
    {
        return Stream.iterate(0, i -> i < rows, i -> i + 1)
                .map(i ->
                        Stream.iterate(0, j -> j < columns, j -> j + 1)
                                .map(j -> j == columns / 2 && i == rows / 2)
                                .map(infected -> infected ?
                                        Person.setupInfected() :

                                        Person.setupNotInfected()).collect(Collectors.toUnmodifiableList())
                )
                .collect(Collectors.toUnmodifiableList());
    }
}