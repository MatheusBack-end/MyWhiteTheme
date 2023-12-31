package JAVARuntime;

public class CodeEditor extends TextScriptingExtension {
    
    private File currentFile;
    private JsonBody config;
    private TextScriptingTheme theme;
    private CodeStyler styler;
    private boolean isLoad = false;
    
    @Override
    public void init() {
        load();
        
        super.setTheme(theme);
        super.setStyler(styler);
    }
    
    @Override
    public void openScript(File file) {
        String contents = "";
        
        try {
            contents = FileLoader.loadTextFromFile(file);
        }
        catch(Exception e) {
            Console.log(e);
        }
        
        currentFile = file;
        setText(contents);
    }
    
    @Override
    public boolean saveScript() {
        try {
            FileLoader.exportTextToFile(getText(), currentFile);
            return true;
        }
        catch(Exception e) {
            Console.log(e);
        }
        
        return false;
    }
    
    @Override
    public boolean supportFile(File file) {
        load();
        
        if(this.config == null)
            return false;
               
        for(String fileType: this.config.supportFiles) {
            if(file.getAbsolutePath().endsWith(fileType))
                return true;
        }
        
        return false;
    }
    
    private void load() {
        if(isLoad)
            return;
            
        CodeTheme codeTheme = new CodeTheme();
        theme = codeTheme.get();
        styler = new CodeStyler(codeTheme.config);
        
        // TODO: remove rendundance fields
        config = codeTheme.config.config;
        
        isLoad = true;
    }
}
