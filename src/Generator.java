import java.util.Scanner;

public class Generator {
    Alphabet alphabet;
    public static Scanner keyboard;

    public Generator(Scanner scanner) {
        keyboard = scanner;
    }

    public Generator(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        alphabet = new Alphabet(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    public void mainLoop() {
        System.out.println("Welcome to Ziz Password Services :)");
        printMenu();

        String userOption = "-1";

        while (!userOption.equals("4")) {

            userOption = keyboard.next();

            switch (userOption) {
                case "1" -> {
                    requestPassword();
                    printMenu();
                }
                case "2" -> {
                    checkPassword();
                    printMenu();
                }
                case "3" -> {
                    printUsefulInfo();
                    printMenu();
                }
                case "4" -> printQuitMessage();
                default -> {
                    System.out.println();
                    System.out.println("Kindly select one of the available commands");
                    printMenu();
                }
            }
        }
    }

    private Password generatePassword(int length) {
        final StringBuilder pass = new StringBuilder();

        final int alphabetLength = alphabet.getAlphabet().length();

        int max = alphabetLength - 1;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * range) + min;
            pass.append(alphabet.getAlphabet().charAt(index));
        }

        return new Password(pass.toString());
    }

    private void printUsefulInfo() {
        System.out.println();
        System.out.println("Use a minimum password length of 8 or more characters if permitted");
        System.out.println("Include lowercase and uppercase alphabetic characters, numbers and symbols if permitted");
        System.out.println("Generate passwords randomly where feasible");
        System.out.println("Avoid using the same password twice (e.g., across multiple user accounts and/or software systems)");
        System.out.println("Avoid character repetition, keyboard patterns, dictionary words, letter or number sequences," +
                "\nusernames, relative or pet names, romantic links (current or past) " +
                "and biographical information (e.g., ID numbers, ancestors' names or dates).");
        System.out.println("Avoid using information that the user's colleagues and/or " +
                "acquaintances might know to be associated with the user");
        System.out.println("Do not use passwords which consist wholly of any simple combination of the aforementioned weak components");
    }

    private void requestPassword() {
        boolean IncludeUpper = false;
        boolean IncludeLower = false;
        boolean IncludeNum = false;
        boolean IncludeSym = false;

        boolean correctParams;

        System.out.println();
        System.out.println("Hello, welcome to the Password Generator :) answer"
                + " the following questions by Yes (or yes, or Y, or y) or No (or no, or N, or n)");

        do {
            String input;
            correctParams = false;

            do {
                System.out.println("Do you want Lowercase letters \"abcd...\" to be used? ");
                input = keyboard.next();
                passwordRequestError(input);
            } while (isBadInput(input));

            if (isInclude(input)) IncludeLower = true;

            do {
                System.out.println("Do you want Uppercase letters \"ABCD...\" to be used? ");
                input = keyboard.next();
                passwordRequestError(input);
            } while (isBadInput(input));

            if (isInclude(input)) IncludeUpper = true;

            do {
            System.out.println("Do you want Numbers \"1234...\" to be used? ");
            input = keyboard.next();
            passwordRequestError(input);
            } while (isBadInput(input));

            if (isInclude(input)) IncludeNum = true;

            do {
            System.out.println("Do you want Symbols \"!@#$...\" to be used? ");
            input = keyboard.next();
            passwordRequestError(input);
            } while (isBadInput(input));

            if (isInclude(input)) IncludeSym = true;

            //No Pool Selected
            if (!IncludeUpper && !IncludeLower && !IncludeNum && !IncludeSym) {
                System.out.println("You have selected no characters to generate your " +
                        "password, at least one of your answers should be Yes");
                correctParams = true;
            }

        } while (correctParams);

        System.out.println("Great! Now enter the length of the password:");
        int length = -1;

        while (length < 0) {
            String inputString = keyboard.next();
            try {
                int input = Integer.parseInt(inputString);
                if (input > 0) {
                    length = input;
                }
                else {
                    System.out.println("Length cannot be less than or equal to 0, let's go over it again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered something incorrect let's go over it again.");
            }
        }

        final Generator generator = new Generator(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
        final Password password = generator.generatePassword(length);

        System.err.println("Your generated password -> " + password);
    }

    private boolean isInclude(String Input) {
        return Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y");
    }

    private void passwordRequestError(String input) {
        if (isBadInput(input)) {
            System.out.println("You have entered something incorrect let's go over it again.");
        }
    }

    private boolean isBadInput(String input) {
        return !input.equalsIgnoreCase("yes")
                && !input.equalsIgnoreCase("y")
                && !input.equalsIgnoreCase("no")
                && !input.equalsIgnoreCase("n");
    }

    private void checkPassword() {
        String input;

        System.out.println("Enter your password:");
        input = keyboard.next();

        final Password p = new Password(input);

        System.out.println(p.calculateScore());
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Enter 1 - Password Generator");
        System.out.println("Enter 2 - Password Strength Check");
        System.out.println("Enter 3 - Useful Information");
        System.out.println("Enter 4 - Quit");
        System.out.println("Choice:");
    }

    private void printQuitMessage() {
        System.out.println("Closing the program bye bye!");
    }
}
