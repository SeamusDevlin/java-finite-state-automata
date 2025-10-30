import java.util.*;

/**
 * Represents a Non-deterministic Finite Automaton (NFA).
 * An NFA is a finite state automaton that may have:
 * - Epsilon (empty) transitions
 * - Multiple transitions from the same state on the same symbol
 * - States with no transitions for certain symbols
 * 
 * Inherits all functionality from the FSA base class.
 */

public class NFA extends FSA {

    // constructs an empty NFA with no states or transitions.
    public NFA() {
        super();
    }

    // currently inherits all methods from FSA without modification.
    
    /**
     * Converts this NFA to an equivalent DFA using subset construction algorithm
     * @return equivalent DFA
     */
    @Override
    public DFA toDFA() {
        DFA dfa = new DFA();
        
        // map from set of NFA states to DFA state ID
        Map<Set<State>, Integer> stateMap = new HashMap<>();
        Queue<Set<State>> unmarked = new LinkedList<>();
        int nextStateId = 0;
        
        // start with epsilon closure of start state
        Set<State> startClosure = closure(getStartState());
        stateMap.put(startClosure, nextStateId);
        
        // check if any state in start closure is accepting
        boolean isStartAccepting = startClosure.stream()
            .anyMatch(s -> getAcceptStates().contains(s));
        
        dfa.addState(nextStateId, true, isStartAccepting);
        unmarked.add(startClosure);
        nextStateId++;
        
        // process each unmarked state
        while (!unmarked.isEmpty()) {
            Set<State> currentSet = unmarked.poll();
            int currentId = stateMap.get(currentSet);
            
            // for each symbol in alphabet
            for (char symbol : getAlphabet()) {
                Set<State> nextSet = new HashSet<>();
                
                // compute next states for this symbol
                for (State state : currentSet) {
                    nextSet.addAll(next(state, symbol));
                }
                
                if (!nextSet.isEmpty()) {
                    // check if this state set already exists
                    if (!stateMap.containsKey(nextSet)) {
                        stateMap.put(nextSet, nextStateId);
                        
                        // check if any state in set is accepting
                        boolean isAccepting = nextSet.stream()
                            .anyMatch(s -> getAcceptStates().contains(s));
                        
                        dfa.addState(nextStateId, false, isAccepting);
                        unmarked.add(nextSet);
                        nextStateId++;
                    }
                    
                    // add transition in DFA
                    int toId = stateMap.get(nextSet);
                    dfa.addTransition(currentId, toId, symbol);
                }
            }
        }
        
        return dfa;
    }
}