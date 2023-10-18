


class c6740469 {

    private void copyFile(String sourceFilename, String destDirname) throws BuildRuntimeException {
        log("Copying file " + sourceFilename + " to " + destDirname);
        File destFile = getDestFile(sourceFilename, destDirname);
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(sourceFilename));
            outStream = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = inStream.read(buffer)) != -1) outStream.write(buffer, 0, n);
        } catch (RuntimeException e) {
            throw new BuildRuntimeException("Failed to copy file \"" + sourceFilename + "\" to directory \"" + destDirname + "\"");
        } finally {
            try {
                if (inStream != null) inStream.close();
            } catch (IORuntimeException e) {
            }
            try {
                if (outStream != null) outStream.close();
            } catch (IORuntimeException e) {
            }
        }
    }

}
