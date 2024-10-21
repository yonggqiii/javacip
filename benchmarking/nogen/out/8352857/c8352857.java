class c8352857 {

    public void sendMessage(Message m) throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.strURL);
        JavaCIPUnknownScope.urlcon = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.urlcon.setUseCaches(false);
        JavaCIPUnknownScope.urlcon.setDefaultUseCaches(false);
        JavaCIPUnknownScope.urlcon.setDoOutput(true);
        JavaCIPUnknownScope.urlcon.setDoInput(true);
        JavaCIPUnknownScope.urlcon.setRequestProperty("Content-type", "application/octet-stream");
        JavaCIPUnknownScope.urlcon.setAllowUserInteraction(false);
        HttpURLConnection.setDefaultAllowUserInteraction(false);
        JavaCIPUnknownScope.urlcon.setRequestMethod("POST");
        ObjectOutputStream oos = new ObjectOutputStream(JavaCIPUnknownScope.urlcon.getOutputStream());
        oos.writeObject(m);
        oos.flush();
        oos.close();
    }
}
