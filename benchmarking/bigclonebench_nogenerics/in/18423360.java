


class c18423360 {

    protected Ontology loadOntology(String ontologyIri) throws IORuntimeException, ParserRuntimeException, InvalidModelRuntimeException {
        assert ontologyIri != null;
        URL url = null;
        Ontology ontology = null;
        url = new URL(ontologyIri);
        InputStream is = url.openStream();
        TopEntity[] identifiable = parser.parse(new InputStreamReader(is));
        if (identifiable.length > 0 && identifiable[0] instanceof Ontology) {
            ontology = ((Ontology) identifiable[0]);
        }
        return ontology;
    }

}
