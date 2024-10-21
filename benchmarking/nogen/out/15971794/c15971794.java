class c15971794 {

    private boolean getWave(String url, String Word) {
        try {
            File FF = new File(JavaCIPUnknownScope.f.getParent() + "/" + JavaCIPUnknownScope.f.getName() + "pron");
            FF.mkdir();
            URL url2 = new URL(url);
            BufferedReader stream = new BufferedReader(new InputStreamReader(url2.openStream()));
            File Fdel = new File(JavaCIPUnknownScope.f.getParent() + "/" + JavaCIPUnknownScope.f.getName() + "pron/" + Word + ".wav");
            if (!Fdel.exists()) {
                FileOutputStream outstream = new FileOutputStream(JavaCIPUnknownScope.f.getParent() + "/" + JavaCIPUnknownScope.f.getName() + "pron/" + Word + ".wav");
                BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(outstream));
                char[] binput = new char[1024];
                int len = stream.read(binput, 0, 1024);
                while (len > 0) {
                    bwriter.write(binput, 0, len);
                    len = stream.read(binput, 0, 1024);
                }
                bwriter.close();
                outstream.close();
            }
            stream.close();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
