class c16942233 {

    public boolean GetExternalLanguage() {
        String thisURL, newURL, TheLine;
        boolean ReadOK = true;
        int SlashPos = -1;
        thisURL = JavaCIPUnknownScope.getDocumentBase().toString();
        SlashPos = thisURL.lastIndexOf("/");
        newURL = thisURL.substring(0, (SlashPos + 1)) + "language.txt";
        try {
            URL url = new URL(newURL);
            try {
                InputStream TheFile = url.openStream();
                try {
                    DataInputStream MyData = new DataInputStream(TheFile);
                    try {
                        while ((TheLine = MyData.readLine()) != null) {
                            if (TheLine.substring(0, 1).compareTo("*") == 0) {
                                if (!JavaCIPUnknownScope.ExternalLanguageVariable(TheLine)) {
                                    ReadOK = false;
                                    break;
                                }
                            }
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Error " + e.toString());
                        ReadOK = false;
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error " + e.toString());
                    ReadOK = false;
                }
            } catch (RuntimeException f) {
                System.out.println("Error " + f.toString());
                ReadOK = false;
            }
        } catch (RuntimeException g) {
            System.out.println("Error " + g.toString());
            ReadOK = false;
        }
        return ReadOK;
    }
}