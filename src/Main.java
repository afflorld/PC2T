import components.*;
import core.*;

import java.util.*;

public class Main {

    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int identifier = 0;

    public static void main(String[] args) {
        String command = "";

        while (!command.equals("exit")) {
            System.out.println("\nCommands: add, collab, remove, find, skill, list, stats, count, save, load, loadsql, exit");
            System.out.print("Enter command: ");

            command = scanner.nextLine().toLowerCase().trim();

            switch (command) {
                case "add": identifier = Options.addEmployee(employees, scanner, identifier); break;
                case "collab": Options.addCollab(employees, scanner); break;
                case "remove": Options.remove(employees, scanner); break;
                case "find": Options.find(employees, scanner); break;
                case "skill": Options.skill(employees, scanner); break;
                case "list": Options.printAlpha(employees); break;
                case "stats": Options.printStats(employees); break;
                case "count": Options.printCounts(employees); break;
                case "save": Filer.saveToFile(employees); break;
                case "load": identifier = Filer.loadFromFile(employees); break;
                case "loadsql": break;
                case "exit": break;
                default: System.out.println("Invalid command."); break;
            }
        }
    }
}
