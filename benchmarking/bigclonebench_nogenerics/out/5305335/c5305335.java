class c5305335 {

    public void testRenderRules() {
        try {
            MappingManager manager = new MappingManager();
            OWLOntologyManager omanager = OWLManager.createOWLOntologyManager();
            OWLOntology srcOntology;
            OWLOntology targetOntology;
            manager.loadMapping(JavaCIPUnknownScope.rulesDoc.toURL());
            srcOntology = omanager.loadOntologyFromPhysicalURI(JavaCIPUnknownScope.srcURI);
            targetOntology = omanager.loadOntologyFromPhysicalURI(JavaCIPUnknownScope.targetURI);
            manager.setSourceOntology(srcOntology);
            manager.setTargetOntology(targetOntology);
            Graph srcGraph = manager.getSourceGraph();
            Graph targetGraph = manager.getTargetGraph();
            System.out.println("Starting to render...");
            FlexGraphViewFactory factory = new FlexGraphViewFactory();
            factory.setColorScheme(ColorSchemes.BLUES);
            factory.visit(srcGraph);
            GraphView view = factory.getGraphView();
            GraphViewRenderer renderer = new FlexGraphViewRenderer();
            renderer.setGraphView(view);
            System.out.println("View updated with graph...");
            InputStream xmlStream = renderer.renderGraphView();
            StringWriter writer = new StringWriter();
            IOUtils.copy(xmlStream, writer);
            System.out.println("Finished writing");
            writer.close();
            System.out.println("Finished render... XML is:");
            System.out.println(writer.toString());
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (OWLOntologyCreationRuntimeException e) {
            e.printStackTrace();
        }
    }
}
