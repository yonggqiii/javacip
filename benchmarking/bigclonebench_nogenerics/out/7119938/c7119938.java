class c7119938 {

    public void putFile(CompoundName file, FileInputStream fileInput) throws IORuntimeException {
        File fullDir = new File(JavaCIPUnknownScope.REMOTE_BASE_DIR.getCanonicalPath());
        for (int i = 0; i < file.size() - 1; i++) fullDir = new File(fullDir, file.get(i));
        fullDir.mkdirs();
        File outputFile = new File(fullDir, file.get(file.size() - 1));
        FileOutputStream outStream = new FileOutputStream(outputFile);
        for (int byteIn = fileInput.read(); byteIn != -1; byteIn = fileInput.read()) outStream.write(byteIn);
        fileInput.close();
        outStream.close();
    }
}
