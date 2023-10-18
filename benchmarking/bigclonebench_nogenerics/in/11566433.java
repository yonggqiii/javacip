


class c11566433 {

    public void write(File file) throws RuntimeException {
        if (getGEDCOMFile() != null) {
            size = getGEDCOMFile().length();
            if (!getGEDCOMFile().renameTo(file)) {
                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    in = new BufferedInputStream(new FileInputStream(getGEDCOMFile()));
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
