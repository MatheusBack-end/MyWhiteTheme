package JAVARuntime;

public class CodeStyler extends TextScriptingStyler {
    
    ThemeLoader themeConfig;
    List<String> types = new ArrayList<String>();
    private boolean typesLoaded = false;
    private Tokenizer tokenizer;
    
    public CodeStyler(ThemeLoader config) {
        themeConfig = config;
        collectNames();
    }
    
    @Override
    public void execute(String source, TextScriptingTheme theme, List<TextScriptingSyntaxHighlightSpan> highlightSpans) {
        collectNames();
        
        highlightSpans.add(createHighlight(new Point2(0, source.length()), theme.textColor));
        
        tokenizer = new Tokenizer(source);
        Token token = tokenizer.getNextToken();
        
        while(!token.type.equals("eof")) {
            if(token.type.equals("identifier")) {
                if(themeConfig.isKeyword(token.value)) {
                    highlightSpans.add(createHighlight(new Point2(token.start, token.end), theme.keywordColor));
                }
                
                token = tokenizer.getNextToken();
                continue;
            }
            
            if(token.type.equals("string")) {
                 highlightSpans.add(createHighlight(new Point2(token.start, token.end + 1), theme.stringColor));
                 applyStringColors(token.start, 0, token.value.length(), token.value, highlightSpans);
                 
                 token = tokenizer.getNextToken();
                 continue;
            }
            
            if(token.type.equals("comment")) {
                highlightSpans.add(createHighlight(new Point2(token.start, token.end), theme.commentColor));
            }
            
            token = tokenizer.getNextToken();
        }
    }
    
    private void applyStringColors(int offset, int start, int end, String string, List<TextScriptingSyntaxHighlightSpan> highlightSpans) {
        String letter = "";
        
        for(; start < end; start++) {
            if(letter.equals("#")) {
                int colorStart = start;
                String color = letter;
                
                letter = Character.toString(string.charAt(start++));

                for(int i = 0; i < 6; i++) {
                    if(start >= end)
                        break;
                        
                    color += letter;
                    
                    letter = Character.toString(string.charAt(start++));
                }
                
                if(color.length() == 7) {
                    highlightSpans.add(createHighlight(new Point2((colorStart + offset) -1, (start + offset)), new Color(color)));
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
