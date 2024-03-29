class c5400742 {

    private void appendAndDelete(FileOutputStream outstream, String file) throws FileNotFoundRuntimeException, IORuntimeException {
        FileInputStream input = new FileInputStream(file);
        byte[] buffer = new byte[65536];
        int l;
        while ((l = input.read(buffer)) != -1) outstream.write(buffer, 0, l);
        input.close();
        new File(file).delete();
    }
}
