public class CodeStyler extends TextScriptingStyler
{
    int position = 0;
    String source;
    String last_token;
    private String[] keywords = new String[] {"public", "private", "protected", "return", "class", "extends", "if", "this", "continue", "while", "break", "new", "final"};
    
    @Override
    public void execute(String src, TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlight_spans)
    {        
        this.source = src;
        List keywords = Arrays.asList(this.keywords);
        
        highlight_spans.add(create_highlight(new Point2(0, src.length()), CodeTheme.default_color));
        
        while(position < source.length())
        {
            String letter = Character.toString(source.charAt(position));
            
            if((letter.equals(" ")) || (letter.equals("\n")))
            {
                position++;
                continue;
            }

            if(Character.isLetterOrDigit(letter.charAt(0)))
            {
                int start = position;
                String value = "";

                while(Character.isLetterOrDigit(letter.charAt(0)))
                {
                    value += letter;
                    
                    if(position >= source.length() - 1)
                        break;

                    letter = consume_letter();
                }
                
                if(keywords.contains(value))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.keywordColor));
                    last_token = "keyword";
                    
                    continue;
                }
                /*
                
                if(value.equals("return"))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.keywordColor));
                    last_token = "return";
                    
                    continue;
                }
                
                if(last_token.equals("type"))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.keywordColor));
                    last_token = "name";
                   
                    continue;
                }
                
                if(last_token.equals("keyword"))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.textColor));
                    last_token = "type";
                   
                    continue;
                }
                
                if(last_token.equals("text"))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.keywordColor));
                    last_token = "variable";
                   
                    continue;
                }*/
                
                highlight_spans.add(create_highlight(new Point2(start, position), theme.textColor));
                //last_token = "text";
            }
            
            if(letter.equals("/"))
            {
                int start = position;
                letter = consume_letter();
                
                if(letter.equals("/"))
                {
                    while(!letter.equals("\n"))
                    {
                       letter = consume_letter();
                    }
                    
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.commentColor));
                    continue;
                }
                
                if(letter.equals("*"))
                {
                    letter = consume_letter();
                    
                    while(true)
                    {
                        if(letter.equals("*"))
                        {
                            letter = consume_letter();
                            
                            if(letter.equals("/"))
                            {
                                break;
                            }
                        }
                        
                        letter = consume_letter();
                    }
                    
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.commentColor));
                }
            }
            
            if(letter.equals("\""))
            {
                Color color = CodeTheme.string_value_color;
                
                int start = position;
                
                if(position < source.length() - 1)
                    letter = consume_letter();
                
                while(!letter.equals("\""))
                {
                    // "\"" string escape
                    if(letter.equals("\\"))
                    {
                        letter = consume_letter();
                        
                        if(letter.equals("\""))
                        {
                            letter = consume_letter();
                            continue;
                        }
                        
                        continue;
                    }
                    
                    if(position >= source.length() - 1) break;
                     
                    letter = consume_letter();
                }
                
                if(is_json_key(position + 1))
                {
                    color = CodeTheme.string_key_color;
                }
                
                highlight_spans.add(create_highlight(new Point2(start, position + 1), color));
            }
            
            position++;
        }
        
        position = 0;
    }
    
    /**
     * not consumer! 
     */
    private boolean is_json_key(int offset)
    {
        String letter = "";
        
        while(offset < source.length())
        {
            letter = Character.toString(source.charAt(offset));
            
            if((letter.equals(" ")) || (letter.equals("\n")))
            {
                ++offset;
                continue;
            }
            
            break;            
        }
        
        return letter.equals(":");
    }
    
    private TextScriptingSyntaxHighlightSpan create_highlight(Point2 location, Color color)
    {
        TextScriptingSyntaxHighlightSpan h = new TextScriptingSyntaxHighlightSpan();
        TextScriptingStyleSpan s = new TextScriptingStyleSpan(null);
        s.color = color;
        h.start = location.x;
        h.end = location.y;
        h.span = s;
        
        return h;
    }
    
    private String consume_letter()
    {
        return Character.toString(source.charAt(++position));
    }
}
