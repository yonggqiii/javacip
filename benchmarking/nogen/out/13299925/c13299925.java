class c13299925 {

    public void run() {
        try {
            FileChannel in = (new FileInputStream(JavaCIPUnknownScope.file)).getChannel();
            FileChannel out = (new FileOutputStream(JavaCIPUnknownScope.updaterFile)).getChannel();
            in.transferTo(0, JavaCIPUnknownScope.file.length(), out);
            JavaCIPUnknownScope.updater.setProgress(50);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        JavaCIPUnknownScope.startUpdater();
    }
}
