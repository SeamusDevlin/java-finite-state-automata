/**
 * Test class for the State class.
 * Verifies constructor, getters, equals, hashCode, and toString functionality.
 */
public class StateTest {
    
    /**
     * Main method that runs all test methods.
     * If all assertions pass, prints success message.
     */
    public static void main(String[] args) {
        testConstructorAndGetters();
        testAcceptState();
        testEquals();
        testHashCode();
        testToString();
        System.out.println("All tests passed!");
    }
    
    /**
     * Tests the State constructor and getter methods.
     * Verifies that a State can be created and its properties retrieved correctly.
     */
    public static void testConstructorAndGetters() {
        // Create a state with ID 1, that is a start state but not an accept state
        State state = new State(1, true, false);
        // Verify the ID is 1
        assert state.getId() == 1;
        // Verify it is a start state
        assert state.isStart();
        // Verify it is not an accept state
        assert !state.isAccept();
    }
    
    /**
     * Tests creating an accept state.
     * Verifies that a State can be configured as an accept state.
     */
    public static void testAcceptState() {
        // Create a state with ID 2, that is not a start state but is an accept state
        State state = new State(2, false, true);
        // Verify the ID is 2
        assert state.getId() == 2;
        // Verify it is not a start state
        assert !state.isStart();
        // Verify it is an accept state
        assert state.isAccept();
    }
    
    /**
     * Tests the equals method.
     * Verifies that States are equal based on ID only, regardless of start/accept status.
     */
    public static void testEquals() {
        // Create three states with different IDs and properties
        State state1 = new State(1, true, false);
        State state2 = new State(1, false, true);
        State state3 = new State(2, true, false);
        
        // Verify states with same ID are equal (even if start/accept differ)
        assert state1.equals(state2); // Same ID
        // Verify states with different IDs are not equal
        assert !state1.equals(state3); // Different ID
        // Verify a state equals itself (reflexive property)
        assert state1.equals(state1); // Same object
        // Verify a state does not equal null
        assert !state1.equals(null); // Null comparison
        // Verify a state does not equal an object of a different class
        assert !state1.equals("not a state"); // Different class
    }
    
    /**
     * Tests the hashCode method.
     * Verifies that States with the same ID have the same hashCode.
     */
    public static void testHashCode() {
        // Create three states
        State state1 = new State(1, true, false);
        State state2 = new State(1, false, true);
        State state3 = new State(2, true, false);
        
        // Verify states with same ID have same hashCode (required for equals/hashCode contract)
        assert state1.hashCode() == state2.hashCode(); // Same ID
        // Verify states with different IDs have different hashCodes (best practice)
        assert state1.hashCode() != state3.hashCode(); // Different ID
    }
    
    /**
     * Tests the toString method.
     * Verifies that the string representation includes the state ID and appropriate labels.
     */
    public static void testToString() {
        // Create a start state and verify its string representation
        State startState = new State(0, true, false);
        assert startState.toString().contains("State 0"); // Contains state ID
        assert startState.toString().contains("START");    // Contains START label
        
        // Create an accept state and verify its string representation
        State acceptState = new State(1, false, true);
        assert acceptState.toString().contains("State 1");  // Contains state ID
        assert acceptState.toString().contains("ACCEPT");   // Contains ACCEPT label
        
        // Create a state that is both start and accept, verify both labels appear
        State startAndAccept = new State(2, true, true);
        assert startAndAccept.toString().contains("START");  // Contains START label
        assert startAndAccept.toString().contains("ACCEPT"); // Contains ACCEPT label
        
        // Create a normal state (neither start nor accept) and verify no labels
        State normalState = new State(3, false, false);
        assert !normalState.toString().contains("START");   // Does not contain START
        assert !normalState.toString().contains("ACCEPT");  // Does not contain ACCEPT
    }
}