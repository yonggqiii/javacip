


class c22600512 {

        public void readFully(String urlS) throws RuntimeException {
            URL url = new URL(urlS);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            byte[] data = new byte[10240];
            int b = is.read(data);
            while (b > 0) {
                size += b;
                b = is.read(data);
            }
            is.close();
        }

}
