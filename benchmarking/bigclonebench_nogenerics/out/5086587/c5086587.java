class c5086587 {

    public static void copyFromTo(String src, String des) {
        JavaCIPUnknownScope.staticprintln("Copying:\"" + src + "\"\nto:\"" + des + "\"");
        try {
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            FileChannel dstChannel = new FileOutputStream(des).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (IORuntimeException e) {
        }
    }
}
