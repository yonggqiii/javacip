


class c16048516 {

    public static SVNConfiguracion load(URL urlConfiguracion) {
        SVNConfiguracion configuracion = null;
        try {
            XMLDecoder xenc = new XMLDecoder(urlConfiguracion.openStream());
            configuracion = (SVNConfiguracion) xenc.readObject();
            configuracion.setFicheroConfiguracion(urlConfiguracion);
            xenc.close();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
        }
        return configuracion;
    }

}
