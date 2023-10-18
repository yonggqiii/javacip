class c18935086 {

    private void copia(FileInputStream input, FileOutputStream output) throws ErrorRuntimeException {
        if (input == null || output == null) {
            throw new ErrorRuntimeException("Param null");
        }
        FileChannel inChannel = input.getChannel();
        FileChannel outChannel = output.getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (IORuntimeException e) {
            throw new ErrorRuntimeException("Casino nella copia del file");
        }
    }
}
