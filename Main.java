package FinalProject;

public final class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start(new OrgManager(null));
    }
}