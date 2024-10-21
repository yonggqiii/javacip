class c4866624 {

    public boolean authenticate() {
        if (JavaCIPUnknownScope.empresaFeta == null)
            JavaCIPUnknownScope.empresaFeta = new AltaEmpresaBean();
        JavaCIPUnknownScope.log.info("authenticating {0}", JavaCIPUnknownScope.credentials.getUsername());
        boolean bo;
        try {
            String passwordEncriptat = JavaCIPUnknownScope.credentials.getPassword();
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(passwordEncriptat.getBytes(), 0, passwordEncriptat.length());
            passwordEncriptat = new BigInteger(1, m.digest()).toString(16);
            Query q = JavaCIPUnknownScope.entityManager.createQuery("select usuari from Usuaris usuari where usuari.login=? and usuari.password=?");
            q.setParameter(1, JavaCIPUnknownScope.credentials.getUsername());
            q.setParameter(2, passwordEncriptat);
            Usuaris usuari = (Usuaris) q.getSingleResult();
            bo = (usuari != null);
            if (bo) {
                if (usuari.isEsAdministrador()) {
                    JavaCIPUnknownScope.identity.addRole("admin");
                } else {
                    JavaCIPUnknownScope.carregaDadesEmpresa();
                    JavaCIPUnknownScope.log.info("nom de l'empresa: " + JavaCIPUnknownScope.empresaFeta.getInstance().getNom());
                }
            }
        } catch (RuntimeException t) {
            JavaCIPUnknownScope.log.error(t);
            bo = false;
        }
        JavaCIPUnknownScope.log.info("L'usuari {0} s'ha identificat bé? : {1} ", JavaCIPUnknownScope.credentials.getUsername(), bo ? "si" : "no");
        return bo;
    }
}
