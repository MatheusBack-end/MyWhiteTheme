package JAVARuntime;

public class CodeStyler extends TextScriptingStyler
{
    int position = 0;
    String source;
    ThemeLoader theme_config;
    
    public CodeStyler(ThemeLoader config)
    {
        theme_config = config;
    }
    
    @Override
    public void execute(String src, TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlight_spans) {        
        this.source = src;
        
        highlight_spans.add(create_highlight(new Point2(0, src.length()), theme.textColor));
        
        while(position < source.length()) {
            String letter = Character.toString(source.charAt(position));
            
            if((letter.equals(" ")) || (letter.equals("\n"))) {
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
                
                if(theme_config.is_keyword(value))
                {
                    highlight_spans.add(create_highlight(new Point2(start, position), theme.keywordColor));
                    
                    continue;
                }
                
                highlight_spans.add(create_highlight(new Point2(start, position), theme.textColor));
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
            
            applyComments(theme, highlight_spans);
            
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
    
    public void applyComments(TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlightSpans) {
        String letter = getCurrentLetter();
        int start = position;
        
        if(Character.isLetterOrDigit(letter.charAt(0)))
            return;
            
        String unk_operator = eatOperators(2, false);
        if(unk_operator == null)
            return;
        
        if(unk_operator.equals("//")) {
            eatOperators(2, true);
            while(!letter.equals("\n")) {
                letter = consume_letter();
            }
            
            highlightSpans.add(create_highlight(new Point2(start, position), theme.commentColor));
        }
            
        if(unk_operator.equals("/*")) {
            eatOperators(2, true);
            while(true) {
                if(!Character.isLetterOrDigit(letter.charAt(0))) {
                    if(eatOperators(2, true).equals("*/"))
                        break;
                }
                    
                if(position >= source.length() - 1)
                    break;
                    
                letter = consume_letter();
            }
            
            highlightSpans.add(create_highlight(new Point2(start, position), theme.commentColor));
        }
    }
    
    public String eatOperators(int size, boolean consume) {
        String operator = "";
        
        if(position >= source.length())
            return null;
        
        String letter = getCurrentLetter();
        int count = 0;
        int offset = position;
        
        while(!Character.isLetterOrDigit(letter.charAt(0))) {
            if(letter.equals(" "))
                break;
                
            if(count >= size)
                break;
                    
            operator += letter;
            
            if(position >= source.length() - 1 || offset >= source.length() - 1)
                break;
            
            if(consume)
                letter = consume_letter();
            else
                letter = Character.toString(source.charAt(++offset));
            
            count++;
        }
        
        return operator;
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
    
    private String getCurrentLetter() {
        return Character.toString(source.charAt(position));
    }
}
