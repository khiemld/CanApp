package com.example.canapp.model.discussion;

public class Discussion {
    private String titleDiscussion;

    private String nameownerDiscussion;

    private String dateDiscussion;

    public Discussion(String titleDiscussion, String nameownerDiscussion, String dateDiscussion) {
        this.titleDiscussion = titleDiscussion;
        this.nameownerDiscussion = nameownerDiscussion;
        this.dateDiscussion = dateDiscussion;
    }

    public String getTitleDiscussion() {
        return titleDiscussion;
    }

    public void setTitleDiscussion(String titleDiscussion) {
        this.titleDiscussion = titleDiscussion;
    }

    public String getNameownerDiscussion() {
        return nameownerDiscussion;
    }

    public void setNameownerDiscussion(String nameownerDiscussion) {
        this.nameownerDiscussion = nameownerDiscussion;
    }

    public String getDateDiscussion() {
        return dateDiscussion;
    }

    public void setDateDiscussion(String dateDiscussion) {
        this.dateDiscussion = dateDiscussion;
    }
}
