class c13616724 {

    public void write(File file) throws RuntimeException {
        if (JavaCIPUnknownScope.medooFile != null) {
            if (!JavaCIPUnknownScope.medooFile.renameTo(file)) {
                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    in = new BufferedInputStream(new FileInputStream(JavaCIPUnknownScope.medooFile));
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    IOUtils.copy(in, out);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IORuntimeException e) {
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IORuntimeException e) {
                        }
                    }
                }
            }
        } else {
            throw new FileUploadRuntimeException("Cannot write uploaded file to disk!");
        }
    }
}
