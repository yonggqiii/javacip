class c1005107 {

    public void googleImageSearch() {
        if (JavaCIPUnknownScope.artist.compareToIgnoreCase(JavaCIPUnknownScope.previousArtist) != 0) {
            MusicBoxView.googleImageLocation = 0;
            try {
                String u = "http://images.google.com/images?q=" + JavaCIPUnknownScope.currentTrack.getArtist() + " - " + JavaCIPUnknownScope.currentTrack.getAlbum() + "&sa=N&start=0&ndsp=21";
                if (u.contains(" ")) {
                    u = u.replace(" ", "+");
                }
                URL url = new URL(u);
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
                BufferedReader readIn = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
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
        }
    }
}
