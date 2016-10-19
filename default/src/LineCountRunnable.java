package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

public class LineCountRunnable implements Runnable{
    private File f;
    private Hashtable<String, FileDetail> table;
    public static Boolean isInit = true;

    public LineCountRunnable(File f, Hashtable<String, FileDetail> watchTable){
        this.f = f;
        this.table = watchTable;
    }
    public void run(){
        try{
            FileDetail fd = table.get(f.getAbsolutePath());
            // if it isn't in the watchTable yet then it is new so print name and num lines
            if(fd == null){
                long lineCount = countLines();
                System.out.println(f.getAbsolutePath() + "    " + lineCount);
                table.put(f.getAbsolutePath(), new FileDetail(f.lastModified(), lineCount));
            } else{
                //here we know about the file so see how many lines have changed and inform the user
                if(fd.getLastModifiedTime() < f.lastModified()){
                    long lineCount = countLines();
                    long diff = lineCount - fd.getLineCount();
                    if (diff < 0) {
                        System.out.println(f.getAbsolutePath() + "    " + diff);
                    }
                    else{
                        System.out.println(f.getAbsolutePath() + "    +" + diff); //print the '+' char if lines were added
                    }

                    fd.setLineCount(lineCount);
                    fd.setLastModifiedTime(f.lastModified());
                }

            }


        } catch (Exception e){
            System.out.println(e);
        } finally{
        }

    }

    private long countLines() throws Exception{
        long lineCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(f));
        while (br.readLine() != null){
            lineCount++;
        }
        return lineCount;
    }
}
