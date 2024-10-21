import java.util.Collections;
import java.util.Map;

public interface ErrorAttributes {

	default Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		return Collections.emptyMap();
	}

	Throwable getError(WebRequest webRequest);

}
