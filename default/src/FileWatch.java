package src;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class FileWatch {

    //use a table since it is synchronized
    private Hashtable<String, FileDetail> watchTable = new Hashtable<String, FileDetail>();
    private ArrayList<Thread> threadPool = new ArrayList<Thread>();
    private PathMatcher matcher;

    public FileWatch(String fileExt){
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileExt);
    }

    /**
     * Go through the directory specified and count the lines for each file and store in a hashtable
     * If we encounter a new file then print name and total number of lines and add it to the watchtable
     * Otherwise see if the file has changed and if it has update its FileDetail and print the statistics to the user
     * Only consider files that end in the appropriate extension (fileExt)
     * @param dirToWatch
     */

    public void watchFilesWrapper(File dirToWatch){
        watchFiles(dirToWatch);
        joinThreads(threadPool);
    }

    public void watchFiles(File dirToWatch) {
        for(int i = 0; i < dirToWatch.list().length; i++){
            File f = new File(dirToWatch + "/" + dirToWatch.list()[i]);
            if(f.isDirectory()){
                watchFiles(f);
            } else if(f.isFile() && matcher.matches(f.toPath())){
                Thread lineCounter = new Thread(new LineCountRunnable(f, watchTable));
                threadPool.add(lineCounter);
                lineCounter.start();
            }

        }
    }

    private void joinThreads(ArrayList<Thread> threadPool) {
        try{
            System.out.println(threadPool.size());
            for(int i = 0; i < threadPool.size(); i++) {
                threadPool.get(i).join();
                threadPool.remove(i);
            }
            System.out.println("Number of active threads " + Thread.activeCount());
        } catch(Exception e){
            System.out.println("Error joining threads");
            e.printStackTrace();
        }
    }

}
