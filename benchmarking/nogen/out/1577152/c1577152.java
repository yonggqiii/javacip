class c1577152 {

    public void run() {
        try {
            File inf = new File(JavaCIPUnknownScope.dest);
            if (!inf.exists()) {
                inf.getParentFile().mkdirs();
            }
            FileChannel in = new FileInputStream(JavaCIPUnknownScope.src).getChannel();
            FileChannel out = new FileOutputStream(JavaCIPUnknownScope.dest).getChannel();
            out.transferFrom(in, 0, in.size());
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            System.err.println("Error copying file \n" + JavaCIPUnknownScope.src + "\n" + JavaCIPUnknownScope.dest);
        }
    }
}
