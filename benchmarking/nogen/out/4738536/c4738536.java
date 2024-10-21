class c4738536 {

    protected Void doInBackground(String... urls) {
        Log.d("ParseTask", "Getting URL " + urls[0]);
        try {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            reader.setContentHandler(JavaCIPUnknownScope.mParser);
            reader.parse(new InputSource(new URL(urls[0]).openStream()));
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope.mCallback != null)
                JavaCIPUnknownScope.mCallback.OnFailure(new ApiResponseObject(ApiResponse.RESPONSE_CRITICAL_FAILURE, e.getLocalizedMessage()));
        }
        return null;
    }
}
