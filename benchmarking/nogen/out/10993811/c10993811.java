class c10993811 {

    Bitmap downloadImage(String uri) {
        try {
            JavaCIPUnknownScope.mGetMethod.setURI(new URI(uri));
            HttpResponse resp = JavaCIPUnknownScope.mClient.execute(JavaCIPUnknownScope.mGetMethod);
            if (resp.getStatusLine().getStatusCode() < 400) {
                InputStream is = resp.getEntity().getContent();
                String tmp = JavaCIPUnknownScope.convertStreamToString(is);
                Log.d(JavaCIPUnknownScope.TAG, "hoge" + tmp);
                is.close();
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
