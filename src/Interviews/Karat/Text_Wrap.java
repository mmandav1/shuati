package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;

public class Text_Wrap {
    /**
     * The title has not been seen in the face-to-face script, but it is a text line wrapper with a given line spacing. The first question is to ensure the integrity of the word to wrap,
     * The second question is more troublesome, you need to add "-" between words first,
     * If there is a place, add "-", the last line is evenly distributed, and any number of "-" is added between words.
     * # We are building a word processor and we would like to implement a "word-wrap" functionality.
     *
     * # Given a maximum number of characters in a line followed by a list of words,
     * return a collection of strings where each string element represents a line that
     * contains as many words as possible, with the words in each line being concatenated
     * with a single '-' (representing a space, but easier to see for testing).
     * The length of each string must not exceed the maximum character length per line.
     *
     * # Your function should take in the maximum characters per line and return a data structure
     * representing all lines in the indicated max length.
     * # Note: built-in functions like Python textwrap module should not be used as solutions to this problem.
     *
     * # Examples:
     *
     * # words1 = [ "The", "day", "began", "as", "still", "as", "the", "night", "abruptly", "lighted", "with", "brilliant", "flame" ]
     * # wrapLines(words1, 13) ... "wrap words1 to line length 13" =>
     * #   [ "The-day-began", "as-still-as", "the-night", "abruptly", "lighted-with", "brilliant", "flame" ]
     *
     * # wrapLines(words1, 20) ... "wrap words1 to line length 20" =>
     * #   [ "The-day-began-as", "still-as-the-night", "abruptly-lighted", "with-brilliant-flame" ]
     *
     * # words2 = [ "Hello" ]
     * # wrapLines(words2, 5) ... "wrap words2 to line length 5" =>
     * #   [ "Hello" ]
     *
     * # words3 = [ "Hello", "world" ]
     * # wrapLines(words3, 5) ... "wrap words3 to line length 5" =>
     * #   [ "Hello",  "world" ]
     *
     * # n = number of words / total characters
     *
     * ####
     * Give a word list such as ["I", "am", "so" "sad"], and the longest number of characters such as 4, wrap these words with underscores and output:
     * ["I_am", "so", "sad"] Keep the length of each output string less than or equal to the maximum number of characters. You can try this question before looking at my answer
     * My feeling is that it is easy to kneel when the last word cannot be output
     *
     * ####
     * The first question word wrap: Give a word list and the maximum length, and require these words to be concatenated with -, but cannot exceed the maximum length.
     * The second word processor: I was a little dizzy at the time, and I didn't understand the meaning of the question for a long time. . . The interviewer said that the function of the first question can be used.
     * We are building a word processor and we would like to implement a "reflow" functionality that
     * also applies full justification to the text.
     *
     * Given an array containing lines of text and a new maximum width, re-flow the text to fit the new
     * width. Each line should have the exact specified width. If any line is too short, insert '-'
     * (as stand-ins for spaces) between words as equally as possible until it fits.
     *
     * Note: we are using '-' instead of spaces between words to make testing and visual verification
     * of the results easier.
     *
     *
     * lines = [ "The day began as still as the",
     * "night abruptly lighted with",
     * "brilliant flame" ]
     *
     * reflowAndJustify(lines, 24) ... "reflow lines and justify to length 24" =>
     *
     * [ "The--day--began-as-still",
     * "as--the--night--abruptly",
     * "lighted--with--brilliant",
     * "flame" ] // <--- a single word on a line is not padded with spac
     *
     *
     * ####
     * The first question: It is required to string as many words as possible with'-', but the length is required to be within a certain number. My main time is stuck on this question,
     * Made it in the last 5 minutes, hey, the hands are too rusty. . . It is easy to figure out what variables need to be maintained. .
     * The second question: It is based on the first question to split the given sentence, and then use a similar task schedule to insert'-' one by one until the maximum length is reached. .
     *
     * ###
     * The first question: Give several strings and the maximum length, rearrange the strings so that the length of each line does not exceed the maximum length (there is a space between the same line of strings)
     * （str1\n str2\n str3\n -> str1 str2\n str3\n）
     * The second question: Find the length of the longest repeated substring in a string (aaaaaaaabbbbbaaa -> a 8) (***)
     *
     * Reference : LE_68_Text_Justification
     */

    public static List<String> processText(String[] words, int max) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        for (int i = 0, w; i < words.length; i = w) {
            int len ​​= -1;
            for (w = i; w < words.length && len + words[w].length() + 1 <= max; w++) {
                len += words[w].length() + 1;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(words[i]);
            for (int j = i + 1; j < w; j++) {
                sb.append("-").append(words[j]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    public static List<String> reflowLine(String[] lines, int max) {
        List<String> res = new ArrayList<>();

        List<String[]> temp = new ArrayList<>();
        int size = 0;
        for (String line : lines) {
            String[] w = line.split(" ");
            temp.add(w);
            size += w.length;
        }

        String[] processedWords = new String[size];
        int idx = 0;
        for (String[] t : temp) {
            for (String s : t) {
                processedWords[idx++] = s;
            }
        }

        List<String> texts = processText(processedWords, max);
        if (texts.size() == 0) return res;

        for (String text : texts) {
            String[] parts = text.split("-");
            int numberOfWords = parts.length;

            if (numberOfWords == 1) {
                System.out.println(text);
                res.add(text);
                continue;
            }

            int numberOfGap = parts.length - 1;
            int numberOfPadding = max - text.length() + numberOfGap;

            int averagePadding = numberOfPadding / numberOfGap;
            int extraPadding = numberOfPadding % numberOfGap;

            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]);

            for (int i = 1; i < parts.length; i++) {
                for (int j = 0; j < averagePadding; j++) {
                    sb.append("-");
                }

                if (extraPadding > 0) {
                    sb.append("-");
                    extraPadding--;
                }

                sb.append(parts[i]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    public static void printRes1(String title, List<String> list) {
        System.out.println("---" + title + "----");
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        String[] words1 = {"The", "day", "began", "as", "still", "as", "the", "night",
                "abruptly", "lighted", "with", "brilliant", "flame"};
        printRes1("max 13", processText(words1, 13));
        printRes1("max 20", processText(words1, 20));

        String[] words2 = {"Hello"};
        String[] words3 = {"Hello", "World"};

        printRes1("max 5", processText(words2, 5));
        printRes1("max 5", processText(words3, 5));

        String[] lines = {"The day began as still as the",
                "night abruptly lighted with",
                "brilliant flame"

        };
        printRes1("max 24, justify line", reflowLine(lines, 24));
    }

}

