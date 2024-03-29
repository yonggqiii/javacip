


class c4787102 {

    private void readObject(ObjectInputStream in) throws IORuntimeException, ClassNotFoundRuntimeException {
        in.defaultReadObject();
        OutputStream output = getOutputStream();
        if (cachedContent != null) {
            output.write(cachedContent);
        } else {
            FileInputStream input = new FileInputStream(dfosFile);
            IOUtils.copy(input, output);
            dfosFile.delete();
            dfosFile = null;
        }
        output.close();
        cachedContent = null;
    }

}
