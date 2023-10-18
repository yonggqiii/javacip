class c14231450 {

    public byte[] download(URL url, OutputStream out) throws IORuntimeException {
        boolean returnByByteArray = (out == null);
        ByteArrayOutputStream helper = null;
        if (returnByByteArray) {
            helper = new ByteArrayOutputStream();
        }
        String s = url.toExternalForm();
        URLConnection conn = url.openConnection();
        String name = Launcher.getFileName(s);
        InputStream in = conn.getInputStream();
        JavaCIPUnknownScope.total = url.openConnection().getContentLength();
        JavaCIPUnknownScope.setStatusText(String.format("Downloading %s (%.2fMB)...", name, ((float) JavaCIPUnknownScope.total / 1024 / 1024)));
        long justNow = System.currentTimeMillis();
        int numRead = -1;
        byte[] buffer = new byte[2048];
        while ((numRead = in.read(buffer)) != -1) {
            JavaCIPUnknownScope.size += numRead;
            if (returnByByteArray) {
                helper.write(buffer, 0, numRead);
            } else {
                out.write(buffer, 0, numRead);
            }
            long now = System.currentTimeMillis();
            if ((now - justNow) > 250) {
                JavaCIPUnknownScope.setProgress((int) (((float) JavaCIPUnknownScope.size / (float) JavaCIPUnknownScope.total) * 100));
                justNow = now;
            }
        }
        JavaCIPUnknownScope.hideProgress();
        if (returnByByteArray) {
            return helper.toByteArray();
        } else {
            return null;
        }
    }
}
