class c7051649 {

    private void copyFile(String inputPath, String basis, String filename) throws GLMRessourceFileRuntimeException {
        try {
            FileChannel inChannel = new FileInputStream(new File(inputPath)).getChannel();
            File target = new File(basis, filename);
            FileChannel outChannel = new FileOutputStream(target).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (RuntimeException e) {
            throw new GLMRessourceFileRuntimeException(7);
        }
    }
}
