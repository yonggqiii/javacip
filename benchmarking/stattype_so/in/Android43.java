public class Android43 {

	public static String main(String[] args) {
		HttpHost target = new HttpHost("google.com", 80);
		HttpGet get = new HttpGet("/");
		String result = null;
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		// try {
			HttpResponse response = client.execute(target, get);
			entity = response.getEntity();
			result = EntityUtils.toString(entity);
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// } finally {
			if (entity != null) {
			}
			// try {
				entity.consumeContent();
			// } catch (IOException e) {
			// }
		// }
		return result;
	}

}
