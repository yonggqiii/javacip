


class c18042543 {

    public void writeTo(OutputStream out) throws IORuntimeException, MessagingRuntimeException {
        InputStream in = getInputStream();
        Base64OutputStream base64Out = new Base64OutputStream(out);
        IOUtils.copy(in, base64Out);
        base64Out.close();
        mFile.delete();
    }

}
