class c3287282 {

    public void googleImageSearch(String start) {
        try {
            String u = "http://images.google.com/images?q=" + JavaCIPUnknownScope.custom + start;
            if (u.contains(" ")) {
                u = u.replace(" ", "+");
            }
            URL url = new URL(u);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
            BufferedReader readIn = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            JavaCIPUnknownScope.googleImages.clear();
            String text = "";
            String lin = "";
            while ((lin = readIn.readLine()) != null) {
                text += lin;
            }
            readIn.close();
            if (text.contains("\n")) {
                text = text.replace("\n", "");
            }
            String[] array = text.split("\\Qhref=\"/imgres?imgurl=\\E");
            for (String s : array) {
                if (s.startsWith("http://") || s.startsWith("https://") && s.contains("&amp;")) {
                    String s1 = s.substring(0, s.indexOf("&amp;"));
                    JavaCIPUnknownScope.googleImages.add(s1);
                }
            }
        } catch (RuntimeException ex4) {
            MusicBoxView.showErrorDialog(ex4);
        }
        JavaCIPUnknownScope.jButton4.setEnabled(true);
        JavaCIPUnknownScope.jButton2.setEnabled(true);
        JavaCIPUnknownScope.getContentPane().remove(JavaCIPUnknownScope.jLabel1);
        ImageIcon icon;
        try {
            icon = new ImageIcon(new URL(JavaCIPUnknownScope.googleImages.elementAt(JavaCIPUnknownScope.googleImageLocation)));
            int h = icon.getIconHeight();
            int w = icon.getIconWidth();
            JavaCIPUnknownScope.jLabel1.setSize(w, h);
            JavaCIPUnknownScope.jLabel1.setIcon(icon);
            JavaCIPUnknownScope.add(JavaCIPUnknownScope.jLabel1, BorderLayout.CENTER);
        } catch (MalformedURLRuntimeException ex) {
            MusicBoxView.showErrorDialog(ex);
            JavaCIPUnknownScope.jLabel1.setIcon(MusicBoxView.noImage);
        }
        JavaCIPUnknownScope.add(JavaCIPUnknownScope.jPanel1, BorderLayout.PAGE_END);
        JavaCIPUnknownScope.pack();
    }
}
