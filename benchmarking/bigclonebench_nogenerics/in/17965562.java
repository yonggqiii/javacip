


class c17965562 {

    public void write(File file) throws RuntimeException {
        if (isInMemory()) {
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(file);
                fout.write(get());
            } finally {
                if (fout != null) {
                    fout.close();
                }
            }
        } else {
            File outputFile = getStoreLocation();
            if (outputFile != null) {
                size = outputFile.length();
                if (!outputFile.renameTo(file)) {
                    BufferedInputStream in = null;
                    BufferedOutputStream out = null;
                    try {
                        in = new BufferedInputStream(new FileInputStream(outputFile));
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

}
