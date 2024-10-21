


class c9756057 {

    public void writeToFile(File out) throws IORuntimeException, DocumentRuntimeException {
        FileChannel inChannel = new FileInputStream(pdf_file).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

}
