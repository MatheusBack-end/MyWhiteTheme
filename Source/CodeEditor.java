public class CodeEditor extends TextScriptingExtension
{
    private File current_file;
    
    @Override
    public void init()
    {
        CodeTheme theme = new CodeTheme();
        super.setTheme(theme.get());
        super.setStyler(new CodeStyler(theme.config));
    }
    
    @Override
    public void openScript(File file)
    {
        String contents = "";
        
        try
        {
            contents = FileLoader.loadTextFromFile(file);
        }
        
        catch(Exception e)
        {
            Console.log(e);
        }
        
        current_file = file;
        setText(contents);
    }
    
    @Override
    public boolean saveScript()
    {
        try
        {
            FileLoader.exportTextToFile(getText(), current_file);
            return true;
        }
        
        catch(Exception e)
        {
            Console.log(e);
        }
        
        return false;
    }
    
    @Override
    public boolean supportFile(File file)
    {
        return file.getAbsolutePath().endsWith(".jv") || file.getAbsolutePath().endsWith(".json");
    }
}
