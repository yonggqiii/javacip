class c3667136 {

    public void fileCopy2(File inFile, File outFile) throws RuntimeException {
        try {
            FileChannel srcChannel = new FileInputStream(inFile).getChannel();
            FileChannel dstChannel = new FileOutputStream(outFile).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (IORuntimeException e) {
            throw new RuntimeException("Could not copy file: " + inFile.getName());
        }
    }
}
