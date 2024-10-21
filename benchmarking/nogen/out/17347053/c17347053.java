class c17347053 {

    public HttpResponse makeRequest() throws RequestCancelledRuntimeException, IllegalStateRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.checkState();
        OutputStream out = null;
        InputStream in = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.destFile));
            URLConnection conn = JavaCIPUnknownScope.url.openConnection();
            in = conn.getInputStream();
            byte[] buffer = new byte[JavaCIPUnknownScope.BUFFRE_SIZE];
            int numRead;
            long totalSize = conn.getContentLength();
            long transferred = 0;
            JavaCIPUnknownScope.started(totalSize);
            while (!JavaCIPUnknownScope.checkAbortFlag() && (numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                out.flush();
                transferred += numRead;
                JavaCIPUnknownScope.progress(transferred);
            }
            if (JavaCIPUnknownScope.checkAbortFlag()) {
                JavaCIPUnknownScope.cancelled();
            } else {
                JavaCIPUnknownScope.finished();
            }
            if (JavaCIPUnknownScope.checkAbortFlag()) {
                throw new RequestCancelledRuntimeException();
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return null;
    }
}
