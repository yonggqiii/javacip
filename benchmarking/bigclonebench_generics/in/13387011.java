


class c13387011 {

    private void updateViewerContent(ScrollingGraphicalViewer viewer) {
        BioPAXGraph graph = (BioPAXGraph) ((BioPAXContents) viewer.getContents()).getModel();
        if (!graph.isMechanistic()) return;
        Map<String, Color> highlightMap = new HashMap<String, Color>();
        for (Object o : graph.getNodes()) {
            IBioPAXNode node = (IBioPAXNode) o;
            if (node.isHighlighted()) {
                String hash = node.getIDHash();
                Color color = node.getHighlightColor();
                highlightMap.put(hash, color);
            }
        }
        for (Object o : graph.getEdges()) {
            IBioPAXEdge edge = (IBioPAXEdge) o;
            if (edge.isHighlighted()) {
                String hash = edge.getIDHash();
                Color color = edge.getHighlightColor();
                highlightMap.put(hash, color);
            }
        }
        HighlightLayer hLayer = (HighlightLayer) ((ChsScalableRootEditPart) viewer.getRootEditPart()).getLayer(HighlightLayer.HIGHLIGHT_LAYER);
        hLayer.removeAll();
        hLayer.highlighted.clear();
        viewer.deselectAll();
        graph.recordLayout();
        PathwayHolder p = graph.getPathway();
        if (withContent != null) {
            p.updateContentWith(withContent);
        }
        RootGraph g = main.getRootGraph();
        BioPAXGraph newGraph = g.excise(p);
        newGraph.setAsRoot();
        viewer.setContents(newGraph);
        boolean layedout = newGraph.fetchLayout();
        if (!layedout) {
            new CoSELayoutAction(main).run();
        }
        viewer.deselectAll();
        GraphAnimation.run(viewer);
        for (Object o : newGraph.getNodes()) {
            IBioPAXNode node = (IBioPAXNode) o;
            String hash = node.getIDHash();
            if (highlightMap.containsKey(hash)) {
                Color c = highlightMap.get(hash);
                node.setHighlightColor(c);
                node.setHighlight(true);
            }
        }
        for (Object o : newGraph.getEdges()) {
            IBioPAXEdge edge = (IBioPAXEdge) o;
            String hash = edge.getIDHash();
            if (highlightMap.containsKey(hash)) {
                Color c = highlightMap.get(hash);
                edge.setHighlightColor(c);
                edge.setHighlight(true);
            }
        }
    }

}
