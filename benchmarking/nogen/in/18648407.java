


class c18648407 {

    public static void copyFile(File file, File destination) throws RuntimeException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(new FileOutputStream(destination));
            int c;
            while ((c = in.read()) != -1) out.write(c);
        } finally {
            try {
                if (out != null) out.close();
            } catch (RuntimeException e) {
            }
            try {
                if (in != null) in.close();
            } catch (RuntimeException e) {
            }
        }
    }

}
