
import org.junit.Test;

import java.io.File;

public class SplitFolderTest {

    @Test
    public void copy() {
        new SplitFolder(new File("D:\\в питер на запись\\видео\\языки"),
                new File("D:\\test"),
                4400L
        ).start();
    }
}
