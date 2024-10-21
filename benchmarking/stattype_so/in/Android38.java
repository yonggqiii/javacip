public class Android38 {
	static String serviceDomain = "http://staging.abaqus.net";
	static String postUrl = serviceDomain + "/geo/upl/wupload/pictures";
	static String CRLF = "\r\n";
	static String twoHyphens = "--";
	static String boundary = "*****mgd*****";

	private String name = null;
	private String password = null;
	private DataOutputStream dataStream = null;


	public Android38(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public static void setServiceDomain(String domainName) {
		serviceDomain = domainName;
	}

	public static String getServiceDomain() {
		return serviceDomain;
	}

	public int uploadPicture(String pictureFileName) {
		File uploadFile = new File(pictureFileName);

		if (uploadFile.exists()) {
			// try {
				FileInputStream fileInputStream = new FileInputStream(uploadFile);
				URL connectURL = new URL(postUrl);
				HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();

				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");

				conn.setRequestProperty("User-Agent", "myGeodiary-V1");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

				conn.connect();

				dataStream = new DataOutputStream(conn.getOutputStream());

				writeFormField("login", name);
				writeFormField("password", password);
				writeFileField("photo1", pictureFileName, "image/jpg", fileInputStream);

				// final closing boundary line
				dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF);

				fileInputStream.close();
				dataStream.flush();
				dataStream.close();
				dataStream = null;

				String response = getResponse(conn);
				conn.getResponseCode();

				if (response.contains("uploaded successfully"))
					return ReturnCode.http201;
				else
					// for now assume bad name/password
					return ReturnCode.http401;
			// } catch (MalformedURLException mue) {
			// 	// Log.e(Tag, "error: " + mue.getMessage(), mue); 
			// 	System.out.println("GeoPictureUploader.uploadPicture: Malformed URL: " + mue.getMessage());
			// 	return ReturnCode.http400;
			// } catch (IOException ioe) {
			// 	// Log.e(Tag, "error: " + ioe.getMessage(), ioe); 
			// 	System.out.println("GeoPictureUploader.uploadPicture: IOE: " + ioe.getMessage());
			// 	return ReturnCode.http500;
			// } catch (Exception e) {
			// 	// Log.e(Tag, "error: " + ioe.getMessage(), ioe); 
			// 	System.out.println("GeoPictureUploader.uploadPicture: unknown: " + e.getMessage());
			// 	return ReturnCode.unknown;
			}
		else {
			return ReturnCode.noPicture;
		}
	}

	/**
	 * @param conn
	 * @return
	 */
	private String getResponse(HttpURLConnection conn) {
		// try {
			DataInputStream dis = new DataInputStream(conn.getInputStream());
			byte[] data = new byte[1024];
			int len = dis.read(data, 0, 1024);

			dis.close();
			conn.getResponseCode();

			if (len > 0)
				return new String(data, 0, len);
			else
				return "";
		// } catch (Exception e) {
		// 	System.out.println("GeoPictureUploader: biffed it getting HTTPResponse");
		// 	//Log.e(TAG, "GeoPictureUploader: biffed it getting HTTPResponse");
		// 	return "";
		// }
	}

	/**
	 *  this mode of reading response no good either
	 */
	private String getResponseOrig(HttpURLConnection conn) {
		InputStream is = null;
		// try {
			is = conn.getInputStream();
			// scoop up the reply from the server
			int ch;
			StringBuffer sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
				if (is != null)
					is.close();
			return sb.toString();
		// } catch (Exception e) {
		// 	System.out.println("GeoPictureUploader: biffed it getting HTTPResponse");
		// 	//Log.e(TAG, "GeoPictureUploader: biffed it getting HTTPResponse");
		// } finally {
			// try {
			// } catch (Exception e) {
			// }
		// }

		// return "";
	}

	/**
	 * write one form field to dataSream
	 * @param fieldName
	 * @param fieldValue
	 */
	private void writeFormField(String fieldName, String fieldValue) {
		// try {
			dataStream.writeBytes(twoHyphens + boundary + CRLF);
			dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);
			dataStream.writeBytes(CRLF);
			dataStream.writeBytes(fieldValue);
			dataStream.writeBytes(CRLF);
		// } catch (Exception e) {
		// 	System.out.println("GeoPictureUploader.writeFormField: got: " + e.getMessage());
		// 	//Log.e(TAG, "GeoPictureUploader.writeFormField: got: " + e.getMessage());
		// }
	}

	/**
	 * write one file field to dataSream
	 * @param fieldName - name of file field
	 * @param fieldValue - file name
	 * @param type - mime type
	 * @param fileInputStream - stream of bytes that get sent up
	 */
	private void writeFileField(String fieldName, String fieldValue, String type, FileInputStream fis) {
		// try {
			// opening boundary line
			dataStream.writeBytes(twoHyphens + boundary + CRLF);
			dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\";filename=\"" + fieldValue
					+ "\"" + CRLF);
			dataStream.writeBytes("Content-Type: " + type + CRLF);
			dataStream.writeBytes(CRLF);

			// create a buffer of maximum size 
			int bytesAvailable = fis.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];
			// read file and write it into form... 
			int bytesRead = fis.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				dataStream.write(buffer, 0, bufferSize);
				bytesAvailable = fis.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fis.read(buffer, 0, bufferSize);
			}

			// closing CRLF
			dataStream.writeBytes(CRLF);
		// } catch (Exception e) {
		// 	System.out.println("GeoPictureUploader.writeFormField: got: " + e.getMessage());
		// 	//Log.e(TAG, "GeoPictureUploader.writeFormField: got: " + e.getMessage());
		// }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length >= 0) {
			Android38 gpu = new Android38("john", "notmyrealpassword");
			String picName = args[0];

			gpu.uploadPicture(picName);
			// System.out.printf("done");
		}
	}

}


class ReturnCode {
        public static final int  noPicture = 1;
        public static final int unknown = 2;
         public static final int http201 = 3;
         public static final int http400 = 4;
         public static final int http401 = 5;
         public static final int http403 = 6;
         public static final int http404 = 7;
         public static final int http500 = 8;
}
