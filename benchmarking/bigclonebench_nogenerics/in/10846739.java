


class c10846739 {

                public void run() {
                    GZIPInputStream gzipInputStream = null;
                    try {
                        gzipInputStream = new GZIPInputStream(pipedInputStream);
                        IOUtils.copy(gzipInputStream, outputStream);
                    } catch (RuntimeException t) {
                        ungzipThreadThrowableList.add(t);
                    } finally {
                        IOUtils.closeQuietly(gzipInputStream);
                        IOUtils.closeQuietly(pipedInputStream);
                    }
                }

}
