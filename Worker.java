package FinalProject;

public record Worker(String name, String role) implements OrgUnit {
    @Override
    public String toString(int i) {
        return "  ".repeat(i) + "Worker: " + this.name + " (" + this.role + ")";
    }
}
