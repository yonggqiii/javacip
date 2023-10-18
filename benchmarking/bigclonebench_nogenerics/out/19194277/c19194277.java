class c19194277 {

    public void create() throws IORuntimeException {
        FileChannel fc = new FileInputStream(JavaCIPUnknownScope.sourceFile).getChannel();
        for (RangeArrayElement element : JavaCIPUnknownScope.array) {
            FileChannel fc_ = fc.position(element.starting());
            File part = new File(JavaCIPUnknownScope.destinationDirectory, "_0x" + Long.toHexString(element.starting()) + ".partial");
            FileChannel partfc = new FileOutputStream(part).getChannel();
            partfc.transferFrom(fc_, 0, element.getSize());
            partfc.force(true);
            partfc.close();
        }
        fc.close();
    }
}
