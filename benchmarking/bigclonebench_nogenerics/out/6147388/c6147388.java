class c6147388 {

    public static GCalendar getNewestCalendar(Calendar startDate) throws IORuntimeException {
        GCalendar hoge = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpClient http = new DefaultHttpClient();
            HttpGet method = new HttpGet("http://localhost:8080/GoogleCalendar/select");
            HttpResponse response = http.execute(method);
            String jsonstr = response.getEntity().toString();
            System.out.println("jsonstr = " + jsonstr);
            hoge = JavaCIPUnknownScope.JSON.decode(jsonstr, GCalendar.class);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return hoge;
    }
}
