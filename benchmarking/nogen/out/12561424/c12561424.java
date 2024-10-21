class c12561424 {

    private boolean copyFile(File file) throws RuntimeException {
        JavaCIPUnknownScope.destination = new File(ServiceLocator.getSqliteDir(), file.getName());
        JavaCIPUnknownScope.logger.debug("Writing to: " + JavaCIPUnknownScope.destination);
        if (JavaCIPUnknownScope.destination.exists()) {
            Frame.showMessage(ServiceLocator.getText("Error.file.exists") + file.getName(), null);
            JavaCIPUnknownScope.logger.debug("File already exists: " + file);
            return false;
        }
        JavaCIPUnknownScope.destination.createNewFile();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(JavaCIPUnknownScope.destination);
            int read = 0;
            byte[] buffer = new byte[2048];
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
        return true;
    }
}
