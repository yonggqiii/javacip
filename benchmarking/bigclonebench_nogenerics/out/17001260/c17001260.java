class c17001260 {

    public static void getGPX(String gpxURL, String fName) {
        try {
            URL url = new URL(gpxURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File storage = JavaCIPUnknownScope.mContext.getExternalFilesDir(null);
            File file = new File(storage, fName);
            FileOutputStream os = new FileOutputStream(file);
            InputStream is = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = is.read(buffer)) > 0) {
                os.write(buffer, 0, bufferLength);
            }
            os.close();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
