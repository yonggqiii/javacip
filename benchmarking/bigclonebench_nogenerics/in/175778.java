


class c175778 {

    public void sendShape(String s) {
        try {
            URLConnection uc = new URL(url + "&add=" + s).openConnection();
            InputStream in = uc.getInputStream();
            int b;
            while ((b = in.read()) != -1) {
            }
            in.close();
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
        }
    }

}
