public class Android30 {

	public static String main(String[] args) {
		// TODO Auto-generated method stub
		HttpHost target = new HttpHost("http://" + ServiceWrapper.SERVER_HOST,ServiceWrapper.SERVER_PORT);
        HttpGet get = new HttpGet("/list");
        String result=null;
     HttpEntity entity = null;
     HttpClient client = new DefaultHttpClient();
     // try {
    HttpResponse response=client.execute(target, get);
    entity = response.getEntity();
    result = EntityUtils.toString(entity);
   // } catch (Exception e) {
   //  e.printStackTrace();
   // } finally {
    if (entity!=null)
     // try {
      entity.consumeContent();
     // } catch (IOException e) {}
   // }
   return result;
	}

}
class ServiceWrapper
{
	static String SERVER_HOST = "host";
	static int SERVER_PORT = 8080;
}
