/*
* Represents a single state in finite state automata.
* Each state has unique ID, and flags for start/accept states.
*/

public class State {
    private int id; // Unique ID for state
    private boolean isStart; // True if start state
    private boolean isAccept; // True if accept state

    /*
    *Contructs new state with given ID, start, and accept status.
     */
    public State (int id, boolean isStart, boolean isAccept) {
        this.id = id;
        this.isStart = isStart;
        this.isAccept = isAccept;
    }

    // gets unique ID of state
    public int getId() {
        return id;
    }

    // checks if state is start state
    public boolean isStart() {
        return isStart;
    }

    // checks if state is accept state
    public boolean isAccept() {
        return isAccept;
    }

    /*
     * Overrides equals to compare states by ID only.
     * Compares two state with another object for equality based on ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // same object
        if (obj == null || getClass() != obj.getClass()) return false; // null or different class
        State state = (State) obj; // change to State
        return id == state.id; // equal if IDs match
    }

    /*
     * Generates hash code based on state ID.
     * Required when overriding equals() for further use in hash-based collections.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /*
     * Provides string representation of state.
     * Includes ID and indicates if start/accept state.
     */
    @Override
    public String toString() {
        return "State " + id + 
               (isStart ? " (START)" : "") + 
               (isAccept ? " (ACCEPT)" : "");
    }
}
