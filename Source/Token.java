package JAVARuntime;

public class Token {
    
    public String type;
    public String value;
    public int start;
    public int end;
    
    public Token(String type, String value, int start, int end) {
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
    }
}