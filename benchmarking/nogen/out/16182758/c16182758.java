class c16182758 {

    public Void doInBackground() {
        Transferable clipData = JavaCIPUnknownScope.clipboard.getContents(this);
        File file = new File("Videos/" + (JavaCIPUnknownScope.mp3.getArtist() + " - " + JavaCIPUnknownScope.mp3.getTitle() + ".jpg").replace("/", "").replace("\\", ""));
        try {
            String test = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
            String testje = test.toLowerCase();
            if (testje.indexOf(".flv") > 0 || testje.indexOf(".wmv") > 0 || testje.indexOf(".mpg") > 0 || testje.indexOf(".mpeg") > 0 || testje.indexOf(".avi") > 0 || testje.indexOf(".avi") > 0 || testje.indexOf(".divx") > 0 || testje.indexOf(".avi") > 0) {
                URL url = new URL(test);
                (new File("Videos/")).mkdirs();
                System.out.println("Saving video to " + file);
                try {
                    URLConnection urlC = url.openConnection();
                    InputStream is = url.openStream();
                    System.out.flush();
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[32768];
                    int len;
                    while ((len = is.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    is.close();
                    fos.close();
                } catch (RuntimeException e) {
                    System.out.println("Error saving video from url: " + url);
                    JavaCIPUnknownScope.mp3.setVideo(file.getAbsolutePath());
                }
            }
        } catch (RuntimeException ex) {
            System.out.println("not a valid video file");
            ex.printStackTrace();
        }
        return null;
    }
}
