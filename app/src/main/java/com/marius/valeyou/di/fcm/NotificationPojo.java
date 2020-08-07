package com.marius.valeyou.di.fcm;

import java.io.Serializable;

/**
 * Created by jatin on 6/13/2017.
 */

public class NotificationPojo implements Serializable {

    private Message message;

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    private int badge;

    public int getBadge() {
        return this.badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    private int section;

    public int getSection() {
        return this.section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    private int tableid;

    public int getTableid() {
        return this.tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    private int notificationid;

    public int getNotificationid() {
        return this.notificationid;
    }

    public void setNotificationid(int notificationid) {
        this.notificationid = notificationid;
    }


    class Message {

        /**
         * sound : default
         * body : Event is registered by R. techwin
         * title : Dojo
         */

        private String sound;
        private String body;
        private String title;

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
