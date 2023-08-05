import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;


public class SplitFolder {
    private final File sourceDir;
    private final File destinationDir;
    /**
     * максимальный размер дробления папки в байтах
     */
    private final Long maxSize;
    private final Map<File, Long> parts = new TreeMap<>();

    /**
     * @param sourceDir папка источник которая дробится (остается не изменной)
     * @param destinationDir папка назначения куда будут сброшенна раздробленнаая папка
     * @param maxSize размер который не должена привышать разбитая папка в мегабайтах
     */
    public SplitFolder(File sourceDir, File destinationDir, Long maxSize) {
        this.sourceDir = sourceDir;
        this.destinationDir = destinationDir;
        this.maxSize = maxSize * 1024 * 1024L;
    }

    /* сначала сортирую по самому большому файлу и пишу большие файлы
        а затем как все большие файлы записал пишу маленькие отсортированные в папке
     */

    public void start() {
        //упорядочить по размеру сначала большие потом маленикие файлы
        List<Folder> folders = Folders.readFolders(sourceDir);
        folders.sort(Comparator.comparingLong(Folder::getSize).reversed());
        for (Folder folder : folders) {
            for (File file : folder.getFiles().stream()
                    //.filter(f -> f.length() >= 100L)
                    .sorted(Comparator.comparingLong(File::length).reversed())
                    .collect(Collectors.toList())) {
                copy(file);
            }
        }
        /*for (Folder folder : folders) {
            for (File file : folder.getFiles().stream().filter(f -> f.length() < 100L)
                    .sorted(Comparator.comparingLong(File::length).reversed())
                    .collect(Collectors.toList())) {
                copy(file);
            }
        }*/
    }

    private void copy(File file) {
        try {
            File part = getPart(file);
            File newDestination = copyHyerarhy(file.getParentFile(), part);
            File newFile = new File(newDestination + "//" + file.getName());
            for (int i = 0; i < 4; i++) {
                if (newFile.exists() && MD5Checksum.sameHash(file, newFile)) {
                    break;
                }
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            if (parts.containsKey(part)) {
                parts.put(part, parts.get(part) + file.length());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //проверить целостность файлов
    }

    private File copyHyerarhy(File file, File destinationDir) {
        if (file.getPath().equals(sourceDir.getAbsolutePath())) {
            return destinationDir;
        }
        destinationDir = copyHyerarhy(file.getParentFile(), destinationDir);
        destinationDir = new File(destinationDir.getAbsolutePath() + "//" + file.getName());
        if (!destinationDir.exists()) {
            destinationDir.mkdir();
        }
        return destinationDir;
    }

    //продумать как собрать в один фаил но с минимум дробления
    //например сортировать только файлы с размером больше 300, ориентируясь на самый большой и самый маленький файлл в папке.
    public static List<File> readFiles(File sourceDir) {
        List<File> result = new ArrayList<>();//Comparator.comparingLong(File::length)
        for (File file : sourceDir.listFiles()) {
            if (file.isDirectory()) {
                result.addAll(readFiles(file));
                continue;
            }
            //добавить фильтрацию по времени
            result.add(file);
        }
        return result;
    }

    public File getPart(File file) {
        if (file.length() > maxSize) {
            return mkPart(sourceDir.getName() + "_BigFiles");
        }
        File part = null;
        for (File dir : parts.keySet()) {
            if (parts.get(dir) + file.length() <= maxSize) {
                part = dir;
            }
        }
        if (part == null) {
            part = mkPart(sourceDir.getName() + "_" + parts.size());
            parts.put(part, 0L);
        }
        return part;
    }

    private File mkPart(String name) {
        File part = new File(destinationDir.getAbsolutePath() + "//" + name);
        if (!part.exists()) {
            part.mkdir();
        }
        return part;
    }
}
