


class c1262833 {

    public Collection<Regula> citesteReguli() throws IORuntimeException {
        URL url = new URL(urlulSpreLocatiaCurenta, fisier);
        BufferedReader reader = new BufferedReader(new InputStreamReader((url.openStream())));
        Collection<Regula> rezultat = new ArrayList<Regula>();
        String line = "";
        while (!"".equals(line = reader.readLine())) {
            Regula r = parseazaRegula(line);
            if (r != null) rezultat.add(r);
        }
        return rezultat;
    }

}
