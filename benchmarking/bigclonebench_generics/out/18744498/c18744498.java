class c18744498 {

    private void read(String url) {
        JavaCIPUnknownScope.session.beginTransaction();
        try {
            Document doc = JavaCIPUnknownScope.reader.read(new URL(url).openStream());
            Element root = doc.getRootElement();
            Dict dic = new Dict();
            Vector<Cent> v = new Vector<Cent>();
            for (Object o : root.elements()) {
                Element e = (Element) o;
                if (e.getName().equals("key")) {
                    dic.setName(e.getTextTrim());
                } else if (e.getName().equals("audio")) {
                    dic.setAudio(e.getTextTrim());
                } else if (e.getName().equals("pron")) {
                    dic.setPron(e.getTextTrim());
                } else if (e.getName().equals("def")) {
                    dic.setDef(e.getTextTrim());
                } else if (e.getName().equals("sent")) {
                    Cent cent = new Cent();
                    for (Object subo : e.elements()) {
                        Element sube = (Element) subo;
                        if (sube.getName().equals("orig")) {
                            cent.setOrig(sube.getTextTrim());
                        } else if (sube.getName().equals("trans")) {
                            cent.setTrans(sube.getTextTrim());
                        }
                    }
                    v.add(cent);
                }
            }
            if (dic.getName() == null || "".equals(dic.getName())) {
                JavaCIPUnknownScope.session.getTransaction().commit();
                return;
            }
            JavaCIPUnknownScope.session.save(dic);
            dic.setCent(new HashSet<Cent>());
            for (Cent c : v) {
                c.setDict(dic);
                dic.getCent().add(c);
            }
            JavaCIPUnknownScope.session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.session.getTransaction().rollback();
        }
    }
}
