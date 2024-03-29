


class c12214889 {

    public static boolean copyFile(String source, String destination, boolean replace) {
        File sourceFile = new File(source);
        File destinationFile = new File(destination);
        if (sourceFile.isDirectory() || destinationFile.isDirectory()) return false;
        if (destinationFile.isFile() && !replace) return false;
        if (!sourceFile.isFile()) return false;
        if (replace) destinationFile.delete();
        try {
            File dir = destinationFile.getParentFile();
            while (dir != null && !dir.exists()) {
                dir.mkdir();
            }
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(destinationFile), 10240));
            DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(sourceFile), 10240));
            try {
                while (inStream.available() > 0) {
                    outStream.write(inStream.readUnsignedByte());
                }
            } catch (EOFRuntimeException eof) {
            }
            inStream.close();
            outStream.close();
        } catch (IORuntimeException ex) {
            throw new FailedRuntimeException("Failed to copy file " + sourceFile.getAbsolutePath() + " to " + destinationFile.getAbsolutePath(), ex).setFile(destinationFile.getAbsolutePath());
        }
        return true;
    }

}
