import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.regex.*;
import java.util.*;
public class Main {

    public static void main(String[] args) throws ParseExc, IOException {
        String[] exp = {
                "v = 1;",
                ".main()  a = 5;",

                "DO a = 7 WHILE (a < 7);",
                "WHILE (a < 10) a = 2;",
                "a = 1;",
                "IF (a ~ 1): a = 2;"
        };
        int len = exp.length;
        TokenType lex = new TokenType();
        ArrayList<Token> tokens = new ArrayList<>();
        String str = "";
        String str_ = "";
        int counter = 0;
        for (int j = 0; j < exp.length;j++){
            for (int i = 0; i < exp[j].length(); i++) {
                if (exp[j].toCharArray()[i] == ' ') {
                    continue;
                }
                str += exp[j].toCharArray()[i];
                if (i < exp[j].length() - 1) {
                    str_ = str + exp[j].toCharArray()[i + 1];
                }
                for (String key : lex.regexp.keySet()) {
                    Pattern p = Pattern.compile(lex.regexp.get(key));
                    Matcher m_1 = p.matcher(str);
                    Matcher m_2 = p.matcher(str_);
                    if (m_1.find() && !m_2.find()) {
                        tokens.add(new Token(key, str));
                        str = "";
                    }
                }
            }
        }
        for (int k = 0; k < tokens.size(); k++) {
            counter++;
            System.out.println("Token [" + counter + "] "+"Type of Regular Exp.: "+ tokens.get(k).type + "; Token: " + tokens.get(k).token);
        }
        Parser par = new Parser(tokens, len);
        par.lang();
        Interpreter in = new Interpreter(tokens);
        System.out.println(in.getVariables());
    }
}