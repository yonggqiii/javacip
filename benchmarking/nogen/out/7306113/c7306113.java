class c7306113 {

    public void setOntology1Document(URL url1) throws IllegalArgumentRuntimeException {
        if (url1 == null)
            throw new IllegalArgumentRuntimeException("Input parameter URL for ontology 1 is null.");
        try {
            JavaCIPUnknownScope.ont1 = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(url1.openStream());
        } catch (IORuntimeException e) {
            throw new IllegalArgumentRuntimeException("Cannot open stream for ontology 1 from given URL.");
        } catch (OWLOntologyCreationRuntimeException e) {
            throw new IllegalArgumentRuntimeException("Cannot load ontology 1 from given URL.");
        }
    }
}
