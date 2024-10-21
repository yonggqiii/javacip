// added by JavaCIP
public interface Camera {

    public static Camera open() {
        return null;
    }

    public abstract void setPreviewDisplay(SurfaceHolder arg0);

    public abstract Parameters getParameters();

    public abstract void setParameters(Parameters arg0);

    public abstract void startPreview();
}
