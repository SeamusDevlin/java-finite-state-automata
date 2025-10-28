/**
 * Main class to test FSA, NFA, DFA functionality & implementations.
 * Demonstrate functionality of finite state automata:
 * 1. Build an NFA that recognizes (a|b)*abb
 * 2. Tests methods: addState, addTransition, closure, next, deterministic, and accepts.
 * 3. Build DFA and tests its operations.
 * 4. Verifies the automata
 */
public class Main {
    // Entry point of program, runs tests on NFA and DFA
    public static void main(String[] args) {
        System.out.println("=== Testing NFA ===");
        testNFA();
        
        System.out.println("\n=== Testing DFA ===");
        testDFA();
    }
    
    /*
    * Tests NFA implementation:
    * - Builds NFA for (a|b)*abb
    * - Tests closure, next, deterministic, and accepts methods
    * - Verifies correct behavior
    * - Checks if NFA is deterministic
    * - Tests acceptance of various strings
    */
    private static void testNFA() {
        // Build the NFA
        NFA nfa = new NFA();
        
        // Adds states (0-10)
        for (int i = 0; i <= 10; i++) {
            nfa.addState(i, i == 0, i == 10);
        }
        
        // Add transitions
        nfa.addTransition(0, 1, '\0');
        nfa.addTransition(0, 7, '\0');
        nfa.addTransition(1, 2, '\0');
        nfa.addTransition(1, 4, '\0');
        nfa.addTransition(2, 3, 'a');
        nfa.addTransition(4, 5, 'b');
        nfa.addTransition(3, 6, '\0');
        nfa.addTransition(5, 6, '\0');
        nfa.addTransition(6, 1, '\0');
        nfa.addTransition(6, 7, '\0');
        nfa.addTransition(7, 8, 'a');
        nfa.addTransition(8, 9, 'b');
        nfa.addTransition(9, 10, 'b');
        
        // Test closure
        State state3 = nfa.getStateById(3);
        System.out.println("Closure of state 3: " + nfa.closure(state3));
        
        // Test next
        State state4 = nfa.getStateById(4);
        System.out.println("Next from state 4 on 'b': " + nfa.next(state4, 'b'));
        
        State state5 = nfa.getStateById(5);
        System.out.println("Next from state 5 on 'a': " + nfa.next(state5, 'a'));
        
        // Test deterministic
        System.out.println("Is NFA deterministic? " + nfa.deterministic());
        
        // Test accepts
        System.out.println("Accepts 'abb': " + nfa.accepts("abb"));
        System.out.println("Accepts 'aabb': " + nfa.accepts("aabb"));
        System.out.println("Accepts 'babb': " + nfa.accepts("babb"));
        System.out.println("Accepts 'ab': " + nfa.accepts("ab"));
    }
    
    /**
     * Tests DFA implementation:
     * - Builds a simple DFA
     * - Tests deterministic and accepts methods
     * - Verifies correct behavior
     */
    private static void testDFA() {
        DFA dfa = new DFA();
        
        // Simple DFA for testing
        dfa.addState(0, true, false);
        dfa.addState(1, false, true);
        
        dfa.addTransition(0, 1, 'a');
        dfa.addTransition(1, 1, 'a');
        
        System.out.println("Is DFA deterministic? " + dfa.deterministic());
        System.out.println("Accepts 'a': " + dfa.accepts("a"));
        System.out.println("Accepts 'aa': " + dfa.accepts("aa"));
        System.out.println("Accepts 'b': " + dfa.accepts("b"));
    }
}