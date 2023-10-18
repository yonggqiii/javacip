class c20077101 {

    public void maj(String titre, String num_version) {
        int res = 2;
        String content_xml = "";
        try {
            URL url = new URL("http://code.google.com/feeds/p/tux-team/downloads/basic");
            InputStreamReader ipsr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(ipsr);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            br.close();
            content_xml = buffer.toString();
            res = JavaCIPUnknownScope.lecture_xml(titre, num_version, content_xml);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        switch(res) {
            case 0:
                JavaCIPUnknownScope.ihm.jl_maj.setText("Pas de mises à jour disponible. (" + num_version + ")");
                JavaCIPUnknownScope.ihm.jl_maj.setIcon(Resources.getImageIcon("images/valide.png", IHM_AProposDe.class));
                break;
            case 1:
                JavaCIPUnknownScope.ihm.jl_maj.setText("Une mise à jour est diponible. (" + JavaCIPUnknownScope.maj_file_version + ")");
                JavaCIPUnknownScope.ihm.jl_maj.setIcon(Resources.getImageIcon("images/warning.png", IHM_AProposDe.class));
                JavaCIPUnknownScope.ihm.jb_maj.setVisible(true);
                break;
            default:
                JavaCIPUnknownScope.ihm.jl_maj.setText("Serveur de mise à jour non disponible.");
                JavaCIPUnknownScope.ihm.jl_maj.setIcon(Resources.getImageIcon("images/erreur.png", IHM_AProposDe.class));
        }
    }
}
