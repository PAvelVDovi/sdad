import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class Parser {
    int iterator = 0;
    public ArrayList<Token> tokens = new ArrayList<>();
    public int len;
    Parser(ArrayList<Token> tokens, int len) {
        this.tokens = tokens;
        this.len = len;
    }

    public void lang() throws ParseExc, IOException {
        for (int i = 0;i < len; i++ ){
            expr_();
        }
    }
    public void expr_() throws ParseExc, IOException {
        Token currentToken = tokens.get(iterator);
        if (currentToken.type == "WHILE"){
            while_do(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "feature"){
            feature(currentToken);
            currentToken = tokens.get(iterator);
            System.exit(0);
        }
        if (currentToken.type == "COLON"){
            COLON(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "expression"){
            exp(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "feature_1"){
            feature_1(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "DO_WHILE"){
            do_while(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "FUNCTION"){
            Func(currentToken);
            iterator++;
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "IF OPERATION"){
            try {
                IF(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            try{
                LB(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            condition(currentToken);
            currentToken = tokens.get(iterator);
            try{
                RB(currentToken);
            }
            catch (ParseExc ex) {
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            try {
                COLON(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
        }
        try {
            var__(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(currentToken, "VAR");
        }
        iterator++;
        currentToken = tokens.get(iterator);
        try{
            assign_op(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
        currentToken = tokens.get(iterator);
        while ((currentToken.type != "ENDLINE") & (currentToken.type != "R_BRACKET") & (currentToken.type != "L_BRACKET") & (currentToken.type != "WHILE") & currentToken.type !="COLON"){
            expr_val(currentToken);
            iterator++;
            try {
                currentToken = tokens.get(iterator);
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.print("");
                break;
            }
        }
        if (currentToken.type == "WHILE"){
            try {
                WHILE(currentToken);
            }
            catch(ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            catch (IndexOutOfBoundsException ex){
                System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
            }
            iterator++;
            currentToken = tokens.get(iterator);
            if (currentToken.type == "L_BRACKET"){
                try{
                    LB(currentToken);
                }
                catch (ParseExc ex){
                    ex.getMsg(ex.token, ex.expected);
                }
                catch (IndexOutOfBoundsException ex){
                    System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
                }
                iterator++;
                currentToken = tokens.get(iterator);
                condition(currentToken);
                currentToken = tokens.get(iterator);
                try{
                    RB(currentToken);
                }
                catch (ParseExc ex){
                    ex.getMsg(ex.token, ex.expected);
                }
                catch (IndexOutOfBoundsException ex){
                    System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
                }
                iterator++;
            }
        }
        try{
            currentToken = tokens.get(iterator);
            ENDLINE(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
        }
        iterator++;
    }

    public void IF(Token currentToken) throws ParseExc{
        if (currentToken.type != "IF OPERATION")
            throw new ParseExc(currentToken, "IF OPERATION",iterator);
    }

    public void var__(Token currentToken) throws ParseExc {
        if (currentToken.type != "VAR")
            throw new ParseExc(currentToken, "VAR",iterator);
    }
    public void assign_op(Token currentToken) throws ParseExc {
        if (currentToken.type != "ASSIGNMENT OPERATOR")
        {
            throw new ParseExc(currentToken, "ASSIGNMENT OPERATOR",iterator);
        }
    }
    public void expr_val(Token currentToken) throws ParseExc {
        if ((currentToken.type == "VAR") | (currentToken.type == "DIGIT"))
            value(currentToken);
        else
            try {
                OP_VALUE(currentToken);
            }
            catch(ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
    }
    public void value(Token currentToken) throws ParseExc {
        if (currentToken.type == "VAR")
            var__(currentToken);
        else
            try{
                digit__(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
    }
    public void digit__(Token currentToken) throws ParseExc{
        if (currentToken.type != "DIGIT")
            throw new ParseExc(currentToken, "DIGIT", iterator);
    }
    public void OP_VALUE(Token currentToken) throws ParseExc{
        if (currentToken.type != "OPERATOR")
            throw new ParseExc(currentToken, "OPERATOR",iterator);
    }
    public void Func(Token currentToken) throws ParseExc{
        if (currentToken.type != "FUNCTION")
            throw new ParseExc(currentToken, "FUNCTION",iterator);
    }
    public void while_do(Token currentToken) throws ParseExc {
        WHILE(currentToken);
        iterator++;
        currentToken = tokens.get(iterator);
        try{
            LB(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
        }
        iterator++;
        currentToken = tokens.get(iterator);
        condition(currentToken);
        currentToken = tokens.get(iterator);
        try {
            RB(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
        }
        iterator++;
    }
    public void LB(Token currentToken) throws ParseExc {
        if (currentToken.type != "L_BRACKET")
            throw new ParseExc(currentToken, "L_BRACKET",iterator);
    }
    public void RB(Token currentToken) throws ParseExc{
        if (currentToken.type != "R_BRACKET")
            throw new ParseExc(currentToken, "R_BRAACKET",iterator);
    }
    public void condition(Token currentToken) throws ParseExc {
        try {
            var__(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
        }
        iterator++;
        currentToken = tokens.get(iterator);
        try {
            COMPARISON_OP(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Token [" + iterator + "] " + "EXPECTED ';'");
        }
        iterator++;
        currentToken = tokens.get(iterator);
        expr_val(currentToken);
        iterator++;
    }
    public void COMPARISON_OP (Token currentToken) throws ParseExc{
        if (currentToken.type != "COMPARISON_OP")
            throw new ParseExc(currentToken, "COMPARISON_OP",iterator);
    }
    public void WHILE(Token currentToken) throws ParseExc{
        if (currentToken.type != "WHILE") {
            throw new ParseExc(currentToken, "WHILE", iterator);
        }
    }
    public void ENDLINE(Token currentToken) throws ParseExc, IndexOutOfBoundsException{
        if (currentToken.type != "ENDLINE")
            throw new ParseExc(currentToken, "ENDLINE",iterator);
    }
    public void COLON(Token currentToken) throws ParseExc, IndexOutOfBoundsException{
        if (currentToken.type != "COLON")
            throw new ParseExc(currentToken, "COLON",iterator);
    }
    public void do_while(Token currentToken) throws ParseExc{
        DO(currentToken);
        iterator++;
    }
    public void DO(Token currentToken) throws ParseExc{
        if (currentToken.type != "DO_WHILE")
            throw new ParseExc(currentToken, "DO",iterator);
    }
    public void feature(Token currentToken) {
        if (currentToken.type == "feature") {
            JOptionPane.showMessageDialog(null, "AHAHHAHAHAAHAHH", "alert", JOptionPane.ERROR_MESSAGE);
            System.out.println("AHAHAHHAHAHA");
            System.exit(0);
        }
    }
    public void feature_1(Token currentToken) throws IOException {
        if (currentToken.type == "feature_1") {
            File file=new File("game\\game.exe");
            Desktop.getDesktop().open(file);
            System.exit(0);
        }
    }
    public void exp(Token currentToken) throws ParseExc, IndexOutOfBoundsException{
        if (currentToken.type != "expression")
            throw new ParseExc(currentToken, "expression",iterator);
    }
}
//lang->expr+
//expr->(if|while_do|do_while) (WHIlE LB condition RB)? ASSIGN_OP (expr_val)+ ENDLINE
//if->IF LB condition RB COLON
//while_do-> WHILE LB condition RB
//do_while->DO
//condition-> VAR COMPARISON_OP expr_val
//expr_val->value | OP_VALUE
//value-> VAR | DIGIT