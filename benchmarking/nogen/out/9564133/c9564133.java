class c9564133 {

    public void copy(File aSource, File aDestDir) throws IORuntimeException {
        FileInputStream myInFile = new FileInputStream(aSource);
        FileOutputStream myOutFile = new FileOutputStream(new File(aDestDir, aSource.getName()));
        FileChannel myIn = myInFile.getChannel();
        FileChannel myOut = myOutFile.getChannel();
        boolean end = false;
        while (true) {
            int myBytes = myIn.read(JavaCIPUnknownScope.theBuffer);
            if (myBytes != -1) {
                JavaCIPUnknownScope.theBuffer.flip();
                myOut.write(JavaCIPUnknownScope.theBuffer);
                JavaCIPUnknownScope.theBuffer.clear();
            } else
                break;
        }
        myIn.close();
        myOut.close();
        myInFile.close();
        myOutFile.close();
        long myEnd = System.currentTimeMillis();
    }
}
