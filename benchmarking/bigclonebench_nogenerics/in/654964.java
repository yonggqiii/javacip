import java.net.*;
import java.io.*;


class c654964 {

    public static void main(String[] args) {
        boolean rotateLeft = false;
        boolean rotateRight = false;
        boolean exclude = false;
        boolean reset = false;
        float quality = 0f;
        int thumbArea = 12000;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-rotl")) rotateLeft = true;
            else if (args[i].equals("-rotr")) rotateRight = true;
            else if (args[i].equals("-exclude")) exclude = true;
            else if (args[i].equals("-reset")) reset = true;
            else if (args[i].equals("-quality")) quality = Float.parseFloat(args[++i]);
            else if (args[i].equals("-area")) thumbArea = Integer.parseInt(args[++i]);
            else {
                File f = new File(args[i]);
                try {
                    Tools t = new Tools(f);
                    if (exclude) {
                        URL url = t.getClass().getResource("exclude.jpg");
                        InputStream is = url.openStream();
                        File dest = t.getExcludeFile();
                        OutputStream os = new FileOutputStream(dest);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = is.read(buf)) > 0) os.write(buf, 0, len);
                        os.close();
                        is.close();
                        f = t.getOutFile();
                        f.delete();
                        f = t.getThumbFile();
                        f.delete();
                        System.exit(0);
                    }
                    if (reset) {
                        f = t.getOutFile();
                        f.delete();
                        f = t.getThumbFile();
                        f.delete();
                        f = t.getExcludeFile();
                        f.delete();
                        System.exit(0);
                    }
                    if (quality > 0) t.setQuality(quality);
                    if (t.getType() == Tools.THUMB || t.getType() == Tools.EXCLUDE) {f = t.getBaseFile(); t.load(f);} else {f = t.getSourceFile(); t.load(f);}
                    File out = t.getOutFile();
                    if (rotateLeft) t.rotateLeft(); else if (rotateRight) t.rotateRight();
                    t.save(out);
                    f = t.getThumbFile();
                    f.delete();
                    f = t.getExcludeFile();
                    f.delete();
                    System.exit(0);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "The operation could not be performed", "JPhotoAlbum", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }

}
