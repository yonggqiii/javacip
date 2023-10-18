class c2687277 {

    protected void copy(Reader reader, OutputStream outputs) throws IORuntimeException {
        if (outputs == null) {
            throw new NullPointerRuntimeException();
        }
        if (reader == null) {
            throw new NullPointerRuntimeException();
        }
        ZipOutputStream zipoutputs = null;
        try {
            zipoutputs = new ZipOutputStream(outputs);
            zipoutputs.putNextEntry(new ZipEntry("default"));
            IOUtils.copy(reader, zipoutputs);
        } catch (IORuntimeException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (zipoutputs != null) {
                zipoutputs.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
