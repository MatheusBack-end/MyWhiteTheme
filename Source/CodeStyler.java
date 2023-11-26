public class CodeStyler extends TextScriptingStyler
{
    int position = 0;
    String source;
    private String[] keywords = new String[] {"public", "private", "protected", "return", "class", "extends", "if", "this", "continue", "while", "break", "new", "final"};
    
    @Override
    public void execute(String src, TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlight_spans)
    {        
        this.source = src;
        List keywords = Arrays.asList(this.keywords);
        
        highlight_spans.add(create_highlight(new Point2(0, src.length()), theme.textColor));
        
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
                    
                    continue;
                }
                
                highlight_spans.add(create_highlight(new Point2(start, position), theme.textColor));
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
                        
                        if(position >= source.length() -1)
                            break;
                            
                        letter = consume_letter();
                    }
                    
                    highlight_spans.add(create_highlight(new Point2(start, position + 1), theme.commentColor));
                }
            }
            
            if(letter.equals("\""))
            {
                int start = position;
                
                if(position < source.length() - 1)
                    letter = consume_letter();
                
                while(!letter.equals("\""))
                {
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
                
                highlight_spans.add(create_highlight(new Point2(start, position + 1), theme.stringColor));
                apply_string_colors(start, position, highlight_spans);
            }
            
            position++;
        }
        
        position = 0;
    }
    
    private void apply_string_colors(int offset, int end, List<TextScriptingSyntaxHighlightSpan> highlight_spans)
    {
        String letter = "";
        
        for(; offset < end; offset++)
        {
            if(letter.equals("#"))
            {
                int color_start = offset;
                String color = letter;
                
                letter = Character.toString(source.charAt(offset++));

                for(int i = 0; i < 6; i++)
                {
                    if(offset > end)
                        break;
                        
                    color += letter;
                    
                    letter = Character.toString(source.charAt(offset++));
                }
                
                if(color.length() == 7)
                {
                    highlight_spans.add(create_highlight(new Point2(color_start -1, offset -1), new Color(color)));
                }
            }
            
            letter = Character.toString(source.charAt(offset));
        }
    }
    
    private TextScriptingSyntaxHighlightSpan create_highlight(Point2 location, Color color)
    {
        TextScriptingSyntaxHighlightSpan highlight = new TextScriptingSyntaxHighlightSpan();
        TextScriptingStyleSpan span = new TextScriptingStyleSpan(null);
        span.color = color;
        highlight.start = location.x;
        highlight.end = location.y;
        highlight.span = span;
        
        return highlight;
    }
    
    private String consume_letter()
    {
        return Character.toString(source.charAt(++position));
    }
}
