package com.mycompany;


public class Diamond
{
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXZ";
    private static String SEPARATOR_DEFAULT = " ";
    private static String ALTERNATIVE_SEPARATOR = "_";

    private String separator = SEPARATOR_DEFAULT;

    public static void main( String[] args ) throws Exception {
        Diamond diamond = new Diamond();

        if (args.length == 1) {
            System.out.print(diamond.generateDiamond(args[0].toUpperCase()));
        } else if (args.length == 2) {
            System.out.print(diamond.generateDiamond(args[0], args[1]));
        } else {
            throw new Exception("Only 2 arguments allowed [input separator]");
        }
    }

    private String generateDiamond(String input, String separator) throws Exception {
        this.setSeparator(separator);
        return generateDiamond(input);
    }

    private void setSeparator(String separator) throws Exception {
        if (!ALTERNATIVE_SEPARATOR.equals(separator)) {
            throw new Exception("Alternative Separator acceptable is '_'");
        } else {
            this.separator = separator;
        }
    }

    private String generateDiamond(String input) throws Exception {
        if (ALPHABET.contains(input) && input.length() == 1) {
            StringBuilder str = new StringBuilder();
            int indexOf = ALPHABET.indexOf(input);
            int rows = (indexOf) * 2 +1;
            int currentChar = 0;
            boolean down = false;
            for (int i = 0; i < rows; i++) {
                process(str, currentChar, rows);

                if ((currentChar + 1) > indexOf || down) {
                    down = true;
                    currentChar--;
                } else {
                    currentChar++;
                }
                if ((i + 1) < rows) {
                    str.append("\n");
                }
            }

            return str.toString();
        } else {
            throw new Exception("Input must be on character between A and Z");
        }
    }

    private void process(StringBuilder str, int currentIndex, int rows) {
        int sequence = rows / 2;
        boolean up = false;
        for (int i = 0; i < rows; i++) {
            if (sequence != currentIndex) {
                str.append(this.separator);
            } else {
                str.append(ALPHABET.charAt(currentIndex));
            }

            if (sequence == 0 || up) {
                up = true;
                sequence++;
            } else {
                sequence--;
            }
        }
    }
}
