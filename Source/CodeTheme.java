package JAVARuntime;

public class CodeTheme {
    
    public TextScriptingTheme theme;
    public ThemeLoader config;
    
    public TextScriptingTheme get() {
        if(theme == null)
            theme = new TextScriptingTheme();
        
        config = new ThemeLoader();
        config.load();
        
        // TODO: remove rendundance fields
        JsonBody data = config.config;
        
        theme.backgroundColor = new Color(data.backgroundColor);
        theme.textColor = new Color(data.textColor);
        theme.gutterColor = new Color(data.gutterColor);
        theme.gutterDividerColor = new Color(data.gutterDividerColor);
        theme.gutterCurrentLineNumberColor =  new Color(data.gutterCurrentLineNumberColor);
        theme.gutterTextColor = new Color(data.gutterTextColor);
        theme.selectedLineColor = new Color(data.selectedLineColor);
        theme.selectionColor = new Color(data.selectionColor);
        theme.suggestionQueryColor = new Color(data.suggestionQueryColor);
        theme.findResultBackgroundColor = new Color(data.findResultBackgroundColor);
        theme.delimiterBackgroundColor = new Color(data.delimiterBackgroundColor);
        theme.numberColor = new Color(data.numberColor);
        theme.operatorColor = new Color(data.operatorColor);
        theme.keywordColor = new Color(data.keywordColor);
        theme.typeColor = new Color(data.typeColor);
        theme.langConstColor = new Color(data.langConstColor);
        theme.preprocessorColor = new Color(data.preprocessorColor);
        theme.variableColor = new Color(data.variableColor);
        theme.methodColor = new Color(data.methodColor);
        theme.stringColor = new Color(data.stringColor);
        theme.commentColor = new Color(data.commentColor);
        theme.tagColor = new Color(data.tagColor);
        theme.tagNameColor = new Color(data.tagNameColor);
        theme.attrNameColor = new Color(data.attrNameColor);
        theme.attrValueColor = new Color(data.attrValueColor);
        theme.entityRefColor = new Color(data.entityRefColor);
        theme.gutterLineError = new Color(data.gutterLineError);
        theme.gutterLineAlert = new Color(data.gutterLineAlert);
        
        return theme;
    }
}
