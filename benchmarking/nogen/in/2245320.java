


class c2245320 {

    void writeToFile(String dir, InputStream input, String fileName) throws FileNotFoundRuntimeException, IORuntimeException {
        makeDirs(dir);
        FileOutputStream fo = null;
        try {
            System.out.println(Thread.currentThread().getName() + " : " + "Writing file " + fileName + " to path " + dir);
            File file = new File(dir, fileName);
            fo = new FileOutputStream(file);
            IOUtils.copy(input, fo);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Failed to write " + fileName);
        }
    }

}
