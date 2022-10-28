public class ParseExc extends Exception{
    String expected;
    Token token;
    int iterator;
    public void getMsg(Token currentToken, String expected){
        if (expected == "ENDLINE"){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
            System.exit(0);
        }
        if (expected == "COLON"){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ':'");
            System.exit(0);
        }
        else {
            System.out.println("Token [" + iterator + "] " + currentToken.type + " but expected: " + expected);
            System.exit(0);
        }
    }
    public ParseExc(Token currentToken, String expected, int iterator){
        this.iterator = iterator;
        this.expected = expected;
        this.token = currentToken;
    }
}