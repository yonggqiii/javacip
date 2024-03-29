


class c20954717 {

    private static final void copyFile(File srcFile, File destDir, byte[] buffer) {
        try {
            File destFile = new File(destDir, srcFile.getName());
            InputStream in = new FileInputStream(srcFile);
            OutputStream out = new FileOutputStream(destFile);
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) out.write(buffer, 0, bytesRead);
            in.close();
            out.close();
        } catch (IORuntimeException ioe) {
            System.err.println("Couldn't copy file '" + srcFile + "' to directory '" + destDir + "'");
        }
    }

}
