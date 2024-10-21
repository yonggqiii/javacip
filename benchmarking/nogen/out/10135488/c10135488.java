class c10135488 {

    public void run() {
        try {
            long pos = JavaCIPUnknownScope.begin;
            byte[] buf = new byte[1024];
            URLConnection cn = JavaCIPUnknownScope.url.openConnection();
            Utils.setHeader(cn);
            cn.setRequestProperty("Range", "bytes=" + JavaCIPUnknownScope.begin + "-" + JavaCIPUnknownScope.end);
            BufferedInputStream bis = new BufferedInputStream(cn.getInputStream());
            int len;
            while ((len = bis.read(buf)) > 0) {
                synchronized (JavaCIPUnknownScope.file) {
                    JavaCIPUnknownScope.file.seek(pos);
                    JavaCIPUnknownScope.file.write(buf, 0, len);
                }
                pos += len;
                Statics.getInstance().addComleted(len);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        JavaCIPUnknownScope.latch.countDown();
    }
}
