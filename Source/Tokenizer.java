package JAVARuntime;

public class Tokenizer {
    
    private String text;
    private int position;
    
    public Tokenizer(String text) {
        this.text = text;
        this.position = 0;
    }
    
    public Token getNextToken() {
        consumeInvalidCharacters();
        
        if(position >= text.length())
            return new Token("eof", null, 0, 0);
        
        String letter = getLetter();
        
        if(Character.isLetterOrDigit(letter.charAt(0))) {
            int start = position;
            String identifier = "";
            
            while(Character.isLetterOrDigit(letter.charAt(0))) {
                identifier += letter;
                
                letter = consumeLetter();
            }
            
            return new Token("identifier", identifier, start, position);
        }
        
        if(letter.equals("\"")) {
            String string = "";
            int start = position;
                
            if(position < text.length() - 1)
                letter = consumeLetter();
                
            while(!letter.equals("\"")) {
                string += letter;
                
                if(letter.equals("\\")) {
                    letter = consumeLetter();
                        
                    if(letter.equals("\"")) {
                        letter = consumeLetter();
                        continue;
                    }
                        
                    continue;
                }
                    
                if(position >= text.length() - 1)
                    break;                    
                    
                letter = consumeLetter();
            }
            
            return new Token("string", string, start, position++);
        }
        
        if(!Character.isLetterOrDigit(letter.charAt(0))) {
            int start = position;
            String unknowOperator = eatOperators(2, false);
            
            if(unknowOperator == null) {
                return new Token("unknow", letter, position, position++);
            }
                
            if(unknowOperator.equals("//")) {
                eatOperators(2, true);
                
                while(!letter.equals("\n"))
                    letter = consumeLetter();
                    
                return new Token("comment", null, start, position);
            }
                
            if(unknowOperator.equals("/*")) {
                eatOperators(2, true);
                
                while(true) {
                    if(!Character.isLetterOrDigit(letter.charAt(0))) {
                        if(eatOperators(2, true).equals("*/"))
                            break;
                    }
                    
                    if(position >= text.length() - 1)
                        break;
                        
                    letter = consumeLetter();
                }
                
                return new Token("comment", null, start, position);
            }
        }   
             
        return new Token("unknow", letter, position, position++);
    }
    
    public boolean isKeyword(String word) {
        return true;
    }
    
    public void consumeInvalidCharacters() {
        if(position >= text.length())
            return;
        
        while(true) {
            String letter = getLetter();
            
            if((letter.equals(" ")) || (letter.equals("\n"))) {
                if(++position >= text.length() - 1)
                    break;
                
                continue;
            }
            
            break;
        }
    }
    
    public String eatOperators(int size, boolean consume) {
        String operator = "";
        
        if(position >= text.length())
            return null;
        
        String letter = getLetter();
        int count = 0;
        int offset = position;
        
        while(!Character.isLetterOrDigit(letter.charAt(0))) {
            if(letter.equals(" "))
                break;
                
            if(count >= size)
                break;
                    
            operator += letter;
            
            if(position >= text.length() - 1 || offset >= text.length() - 1)
                break;
            
            if(consume)
                letter = consumeLetter();
            else
                letter = Character.toString(text.charAt(++offset));
            
            count++;
        }
        
        return operator;
    }
    
    private String consumeLetter() {
        return Character.toString(text.charAt(++position));
    }
    
    public String getLetter() {
        return Character.toString(text.charAt(position));
    }
}