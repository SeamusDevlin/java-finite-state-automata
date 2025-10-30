import java.util.HashMap;
import java.util.Map;

/* Represents DFA
 * Must have:
 * - No epsilon transitions
 * - At most one transition from a state on a given symbol
 * - Always deterministic
 */
public class DFA extends FSA {
    // DFA has a transition function: (state, symbol) -> state
    private Map<State, Map<Character, State>> transitionFunction;
    
    // contructs empty DFA with no states or transitions.
    public DFA() {
        super();
        transitionFunction = new HashMap<>();
    }
    
    /*
     * Adds a transition to the DFA
     * Ensures no epsilon transitions and no multiple transitions
     */
    @Override
    public void addTransition(int fromId, int toId, char symbol) {
        // DFAs cannot have epsilon transitions
        if (symbol == FSA.EPSILON || symbol == 'ε' || symbol == '\u03B5') {
            throw new IllegalArgumentException("DFA cannot have epsilon transitions");
        }
        
        State from = getStateById(fromId);
        State to = getStateById(toId);
        
        if (from == null || to == null) {
            System.out.println("Error: States not found.");
            return;
        }
        
        // check if transition already exists for this state and symbol
        if (transitionFunction.containsKey(from) && 
            transitionFunction.get(from).containsKey(symbol)) {
            throw new IllegalArgumentException("DFA cannot have multiple transitions from same state on same symbol");
        }
        
        // add to transition function
        transitionFunction.putIfAbsent(from, new HashMap<>());
        transitionFunction.get(from).put(symbol, to);
        
        // add to parent's transitions set
        super.addTransition(fromId, toId, symbol);
    }
    
    /**
     * DFA already is a DFA, so return itself
     * @return this DFA instance
     */
    @Override
    public DFA toDFA() {
        return this;
    }
    
    /**
     * Optimized accepts for DFA - uses single state instead of set
     * @param input string to test
     * @return true if accepted, false otherwise
     */
    @Override
    public boolean accepts(String input) {
        State currentState = getStartState();
        
        if (currentState == null) {
            return false;
        }
        
        // process each character
        for (char symbol : input.toCharArray()) {
            State nextState = null;
            
            // find transition from current state on this symbol
            for (Transition t : getTransitions()) {
                if (t.getFromState().equals(currentState) && 
                    t.getSymbol() == symbol) {
                    nextState = t.getToState();
                    break;
                }
            }
            
            // if no transition found, reject
            if (nextState == null) {
                return false;
            }
            
            currentState = nextState;
        }
        
        // check if final state is accepting
        return getAcceptStates().contains(currentState);
    }
}