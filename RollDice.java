import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollDice {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("No input file has been provided.");
            return;
        }

        String inputFilePath = args[0];

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                int totalRollResult = 0;
                int minPossibleResult = 0;
                int maxPossibleResult = 0;

                line = line.replaceAll("\\s+", "");

                Pattern pattern = Pattern.compile("([+-]?\\d*[dD]\\d+)|([+-]?\\d+)");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String match = matcher.group();

                    if (match.contains("d") || match.contains("D")) {

                        if (match.startsWith("+d") || match.startsWith("+D")) {
                            match = match.replace("+", "1");
                        }

                        if (match.startsWith("-d") || match.startsWith("-D")) {
                            match = match.replace("-", "-1");
                        }

                        if (match.startsWith("d") || match.startsWith("D")) {
                            match = match.replace("d", "1d");
                        }

                        totalRollResult += calculateRollResult(match);
                        minPossibleResult += calculateMinResult(match);
                        maxPossibleResult += calculateMaxResult(match);
                    } else {
                        int flatValue = Integer.parseInt(match);
                        minPossibleResult += flatValue;
                        maxPossibleResult += flatValue;
                    }
                }

                System.out.println("Roll result: " + totalRollResult);
                System.out.println("Min result: " + minPossibleResult);
                System.out.println("Max result: " + maxPossibleResult);
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int calculateMinResult(String diceNotation) {
        diceNotation = diceNotation.trim();
        String[] diceParts = diceNotation.split("[dD]");
        int numberOfDice = diceParts[0].isEmpty() ? 1 : Integer.parseInt(diceParts[0]);
        return numberOfDice;
    }

    private static int calculateMaxResult(String diceNotation) {
        diceNotation = diceNotation.trim();
        String[] diceParts = diceNotation.split("[dD]");
        int numberOfDice = diceParts[0].isEmpty() ? 1 : Integer.parseInt(diceParts[0]);
        int diceSides = Integer.parseInt(diceParts[1]);
        return numberOfDice * diceSides;
    }

    private static int calculateRollResult(String diceNotation) {
        String[] diceParts = diceNotation.split("[dD]");
        int numberOfDice = diceParts[0].isEmpty() ? 1 : Integer.parseInt(diceParts[0]);
        int diceSides = Integer.parseInt(diceParts[1]);

        if (numberOfDice < 0) {
            numberOfDice = -numberOfDice;
        }

        Random random = new Random();
        int rollResult = 0;

        for (int i = 0; i < numberOfDice; i++) {
            rollResult += random.nextInt(diceSides) + 1;
        }
        return rollResult;
    }
}
