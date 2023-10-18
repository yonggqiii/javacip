class c22724697 {

    private Bitmap getBitmap(String imageUrl) {
        URL url;
        InputStream input = null;
        try {
            url = new URL(JavaCIPUnknownScope.address + imageUrl);
            input = url.openStream();
            return BitmapFactory.decodeStream(input);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
