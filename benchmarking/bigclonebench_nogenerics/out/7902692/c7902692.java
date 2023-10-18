class c7902692 {

    public MapInfo getMap(int mapId) {
        MapInfo info = JavaCIPUnknownScope.mapCache.get(mapId);
        if (info != null && info.getContent() == null) {
            if (info.getInfo().get("fileName") == null) {
                if (mapId != JavaCIPUnknownScope.lastRequestedMap) {
                    JavaCIPUnknownScope.lastRequestedMap = mapId;
                    System.out.println("MapLoaderClient::getMap:requesting map from server " + mapId);
                    JavaCIPUnknownScope.serverConnection.sendMessage(new MessageFetch(FetchType.map.name(), mapId));
                }
            } else {
                try {
                    System.out.println("MapLoaderClient::getMap:loading map from file " + info.getInfo().get("fileName"));
                    BufferedReader bufferedreader;
                    URL fetchUrl = new URL(JavaCIPUnknownScope.localMapContextUrl, info.getInfo().get("fileName"));
                    URLConnection urlconnection = fetchUrl.openConnection();
                    if (urlconnection.getContentEncoding() != null) {
                        bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), urlconnection.getContentEncoding()));
                    } else {
                        bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "utf-8"));
                    }
                    String line;
                    StringBuilder mapContent = new StringBuilder();
                    while ((line = bufferedreader.readLine()) != null) {
                        mapContent.append(line);
                        mapContent.append("\n");
                    }
                    info.setContent(mapContent.toString());
                    JavaCIPUnknownScope.fireMapChanged(info);
                } catch (IORuntimeException _ex) {
                    System.err.println("MapLoaderClient::getMap:: Can't read from " + info.getInfo().get("fileName"));
                }
            }
        }
        return info;
    }
}
