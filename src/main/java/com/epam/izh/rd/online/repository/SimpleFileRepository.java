package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long count = 0;

        File[] files = new File("./src/main/resources/" + path).listFiles();
        if (files == null)
            return 0;

        for (File file : files) {
            if (file.isFile())
                count++;
            else
                count += countFilesInDirectory(path + "/" + file.getName());
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long count = 1;

        File[] files = new File("./src/main/resources/" + path).listFiles();
        if (files == null)
            return 1;

        for (File file : files) {
            if (file.isDirectory()) {
                count += countDirsInDirectory(path + "/" + file.getName());
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File[] files = new File(from).listFiles();
        int index = 0;
        String extension;

        if (files == null)
            return;
        try {
            for (File file : files) {
                index = file.getName().lastIndexOf('.');
                extension = file.getName().substring(index + 1);
                if (extension.equals("txt"))
                    Files.copy(file.toPath(), new File(to).toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File file = new File("./src/main/resources/" + path);

        if (file.isFile())
            return false;

        if(!file.exists()) {
            file.mkdir();
        }

        try {
                file = new File(file.getPath() + "/" + name);
                file.createNewFile();
            } catch (IOException io) {
                io.printStackTrace();
            }

        return file.exists();
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder content = new StringBuilder();
        fileName = "./src/main/resources/" + fileName;
        String temp;

        try(FileReader reader = new FileReader(fileName); BufferedReader bufferedReader = new BufferedReader(reader)) {
            temp = bufferedReader.readLine();
            while (temp != null){
                content.append(temp);
                temp = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
