


class c7455432 {

    private void saveScore(int score) {
        String name = JOptionPane.showInputDialog(this, "Skriv navn for å komme på highscorelisten!", "Lagre score!", JOptionPane.INFORMATION_MESSAGE);
        URL url;
        try {
            url = new URL("http://129.177.17.51:8080/GuestBook/TheOnlyServlet?name=" + name + "&score=" + score);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            urlConnection.getInputStream();
            BrowserControl.openUrl("http://129.177.17.51:8080/GuestBook/TheOnlyServlet");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
