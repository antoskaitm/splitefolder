package ru.ton_ton.splitFolder.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folders {

    //продумать как собрать в один фаил но с минимум дробления
    //например сортировать только файлы с размером больше 300, ориентируясь на самый большой и самый маленький файлл в папке.
    public static List<Folder> readFolders(File sourceDir) {
        Folder folder = new Folder();
        List<Folder> result = new ArrayList<>();//Comparator.comparingLong(File::length)
        for (File file : sourceDir.listFiles()) {
            if (file.isDirectory()) {
                result.addAll(readFolders(file));
                continue;
            }
            //добавить фильтрацию по времени
            folder.addFile(file);
        }
        result.add(folder);
        return result;
    }
}
