import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder {
    private Long size = 0L;
    private Long maxFileSize = 0L;
    private List<File> files = new ArrayList<>();

    public Long getMaxFileSize() {
        return maxFileSize;
    }

    public Long getSize() {
        return size;
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void addFile(File file) {
        if (maxFileSize < file.length()) {
            maxFileSize = file.length();
        }
        size += file.length();
        files.add(file);
    }
}
