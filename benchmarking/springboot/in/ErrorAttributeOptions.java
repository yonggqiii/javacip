import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public final class ErrorAttributeOptions {

	private final Set<Include> includes;

	private ErrorAttributeOptions(Set<Include> includes) {
		this.includes = includes;
	}

	public boolean isIncluded(Include include) {
		return this.includes.contains(include);
	}

	public Set<Include> getIncludes() {
		return this.includes;
	}

	public ErrorAttributeOptions including(Include... includes) {
		EnumSet<Include> updated = copyIncludes();
    for (Include i : includes)
      updated.add(i);
		return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
	}

	public ErrorAttributeOptions excluding(Include... excludes) {
		EnumSet<Include> updated = copyIncludes();
		return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
	}

	public void retainIncluded(Map<String, Object> map) {
		for (Include candidate : Include.values()) {
			if (!this.includes.contains(candidate)) {
				map.remove(candidate.key);
			}
		}
	}

	private EnumSet<Include> copyIncludes() {
		return (this.includes.isEmpty()) ? EnumSet.noneOf(Include.class) : EnumSet.copyOf(this.includes);
	}

	public static ErrorAttributeOptions defaults() {
		return of(Include.PATH, Include.STATUS, Include.ERROR);
	}

	public static ErrorAttributeOptions of(Include[] includes) {
    List<Include> includesList = new ArrayList<>();
    for (Include i : includes)
      includesList.add(i);
		return of(includesList);
	}

	public static ErrorAttributeOptions of(Collection<Include> includes) {
		return new ErrorAttributeOptions(
				(includes.isEmpty()) ? Collections.emptySet() : Collections.unmodifiableSet(EnumSet.copyOf(includes)));
	}

}
