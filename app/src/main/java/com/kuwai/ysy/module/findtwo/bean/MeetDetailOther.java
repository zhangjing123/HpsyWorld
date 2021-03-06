package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class MeetDetailOther {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"uid":10044,"nickname":"七娃","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/18/b0ab0387d1dbbfc495f260133abf006e.jpg","gender":1,"age":"25","lastarea":"吴中区","r_id":27,"r_img":[],"sincerity":4,"name":"旅行","girl_friend":0,"consumption_type":0,"is_shuttle":1,"is_carry":0,"return_time":1553472000,"trip_mode":"往返双飞","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553385600,"other":"","message":"","address_name":"东华度假村","film_name":"","cover":"","year":"","score":"","director":"","stardom":"","genres":"","type":0,"address":"胥口镇孙武路601号(近苏福路)","distance":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
         * uid : 10044
         * nickname : 七娃
         * avatar : http://test.yushuiyuan.cn/public/static/img/avatar/201901/18/b0ab0387d1dbbfc495f260133abf006e.jpg
         * gender : 1
         * age : 25
         * lastarea : 吴中区
         * r_id : 27
         * r_img : []
         * sincerity : 4
         * name : 旅行
         * girl_friend : 0
         * consumption_type : 0
         * is_shuttle : 1
         * is_carry : 0
         * return_time : 1553472000
         * trip_mode : 往返双飞
         * motion_name :
         * game_theme :
         * game_area :
         * online_time :
         * release_time : 1553385600
         * other :
         * message :
         * address_name : 东华度假村
         * film_name :
         * cover :
         * year :
         * score :
         * director :
         * stardom :
         * genres :
         * type : 0
         * address : 胥口镇孙武路601号(近苏福路)
         * distance : 0
         */

        private int uid;
        private String nickname;
        private String avatar;
        private int gender;
        private String age;
        private String lastarea;
        private int r_id;
        private int sincerity;
        private String name;
        private int girl_friend;
        private int consumption_type;
        private int is_shuttle;
        private int is_carry;
        private int return_time;
        private String trip_mode;
        private String motion_name;
        private String game_theme;
        private String game_area;
        private String online_time;
        private int release_time;
        private String other;
        private String message;
        private String address_name;
        private String film_name;
        private String cover;
        private String year;
        private String score;
        private String director;
        private String stardom;
        private String genres;
        private int type;
        private String address;
        private float distance;
        private List<String> r_img;
        private int is_success;
        private String game_nickname;
        private double longitude;
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getIs_success() {
            return is_success;
        }

        public void setIs_success(int is_success) {
            this.is_success = is_success;
        }

        public String getGame_nickname() {
            return game_nickname == null ? "" : game_nickname;
        }

        public void setGame_nickname(String game_nickname) {
            this.game_nickname = game_nickname == null ? "" : game_nickname;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getLastarea() {
            return lastarea;
        }

        public void setLastarea(String lastarea) {
            this.lastarea = lastarea;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getSincerity() {
            return sincerity;
        }

        public void setSincerity(int sincerity) {
            this.sincerity = sincerity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGirl_friend() {
            return girl_friend;
        }

        public void setGirl_friend(int girl_friend) {
            this.girl_friend = girl_friend;
        }

        public int getConsumption_type() {
            return consumption_type;
        }

        public void setConsumption_type(int consumption_type) {
            this.consumption_type = consumption_type;
        }

        public int getIs_shuttle() {
            return is_shuttle;
        }

        public void setIs_shuttle(int is_shuttle) {
            this.is_shuttle = is_shuttle;
        }

        public int getIs_carry() {
            return is_carry;
        }

        public void setIs_carry(int is_carry) {
            this.is_carry = is_carry;
        }

        public int getReturn_time() {
            return return_time;
        }

        public void setReturn_time(int return_time) {
            this.return_time = return_time;
        }

        public String getTrip_mode() {
            return trip_mode;
        }

        public void setTrip_mode(String trip_mode) {
            this.trip_mode = trip_mode;
        }

        public String getMotion_name() {
            return motion_name;
        }

        public void setMotion_name(String motion_name) {
            this.motion_name = motion_name;
        }

        public String getGame_theme() {
            return game_theme;
        }

        public void setGame_theme(String game_theme) {
            this.game_theme = game_theme;
        }

        public String getGame_area() {
            return game_area;
        }

        public void setGame_area(String game_area) {
            this.game_area = game_area;
        }

        public String getOnline_time() {
            return online_time;
        }

        public void setOnline_time(String online_time) {
            this.online_time = online_time;
        }

        public int getRelease_time() {
            return release_time;
        }

        public void setRelease_time(int release_time) {
            this.release_time = release_time;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getFilm_name() {
            return film_name;
        }

        public void setFilm_name(String film_name) {
            this.film_name = film_name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getStardom() {
            return stardom;
        }

        public void setStardom(String stardom) {
            this.stardom = stardom;
        }

        public String getGenres() {
            return genres;
        }

        public void setGenres(String genres) {
            this.genres = genres;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public List<String> getR_img() {
            return r_img;
        }

        public void setR_img(List<String> r_img) {
            this.r_img = r_img;
        }
    }
}
