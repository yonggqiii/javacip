class c7306114 {

    public void setOntology2Document(URL url2) throws IllegalArgumentRuntimeException {
        if (url2 == null)
            throw new IllegalArgumentRuntimeException("Input parameter URL for ontology 2 is null.");
        try {
            JavaCIPUnknownScope.ont2 = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(url2.openStream());
        } catch (IORuntimeException e) {
            throw new IllegalArgumentRuntimeException("Cannot open stream for ontology 2 from given URL.");
        } catch (OWLOntologyCreationRuntimeException e) {
            throw new IllegalArgumentRuntimeException("Cannot load ontology 2 from given URL.");
        }
    }
}
