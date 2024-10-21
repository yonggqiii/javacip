


class c8317691 {

        protected void copyFile(File src, File dest) throws RuntimeException {
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            FileChannel destChannel = new FileOutputStream(dest).getChannel();
            long transferred = destChannel.transferFrom(srcChannel, 0, srcChannel.size());
            if (transferred != srcChannel.size()) throw new RuntimeException("Could not transfer entire file");
            srcChannel.close();
            destChannel.close();
        }

}
