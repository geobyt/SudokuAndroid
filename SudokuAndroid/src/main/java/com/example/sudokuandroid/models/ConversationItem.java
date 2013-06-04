package com.example.sudokuandroid.models;

/**
 * Created by Ken on 6/2/13.
 */
public class ConversationItem {
    private String content;
    private String jid;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public ConversationItem(String jid, String content) {
        this.jid = jid;
        this.content = content;
    }
}
