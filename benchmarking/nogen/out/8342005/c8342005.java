class c8342005 {

    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://www.foamsnet.com/smsapi/send.php?username=" + JavaCIPUnknownScope.username + "&password=" + JavaCIPUnknownScope.password + "&to=" + JavaCIPUnknownScope.to + "&msg=" + URLEncoder.encode(JavaCIPUnknownScope.msg));
            URLConnection urlc = url.openConnection();
            BufferedReader sin = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String inputLine = sin.readLine();
            inputLine = inputLine == null ? "null" : inputLine;
            sin.close();
            JavaCIPUnknownScope.output = inputLine;
            if (JavaCIPUnknownScope.logsent) {
                ContentResolver contentResolver = JavaCIPUnknownScope.cr;
                ContentValues values = new ContentValues();
                values.put("address", "+91" + inputLine.split(" ")[3]);
                values.put("body", JavaCIPUnknownScope.msg);
                contentResolver.insert(Uri.parse("content://sms/sent"), values);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
