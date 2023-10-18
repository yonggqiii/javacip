class c12728214 {

    protected GraphicalViewer createGraphicalViewer(Composite parent) {
        GraphicalViewer viewer = new ScrollingGraphicalViewer();
        viewer.createControl(parent);
        viewer.getControl().setBackground(parent.getBackground());
        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        GraphicalViewerKeyHandler graphicalViewerKeyHandler = new GraphicalViewerKeyHandler(viewer);
        KeyHandler parentKeyHandler = graphicalViewerKeyHandler.setParent(JavaCIPUnknownScope.getCommonKeyHandler());
        viewer.setKeyHandler(parentKeyHandler);
        JavaCIPUnknownScope.getEditDomain().addViewer(viewer);
        JavaCIPUnknownScope.getSite().setSelectionProvider(viewer);
        ContextMenuProvider provider = new TestContextMenuProvider(viewer, JavaCIPUnknownScope.getActionRegistry());
        viewer.setContextMenu(provider);
        JavaCIPUnknownScope.getSite().registerContextMenu("cubicTestPlugin.editor.contextmenu", provider, viewer);
        viewer.addDropTargetListener(new DataEditDropTargetListner(((IFileEditorInput) JavaCIPUnknownScope.getEditorInput()).getFile().getProject(), viewer));
        viewer.addDropTargetListener(new FileTransferDropTargetListener(viewer));
        viewer.setEditPartFactory(JavaCIPUnknownScope.getEditPartFactory());
        viewer.setContents(JavaCIPUnknownScope.getContent());
        return viewer;
    }
}
