package at.htlkaindorf.bigbrain.beans;

public class Category {
    private int cid;
    private String title;
    private String lang;

    public Category() {
    }

    public Category(int cid, String title, String lang) {
        this.cid = cid;
        this.title = title;
        this.lang = lang;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
