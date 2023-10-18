class c17076120 {

    public String getTextData() {
        if (JavaCIPUnknownScope.tempFileWriter != null) {
            try {
                JavaCIPUnknownScope.tempFileWriter.flush();
                JavaCIPUnknownScope.tempFileWriter.close();
                FileReader in = new FileReader(JavaCIPUnknownScope.tempFile);
                StringWriter out = new StringWriter();
                int len;
                char[] buf = new char[JavaCIPUnknownScope.BUFSIZ];
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                out.close();
                in.close();
                return out.toString();
            } catch (IORuntimeException ioe) {
                Logger.instance().log(Logger.ERROR, JavaCIPUnknownScope.LOGGER_PREFIX, "XMLTextData.getTextData", ioe);
                return "";
            }
        } else if (JavaCIPUnknownScope.textBuffer != null)
            return JavaCIPUnknownScope.textBuffer.toString();
        else
            return null;
    }
}
