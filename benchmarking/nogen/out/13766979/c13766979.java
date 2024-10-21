class c13766979 {

    private GraphicalViewer createGraphicalViewer(Composite parent) {
        GraphicalViewer viewer = new ScrollingGraphicalViewer();
        viewer.createControl(parent);
        viewer.getControl().setBackground(parent.getBackground());
        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));
        JavaCIPUnknownScope.getEditDomain().addViewer(viewer);
        JavaCIPUnknownScope.getSite().setSelectionProvider(viewer);
        viewer.setEditPartFactory(JavaCIPUnknownScope.getEditPartFactory());
        viewer.setContents(JavaCIPUnknownScope.getContent());
        return viewer;
    }
}
