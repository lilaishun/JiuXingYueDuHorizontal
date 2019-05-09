package com.jiuxingyuedu.horizontal.Bean;

public class DateNews {
    /**
     *   <Paper>
     *     <id>N00001</id>
     *     <Name>人民日报</Name>
     *     <Url>jpg/2019/03/13/人民日报/0_s.jpg</Url>
     *     <online>http://paper.people.com.cn/rmrb/html/2019-03/13/nbs.D110000renmrb_01.htm</online>
     *   </Paper>
     */

    private String id;
    private String Name;
    private String Url;
    private String online;
private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
