package JAVARuntime;

public class CodeStyler extends TextScriptingStyler
{
    int position = 0;
    ThemeLoader themeConfig;
    List<String> types = new ArrayList<String>();
    private boolean typesLoaded = false;
    private Tokenizer tokenizer;
    
    public CodeStyler(ThemeLoader config) {
        themeConfig = config;
        collectNames();
    }
    
    @Override
    public void execute(String source, TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlight_spans) {
        collectNames();
        
        highlight_spans.add(createHighlight(new Point2(0, source.length()), theme.textColor));
        
        tokenizer = new Tokenizer(source);
        Token currentToken = tokenizer.getNextToken();
        
        while(!currentToken.type.equals("eof")) {
            if(currentToken.type.equals("identifier")) {
                if(themeConfig.is_keyword(currentToken.value)) {
                    highlight_spans.add(createHighlight(new Point2(currentToken.start, currentToken.end), theme.keywordColor));
                }
            }
            
            if(currentToken.type.equals("string")) {
                 highlight_spans.add(createHighlight(new Point2(currentToken.start, currentToken.end + 1), theme.stringColor));
                 applyStringColors(currentToken.start, 0, currentToken.value.length(), currentToken.value, highlight_spans);
            }
            
            if(currentToken.type.equals("comment")) {
                highlight_spans.add(createHighlight(new Point2(currentToken.start, currentToken.end), theme.commentColor));
            }
            
            currentToken = tokenizer.getNextToken();
        }
    }
    
    private void applyStringColors(int offset, int start, int end, String string, List<TextScriptingSyntaxHighlightSpan> highlightSpans)
    {
        String letter = "";
        
        for(; start < end; start++) {
            if(letter.equals("#")) {
                int color_start = start;
                String color = letter;
                
                letter = Character.toString(string.charAt(start++));

                for(int i = 0; i < 6; i++) {
                    if(start >= end)
                        break;
                        
                    color += letter;
                    
                    letter = Character.toString(string.charAt(start++));
                }
                
                if(color.length() == 7) {
                    highlightSpans.add(createHighlight(new Point2((color_start + offset) -1, (start + offset)), new Color(color)));
                }
            }
            
            letter = Character.toString(string.charAt(start));
        }
    }
    
    private TextScriptingSyntaxHighlightSpan createHighlight(Point2 location, Color color) {
        TextScriptingSyntaxHighlightSpan highlight = new TextScriptingSyntaxHighlightSpan();
        TextScriptingStyleSpan span = new TextScriptingStyleSpan(null);
        span.color = color;
        highlight.start = location.x;
        highlight.end = location.y;
        highlight.span = span;
        
        return highlight;
    }
    
    private void collectNames() {
        if(JCompiler.isCompiling() || typesLoaded)
            return;
        
        List<JClass> classes = JCompiler.getAllClasses(); 
        
        for (int i = 0; i < classes.size(); i++) { 
            JClass current = (JClass) classes.get(i); 
            String name = current.getName();
            types.add(name);
        }
        
        typesLoaded = true; 
    }
}
