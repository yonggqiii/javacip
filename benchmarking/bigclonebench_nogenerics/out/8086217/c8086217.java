class c8086217 {

    public void write(OutputStream out, String className, InputStream classDefStream) throws IORuntimeException {
        ByteArrayOutputStream a = new ByteArrayOutputStream();
        IOUtils.copy(classDefStream, a);
        a.close();
        DataOutputStream da = new DataOutputStream(out);
        da.writeUTF(className);
        da.writeUTF(new String(JavaCIPUnknownScope.base64.cipher(a.toByteArray())));
    }
}
