class c19332852 {

    public void access() {
        Authenticator.setDefault(new MyAuthenticator());
        try {
            URL url = new URL("http://localhost/ws/test");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
    }
}
