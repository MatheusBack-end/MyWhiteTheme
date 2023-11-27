public class CodeTheme
{   
    public TextScriptingTheme theme;
    public ThemeLoader config;
    
    public TextScriptingTheme get()
    {
        if(theme == null)
            theme = new TextScriptingTheme();
        
        config = new ThemeLoader();
        config.load();
        
        theme.backgroundColor = config.background_color(); //new Color("#FFFFFF");
        theme.textColor = new Color("#000000");
        theme.gutterColor = new Color("#FFFFFF");
        theme.gutterDividerColor = new Color("#555555");
        theme.gutterCurrentLineNumberColor = new Color("#A4A3A3");
        theme.gutterTextColor = new Color("#616366");
        theme.selectedLineColor = new Color("#ffffff");
        theme.selectionColor = new Color("#66747B");
        theme.suggestionQueryColor = new Color("#987DAC");
        theme.findResultBackgroundColor = new Color("#33654B");
        theme.delimiterBackgroundColor = new Color("#66747B");
        theme.numberColor = new Color("#6897BB");
        theme.operatorColor = new Color("#E8E2B7");
        theme.keywordColor = new Color("#DDDD00");
        theme.typeColor = new Color("#EC7600");
        theme.langConstColor = new Color("#EC7600");
        theme.preprocessorColor = new Color("#C9C54E");
        theme.variableColor = new Color("#9378A7");
        theme.methodColor = new Color("#FEC76C");
        theme.stringColor = new Color("#6B8EC3");
        theme.commentColor = new Color("#66747B");
        theme.tagColor = new Color("#E2C077");
        theme.tagNameColor = new Color("#E2C077");
        theme.attrNameColor = new Color("#BABABA");
        theme.attrValueColor = new Color("#ABC16D");
        theme.entityRefColor = new Color("#6897BB");
        theme.gutterLineError = new Color("#ff0000");
        theme.gutterLineAlert = new Color("#f1c40f");
        
        return theme;
    }
}
