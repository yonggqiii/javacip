class c15376025 {

    protected svm_model loadModel(InputStream inputStream) throws IORuntimeException {
        File tmpFile = File.createTempFile("tmp", ".mdl");
        FileOutputStream output = new FileOutputStream(tmpFile);
        try {
            IOUtils.copy(inputStream, output);
            return JavaCIPUnknownScope.libsvm.svm.svm_load_model(tmpFile.getPath());
        } finally {
            output.close();
            tmpFile.delete();
        }
    }
}
