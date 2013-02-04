import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * User: jtempleton
 */
public class Converter {

    final static protected HashMap<Integer, String> numbersAsWordsOnes = new HashMap<Integer, String>();
    final static protected HashMap<Integer, String> numbersAsWordsTeens = new HashMap<Integer, String>();
    final static protected HashMap<Integer, String> numbersAsWordsTens = new HashMap<Integer, String>();

    static String[] numberNames = { "", "thousand", "million", "billion", "trillion"};

    public Converter() {
        init();
    }

    protected void init() {
        // Ones
        numbersAsWordsOnes.put(1, "one");
        numbersAsWordsOnes.put(2, "two");
        numbersAsWordsOnes.put(3, "three");
        numbersAsWordsOnes.put(4, "four");
        numbersAsWordsOnes.put(5, "five");
        numbersAsWordsOnes.put(6, "six");
        numbersAsWordsOnes.put(7, "seven");
        numbersAsWordsOnes.put(8, "eight");
        numbersAsWordsOnes.put(9, "nine");

        // Teens
        numbersAsWordsTeens.put(10, "ten");
        numbersAsWordsTeens.put(11, "eleven");
        numbersAsWordsTeens.put(12, "twelve");
        numbersAsWordsTeens.put(13, "thirteen");
        numbersAsWordsTeens.put(14, "fourteen");
        numbersAsWordsTeens.put(15, "fifteen");
        numbersAsWordsTeens.put(16, "sixteen");
        numbersAsWordsTeens.put(17, "seventeen");
        numbersAsWordsTeens.put(18, "eighteen");
        numbersAsWordsTeens.put(19, "nineteen");

        // Tens
        //numbersAsWordsTens.put(1, "ten");
        numbersAsWordsTens.put(2, "twenty");
        numbersAsWordsTens.put(3, "thirty");
        numbersAsWordsTens.put(4, "fourty");
        numbersAsWordsTens.put(5, "fifty");
        numbersAsWordsTens.put(6, "sixty");
        numbersAsWordsTens.put(7, "seventy");
        numbersAsWordsTens.put(8, "eighty");
        numbersAsWordsTens.put(9, "ninety");
    }

    //
    //
    //         Converter Methods
    //
    //
    protected String convertTeens(final String numberAsString) {
        Integer num = new Integer(numberAsString);
        if (num == 0) return "";
        if (num < 10)
            return numbersAsWordsOnes.get(num);

        return numbersAsWordsTeens.get(num);
    }

    protected String convertTens(final String numberAsString) {
        Integer num = Integer.parseInt(numberAsString);
        if (num < 20)
            return convertTeens(numberAsString);

        Integer i = Integer.parseInt(numberAsString.substring(0, 1));
        String wholeWord = numbersAsWordsTens.get(i);
        i = Integer.parseInt("" + numberAsString.charAt(1));
        if (i > 0) {
            wholeWord = wholeWord + "-" + numbersAsWordsOnes.get(i);
        }
        return wholeWord;
    }

    protected String convertHundreds(final String numberAsString) {
        Integer i = Integer.parseInt(numberAsString.substring(0, 1));
        String wholeWord = "";
        if (i > 0) {
            wholeWord = numbersAsWordsOnes.get(i) + " hundred";
        }
        String tens = numberAsString.substring(1);
        String word = convertTens(tens);
        return conditionalConcat(wholeWord, word);
    }


    /***************************************
     *
     *    9123456654 <-- this as input
     *
     *    Breaks it up by 3's, from right to left
     *    9 123 456 654
     *
     *
     * @param numberAsString
     * @return
     ***************************************/
    protected String convertBigNumber(String numberAsString) {
        String wholeWord = "";

        while (numberAsString.length() % 3 != 0) {
            numberAsString = "0" + numberAsString;
        }
        int count = numberAsString.length() / 3;
        for (int i = 0; i < count; i++) {
            String place = numberNames[count-i-1];
            String word = convertHundreds(numberAsString.substring(i*3, i*3+ 3));
            if (word.length() > 0) {
               wholeWord = wholeWord + " " +  word + " " + place;
            }
        }
        return wholeWord.trim();
    }

