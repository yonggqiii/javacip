


class c7682648 {

    public static void copyFile(File oldPathFile, File newPathFile) throws IORuntimeException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(oldPathFile));
            out = new BufferedOutputStream(new FileOutputStream(newPathFile));
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            while (in.read(buffer) > 0) out.write(buffer);
        } finally {
            if (null != in) in.close();
            if (null != out) out.close();
        }
    }

}
