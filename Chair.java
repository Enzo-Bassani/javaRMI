public class Chair {
    private boolean available;

    public Chair() {
		available = true;
	}

	public synchronized void reserve() {
		available = false;
	}

	public synchronized void free() {
		available = true;	
	}

    public boolean isAvailable() {
        return available;
    }

}
