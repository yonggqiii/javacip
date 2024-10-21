class c21570965 {

    public static void main(String[] args) throws FileNotFoundRuntimeException, IORuntimeException {
        String filePath = "/Users/francisbaril/Downloads/test-1.pdf";
        String testFilePath = "/Users/francisbaril/Desktop/testpdfbox/test.pdf";
        File file = new File(filePath);
        final File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
        IOUtils.copy(new FileInputStream(file), new FileOutputStream(testFile));
        System.out.println(JavaCIPUnknownScope.getLongProperty(new FileInputStream(testFile), JavaCIPUnknownScope.PROPRIETE_ID_IGID));
    }
}
