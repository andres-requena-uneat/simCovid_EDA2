package edaii.simcovid.app;

public class Person {
    final int state;
    int x;
    int y;
    int daysInState;

    public Person(int x, int y, int state, int daysInState) {
        this.y = y;
        this.x = x;
        this.state = state;
        this.daysInState = daysInState;
    }
    public Person(int state) {
        this.state = state;
    }
    public Person Person() {
        return new Person(this.x, this.y, this.getState(), this.getDaysInState());
    }

    public static Person setupNotInfected() {
        return new Person(0);
    }
    public static Person setupInfected() {
        return new Person( 1);
    }

    public Person notInfected() {
        return new Person(this.x, this.y, 0, 0);
    }

    public Person infected() {
        return new Person(this.x, this.y, 1, this.getDaysInState()+1);
    }

    public Person immune() {
        return new Person(this.x, this.y, 2, 0);
    }

    public Person surrounded() {
        return new Person(this.x, this.y, 3, 0);
    }

    public Person masked() {
        return new Person(this.x, this.y, 4, 0);
    }

    public Person dead() {
        return new Person(this.x, this.y, 5, 0);
    }

    public Integer getState() {
        return this.state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDaysInState() {
        return daysInState;
    }

}
