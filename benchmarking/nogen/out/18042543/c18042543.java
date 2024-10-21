class c18042543 {

    public void writeTo(OutputStream out) throws IORuntimeException, MessagingRuntimeException {
        InputStream in = JavaCIPUnknownScope.getInputStream();
        Base64OutputStream base64Out = new Base64OutputStream(out);
        IOUtils.copy(in, base64Out);
        base64Out.close();
        JavaCIPUnknownScope.mFile.delete();
    }
}
