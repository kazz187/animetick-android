package net.animetick.animetick_android.model.ticket;

import net.animetick.animetick_android.model.Episode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kazz on 2013/08/11.
 */
public class Ticket extends Episode {
    private String chName = null;
    private int chNum = -1;
    private Date startAt = null;
    private List<String> flags = new ArrayList<String>();

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

    public void setFlags(List<String> flags) {
        this.flags = flags;
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

    @Override
    public boolean isBroadcasted() {
        Date now = new Date();
        return now.after(startAt);
    }

    public List<String> getFlags() {
        return flags;
    }

}
