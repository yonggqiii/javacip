class c12261686 {

    protected byte[] fetch0() throws IORuntimeException {
        if (JavaCIPUnknownScope.sourceFile.getProtocol().equalsIgnoreCase("jar")) {
            throw new IORuntimeException("Jar protocol unsupported!");
        } else {
            URL url;
            if (JavaCIPUnknownScope.sourceFile.getFile().endsWith(JavaCIPUnknownScope.CLASS_FILE_EXTENSION)) {
                url = JavaCIPUnknownScope.sourceFile;
            } else {
                url = new URL(JavaCIPUnknownScope.sourceFile, JavaCIPUnknownScope.className.replace(JavaCIPUnknownScope.PACKAGE_SEPARATOR, JavaCIPUnknownScope.URL_DIRECTORY_SEPARATOR) + JavaCIPUnknownScope.CLASS_FILE_EXTENSION);
            }
            InputStream stream = url.openStream();
            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[JavaCIPUnknownScope.PACKET_SIZE];
                int bytesRead;
                while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                return output.toByteArray();
            } finally {
                stream.close();
            }
        }
    }
}
