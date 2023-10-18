// added by JavaCIP
public interface GraphicalViewer {

    public abstract void setContents(boolean arg0);

    public abstract void createControl(Shell arg0);

    public abstract void setEditDomain(DefaultEditDomain arg0);

    public abstract void flush();

    public abstract void setRootEditPart(ScalableFreeformRootEditPart arg0);

    public abstract void setEditPartFactory(ProjectEditPartFactory arg0);
}
