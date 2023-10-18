class c118288 {

    public void fetchFile(String ID) {
        String url = "http://www.nal.usda.gov/cgi-bin/agricola-ind?bib=" + ID + "&conf=010000++++++++++++++&screen=MA";
        System.out.println(url);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("MARC" + ID + ".txt"));
            if (!JavaCIPUnknownScope.id.contains("MARC" + ID + ".txt")) {
                JavaCIPUnknownScope.id.add("MARC" + ID + ".txt");
            }
            JavaCIPUnknownScope.in = new BufferedReader(new InputStreamReader((new URL(url)).openStream()));
            JavaCIPUnknownScope.in.readLine();
            String inputLine, stx = "";
            StringBuffer sb = new StringBuffer();
            while ((inputLine = JavaCIPUnknownScope.in.readLine()) != null) {
                if (inputLine.startsWith("<TR><TD><B>")) {
                    String sts = (inputLine.substring(inputLine.indexOf("B>") + 2, inputLine.indexOf("</")));
                    int i = 0;
                    try {
                        i = Integer.parseInt(sts);
                    } catch (NumberFormatRuntimeException nfe) {
                    }
                    if (i > 0) {
                        stx = stx + "\n" + sts + " - ";
                    } else {
                        stx += sts;
                    }
                }
                if (!(inputLine.startsWith("<") || inputLine.startsWith(" <") || inputLine.startsWith(">"))) {
                    String tx = inputLine.trim();
                    stx += tx;
                }
            }
            pw.println(stx);
            pw.close();
        } catch (RuntimeException e) {
            System.out.println("Couldn't open stream");
            System.out.println(e);
        }
    }
}
