package com.omar.fbank.customer;

public class NifValidator {
    public static boolean isValidNIF(String nif) {
        if (nif == null) return false;

        nif = nif.toUpperCase();

        // DNI: 8 digits + letter
        if (nif.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
            int number = Integer.parseInt(nif.substring(0, 8));
            char expectedLetter = letters.charAt(number % 23);
            return expectedLetter == nif.charAt(8);
        }

        // NIE: letter (X/Y/Z) + 7 digits + letter
        if (nif.matches("[XYZ]\\d{7}[A-HJ-NP-TV-Z]")) {
            String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
            char firstChar = nif.charAt(0);
            String numberStr;

            switch (firstChar) {
                case 'X' -> numberStr = "0" + nif.substring(1, 8);
                case 'Y' -> numberStr = "1" + nif.substring(1, 8);
                case 'Z' -> numberStr = "2" + nif.substring(1, 8);
                default -> {
                    return false;
                }
            }

            int number = Integer.parseInt(numberStr);
            char expectedLetter = letters.charAt(number % 23);
            return expectedLetter == nif.charAt(8);
        }

        return false;
    }
}
