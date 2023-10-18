


class c8801744 {

    public static void copyFile(File src, File dest, boolean force) throws IORuntimeException {
        if (dest.exists()) {
            if (force) {
                dest.delete();
            } else {
                throw new IORuntimeException("Cannot overwrite existing file: " + dest);
            }
        }
        byte[] buffer = new byte[1];
        int read = 0;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            while (true) {
                read = in.read(buffer);
                if (read == -1) {
                    break;
                }
                out.write(buffer, 0, read);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }
    }

}
