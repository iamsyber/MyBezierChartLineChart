

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:
 * Copyright: Copyright(c)
 * CreateTime: 2019/7/1 16:05
 * <p>
 * version 1.0
 */

/*
Id	String	服务端运动数据 唯一标识符,只有上传成功的记录 才会有
        sportType	Int	跑步:0 、健走:1、骑行:2 （太爱跑只有 跑步）
        userId	Int	用户id
        sportSubType	Int	室外:0 、室内:1
        startTime	String	开始时间 精确到毫秒，也可以用这个来作为 数据 唯一判重
        endTime	String	结束时间
        city	String	所在城市
        totalCalories	Double	总卡路里
        totalDistance	Double	总距离
        totalDuration	Double	总时长
        avgPace	Double	平均配速
        sportRoute Thumbnail	String	轨迹缩略图URL
        sportRouteImage	String	轨迹 大图 （占位），可能会用于H5分享 等等
        isCheat	Int    0未作弊，1 作弊	是否作弊【本地 或者服务器判断，本地可以是粗略判断】，与服务器同步
        steps		当前记录的总步数（占位）
        pacePerKM	200,300,500	每公里配速
        strideFrequency	String 45,90,120,200	每分钟 步数 （占位），单位：步/分钟
        gpsPoints	Array[Dictionary,Dictionary](本地数据结构使用)	GPS 点位
        gpsUrl	String   http://taiaipao.com/xxx.zip	GPS 点位 文件 的zip 包
        fileHash	String	GPS 点位 文件 的zip 包 的哈希 校验 值，用于保证 数据正确性
        identification	Int  0 新数据     1 旧数据	标记是否为老数据，方便区别解析
*/


public class RunDataNormal  implements Comparable<RunDataNormal> {


    @Id(autoincrement = true)
    public Long routeId;

    public Long id;
    public String strideFrequency="";
    public String pacePerKm="";
    public int sportSubType=0;
    public int steps=0;
    public int isCheat=0;
    public String sportRouteImage="";
    public String sportRouteThumbnail="";
    public double avgPace=0;
    public double totalDuration=0;
    public double totalDistance=0;
    public double totalCalories=0;
    public String city="";
    public String endTime="";
    public String startTime ;
    public int sportType=0;
    public int userId=0;
    public int identification  =0;   //新数据为0  老数据为1
    public String gpsUrl="";
    public String fileHash="";





    public RunDataNormal(Long routeId, Long id, String strideFrequency, String pacePerKm,
                         int sportSubType, int steps, int isCheat, String sportRouteImage,
                         String sportRouteThumbnail, double avgPace, double totalDuration, double totalDistance,
                         double totalCalories, String city, String endTime, String startTime, int sportType,
                         int userId, int identification, String gpsUrl, String fileHash) {
        this.routeId = routeId;
        this.id = id;
        this.strideFrequency = strideFrequency;
        this.pacePerKm = pacePerKm;
        this.sportSubType = sportSubType;
        this.steps = steps;
        this.isCheat = isCheat;
        this.sportRouteImage = sportRouteImage;
        this.sportRouteThumbnail = sportRouteThumbnail;
        this.avgPace = avgPace;
        this.totalDuration = totalDuration;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.city = city;
        this.endTime = endTime;
        this.startTime = startTime;
        this.sportType = sportType;
        this.userId = userId;
        this.identification = identification;
        this.gpsUrl = gpsUrl;
        this.fileHash = fileHash;
    }



    public RunDataNormal() {
    }




    @Override
    public int compareTo(RunDataNormal runDataNormal) {
        return (int)(Long.parseLong(runDataNormal.getStartTime())-Long.parseLong(this.getStartTime()));
    }




    public Long getRouteId() {
        return this.routeId;
    }




    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }




    public Long getId() {
        return this.id;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public String getStrideFrequency() {
        return this.strideFrequency;
    }




    public void setStrideFrequency(String strideFrequency) {
        this.strideFrequency = strideFrequency;
    }




    public String getPacePerKm() {
        return this.pacePerKm;
    }




    public void setPacePerKm(String pacePerKm) {
        this.pacePerKm = pacePerKm;
    }




    public int getSportSubType() {
        return this.sportSubType;
    }




    public void setSportSubType(int sportSubType) {
        this.sportSubType = sportSubType;
    }




    public int getSteps() {
        return this.steps;
    }




    public void setSteps(int steps) {
        this.steps = steps;
    }




    public int getIsCheat() {
        return this.isCheat;
    }




    public void setIsCheat(int isCheat) {
        this.isCheat = isCheat;
    }




    public String getSportRouteImage() {
        return this.sportRouteImage;
    }




    public void setSportRouteImage(String sportRouteImage) {
        this.sportRouteImage = sportRouteImage;
    }




    public String getSportRouteThumbnail() {
        return this.sportRouteThumbnail;
    }




    public void setSportRouteThumbnail(String sportRouteThumbnail) {
        this.sportRouteThumbnail = sportRouteThumbnail;
    }




    public double getAvgPace() {
        return this.avgPace;
    }




    public void setAvgPace(double avgPace) {
        this.avgPace = avgPace;
    }




    public double getTotalDuration() {
        return this.totalDuration;
    }




    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }




    public double getTotalDistance() {
        return this.totalDistance;
    }




    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }




    public double getTotalCalories() {
        return this.totalCalories;
    }




    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }




    public String getCity() {
        return this.city;
    }




    public void setCity(String city) {
        this.city = city;
    }




    public String getEndTime() {
        return this.endTime;
    }




    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }




    public String getStartTime() {
        return this.startTime;
    }




    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }




    public int getSportType() {
        return this.sportType;
    }




    public void setSportType(int sportType) {
        this.sportType = sportType;
    }




    public int getUserId() {
        return this.userId;
    }




    public void setUserId(int userId) {
        this.userId = userId;
    }




    public int getIdentification() {
        return this.identification;
    }




    public void setIdentification(int identification) {
        this.identification = identification;
    }




    public String getGpsUrl() {
        return this.gpsUrl;
    }




    public void setGpsUrl(String gpsUrl) {
        this.gpsUrl = gpsUrl;
    }




    public String getFileHash() {
        return this.fileHash;
    }




    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    @Override
    public String toString() {
        return "RunDataNormal{" +
                "routeId=" + routeId +
                ", id=" + id +
                ", strideFrequency='" + strideFrequency + '\'' +
                ", pacePerKm='" + pacePerKm + '\'' +
                ", sportSubType=" + sportSubType +
                ", steps=" + steps +
                ", isCheat=" + isCheat +
                ", sportRouteImage='" + sportRouteImage + '\'' +
                ", sportRouteThumbnail='" + sportRouteThumbnail + '\'' +
                ", avgPace=" + avgPace +
                ", totalDuration=" + totalDuration +
                ", totalDistance=" + totalDistance +
                ", totalCalories=" + totalCalories +
                ", city='" + city + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", sportType=" + sportType +
                ", userId=" + userId +
                ", identification=" + identification +
                ", gpsUrl='" + gpsUrl + '\'' +
                ", fileHash='" + fileHash + '\'' +
                '}';
    }
}
