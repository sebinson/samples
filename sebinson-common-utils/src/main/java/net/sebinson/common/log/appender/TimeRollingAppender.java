package net.sebinson.common.log.appender;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import net.sebinson.common.log.LogConfiguration;
import net.sebinson.common.utils.TimeUtil;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class TimeRollingAppender extends FileAppender {

    private String scheduledFilename;
    private long   nextCheck   = System.currentTimeMillis() - 1;
    private String datePattern = TimeUtil.FORMAT6;
    Date           now         = new Date();

    public TimeRollingAppender() {
        super();
    }

    /**
     * Instantiate a <code>MinuteRollingAppender2</code> and open the file designated by <code>filename</code>. The opened filename will become the ouput
     * destination for this appender.
     */
    public TimeRollingAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, true);
        this.activateOptions();
    }

    @Override
    public void activateOptions() {
        org.apache.log4j.PatternLayout pl = new org.apache.log4j.PatternLayout();
        pl.setConversionPattern("%m%n");
        this.setLayout(pl);
        this.setAppend(true);
        this.setEncoding("UTF-8");

        super.activateOptions();
        //sdf = new SimpleDateFormat(datePattern);
        if (this.fileName != null) {
            this.now.setTime(System.currentTimeMillis());
            File file = new File(this.fileName);
            String name = null;
            if (file.exists() && file.length() > 0) {
                Date fileLMDate = new Date(file.lastModified());
                Date fileLMDate2 = this.getIntervalDate(fileLMDate);
                name = this.getDatedFilename(fileLMDate2);
                if (new File(name).exists()) {
                    name = this.getDatedFilename(fileLMDate);
                    if (new File(name).exists()) {
                        name = null;
                    }
                }
            }
            if (name == null) {
                name = this.getDatedFilename(this.now);
            }
            this.scheduledFilename = name;
        } else {
            LogLog.error("Either File or DatePattern options are not set for appender [" + this.name + "].");
        }
    }

    /**
     * This method differentiates MinuteRollingAppender2 from its super class.
     *
     * <p>
     * Before actually logging, this method will check whether it is time to do a rollover. If it is, it will schedule the next rollover time and then rollover.
     * */
    @Override
    protected void subAppend(LoggingEvent event) {
        long n = System.currentTimeMillis();
        if (n >= this.nextCheck) {
            this.now.setTime(n);
            long nc = this.getIntervalDate(this.now).getTime();
            if (nc != this.nextCheck) {
                this.nextCheck = nc;
                try {
                    this.rollOver();
                } catch (IOException ioe) {
                    if (ioe instanceof InterruptedIOException) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.error("rollOver() failed.", ioe);
                }
            }
        }
        super.subAppend(event);
    }

    /**
     * Rollover the current file to a new file.
     */
    void rollOver() throws IOException {
        String datedFilename = this.getDatedFilename(new Date(this.nextCheck));
        if (this.scheduledFilename.equals(datedFilename)) {
            return;
        }
        // close current file, and rename it to datedFilename
        this.closeFile();

        File target = new File(this.scheduledFilename);
        if (target.exists()) {
            Random r = new Random();
            int n2 = r.nextInt(10000);
            target = new File(this.scheduledFilename + "." + n2);
            if (target.exists()) {
                target.delete();
                LogLog.debug("delete file -> " + target.getPath());
            } else {
                target.getParentFile().mkdirs();
            }
        } else {
            target.getParentFile().mkdirs();
        }
        File file = new File(this.fileName);
        boolean result = file.renameTo(target);
        if (result) {
            LogLog.debug(this.fileName + " -> " + this.scheduledFilename);
        } else {
            LogLog.error("Failed to rename [" + this.fileName + "] to [" + this.scheduledFilename + "].");
        }

        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            this.errorHandler.error("setFile(" + this.fileName + ", true) call failed.");
        }
        this.scheduledFilename = datedFilename;
    }

    private String getDatedFilename(Date date) {
        File file = new File(this.fileName);
        String ffn = file.getName();

        String fn = ffn.split(LogConfiguration.SPLIT_CHAR)[0];
        String ds = TimeUtil.convertDateToString(date, this.datePattern);
        String flume = "";
        String flumeDir = LogConfiguration.getFlumeDir();
        if (flumeDir != null && !"".equals(flumeDir.trim())) {
            flume = flumeDir + File.separator;
        }
        String datedFilename = file.getParent() + File.separator + flume + fn + LogConfiguration.SPLIT_CHAR + ds;
        return datedFilename;
    }

    private Date getIntervalDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        long ms = instance.getTimeInMillis();
        long ms0 = ms % (LogConfiguration.getIntervalSencond() * 1000);
        instance.setTimeInMillis(ms - ms0);

        return instance.getTime();
    }

    public void setDatePattern(String pattern) {
        this.datePattern = pattern;
    }

    public String getDatePattern() {
        return this.datePattern;
    }
}
