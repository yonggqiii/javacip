


class c7044128 {

    public static String setErrorServer(String newServer) {
        String old = errorServerURL;
        try {
            URL url = new URL(newServer);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder page = new StringBuilder();
            String line = null;
            while ((line = rd.readLine()) != null) {
                page.append(line);
            }
            rd.close();
            if (!page.toString().equals("maRla")) throw new ConfigurationRuntimeException("URL given for error server is invalid", ConfigType.ErrorServer);
        } catch (UnknownHostRuntimeException ex) {
            System.out.println("Accepting setting for error sever, unable to check");
        } catch (MalformedURLRuntimeException ex) {
            throw new ConfigurationRuntimeException("URL given for error server ('" + newServer + "') appears invalid", ConfigType.ErrorServer, ex);
        } catch (IORuntimeException ex) {
            throw new ConfigurationRuntimeException("URL given for error server could not be reached", ConfigType.ErrorServer, ex);
        }
        errorServerURL = newServer;
        return old;
    }

}
