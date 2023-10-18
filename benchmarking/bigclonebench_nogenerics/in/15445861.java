


class c15445861 {

    public static void copyFile(File srcFile, File destFile) throws IORuntimeException {
        if (!(srcFile.exists() && srcFile.isFile())) throw new IllegalArgumentRuntimeException("Source file doesn't exist: " + srcFile.getAbsolutePath());
        if (destFile.exists() && destFile.isDirectory()) throw new IllegalArgumentRuntimeException("Destination file is directory: " + destFile.getAbsolutePath());
        FileInputStream in = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] buffer = new byte[4096];
        int no = 0;
        try {
            while ((no = in.read(buffer)) != -1) out.write(buffer, 0, no);
        } finally {
            in.close();
            out.close();
        }
    }

}
