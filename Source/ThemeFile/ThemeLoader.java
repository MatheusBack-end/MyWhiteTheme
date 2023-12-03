public class ThemeLoader
{
    private boolean is_loaded = false;
    public String path = "Files/WhiteTheme/Assets/Theme.json";
    public JsonBody config;
    
    public void load()
    {
        if(is_loaded)
            return;
            
        config = get_config();
        is_loaded = true;
    }
    
    public boolean is_keyword(String word)
    {
        return config.keywords.contains(word);
    }
    
    public Color background_color()
    {
        return new Color(config.backgroundColor);
    }
    
    public JsonBody get_config()
    {
        try
        {
            File file = new File(Directories.getProjectFolder() + path);
            String file_contents = FileLoader.loadTextFromFile(file);
            JsonBody theme = (JsonBody) Json.fromJson(file_contents, JsonBody.class);
            
            return theme;
        }
        
        catch(Exception e)
        {
            Console.log(e);
        }
        
        return null;
    }
}
