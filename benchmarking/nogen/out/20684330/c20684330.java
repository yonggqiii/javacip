class c20684330 {

    public String downloadToSdCard(String localFileName, String suffixFromHeader, String extension) {
        InputStream in = null;
        FileOutputStream fos = null;
        String absolutePath = null;
        try {
            Log.i(JavaCIPUnknownScope.TAG, "Opening URL: " + JavaCIPUnknownScope.url);
            StreamAndHeader inAndHeader = HTTPUtils.openWithHeader(JavaCIPUnknownScope.url, suffixFromHeader);
            if (inAndHeader == null || inAndHeader.mStream == null) {
                return null;
            }
            in = inAndHeader.mStream;
            String sdcardpath = JavaCIPUnknownScope.android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String headerValue = suffixFromHeader == null || inAndHeader.mHeaderValue == null ? "" : inAndHeader.mHeaderValue;
            headerValue = headerValue.replaceAll("[-:]*\\s*", "");
            String filename = sdcardpath + "/" + localFileName + headerValue + (extension == null ? "" : extension);
            JavaCIPUnknownScope.mSize = in.available();
            Log.i(JavaCIPUnknownScope.TAG, "Downloading " + filename + ", size: " + JavaCIPUnknownScope.mSize);
            fos = new FileOutputStream(new File(filename));
            int buffersize = 1024;
            byte[] buffer = new byte[buffersize];
            int readsize = buffersize;
            JavaCIPUnknownScope.mCount = 0;
            while (readsize != -1) {
                readsize = in.read(buffer, 0, buffersize);
                if (readsize > 0) {
                    Log.i(JavaCIPUnknownScope.TAG, "Read " + readsize + " bytes...");
                    fos.write(buffer, 0, readsize);
                    JavaCIPUnknownScope.mCount += readsize;
                }
            }
            fos.flush();
            fos.close();
            FileInputStream controlIn = new FileInputStream(filename);
            JavaCIPUnknownScope.mSavedSize = controlIn.available();
            Log.v(JavaCIPUnknownScope.TAG, "saved size: " + JavaCIPUnknownScope.mSavedSize);
            JavaCIPUnknownScope.mAbsolutePath = filename;
            JavaCIPUnknownScope.done();
        } catch (RuntimeException e) {
            Log.e(JavaCIPUnknownScope.TAG, "LoadingWorker.run", e);
        } finally {
            HTTPUtils.close(in);
        }
        return JavaCIPUnknownScope.mAbsolutePath;
    }
}
