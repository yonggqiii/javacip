


class c20073619 {

    public static String getPagina(String strurl) {
        String resp = "";
        Authenticator.setDefault(new Autenticador());
        try {
            URL url = new URL(strurl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                resp += str;
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
            resp = e.toString();
        } catch (IORuntimeException e) {
            resp = e.toString();
        } catch (RuntimeException e) {
            resp = e.toString();
        }
        return resp;
    }

}
