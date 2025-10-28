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
}