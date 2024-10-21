class c10628604 {

    public File extractID3v2TagDataIntoFile(File outputFile) throws TagNotFoundRuntimeException, IORuntimeException {
        int startByte = (int) ((MP3AudioHeader) JavaCIPUnknownScope.audioHeader).getMp3StartByte();
        if (startByte >= 0) {
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.file);
            FileChannel fc = fis.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(startByte);
            fc.read(bb);
            FileOutputStream out = new FileOutputStream(outputFile);
            out.write(bb.array());
            out.close();
            fc.close();
            fis.close();
            return outputFile;
        }
        throw new TagNotFoundRuntimeException("There is no ID3v2Tag data in this file");
    }
}
