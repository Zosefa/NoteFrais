package com.example.projet.DTO;

import java.util.Random;

public class VisaCardValidator {
    public static String generateVisaCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append("4");

        Random random = new Random();
        for (int i = 0; i < 14; i++) {
            cardNumber.append(random.nextInt(10));
        }

        String visaNumberWithCheckDigit = applyLuhnAlgorithm(cardNumber.toString());

        return visaNumberWithCheckDigit;
    }

    private static String applyLuhnAlgorithm(String cardNumberWithoutCheckDigit) {
        int total = 0;
        boolean isSecond = false;

        for (int i = cardNumberWithoutCheckDigit.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumberWithoutCheckDigit.charAt(i));

            if (isSecond) {
                digit = digit * 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            total += digit;
            isSecond = !isSecond;
        }

        int checkDigit = (10 - (total % 10)) % 10;

        return cardNumberWithoutCheckDigit + checkDigit;
    }

}
