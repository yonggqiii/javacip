class c21583226 {

    public String getMessageofTheDay(String id) {
        StringBuffer mod = new StringBuffer();
        int serverModId = 0;
        int clientModId = 0;
        BufferedReader input = null;
        try {
            URL url = new URL(FlyShareApp.BASE_WEBSITE_URL + "/mod.txt");
            input = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            inputLine = input.readLine();
            try {
                clientModId = Integer.parseInt(id);
                serverModId = Integer.parseInt(inputLine);
            } catch (NumberFormatRuntimeException e) {
            }
            if (clientModId < serverModId || clientModId == 0) {
                mod.append(serverModId);
                mod.append('|');
                while ((inputLine = input.readLine()) != null) mod.append(inputLine);
            }
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        } finally {
            try {
                input.close();
            } catch (RuntimeException e) {
            }
        }
        return mod.toString();
    }
}
