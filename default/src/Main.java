package src;

import java.io.File;

/**
 * Created by tarik on 10/15/16.
 */
public class Main {

    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("You must provide a directory and a file pattern");
            return;
        }

        File dirToWatch = new File(args[0]);
        String fileExt = args[1];
        System.out.println(args[0]);
        System.out.println(args[1]);

        if(!dirToWatch.exists() || !dirToWatch.isDirectory()){
            System.out.println("Please specify an existing file directory.");
            return;
        }

        FileWatch fw = new FileWatch(fileExt);
        //Initial setup
        fw.watchFilesWrapper(dirToWatch);

        //Set a timer to go off every 10 seconds and see if any files have changed
        long tenSec = System.currentTimeMillis() + 10000; // 10 seconds timer
        while(true){
            if(System.currentTimeMillis() >= tenSec){
                fw.watchFilesWrapper(dirToWatch);
                tenSec = System.currentTimeMillis() + 10000; //reset the timer
            }
        }

    }
}
