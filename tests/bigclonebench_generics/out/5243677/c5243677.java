class c5243677 {

    public static void main(String[] args) throws Exception {
        JavaCIPUnknownScope.dataList = new ArrayList<String>();
        System.setProperty("http.agent", Phex.getFullPhexVendor());
        URL url = new URL(JavaCIPUnknownScope.listUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        JavaCIPUnknownScope.readData(inputStream);
        System.out.println("Total data read: " + JavaCIPUnknownScope.dataList.size());
        inputStream.close();
        JavaCIPUnknownScope.writeToOutputFile();
    }
}
