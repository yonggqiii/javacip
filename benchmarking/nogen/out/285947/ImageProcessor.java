// added by JavaCIP
public interface ImageProcessor {

    public abstract void setMinAndMax(double arg0, double arg1);

    public abstract ImageProcessor resize(int arg0, int arg1);

    public abstract ColorModel getColorModel();

    public abstract double getMin();

    public abstract ImageProcessor convertToByte(boolean arg0);

    public abstract double getMax();
}
