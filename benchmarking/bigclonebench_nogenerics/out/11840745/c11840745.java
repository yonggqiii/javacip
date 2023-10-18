class c11840745 {

    public final void close() throws IORuntimeException {
        if (JavaCIPUnknownScope.dataStream == null)
            throw new NullPointerRuntimeException("Write stream is null.");
        JavaCIPUnknownScope.dataStream.flush();
        JavaCIPUnknownScope.dataStream.close();
        JavaCIPUnknownScope.dataStream = null;
        File tmpFile = new File(JavaCIPUnknownScope.packPath + ".tmp");
        FileOutputStream packStream = new FileOutputStream(JavaCIPUnknownScope.packPath);
        try {
            String nbFiles = Long.toString(JavaCIPUnknownScope.currentNbFiles) + "\0";
            packStream.write(JavaCIPUnknownScope.FLAT_PACK_HEADER.getBytes(Charsets.ISO_8859_1));
            JavaCIPUnknownScope.structBufferWriter.flush();
            JavaCIPUnknownScope.structBufferWriter.close();
            int headerSize = JavaCIPUnknownScope.structBuffer.size() + nbFiles.length();
            packStream.write(Integer.toString(headerSize).getBytes(Charsets.ISO_8859_1));
            packStream.write('\0');
            packStream.write(nbFiles.getBytes(Charsets.ISO_8859_1));
            JavaCIPUnknownScope.structBuffer.writeTo(packStream);
            JavaCIPUnknownScope.structBufferWriter = null;
            JavaCIPUnknownScope.structBuffer = null;
            FileInputStream in = new FileInputStream(tmpFile);
            try {
                byte[] buffer = new byte[JavaCIPUnknownScope.FILE_COPY_BUFFER_LEN];
                int read;
                while ((read = in.read(buffer)) > 0) packStream.write(buffer, 0, read);
                packStream.flush();
                packStream.close();
            } finally {
                Utilities.closeStream(in);
            }
        } finally {
            Utilities.closeStream(packStream);
        }
        if (tmpFile.isFile())
            Utilities.deleteFile(tmpFile);
        JavaCIPUnknownScope.packPath = null;
        JavaCIPUnknownScope.structBuffer = null;
    }
}