    //
    //
    //         Helper Methods
    //
    //
    protected String conditionalConcat(final String wholeWord, final String word) {
        String retVal = wholeWord;
        if ((wholeWord.length() > 0) && (word.length() > 0))
            retVal = wholeWord + " " + word;
        else if ((word != null) && (word.length() > 0))
            retVal = word;
        return retVal;
    }

    protected void sanityCheck(String value) throws IllegalArgumentException{
        if ((value == null) || (value.length() <= 0)) {
            throw new IllegalArgumentException("No number was given");
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if ((c <= 45) || (c >= 58) || (c == 47)) {
                throw new IllegalArgumentException("Illegal Characters Provided");
            }
        }
        if (value.startsWith("0")) {
            throw new IllegalArgumentException("Cannot start with a zero");
        }
        String[] leftRight = value.split(".");
        // Is there more than 1 decimal?
        if (leftRight.length > 2) {
            throw new IllegalArgumentException("Decimal formatting incorrect");
        }
        int index = value.indexOf(".");
        if (index > 0) {
            if (value.substring(index+1).length() != 2) {
                throw new IllegalArgumentException("Decimal formatting incorrect");
            }
        }
    }

    protected Integer getLengthLeftOfDecimal(final String value) {
        int index = value.indexOf(".");
        if (index < 0) {
            return value.length();
        }
        return value.substring(0, index).length();
    }

    protected String convertDecimalPlace(final String value) {
        int index = value.indexOf(".");
        return value.substring(index+1) + "/100";
    }


    //
    //     Entry
    //
    public String convert(String value) {
        sanityCheck(value);
        value = value.trim();
        System.out.println(value);
        String wholeWord = "";

        int lengthLeftOfDec = getLengthLeftOfDecimal(value);
        final String leftOfDec = value.substring(0, lengthLeftOfDec);

        if (lengthLeftOfDec == 1) {
            wholeWord = numbersAsWordsOnes.get(Integer.parseInt(leftOfDec));
        } else if (lengthLeftOfDec == 2) {
            wholeWord = convertTens(leftOfDec);
        } else if (lengthLeftOfDec == 3) {
            wholeWord = convertHundreds(leftOfDec);
        } else {
            wholeWord = convertBigNumber(leftOfDec);
        }
/*
        else if (lengthLeftOfDec == 4) {
            wholeWord = convertThousands(leftOfDec);
        } else if (lengthLeftOfDec == 5) {
            wholeWord = convertTenThousands(leftOfDec);
        } else if (lengthLeftOfDec == 6) {
            wholeWord = convertHundredThousands(leftOfDec);
        } else if (lengthLeftOfDec == 7) {
            wholeWord = convertMillions(leftOfDec);
        } else {
            throw new IllegalArgumentException("Unsupported number");
        }
*/
        if (value.indexOf(".") >= 0) {
            wholeWord = wholeWord + " and " + convertDecimalPlace(value);
        }
        wholeWord += " dollars";
        return wholeWord.substring(0, 1).toUpperCase() + wholeWord.substring(1);
    }

    public static void main(String[] args) {

        Converter converter = new Converter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;

        while (true) {
            try {
                System.out.print("\n\nConvert (q to quit): ");
                input = br.readLine();
                if (input.toLowerCase().equals("q")) System.exit(0);
                System.out.println("\t" + converter.convert(input));
            } catch (IllegalArgumentException iae) {
                System.out.println("Error: Was not able to get your last input to convert.\n");
                System.out.println("Usage: #######.##");
            } catch (IOException ioe) {
                System.err.println("Was not able to get your last input to convert.");
                System.err.println("Exiting...");
                System.exit(1);
            }
        }
    }
}
