public class CodeTheme extends TextScriptingTheme
{
    public TextScriptingTheme theme;
    
    public TextScriptingTheme get()
    {
        if(theme == null)
            theme = new TextScriptingTheme();
            
        // editor default colors
        theme.backgroundColor = new Color("#ffffff");
        theme.gutterColor = new Color("#ffffff");
        theme.selectedLineColor = new Color("#ffffff");
        
        return theme;
    }
    
        
    public static Color string_value_color = new Color("#6b8ec3"); // "baz": ("foo")
    public static Color string_key_color = new Color("#dddd00");   // 588152 ("baz"): "foo"
    public static Color default_color = new Color("#000000");      // ",", ":", "[]", "{}" etc..
}
