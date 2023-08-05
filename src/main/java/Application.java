public class Application {
    public static void main(String[] args) {
        new SplitFolderUI(startEvent ->
                new SplitFolder(startEvent.getSourceDir(),
                        startEvent.getDestinationDir(),
                        startEvent.getMaxSize())
                        .start()
        );
    }
}
