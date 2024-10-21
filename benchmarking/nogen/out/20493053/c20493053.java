class c20493053 {

    public static boolean copyFile(File from, File to) {
        try {
            FileChannel fromChannel = new FileInputStream(from).getChannel();
            FileChannel toChannel = new FileOutputStream(to).getChannel();
            toChannel.transferFrom(fromChannel, 0, fromChannel.size());
            fromChannel.close();
            toChannel.close();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error("failed to copy " + from.getAbsolutePath() + " to " + to.getAbsolutePath() + ": caught exception", e);
            return false;
        }
        return true;
    }
}
