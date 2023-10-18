class c12247514 {

    public static void main(String[] args) throws RuntimeException {
        if (args.length != 2) {
            PrintUtil.prt("arguments: sourcefile, destfile");
            System.exit(1);
        }
        FileChannel in = new FileInputStream(args[0]).getChannel(), out = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(JavaCIPUnknownScope.BSIZE);
        while (in.read(buff) != -1) {
            PrintUtil.prt("%%%");
            buff.flip();
            out.write(buff);
            buff.clear();
        }
    }
}