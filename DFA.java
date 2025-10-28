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
        if (symbol == '\0') {
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
    
    @Override
    public boolean accepts(String input) {
        State current = getStartState();
        
        for (char symbol : input.toCharArray()) {
            if (!transitionFunction.containsKey(current) || 
                !transitionFunction.get(current).containsKey(symbol)) {
                return false;  // No transition defined
            }
            current = transitionFunction.get(current).get(symbol);
        }
        
        return getAcceptStates().contains(current);
    }
    
    @Override
    public boolean deterministic() {
        return true;  // DFAs are always deterministic
    }
}