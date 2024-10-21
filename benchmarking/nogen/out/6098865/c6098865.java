class c6098865 {

    private ScrollingGraphicalViewer createGraphicalViewer(final Composite parent) {
        final ScrollingGraphicalViewer viewer = new ScrollingGraphicalViewer();
        viewer.createControl(parent);
        JavaCIPUnknownScope._root = new ScalableRootEditPart();
        viewer.setRootEditPart(JavaCIPUnknownScope._root);
        JavaCIPUnknownScope.getEditDomain().addViewer(viewer);
        JavaCIPUnknownScope.getSite().setSelectionProvider(viewer);
        viewer.setEditPartFactory(JavaCIPUnknownScope.getEditPartFactory());
        viewer.setContents(JavaCIPUnknownScope.getEditorInput().getAdapter(ScannedMap.class));
        return viewer;
    }
}
