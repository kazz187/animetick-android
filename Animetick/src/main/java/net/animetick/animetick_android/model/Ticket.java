package net.animetick.animetick_android.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kazz on 2013/08/11.
 */
public class Ticket {
    private int titleId;
    private String title = null;
    private int count = -1;
    private String subTitle = null;
    private String chName = null;
    private int chNum = -1;
    private Date startAt = null;
    private String iconPath;
    private List<String> flags;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public void setChNum(int chNum) {
        this.chNum = chNum;
    }

    public void setStartAt(String startAt) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        this.startAt = (Date) df.parseObject(startAt);
    }

    public void setStartAt(Date startAt) throws ParseException {
        this.startAt = startAt;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getChName() {
        return chName;
    }

    public int getChNum() {
        return chNum;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public String getIconPath() {
        return iconPath;
    }
}
