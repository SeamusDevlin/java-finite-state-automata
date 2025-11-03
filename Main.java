/**
 * Main class to test FSA, NFA, DFA functionality & implementations.
 * Demonstrate functionality of finite state automata:
 * 1. Build an NFA that recognizes (a|b)*abb
 * 2. Tests methods: addState, addTransition, closure, next, deterministic, and accepts.
 * 3. Convert NFA to DFA and verify equivalence
 * 4. Build and test a standalone DFA
 */
public class Main {
    // entry point of program, runs tests on NFA and DFA
    public static void main(String[] args) {
        System.out.println("=== Testing NFA for (a|b)*abb ===");
        testNFA();
        
        System.out.println("\n=== Testing NFA to DFA Conversion ===");
        testNFAtoDFA();
        
        System.out.println("\n=== Testing Standalone DFA ===");
        testDFA();
    }
    
    /*
    * Tests NFA implementation:
    * - Builds NFA for (a|b)*abb
    * - Tests closure, next, deterministic, and accepts methods
    * - Verifies correct behavior
    */
    private static void testNFA() {
        // Build the NFA for (a|b)*abb
        NFA nfa = new NFA();
        
        // add states (0-10)
        for (int i = 0; i <= 10; i++) {
            nfa.addState(i, i == 0, i == 10);
        }
        
        // Add transitions using epsilon symbol
        nfa.addTransition(0, 1, FSA.EPSILON);
        nfa.addTransition(0, 7, FSA.EPSILON);
        nfa.addTransition(1, 2, FSA.EPSILON);
        nfa.addTransition(1, 4, FSA.EPSILON);
        nfa.addTransition(2, 3, 'a');
        nfa.addTransition(4, 5, 'b');
        nfa.addTransition(3, 6, FSA.EPSILON);
        nfa.addTransition(5, 6, FSA.EPSILON);
        nfa.addTransition(6, 1, FSA.EPSILON);
        nfa.addTransition(6, 7, FSA.EPSILON);
        nfa.addTransition(7, 8, 'a');
        nfa.addTransition(8, 9, 'b');
        nfa.addTransition(9, 10, 'b');
        
        System.out.println("NFA constructed with " + nfa.getStates().size() + " states");
        System.out.println("Alphabet: " + nfa.getAlphabet());
        
        // test closure - state 3 should reach {3,6,7,1,2,4}
        State state3 = nfa.getStateById(3);
        System.out.println("\nTest closure(3):");
        System.out.println("Expected: {1,2,3,4,6,7}");
        System.out.println("Actual:   " + getStateIds(nfa.closure(state3)));
        
        // test next - state 4 on 'b' should reach {1,2,4,5,6,7}
        State state4 = nfa.getStateById(4);
        System.out.println("\nTest next(4, 'b'):");
        System.out.println("Expected: {1,2,4,5,6,7}");
        System.out.println("Actual:   " + getStateIds(nfa.next(state4, 'b')));
        
        // test next - state 5 on 'a' should reach {1,2,3,4,6,7,8}
        State state5 = nfa.getStateById(5);
        System.out.println("\nTest next(5, 'a'):");
        System.out.println("Expected: {1,2,3,4,6,7,8}");
        System.out.println("Actual:   " + getStateIds(nfa.next(state5, 'a')));
        
        // test deterministic - should be false
        System.out.println("\nTest deterministic():");
        System.out.println("Expected: false");
        System.out.println("Actual:   " + nfa.deterministic());
        
        // test accepts
        System.out.println("\nTest accepts():");
        testAccepts(nfa, "abb", true);
        testAccepts(nfa, "aabb", true);
        testAccepts(nfa, "babb", true);
        testAccepts(nfa, "aaabb", true);
        testAccepts(nfa, "bbbabb", true);
        testAccepts(nfa, "ab", false);
        testAccepts(nfa, "abba", false);
        testAccepts(nfa, "a", false);
        testAccepts(nfa, "", false);
    }
    
    // tests NFA to DFA conversion
    private static void testNFAtoDFA() {
        // build the same NFA
        NFA nfa = new NFA();
        
        for (int i = 0; i <= 10; i++) {
            nfa.addState(i, i == 0, i == 10);
        }
        
        nfa.addTransition(0, 1, FSA.EPSILON);
        nfa.addTransition(0, 7, FSA.EPSILON);
        nfa.addTransition(1, 2, FSA.EPSILON);
        nfa.addTransition(1, 4, FSA.EPSILON);
        nfa.addTransition(2, 3, 'a');
        nfa.addTransition(4, 5, 'b');
        nfa.addTransition(3, 6, FSA.EPSILON);
        nfa.addTransition(5, 6, FSA.EPSILON);
        nfa.addTransition(6, 1, FSA.EPSILON);
        nfa.addTransition(6, 7, FSA.EPSILON);
        nfa.addTransition(7, 8, 'a');
        nfa.addTransition(8, 9, 'b');
        nfa.addTransition(9, 10, 'b');
        
        // convert to DFA
        System.out.println("Converting NFA to DFA...");
        DFA dfa = nfa.toDFA();
        
        System.out.println("DFA created with " + dfa.getStates().size() + " states");
        System.out.println("Number of transitions: " + dfa.getTransitions().size());
        
        // test that DFA is deterministic
        System.out.println("\nTest DFA deterministic():");
        System.out.println("Expected: true");
        System.out.println("Actual:   " + dfa.deterministic());
        
        // test that DFA accepts same strings as NFA
        System.out.println("\nTest DFA accepts same strings as NFA:");
        testAccepts(dfa, "abb", true);
        testAccepts(dfa, "aabb", true);
        testAccepts(dfa, "babb", true);
        testAccepts(dfa, "aaabb", true);
        testAccepts(dfa, "bbbabb", true);
        testAccepts(dfa, "ab", false);
        testAccepts(dfa, "abba", false);
        testAccepts(dfa, "a", false);
        testAccepts(dfa, "", false);
    }
    
    // tests DFA implementation
    private static void testDFA() {
        DFA dfa = new DFA();
        
        // simple DFA that accepts strings with at least one 'a'
        dfa.addState(0, true, false);
        dfa.addState(1, false, true);
        
        dfa.addTransition(0, 1, 'a');
        dfa.addTransition(1, 1, 'a');
        
        System.out.println("Simple DFA constructed");
        System.out.println("Is deterministic? " + dfa.deterministic());
        
        System.out.println("\nTest accepts:");
        testAccepts(dfa, "a", true);
        testAccepts(dfa, "aa", true);
        testAccepts(dfa, "aaa", true);
        testAccepts(dfa, "", false);
        testAccepts(dfa, "b", false);
    }
    
    // helper method to test accepts and print result
    private static void testAccepts(FSA fsa, String input, boolean expected) {
        boolean actual = fsa.accepts(input);
        String result = (actual == expected) ? "✓" : "✗";
        System.out.println(result + " '" + input + "' -> " + actual + " (expected: " + expected + ")");
    }
    
    // helper method to get state IDs from set of states
    private static java.util.Set<Integer> getStateIds(java.util.Set<State> states) {
        java.util.Set<Integer> ids = new java.util.TreeSet<>();
        for (State s : states) {
            ids.add(s.getId());
        }
        return ids;
    }
}