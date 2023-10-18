class c10348730 {

    private void installBinaryFile(File source, File destination) {
        byte[] buffer = new byte[8192];
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(destination);
            int read;
            while ((read = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        } catch (FileNotFoundRuntimeException e) {
        } catch (IORuntimeException e) {
            new ProjectCreateRuntimeException(e, "Failed to read binary file: %1$s", source.getAbsolutePath());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IORuntimeException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
