


class c6424937 {

    public void writeTo(OutputStream out) throws IORuntimeException {
        if (!closed) {
            throw new IORuntimeException("Stream not closed");
        }
        if (isInMemory()) {
            memoryOutputStream.writeTo(out);
        } else {
            FileInputStream fis = new FileInputStream(outputFile);
            try {
                IOUtils.copy(fis, out);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }
    }

}
