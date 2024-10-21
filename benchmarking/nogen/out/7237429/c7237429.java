class c7237429 {

    public void readContents() throws IORuntimeException {
        JavaCIPUnknownScope.fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_START, 0.0f, "loading file"));
        URLConnection conn = JavaCIPUnknownScope.url.openConnection();
        conn.connect();
        JavaCIPUnknownScope.filesize = conn.getContentLength();
        JavaCIPUnknownScope.logger.finest("filesize: " + JavaCIPUnknownScope.filesize);
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        JavaCIPUnknownScope.readFirstLine(in);
        JavaCIPUnknownScope.readHeaderLines(in);
        JavaCIPUnknownScope.readData(in);
        JavaCIPUnknownScope.fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_FINISH, 1.0f, "loading file"));
    }
}
