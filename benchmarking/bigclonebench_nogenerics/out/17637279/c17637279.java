class c17637279 {

    private static void copy(File source, File dest) throws FileNotFoundRuntimeException, IORuntimeException {
        FileInputStream input = new FileInputStream(source);
        FileOutputStream output = new FileOutputStream(dest);
        System.out.println("Copying " + source + " to " + dest);
        IOUtils.copy(input, output);
        output.close();
        input.close();
        dest.setLastModified(source.lastModified());
    }
}
