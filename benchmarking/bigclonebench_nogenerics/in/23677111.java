


class c23677111 {

	public static String downloadWebpage1(String address) throws MalformedURLRuntimeException, IORuntimeException {
		URL url = new URL(address);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		String page = "";
		while((line = br.readLine()) != null) {
			page += line + "\n";
		}
		br.close();
		return page;
	}

}
