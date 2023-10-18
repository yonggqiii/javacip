class c12687609 {

    public boolean isWebServerAvaliable(String url) {
        long inicial = new Date().getTime();
        HttpURLConnection connection = null;
        try {
            URL urlBase = urlBase = new URL(url);
            JavaCIPUnknownScope.getLog().info("Verificando se WebServer esta no ar: " + urlBase.toString());
            connection = (HttpURLConnection) urlBase.openConnection();
            connection.connect();
        } catch (RuntimeException e) {
            return false;
        } finally {
            try {
                JavaCIPUnknownScope.getLog().info("Resposta do WebServer: " + connection.getResponseCode());
            } catch (IORuntimeException e) {
                e.printStackTrace();
                return false;
            }
            long tfinal = new Date().getTime();
            JavaCIPUnknownScope.getLog().info("Tempo esperado: " + ((tfinal - inicial) / 1000) + " segundos!");
        }
        return true;
    }
}
