import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String num1Str = parts[0];
        String operator = parts[1];
        String num2Str = parts[2];

        int num1;
        int num2;
        try {
            num1 = parseNumber(num1Str);
            num2 = parseNumber(num2Str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректные числа");
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10");
        }
        
        if (isRomanNumeral(input) && isArabicNumeral(input)) {
         throw new IllegalArgumentException("Некорректный ввод: одновременно указаны римские и арабские цифры");
        }
        
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Некорректный оператор");
        }

        if (isRomanNumeral(input)) {
            return toRomanNumeral(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static int parseNumber(String numberStr) {
        if (isRomanNumber(numberStr)) {
            return parseRomanNumber(numberStr);
        } else {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное выражение");
            }
        }
    }

    private static boolean isRomanNumber(String numberStr) {
        return numberStr.matches("[IVXLCDM]+");
    }

    private static int parseRomanNumber(String numberStr) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = numberStr.length() - 1; i >= 0; i--) {
            char currentChar = numberStr.charAt(i);
            int currentValue = romanNumerals.get(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            }
            else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static boolean isArabicNumeral(String input) {
        return input.matches(".*\\d+.*");
    }
    
    private static boolean isRomanNumeral(String input) {
        return input.contains("I") || input.contains("V") || input.contains("X");
    }

    private static String toRomanNumeral(int number) {
        if (number < 1 || number > 100) {
            throw new IllegalArgumentException("Число должно быть от 1 до 100");
        }

        StringBuilder romanNumeral = new StringBuilder();
        int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int remaining = number;
        for (int i = 0; i < arabicValues.length; i++) {
            while (remaining >= arabicValues[i]) {
                romanNumeral.append(romanSymbols[i]);
                remaining -= arabicValues[i];
            }
        }

        return romanNumeral.toString();
    }
}
