// added by JavaCIP
public interface IUsuario extends Requerente, RecursoHumano {

    public abstract boolean getEmail();

    public abstract boolean getTelefone();

    public abstract boolean getNome();

    public abstract boolean getSenha();

    public abstract void setIdUsuario(boolean arg0);

    public abstract boolean getCpf();

    public abstract boolean getLogin();
}
