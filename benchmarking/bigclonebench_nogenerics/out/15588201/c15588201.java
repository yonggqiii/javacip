class c15588201 {

    public void copy(File source, File destination) {
        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            FileOutputStream fileOutputStream = new FileOutputStream(destination);
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();
            JavaCIPUnknownScope.transfer(inputChannel, outputChannel, source.length(), 1024 * 1024 * 32, true, true);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
