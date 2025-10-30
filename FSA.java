import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/*
 * Finite State Automaton (FSA) base class
 * Contains, states, transitions, start state, accept states, and alphabet
 * Can be used as NFA or DFA
 */
public abstract class FSA {
    public static final char EPSILON = 'ε';
    
    private Set<State> states;
    private Set<Transition> transitions;
    private State startState;
    private Set<State> acceptStates;
    private Set<Character> alphabet;

    // constructs empty FSA with no states or transitions
    public FSA() {
        states = new HashSet<>();
        transitions = new HashSet<>();
        acceptStates = new HashSet<>();
        alphabet = new HashSet<>();
    }

    // finds state by ID
    private State findStatebyId(int id) {
        for (State s : states) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // public method to access findStatebyId
    public State getStateById(int id) {
        return findStatebyId(id);
    }

    // adds a new state
    public void addState(int id, boolean isStart, boolean isAccept) {
        State newState = new State(id, isStart, isAccept);
        states.add(newState);
        if (isStart) {
            startState = newState; // set as start state
        }
        if (isAccept) {
            acceptStates.add(newState); // add to accept states
        }
    }

     // adds a transition from fromId to toId on symbol
     public void addTransition(int fromId, int toId, char symbol) {
        State from = findStatebyId(fromId);
        State to = findStatebyId(toId);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Error: States not found. From: " + fromId + ", To: " + toId);
        }

        Transition t = new Transition(from, to, symbol);
        transitions.add(t);

        // add symbol to alphabet if not epsilon
        if (!t.isEpsilon() && symbol != EPSILON) {
            alphabet.add(symbol);
        }
    }

    /*
     * Computes epsilon clousre of a given state
     * Returns states which are reachable from the given state via epsilon transitions
     * Uses DFS with a stack
     */
    public Set<State> closure(State state) {
        Set<State> result = new HashSet<>();
        Stack<State> stack = new Stack<>();
    
        // start with given state
        result.add(state);
        stack.push(state);
    
        // keep following epsilon transitions
        while (!stack.isEmpty()) {
        State current = stack.pop();
        
        // find all epsilon transitions from current
            for (Transition t : transitions) {
                if (t.getFromState().equals(current) && t.isEpsilon()) {
                    State next = t.getToState();
                
                // if state not found, add it and continue
                    if (!result.contains(next)) {
                        result.add(next);
                        stack.push(next);
                    }
                }
            }
        }
    
        return result;
    }

    /*
     * Sees if the FSA accepts the given input string
     * Returns true if accepted, false otherwise
     */
    public boolean accepts(String input) {
        // start from the start state
        Set<State> currentStates = closure(startState);
        
        // process each character in the input
        for (char symbol : input.toCharArray()) {
            Set<State> nextStates = new HashSet<>();
            for (State state : currentStates) {
                nextStates.addAll(next(state, symbol));
            }
            currentStates = nextStates;
        }
        
        // check if any current state is an accepting state
        for (State state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    // computes the set of states reachable from given state on given symbol
    public Set<State> next(State state, char symbol) {
        Set<State> result = new HashSet<>();
        
        // get the epsilon closure of the starting state
        Set<State> startClosure = closure(state);
        
        // find all transitions from states in the closure with the given symbol
        for (State s : startClosure) {
            for (Transition t : transitions) {
                if (t.getFromState().equals(s) && 
                    !t.isEpsilon() && 
                    t.getSymbol() == symbol) {
                    // add the closure of the destination state
                    result.addAll(closure(t.getToState()));
                }
            }
        }
        
        return result;
    }

    /*
     * Checks if the FSA is deterministic
     * Returns true if deterministic, false otherwise
     */
    public boolean deterministic() {
        // check for epsilon transitions
        for (Transition t : transitions) {
            if (t.isEpsilon()) {
                return false;
            }
        }
        
        // check for multiple transitions from same state on same symbol
        for (State state : states) {
            Set<Character> symbols = new HashSet<>();
            for (Transition t : transitions) {
                if (t.getFromState().equals(state)) {
                    char symbol = t.getSymbol();
                    if (symbols.contains(symbol)) {
                        return false;  // multiple transitions on same symbol
                    }
                    symbols.add(symbol);
                }
            }
        }
        
        return true;
    }

    /*
     * Everything below:
     * Gets all methods and puts them into the automata.
     */

    public abstract DFA toDFA();

    public Set<State> getStates() {
        return states;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public State getStartState() {
        return startState;
    }

    public Set<State> getAcceptStates() {
        return acceptStates;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }
}
