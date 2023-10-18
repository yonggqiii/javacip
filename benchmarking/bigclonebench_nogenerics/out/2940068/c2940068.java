class c2940068 {

    public void run() {
        JavaCIPUnknownScope.checkupdates.BetaST.setText("");
        JavaCIPUnknownScope.checkupdates.stableST.setText("");
        String[] s = new String[7];
        int i = 0;
        try {
            URL url = new URL("http://memorize-words.sourceforge.net/latest.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while (((str = in.readLine()) != null) && (i < 7)) {
                s[i] = str;
                i++;
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        if (i < 5) {
            JavaCIPUnknownScope.checkupdates.CheckBT.setEnabled(true);
            return;
        }
        // S[0]=latest Beta version
        // S[1]=latest Stable version
        // s[2]=Beta version download path
        // s[3]=Stable version download path
        // s[4]= Beta name
        // s[5]=Stable name
        boolean updated = false;
        if ((JavaCIPUnknownScope.MF.CurVersion < Integer.parseInt(s[0])) && (JavaCIPUnknownScope.checkupdates.BetaCHK.isSelected())) {
            JavaCIPUnknownScope.checkupdates.BetaST.setText("<HTML>A newer BETA version (<b>" + s[4] + "</b>) is available at<BR> <a href=''>" + s[2] + "</a></HTML>");
            updated = true;
            JavaCIPUnknownScope.checkupdates.hasBeta = true;
            JavaCIPUnknownScope.checkupdates.BetaURL = s[2];
        } else
            JavaCIPUnknownScope.checkupdates.BetaST.setText("");
        if ((JavaCIPUnknownScope.MF.CurVersion < Integer.parseInt(s[1])) && (JavaCIPUnknownScope.checkupdates.StableCHK.isSelected())) {
            JavaCIPUnknownScope.checkupdates.stableST.setText("<HTML>A newer STABLE version (<b>" + s[5] + "</b>) is available at<BR> <a href=''>" + s[3] + "</a></HTML>");
            updated = true;
            JavaCIPUnknownScope.checkupdates.hasStable = true;
            JavaCIPUnknownScope.checkupdates.StableURL = s[3];
        } else
            JavaCIPUnknownScope.checkupdates.stableST.setText("");
        if (updated)
            JavaCIPUnknownScope.checkupdates.setVisible(true);
        JavaCIPUnknownScope.checkupdates.CheckBT.setEnabled(true);
        // "A newer stable version (memorize-words 1-2-2) is available at http://"
        // MF.CurVersion;
    }
}
