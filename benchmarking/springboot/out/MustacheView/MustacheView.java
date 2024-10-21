import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map;

public class MustacheView extends AbstractTemplateView {

    private Compiler1 compiler;

    private String charset;

    public void setCompiler(Compiler1 compiler) {
        this.compiler = compiler;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean checkResource(Locale locale) throws Exception {
        Resource resource = JavaCIPUnknownScope.getApplicationContext().getResource(JavaCIPUnknownScope.getUrl());
        return (resource != null && resource.exists());
    }

    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Template template = createTemplate(JavaCIPUnknownScope.getApplicationContext().getResource(JavaCIPUnknownScope.getUrl()));
        if (template != null) {
            template.execute(model, response.getWriter());
        }
    }

    private Template createTemplate(Resource resource) throws IOException {
        try (Reader reader = getReader(resource)) {
            return this.compiler.compile(reader);
        }
    }

    private Reader getReader(Resource resource) throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(resource.getInputStream(), this.charset);
        }
        return new InputStreamReader(resource.getInputStream());
    }
}
