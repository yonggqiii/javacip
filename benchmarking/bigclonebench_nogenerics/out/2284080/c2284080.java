class c2284080 {

    private static void copy(String srcFilename, String dstFilename, boolean override) throws IORuntimeException, XPathFactoryConfigurationRuntimeException, SAXRuntimeException, ParserConfigurationRuntimeException, XPathExpressionRuntimeException {
        File fileToCopy = new File(JavaCIPUnknownScope.rootDir + "test-output/" + srcFilename);
        if (fileToCopy.exists()) {
            File newFile = new File(JavaCIPUnknownScope.rootDir + "test-output/" + dstFilename);
            if (!newFile.exists() || override) {
                try {
                    FileChannel srcChannel = new FileInputStream(JavaCIPUnknownScope.rootDir + "test-output/" + srcFilename).getChannel();
                    FileChannel dstChannel = new FileOutputStream(JavaCIPUnknownScope.rootDir + "test-output/" + dstFilename).getChannel();
                    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                    srcChannel.close();
                    dstChannel.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
