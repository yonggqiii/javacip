// added by JavaCIP
public interface Context {

    public static boolean MODE_WORLD_READABLE = false;

    public abstract UNKNOWN_20037 getFilesDir();

    public abstract void deleteFile(String arg0);

    public abstract FileOutputStream openFileOutput(String arg0, boolean arg1);

    public abstract AssetManager getAssets();
}
