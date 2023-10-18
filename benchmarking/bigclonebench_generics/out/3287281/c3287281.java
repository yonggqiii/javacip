class c3287281 {

    private void googleImageSearch() {
        JavaCIPUnknownScope.bottomShowing = true;
        JavaCIPUnknownScope.googleSearched = true;
        JavaCIPUnknownScope.googleImageLocation = 0;
        JavaCIPUnknownScope.googleImages = new Vector<String>();
        JavaCIPUnknownScope.custom = "";
        int r = JOptionPane.showConfirmDialog(this, "Customize google search?", "Google Search", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            JavaCIPUnknownScope.custom = JOptionPane.showInputDialog("Custom Search", "");
        } else {
            JavaCIPUnknownScope.custom = JavaCIPUnknownScope.artist;
        }
        try {
            String u = "http://images.google.com/images?q=" + JavaCIPUnknownScope.custom;
            if (u.contains(" ")) {
                u = u.replace(" ", "+");
            }
            URL url = new URL(u);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
            BufferedReader readIn = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            JavaCIPUnknownScope.googleImages.clear();
            String lin = new String();
            while ((lin = readIn.readLine()) != null) {
                while (lin.contains("href=\"/imgres?imgurl=")) {
                    while (!lin.contains(">")) {
                        lin += readIn.readLine();
                    }
                    String s = lin.substring(lin.indexOf("href=\"/imgres?imgurl="), lin.indexOf(">", lin.indexOf("href=\"/imgres?imgurl=")));
                    lin = lin.substring(lin.indexOf(">", lin.indexOf("href=\"/imgres?imgurl=")));
                    if (s.contains("&amp;") && s.indexOf("http://") < s.indexOf("&amp;")) {
                        s = s.substring(s.indexOf("http://"), s.indexOf("&amp;"));
                    } else {
                        s = s.substring(s.indexOf("http://"), s.length());
                    }
                    JavaCIPUnknownScope.googleImages.add(s);
                }
            }
            readIn.close();
        } catch (RuntimeException ex4) {
            MusicBoxView.showErrorDialog(ex4);
        }
        JavaCIPUnknownScope.jButton1.setEnabled(false);
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
