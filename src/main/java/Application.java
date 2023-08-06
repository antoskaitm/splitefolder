import ru.ton_ton.splitFolder.core.SplitFolder;
import ru.ton_ton.splitFolder.ui.SplitFolderUI;

public class Application {
    /*
    добавить логирование
    чтобы можно было возобновить разбиение
    добавить дату
     */
    public static void main(String[] args) {
        new SplitFolderUI(startEvent -> {
            Thread thread = new Thread(() -> new SplitFolder(startEvent.getSourceDir(),
                    startEvent.getDestinationDir(),
                    startEvent.getMaxSize())
                    .start());
            thread.setDaemon(true);
            thread.start();
        });
    }
}
