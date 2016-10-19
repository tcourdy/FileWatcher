package src;

/**
 * Created by tarik on 10/12/16.
 */
public class FileDetail {

    private long lineCount;
    private long lastModifiedTime;

    public FileDetail(long lastModifiedTime, long lineCount){
        this.lineCount = lineCount;
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getLineCount() {
        return lineCount;
    }

    public void setLineCount(long lineCount) {
        this.lineCount = lineCount;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
