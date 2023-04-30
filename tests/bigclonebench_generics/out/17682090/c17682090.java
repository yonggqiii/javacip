class c17682090 {

    public Reader transform(Reader reader, Map<String, Object> parameterMap) {
        try {
            File file = File.createTempFile("srx2", ".srx");
            file.deleteOnExit();
            Writer writer = JavaCIPUnknownScope.getWriter(JavaCIPUnknownScope.getFileOutputStream(file.getAbsolutePath()));
            JavaCIPUnknownScope.transform(reader, writer, parameterMap);
            writer.close();
            Reader resultReader = JavaCIPUnknownScope.getReader(JavaCIPUnknownScope.getFileInputStream(file.getAbsolutePath()));
            return resultReader;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}
