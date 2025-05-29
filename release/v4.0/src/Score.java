public class Score implements Comparable<Score> {
    private String name;
    private int attempts;

    public Score(String name, int attempts) {
        this.name = name;
        this.attempts = attempts;
    }

    public String getName() { return name; }
    public int getAttempts() { return attempts; }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.attempts, other.attempts); // menor tentativa é melhor
    }

    @Override
    public String toString() {
        return name + "," + attempts;
    }

    public static Score fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato inválido: " + line);
        }
        try {
            int attempts = Integer.parseInt(parts[1]);
            return new Score(parts[0], attempts);
        } catch (NumberFormatException e) {
            throw e; 
        }
    }
}
