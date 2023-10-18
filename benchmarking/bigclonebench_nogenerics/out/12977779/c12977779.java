class c12977779 {

    public void run() {
        FileInputStream src;
        FileOutputStream dest;
        try {
            dest = new FileOutputStream(JavaCIPUnknownScope.srcName);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
            return;
        }
        FileChannel destC = dest.getChannel();
        FileChannel srcC;
        ByteBuffer buf = ByteBuffer.allocateDirect(JavaCIPUnknownScope.BUFFER_SIZE);
        try {
            int fileNo = 0;
            while (true) {
                int i = 1;
                String destName = JavaCIPUnknownScope.srcName + "_" + fileNo;
                src = new FileInputStream(destName);
                srcC = src.getChannel();
                while ((i > 0)) {
                    i = srcC.read(buf);
                    buf.flip();
                    destC.write(buf);
                    buf.compact();
                }
                srcC.close();
                src.close();
                fileNo++;
            }
        } catch (IORuntimeException e1) {
            e1.printStackTrace();
            return;
        }
    }
}