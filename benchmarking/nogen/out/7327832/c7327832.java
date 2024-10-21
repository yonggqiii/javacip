class c7327832 {

    protected File doInBackground(String... params) {
        try {
            String urlString = params[0];
            final String fileName = params[1];
            if (!urlString.endsWith("/")) {
                urlString += "/";
            }
            urlString += "apk/" + fileName;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            File dir = new File(Environment.getExternalStorageDirectory(), "imogenemarket");
            dir.mkdirs();
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int count;
            int bigCount = 0;
            while ((count = input.read(data)) != -1) {
                if (JavaCIPUnknownScope.isCancelled()) {
                    break;
                }
                bigCount += count;
                if (!JavaCIPUnknownScope.mLocker.isLocked()) {
                    JavaCIPUnknownScope.publishProgress(bigCount);
                    bigCount = 0;
                    JavaCIPUnknownScope.mLocker.lock();
                }
                output.write(data, 0, count);
            }
            JavaCIPUnknownScope.mLocker.cancel();
            JavaCIPUnknownScope.publishProgress(bigCount);
            output.flush();
            output.close();
            input.close();
            if (JavaCIPUnknownScope.isCancelled()) {
                file.delete();
                return null;
            }
            return file;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
