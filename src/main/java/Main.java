import com.google.common.collect.Lists;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int NEW_WIDTH = 300;

    public static void main(String[] args) {
        String sourceFolder = "F:\\srcfolder";
        String destFolder = "F:\\dst";

        File srcDir = new File(sourceFolder);

        //Время старта программы
        long startTime = System.currentTimeMillis();

        //Лист всех файлов в папке, из которой будем копировать
        List<File> files = Arrays.asList(srcDir.listFiles());

        //Определяем количество ядер процессора
        int cores = Runtime.getRuntime().availableProcessors();
        //С помощью библиотеки Google Guava разобъём лист файлов на cores равных листов:
        List<List<File>> dividedFileLists = Lists.partition(files, cores);
        dividedFileLists.forEach(list ->
                new Thread(new ImageResizer(list, NEW_WIDTH, destFolder, startTime)).start());
    }
}
