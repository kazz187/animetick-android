package net.animetick.animetick_android.model.ticket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private List<String> flags = new ArrayList<String>();
    private boolean isWatched = false;

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

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

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public int getTitleId() {
        return titleId;
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

    public String getIconPath() {
        return iconPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ticket ticket = (Ticket) obj;
        return this.titleId == ticket.getTitleId() && this.count == ticket.getCount();
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public boolean isBroadcasted() {
        Date now = new Date();
        return now.before(startAt);
    }

    public List<String> getFlags() {
        return flags;
    }
}
