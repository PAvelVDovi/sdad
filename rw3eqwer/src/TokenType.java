import java.lang.reflect.Array;
import java.util.*;
public class TokenType {
    static Map<String,String> regexp = new HashMap<String,String>();
    public TokenType(){
        regexp.put("FUNCTION", "^.[a-z()]+$");
        regexp.put("WHILE","^[W][H][I][L][E]$");
        regexp.put("COMPARISON_OP", "^[>|<|~]$");
        regexp.put("L_BRACKET","^\\($");
        regexp.put("R_BRACKET","^\\)$");
        regexp.put("L_BRACE","^\\{$");
        regexp.put("R_BRACE","^\\}$");
        regexp.put("IF OPERATION", "^[I][F]$");
        regexp.put("VAR", "^[a-z]+$");
        regexp.put("DIGIT", "^0|[1-9][0-9]*$");
        regexp.put("OPERATOR", "^[-|+|/|*|%]$");
        regexp.put("ASSIGNMENT OPERATOR", "^=$");
        regexp.put("COLON", "\\:$");
        regexp.put("ENDLINE", "^\\;$");
        regexp.put("DO_WHILE", "^[D][O]$");
        
    }
}
