// represents a transition between two states in a finite state automaton.
public class Transition {
    private State fromState;
    private State toState;
    private char symbol;

    public Transition(State from, State to, char symbol) {
        this.fromState = from; // source state
        this.toState = to; // destination state
        this.symbol = symbol; // transition symbol (ε for epsilon)
    }

    public State getFromState() {
        return fromState;
    }

    public State getToState() {
        return toState;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isEpsilon() {
        return symbol == 'ε' || symbol == '\u03B5';
    }

    @Override
    public String toString() {
        return fromState.getId() + " --" + symbol + "-->" + toState.getId();
    }
}
