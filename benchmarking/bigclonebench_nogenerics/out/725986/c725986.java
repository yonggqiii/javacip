class c725986 {

    public void writeToFtp(String login, String password, String address, String directory, String filename) {
        String newline = System.getProperty("line.separator");
        try {
            URL url = new URL("ftp://" + login + ":" + password + "@ftp." + address + directory + filename + ".html" + ";type=i");
            URLConnection urlConn = url.openConnection();
            urlConn.setDoOutput(true);
            OutputStreamWriter stream = new OutputStreamWriter(urlConn.getOutputStream());
            stream.write("<html><title>" + JavaCIPUnknownScope.title + "</title>" + newline);
            stream.write("<h1><b>" + JavaCIPUnknownScope.title + "</b></h1>" + newline);
            stream.write("<h2>Table Of Contents:</h2><ul>" + newline);
            for (int k = 0; k < JavaCIPUnknownScope.rings.size(); k++) {
                stream.write("<li><i><a href=\"#");
                stream.write(JavaCIPUnknownScope.readNoteTitle(k));
                stream.write("\">");
                stream.write(JavaCIPUnknownScope.readNoteTitle(k));
                stream.write("</a></i></li>" + newline);
            }
            stream.write("</ul><hr>" + newline + newline);
            for (int k = 0; k < JavaCIPUnknownScope.rings.size(); k++) {
                stream.write("<h3><b>");
                stream.write("<a name=\"");
                stream.write(JavaCIPUnknownScope.readNoteTitle(k));
                stream.write("\">");
                stream.write(JavaCIPUnknownScope.readNoteTitle(k) + "</a>");
                stream.write("</b></h3>" + newline);
                stream.write(JavaCIPUnknownScope.readNoteBody(k) + newline);
            }
            stream.write(newline + "<br><hr><a>This was created using Scribe, a free crutch editor.</a></html>");
            stream.close();
        } catch (IORuntimeException error) {
            System.out.println("There was an error: " + error);
        }
    }
}