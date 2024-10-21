class c18781063 {

    private void createGraphicalViewer(Composite parent) {
        JavaCIPUnknownScope.viewer = new ScrollingGraphicalViewer();
        JavaCIPUnknownScope.viewer.createControl(parent);
        JavaCIPUnknownScope.viewer.getControl().setBackground(parent.getBackground());
        JavaCIPUnknownScope.viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        JavaCIPUnknownScope.viewer.setKeyHandler(new GraphicalViewerKeyHandler(JavaCIPUnknownScope.viewer));
        JavaCIPUnknownScope.registerEditPartViewer(JavaCIPUnknownScope.viewer);
        JavaCIPUnknownScope.configureEditPartViewer(JavaCIPUnknownScope.viewer);
        JavaCIPUnknownScope.viewer.setEditPartFactory(new GraphicalEditPartsFactory(JavaCIPUnknownScope.getSite().getShell()));
        JavaCIPUnknownScope.viewer.setContents(JavaCIPUnknownScope.getContractEditor().getContract());
        ContextMenuProvider provider = new ContractContextMenuProvider(JavaCIPUnknownScope.getGraphicalViewer(), JavaCIPUnknownScope.getContractEditor().getActionRegistry());
        JavaCIPUnknownScope.getGraphicalViewer().setContextMenu(provider);
        JavaCIPUnknownScope.getSite().registerContextMenu(provider, JavaCIPUnknownScope.getGraphicalViewer());
    }
}
