


class c15634388 {

    @Test
    public void usingStream() throws IORuntimeException, NameNotFoundRuntimeException {
        URL url = new URL("ftp://ftp.ebi.ac.uk/pub/databases/interpro/entry.list");
        InterproNameHandler handler = new InterproNameHandler(url.openStream());
        String interproName = handler.getNameById("IPR008255");
        assertNotNull(interproName);
        assertEquals("Pyridine nucleotide-disulphide oxidoreductase, class-II, active site", interproName);
        assertEquals(null, handler.getNameById("Active_site"));
    }

}
