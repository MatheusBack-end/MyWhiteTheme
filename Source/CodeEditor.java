public class CodeEditor extends TextScriptingExtension
{
    private File current_file;
    
    @Override
    public void init()
    {
        super.setStyler(new CodeStyler());
        super.setTheme(new CodeTheme().get());
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
        return file.getAbsolutePath().endsWith(".jv");
    }
}
