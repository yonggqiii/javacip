class c8216603 {

    protected Object doInBackground() throws RuntimeException {
        ArchiveInputStream bufIn = null;
        FileOutputStream fileOut = null;
        try {
            bufIn = DecompressionWorker.guessStream(JavaCIPUnknownScope.fileToExtract);
            ArchiveEntry curZip = null;
            int progress = 0;
            while ((curZip = bufIn.getNextEntry()) != null) {
                if (!curZip.isDirectory()) {
                    byte[] content = new byte[(int) curZip.getSize()];
                    fileOut = new FileOutputStream(JavaCIPUnknownScope.extractionFile.getAbsolutePath() + File.separator + curZip.getName());
                    for (int i = 0; i < content.length; i++) {
                        fileOut.write(content[i]);
                    }
                    JavaCIPUnknownScope.publish(new Integer(progress));
                    progress++;
                }
            }
        } finally {
            if (bufIn != null) {
                bufIn.close();
            }
        }
        return null;
    }
}
