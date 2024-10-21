public class Android14 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // try {
        URL u = new URL("http://www.path.to/a.mp4?video");
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.setRequestMethod("GET");
        c.setDoOutput(true);
        c.connect();
        String root = "root";
        FileOutputStream f = new FileOutputStream(new File(root, "Video.mp4"));
        InputStream in = c.getInputStream();
        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = in.read(buffer)) > 0) {
            f.write(buffer);
        }
        f.close();
        // } catch (MalformedURLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (ProtocolException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (FileNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}
