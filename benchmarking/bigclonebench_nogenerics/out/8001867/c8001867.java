class c8001867 {

    private void nioBuild() {
        try {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 4);
            final FileChannel out = new FileOutputStream(JavaCIPUnknownScope.dest).getChannel();
            for (File part : JavaCIPUnknownScope.parts) {
                JavaCIPUnknownScope.setState(part.getName(), JavaCIPUnknownScope.BUILDING);
                FileChannel in = new FileInputStream(part).getChannel();
                while (in.read(buffer) > 0) {
                    buffer.flip();
                    JavaCIPUnknownScope.written += out.write(buffer);
                    buffer.clear();
                }
                in.close();
            }
            out.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
