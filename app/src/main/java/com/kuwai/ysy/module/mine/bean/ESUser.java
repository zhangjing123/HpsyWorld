package com.kuwai.ysy.module.mine.bean;

/**
 * @author
 * @blog
 * @time
 * @tips
 * @fuction
 */
public class ESUser {

    /**
     * status : 0
     * msg : 操作成功
     * data : {"id":8,"username":"ray","password":"19940531","screenname":"admin","email":"leizhkkk@126.com","phone":"15102089158","cover":"http://rayhahah.com","question":"问题","answer":"答案","hupuUsername":"hupuray","hupuPassword":"F835B9CCD1DA1623672AEBD9B7626852","createTime":1505196640000,"updateTime":1505196640000}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8
         * username : ray
         * password : 19940531
         * screenname : admin
         * email : leizhkkk@126.com
         * phone : 15102089158
         * cover : http://rayhahah.com
         * question : 问题
         * answer : 答案
         * hupuUsername : hupuray
         * hupuPassword : F835B9CCD1DA1623672AEBD9B7626852
         * createTime : 1505196640000
         * updateTime : 1505196640000
         */

        private int id;
        private String username;
        private String password;
        private String screenname;
        private String email;
        private String phone;
        private String cover;
        private String question;
        private String answer;
        private String hupuUsername;
        private String hupuPassword;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getScreenname() {
            return screenname;
        }

        public void setScreenname(String screenname) {
            this.screenname = screenname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getHupuUsername() {
            return hupuUsername;
        }

        public void setHupuUsername(String hupuUsername) {
            this.hupuUsername = hupuUsername;
        }

        public String getHupuPassword() {
            return hupuPassword;
        }

        public void setHupuPassword(String hupuPassword) {
            this.hupuPassword = hupuPassword;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
