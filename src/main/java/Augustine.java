import java.util.Scanner;

public class Augustine {
    public static void main(String[] args) {
        Scanner line = new Scanner (System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Augustine");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true){
            String input = line.nextLine();

            if (input.equalsIgnoreCase("bye")){
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(" " + input);
            System.out.println("____________________________________________________________");
        }

        line.close();
    }
}

