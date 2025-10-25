public class State {
    private int id;
    private boolean isStart;
    private boolean isAccept;

    public State (int id, boolean isStart, boolean isAccept) {
        this.id = id;
        this.isStart = isStart;
        this.isAccept = isAccept;
    }

    public int getId() {
        return id;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isAccept() {
        return isAccept;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return id == state.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

     @Override
    public String toString() {
        return "State " + id + 
               (isStart ? " (START)" : "") + 
               (isAccept ? " (ACCEPT)" : "");
    }
}
