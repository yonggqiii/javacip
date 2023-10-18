class c4787102 {

    private void readObject(ObjectInputStream in) throws IORuntimeException, ClassNotFoundRuntimeException {
        in.defaultReadObject();
        OutputStream output = JavaCIPUnknownScope.getOutputStream();
        if (JavaCIPUnknownScope.cachedContent != null) {
            output.write(JavaCIPUnknownScope.cachedContent);
        } else {
            FileInputStream input = new FileInputStream(JavaCIPUnknownScope.dfosFile);
            IOUtils.copy(input, output);
            JavaCIPUnknownScope.dfosFile.delete();
            JavaCIPUnknownScope.dfosFile = null;
        }
        output.close();
        JavaCIPUnknownScope.cachedContent = null;
    }
}
