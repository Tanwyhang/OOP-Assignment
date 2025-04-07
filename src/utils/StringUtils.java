package utils;

public class StringUtils {
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private static final char HORIZONTAL = '─';
    private static final char VERTICAL = '│';
    private static final char TOP_LEFT = '┌';
    private static final char TOP_RIGHT = '┐';
    private static final char BOTTOM_LEFT = '└';
    private static final char BOTTOM_RIGHT = '┘';
    
    public enum BorderColor {
        BLUE, RED
    }

    public static String beautify(String input) {
        return beautify(input, BorderColor.BLUE);
    }
    
    public static String beautify(String input, BorderColor color) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        
        String colorCode = switch (color) {
            case RED -> RED;
            case BLUE -> BLUE;
        };

        String[] lines = input.split("\n");
        int maxLength = getMaxLength(lines);
        int padding = 2;
        
        StringBuilder result = new StringBuilder();
        
        // Top border
        result.append(colorCode).append(TOP_LEFT).append(RESET)
              .append(repeatChar(HORIZONTAL, maxLength + padding * 2))
              .append(colorCode).append(TOP_RIGHT).append(RESET).append("\n");
        
        // Content with padding
        for (String line : lines) {
            result.append(VERTICAL)
                  .append(" ".repeat(padding))
                  .append(line)
                  .append(" ".repeat(maxLength - line.length()))
                  .append(" ".repeat(padding))
                  .append(VERTICAL)
                  .append("\n");
        }
        
        // Bottom border
        result.append(colorCode).append(BOTTOM_LEFT).append(RESET)
              .append(repeatChar(HORIZONTAL, maxLength + padding * 2))
              .append(colorCode).append(BOTTOM_RIGHT).append(RESET);
        
        return result.toString();
    }
    
    private static int getMaxLength(String[] lines) {
        int maxLength = 0;
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }
        return maxLength;
    }
    
    private static String repeatChar(char c, int count) {
        return String.valueOf(c).repeat(count);
    }
}
