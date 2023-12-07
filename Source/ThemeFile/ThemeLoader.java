package JAVARuntime;

public class ThemeLoader {
    
    private boolean isLoaded = false;
    public String path = "Files/WhiteTheme/Assets/Theme.json";
    public JsonBody config;
    
    public void load() {
        if(isLoaded)
            return;
            
        config = getConfig();
        isLoaded = true;
    }
    
    public boolean isKeyword(String word) {
        return config.keywords.contains(word);
    }
    
    public JsonBody getConfig() {
        try {
            File file = new File(Directories.getProjectFolder() + path);
            String fileContents = FileLoader.loadTextFromFile(file);
            JsonBody theme = (JsonBody) Json.fromJson(fileContents, JsonBody.class);
            
            return theme;
        }
        catch(Exception e) {
            Console.log(e);
        }
        
        return null;
    }
}
