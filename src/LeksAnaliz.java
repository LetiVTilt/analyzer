import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeksAnaliz {
    private String input;
    private List<Token> tokens;
    private ArrayList<String> digitUnique = new ArrayList<String>();
    private ArrayList<String> keyWordUnique = new ArrayList<String>();
    private ArrayList<String> identifyUnique = new ArrayList<String>();
    private ArrayList<String> twoSeparatorUnique = new ArrayList<String>();
    private ArrayList<String> oneSeparatorUnique = new ArrayList<String>();
    private ArrayList<String> literalUnique = new ArrayList<String>();
    private ArrayList<String> commentUnique = new ArrayList<String>();

    public LeksAnaliz(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            this.input = sb.toString();
            this.tokens = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList<Token> analiz() {
        StringBuilder buffer = new StringBuilder();
        int S = 0;
        int i = 0;

        while (i < input.length()) {
            char currentChar = input.charAt(i);

            switch (S) {
                case 0:
                    if (currentChar >= '0' && currentChar <= '9') {
                        buffer.append(currentChar);
                        S = 1;
                    } else if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')
                            || currentChar == '_') {
                        buffer.append(currentChar);
                        S = 2;
                    } else if (currentChar == '<' || currentChar == '>' || currentChar == ':' || currentChar == '.'
                            || currentChar == '=') {
                        buffer.append(currentChar);
                        S = 3;
                    } else if (currentChar == '/') {
                        buffer.append(currentChar);
                        S = 7;
                    } else if (currentChar == '\'') {
                        buffer.append(currentChar);
                        S = 4;
                    } else if (isOneSeparator(String.valueOf(currentChar))) {
                        buffer.append(currentChar);
                        S = 6;
                    }
                    break;

                case 1:
                    if (currentChar >= '0' && currentChar <= '9') {
                        buffer.append(currentChar);
                    } else {

                        tokens.add(new Token("Число", buffer.toString()));
                        buffer.setLength(0);
                        S = 0;
                        i--;
                    }
                    break;

                case 2:
                    if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')
                            || currentChar == '_' || (currentChar >= '0' && currentChar <= '9')) {
                        buffer.append(currentChar);
                    } else {
                        if (isKeyWord(buffer.toString())) {

                            tokens.add(new Token("Ключевое слово", buffer.toString()));
                            buffer.setLength(0);
                            S = 0;
                            i--;
                        } else {
                            S = 5;
                            i--;
                        }
                    }
                    break;

                case 3:
                    if (currentChar == '=' || currentChar == '<' || currentChar == '>' || currentChar == '.') {
                        buffer.append(currentChar);

                        try (BufferedReader br = new BufferedReader(new FileReader("TwoSeparator.txt"))) {
                            String line;

                            while ((line = br.readLine()) != null) {
                                if (line.equals(buffer.toString())) {

                                    if (line.equals(":=")) {
                                        tokens.add(new Token("D1", line));
                                    } else if (line.equals("<=")) {
                                        tokens.add(new Token("D2", line));
                                    } else if (line.equals(">=")) {
                                        tokens.add(new Token("D3", line));
                                    } else if (line.equals("<>")) {
                                        tokens.add(new Token("D4", line));
                                    } else if (line.equals("..")) {
                                        tokens.add(new Token("D4", line));
                                    } else if (line.equals("==")) {
                                        tokens.add(new Token("D4", line));
                                    }

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        buffer.setLength(0);
                        S = 0;
                    } else {
                        S = 6;
                        i--;
                    }
                    break;

                case 4:
                    buffer.append(currentChar);
                    while (input.charAt(i) != '\'') {
                        i++;
                        buffer.append(input.charAt(i));
                    }

                    tokens.add(new Token("Литерал", buffer.toString()));

                    buffer.setLength(0);
                    S = 0;
                    break;

                case 5:
                    if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')
                            || currentChar == '_' || (currentChar >= '0' && currentChar <= '9')) {
                        buffer.append(currentChar);
                    } else {

                        tokens.add(new Token("Идентификатор", buffer.toString()));

                        buffer.setLength(0);
                        S = 0;
                    }
                    i--;
                    break;

                case 6:
                    if (buffer.length() == 1 && isOneSeparator(buffer.toString())) {

                        tokens.add(new Token("Однолитеральный", buffer.toString()));
                    }
                    buffer.setLength(0);
                    S = 0;
                    i--;
                    break;

                case 7:
                    if (currentChar == '/') {
                        buffer.append(currentChar);
                        while (i < input.length() - 1 && input.charAt(i + 1) != '\n') {
                            i++;
                            buffer.append(input.charAt(i));
                        }
                    } else if (currentChar == '*') {
                        buffer.append(currentChar);
                        i++;

                        while (i < input.length() - 1 && input.charAt(i) != '*' && input.charAt(i + 1) != '/') {
                            i++;
                            buffer.append(input.charAt(i));
                        }

                        i++;

                        if (i < input.length() - 1 && input.charAt(i) == '/') {
                            buffer.append(input.charAt(i));
                        }
                    } else {
                        i--;
                        S = 6;
                        break;
                    }

                    tokens.add(new Token("Комментарий", buffer.toString()));
                    buffer.setLength(0);
                    S = 0;
                    break;
            }

            i++;
        }

        int countM = -1, countB = -1, countI = -1, countL = -1, countD = -1, countK = -1, countC = -1, count = 0;
        ArrayList<Token> arrayTokens = new ArrayList<Token>();

        for (Token element : tokens) {
            if (!isDublikat(element, count)) {
                if (element.getTypeValue().equals("Комментарий")) {
                    countM++;
                    commentUnique.add(element.getValue());
                    arrayTokens.add(new Token("M", String.valueOf(countM)));
                    count++;
                }
                if (element.getTypeValue().equals("Однолитеральный")) {
                    countB++;
                    oneSeparatorUnique.add(element.getValue());
                    arrayTokens.add(new Token("B", String.valueOf(countB)));
                    count++;
                }
                if (element.getTypeValue().equals("Идентификатор")) {
                    countI++;
                    identifyUnique.add(element.getValue());
                    arrayTokens.add(new Token("I", String.valueOf(countI)));
                    count++;
                }
                if (element.getTypeValue().equals("Литерал")) {
                    countL++;
                    literalUnique.add(element.getValue());
                    arrayTokens.add(new Token("L", String.valueOf(countL)));
                    count++;
                }
                if (element.getTypeValue().equals("D1") || element.getTypeValue().equals("D2")
                        || element.getTypeValue().equals("D3")
                        || element.getTypeValue().equals("D4")) {
                    countD++;
                    twoSeparatorUnique.add(element.getValue());
                    arrayTokens.add(new Token("D", String.valueOf(countD)));
                    count++;
                }
                if (element.getTypeValue().equals("Ключевое слово")) {
                    countK++;
                    keyWordUnique.add(element.getValue());
                    arrayTokens.add(new Token("K", String.valueOf(countK)));
                    count++;
                }
                if (element.getTypeValue().equals("Число")) {
                    countC++;
                    digitUnique.add(element.getValue());
                    arrayTokens.add(new Token("C", String.valueOf(countC)));
                    count++;
                }
            } else {
                for (int k = 0; k < arrayTokens.size(); k++) {
                    if (element.getValue().equals(tokens.get(k).getValue())) {
                        arrayTokens.add(arrayTokens.get(k));
                        break;
                    }
                }
                count++;
            }
        }

        System.out.println("\t\t\t=================================Лексер=================================");
        for (int d = 0; d < arrayTokens.size(); d++) {
            System.out.print(arrayTokens.get(d));
        }

        return arrayTokens;
    }

    public boolean isKeyWord(String buffer) {
        try (BufferedReader br = new BufferedReader(new FileReader("KeyWord.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(buffer)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isOneSeparator(String buffer) {
        try (BufferedReader br = new BufferedReader(new FileReader("OneSeparator.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(buffer)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isDublikat(Token element, int count) {
        boolean foundDuplicate = false;

        for (int k = 0; k < count; k++) {
            if (tokens.get(k).getValue().equals(element.getValue())) {
                foundDuplicate = true;
                break;
            }
        }
        return foundDuplicate;
    }

    public ArrayList<String> GetKeyWordUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : keyWordUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetIdentifyUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : identifyUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetDigitUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : digitUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetOneSeparatorUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : oneSeparatorUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetTwoSeparatorUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : twoSeparatorUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetLiteralUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : literalUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetCommentUnique() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : commentUnique) {
            list.add(item);
        }
        return list;
    }

    public ArrayList<String> GetList(char simb) {
        switch (simb) {
            case 'M':
                return GetCommentUnique();
            case 'B':
                return GetOneSeparatorUnique();
            case 'I':
                return GetIdentifyUnique();
            case 'L':
                return GetLiteralUnique();
            case 'D':
                return GetTwoSeparatorUnique();
            case 'K':
                return GetKeyWordUnique();
            case 'C':
                return GetDigitUnique();
            default:
                return new ArrayList<>();
        }
    }

    public int GetIndex(ArrayList<String> list, String word) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(word)) {
                return i;
            }
        }
        return -1;
    }
}
