class c8515616 {

    public ResponseStatus nowPlaying(String artist, String track, String album, int length, int tracknumber) throws IORuntimeException {
        if (JavaCIPUnknownScope.sessionId == null)
            throw new IllegalStateRuntimeException("Perform successful handshake first.");
        String b = album != null ? JavaCIPUnknownScope.encode(album) : "";
        String l = length == -1 ? "" : String.valueOf(length);
        String n = tracknumber == -1 ? "" : String.valueOf(tracknumber);
        String body = String.format("s=%s&a=%s&t=%s&b=%s&l=%s&n=%s&m=", JavaCIPUnknownScope.sessionId, JavaCIPUnknownScope.encode(artist), JavaCIPUnknownScope.encode(track), b, l, n);
        if (Caller.getInstance().isDebugMode())
            System.out.println("now playing: " + body);
        Proxy proxy = Caller.getInstance().getProxy();
        HttpURLConnection urlConnection = Caller.getInstance().openConnection(JavaCIPUnknownScope.nowPlayingUrl);
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        OutputStream outputStream = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(body);
        writer.close();
        InputStream is = urlConnection.getInputStream();
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String status = r.readLine();
        r.close();
        return new ResponseStatus(ResponseStatus.codeForStatus(status));
    }
}
