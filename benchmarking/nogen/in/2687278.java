


class c2687278 {

    @Override
    protected void copy(InputStream inputs, OutputStream outputs) throws IORuntimeException {
        if (outputs == null) {
            throw new NullPointerRuntimeException();
        }
        if (inputs == null) {
            throw new NullPointerRuntimeException();
        }
        ZipOutputStream zipoutputs = null;
        try {
            zipoutputs = new ZipOutputStream(outputs);
            zipoutputs.putNextEntry(new ZipEntry("default"));
            IOUtils.copy(inputs, zipoutputs);
        } catch (IORuntimeException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (zipoutputs != null) {
                zipoutputs.close();
            }
            if (inputs != null) {
                inputs.close();
            }
        }
    }

}
