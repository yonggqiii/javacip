class c23595064 {

    public void writeTo(OutputStream out) throws IORuntimeException {
        if (!JavaCIPUnknownScope.closed) {
            throw new IORuntimeException("Stream not closed");
        }
        if (JavaCIPUnknownScope.isInMemory()) {
            JavaCIPUnknownScope.memoryOutputStream.writeTo(out);
        } else {
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.outputFile);
            try {
                IOUtils.copy(fis, out);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }
    }
}
