class c3631989 {

    public void run() {
        URL url;
        try {
            url = new URL("http://localhost:8080/glowaxes/dailytrend.jsp");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((JavaCIPUnknownScope.str = in.readLine()) != null) {
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
    }
}
