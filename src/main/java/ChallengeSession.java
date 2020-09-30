import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;

class CreditCard {
    public static String maskify(String creditCardNumber) {

        // Input cannot be null. No null check required
        final int ccnlength = creditCardNumber.length();

        // Less than 6 chars ccn are not masked
        if (ccnlength < 6) {
            return creditCardNumber;
        }

        return
                // First character
                creditCardNumber.charAt(0)
                        // Masked part
                        + getMaskedPart(creditCardNumber, ccnlength)
                        // Last 4 character
                        + getLastFourChars(creditCardNumber, ccnlength);
    }


    private static String getLastFourChars(String creditCardNumber, int ccnlength) {
        return creditCardNumber.substring(ccnlength - 4);
    }

    private static String getMaskedPart(String creditCardNumber, int ccnlength) {
        return creditCardNumber
                // From 2nd to length minus last 4 chars
                .substring(1, ccnlength - 4)
                .replaceAll("[0-9]", "#");
    }
}

class Challenge {
    public static String numberToOrdinal(Integer number) {

        // Constraint check
        if (number == null || number < 0 || number > 10000) {
            throw new IllegalArgumentException();
        }

        if (number == 0) {
            return "0";
        }

        final String numberString = number.toString();

        // Teen exception rule
        if (number > 10) {
            final String lastTwoDigitsString = numberString.substring(numberString.length() - 2);

            final int lastTwoDigits = Integer.parseInt(lastTwoDigitsString);
            if (lastTwoDigits >= 11 && lastTwoDigits <= 13) {
                return number + "th";
            }
        }

        final char lastDigit = numberString.charAt(numberString.length() - 1);
        // Last digit check
        switch (lastDigit) {
            case '1':
                return numberString + "st";
            case '2':
                return numberString + "nd";
            case '3':
                return numberString + "rd";
            default:
                return numberString + "th";
        }
    }
}

class Calc {

    private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");


    public double evaluate(String expr) {

        final String[] elements = expr.split(" ");

        // Empty input
        if(elements.length == 1 && "".equals(elements[0])) {
            return 0;
        }

        // No operation
        if(!isOperator(elements[elements.length -1])) {
            return Double.parseDouble(elements[elements.length -1]);
        }

        String[] result;
        try {
            result = calculate(elements);
        } catch (ScriptException se) {
            throw new IllegalStateException("Calculation failed", se);
        }

        return Double.parseDouble(result[0]);
    }

    private String[] calculate(String[] elements) throws ScriptException {
        String secondOperand;
        String firstOperand;
        // We get the current operator (last element must be an operator. NOTE We go from right to left)
        String operator = elements[elements.length - 1];
        // if second to last element is an operator then recursively calculate on sub array (-1)
        if (isOperator(elements[elements.length - 2])) {
            // Recursive calculation
            elements = calculate(shrinkArrayBy(elements, 1));
            // After recursive calculation last and second to last elements are operators
            firstOperand = elements[elements.length - 2];
            secondOperand = elements[elements.length - 1];
            // Shrink array by one (only one because last spot will be taken by the calculation result)
            elements = shrinkArrayBy(elements, 1);
        } else {
            // Initialize the second operand (as, we checked, it's not an operator)
            secondOperand = elements[elements.length - 2];
            // and check if third to last element is an operator
            if (isOperator(elements[elements.length - 3])) {
                // If third to last element is an operator we calculate on sub array (-2)
                // On the shrunk array the last element is an operator
                elements = calculate(shrinkArrayBy(elements, 2));
                // Eventually we get our first operand on last spot
                firstOperand = elements[elements.length - 1];
            } else {
                // Initialize the first operand (third to last element is not an operator)
                firstOperand = elements[elements.length - 3];
                // Shrink array by two (1 for the operator, 1 for the operand, last spot will contain the result)
                elements = shrinkArrayBy(elements, 2);
            }
        }
        // Init the calculated value on the last spot of the shrunk array and return
        elements[elements.length - 1] = evaluate(firstOperand, operator, secondOperand).toString();
        return elements;
    }

    private String[] shrinkArrayBy(String[] elements, int numElementsToShrink) {
        return Arrays.copyOf(elements, elements.length - numElementsToShrink);
    }

    private Object evaluate(String firstOperand, String operator, String secondOperand) throws ScriptException {
        final String script = firstOperand + " " + operator + " " + secondOperand;
        return scriptEngine.eval(script);
    }

    private boolean isOperator(String element) {
        return Arrays.asList("+", "-", "*", "/").contains(element);
    }
}