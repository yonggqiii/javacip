class c6456825 {

    protected final Boolean doInBackground(Void... v) {
        Bitmap bmp = ((BitmapDrawable) ((ImageView) JavaCIPUnknownScope.findViewById(JavaCIPUnknownScope.R.id.post_img)).getDrawable()).getBitmap();
        HttpURLConnection con;
        try {
            URL url = new URL(JavaCIPUnknownScope.POST_URL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Accept-Language", "multipart/form-data");
            con.setRequestProperty("X-RAW", "true");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.finish();
            return false;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.finish();
            return false;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 100, bos);
        OutputStream os = null;
        try {
            os = con.getOutputStream();
            os.write(bos.toByteArray());
            os.flush();
            os.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            try {
                os.close();
            } catch (IORuntimeException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        InputStream is = null;
        BufferedReader reader;
        try {
            is = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            is.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IORuntimeException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        String s;
        try {
            while ((s = reader.readLine()) != null) {
                Log.v(JavaCIPUnknownScope.TAG, s);
            }
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
