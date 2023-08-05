public class Application {
    /*
    добавить логирование
    чтобы можно было возобновить разбиение
    добавить дату
     */
    public static void main(String[] args) {
        new SplitFolderUI(startEvent ->
                new SplitFolder(startEvent.getSourceDir(),
                        startEvent.getDestinationDir(),
                        startEvent.getMaxSize())
                        .start()
        );
    }
}
