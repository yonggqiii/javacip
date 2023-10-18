class c9594919 {

    public boolean handle(Message m, XMPPConnection connection) {
        if (m.getBody().toLowerCase().startsWith("http://")) {
            try {
                URL url = new URL(m.getBody());
                URLConnection urlconn = url.openConnection();
                urlconn.connect();
                String type = urlconn.getContentType();
                boolean found = false;
                for (ContentGrabber h : JavaCIPUnknownScope.grabbers) {
                    if (type.equals(h.contentType)) {
                        found = true;
                        SheevaSage.reply(m, "Give me a moment here...", connection);
                        h.handle(urlconn);
                        SheevaSage.reply(m, "Done", connection);
                        break;
                    }
                }
                if (!found) {
                    SheevaSage.reply(m, "I have no idea what to do with that", connection);
                }
            } catch (MalformedURLRuntimeException e) {
                SheevaSage.reply(m, "That is one ass-ugly URL. What in the name of " + "shuddering fuck do you expect me to do with it?", connection);
                e.printStackTrace();
            } catch (IORuntimeException e) {
                SheevaSage.reply(m, "Something's fucked up with that URL. You sure it works?", connection);
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
