


class c11453456 {

    public static boolean canWeConnectToInternet() {
        String s = "http://www.google.com/";
        URL url = null;
        boolean can = false;
        URLConnection conection = null;
        try {
            url = new URL(s);
        } catch (MalformedURLRuntimeException e) {
            System.out.println("This should never happend. Error in URL name. URL specified was:" + s + ".");
        }
        try {
            conection = url.openConnection();
            conection.connect();
            can = true;
        } catch (IORuntimeException e) {
            can = false;
        }
        if (can) {
        }
        return can;
    }

}
