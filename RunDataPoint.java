


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:
 * Copyright: Copyright(c)
 * CreateTime: 2019/7/1 15:58
 * <p>
 * author syber
 * version 1.0
 */
@Entity
public class RunDataPoint {

/*    sportId	String	关联 到 运动记录的Id
    timeStamp	String	GPS 点的时间戳
    longtitude	Double	经度
    latitude	Double	纬度
    elevation	Double	海拔
    toPrePace	Double	与前一个点的配速
    toPreDuration	Double	与前一个点的时长
    toPreDistance	Double	与前一个点的距离
    toStartDuration	Double	与运动开始的总时长
    toStartDistance	Double	与运动开始 的总距离
    isPause	Int  0不是，1是	是否是暂停点
    kmStone	Int  0不是，1是	是否是公里配速点
    steps	Int  系统读取的的步数
    kmStoneIndex	Int	标记 第几公里索引*/

    //不能用int

    private Long pointId;

    public  String sportId="";
    public  String timeStamp="";
    public  double longtitude = 0;
    public  double latitude = 0;
    public  double elevation = 0;
    public  double toPrePace = 0;
    public  double toPreDistance = 0;
    public  double toPreDuration = 0;
    public  double toStartDuration = 0;
    public  double toStartDistance = 0;
    public  double speed = 0;
    public  int isPause = 0;
    public  int kmStone = 0;
    public  int kmStoneIndex = 0;
    public  int steps = 0;
    public  int toPreSteps = 0;
    public  int toStartSteps = 0;

    public RunDataPoint(Long pointId, String sportId, String timeStamp,
            double longtitude, double latitude, double elevation, double toPrePace,
            double toPreDistance, double toPreDuration, double toStartDuration,
            double toStartDistance, double speed, int isPause, int kmStone,
            int kmStoneIndex, int steps, int toPreSteps, int toStartSteps) {
        this.pointId = pointId;
        this.sportId = sportId;
        this.timeStamp = timeStamp;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.elevation = elevation;
        this.toPrePace = toPrePace;
        this.toPreDistance = toPreDistance;
        this.toPreDuration = toPreDuration;
        this.toStartDuration = toStartDuration;
        this.toStartDistance = toStartDistance;
        this.speed = speed;
        this.isPause = isPause;
        this.kmStone = kmStone;
        this.kmStoneIndex = kmStoneIndex;
        this.steps = steps;
        this.toPreSteps = toPreSteps;
        this.toStartSteps = toStartSteps;
    }

    public RunDataPoint() {
    }
    public Long getPointId() {
        return this.pointId;
    }
    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }
    public String getSportId() {
        return this.sportId;
    }
    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
    public String getTimeStamp() {
        return this.timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public double getLongtitude() {
        return this.longtitude;
    }
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getElevation() {
        return this.elevation;
    }
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
    public double getToPrePace() {
        return this.toPrePace;
    }
    public void setToPrePace(double toPrePace) {
        this.toPrePace = toPrePace;
    }
    public double getToPreDistance() {
        return this.toPreDistance;
    }
    public void setToPreDistance(double toPreDistance) {
        this.toPreDistance = toPreDistance;
    }
    public double getToPreDuration() {
        return this.toPreDuration;
    }
    public void setToPreDuration(double toPreDuration) {
        this.toPreDuration = toPreDuration;
    }
    public double getToStartDuration() {
        return this.toStartDuration;
    }
    public void setToStartDuration(double toStartDuration) {
        this.toStartDuration = toStartDuration;
    }
    public double getToStartDistance() {
        return this.toStartDistance;
    }
    public void setToStartDistance(double toStartDistance) {
        this.toStartDistance = toStartDistance;
    }
    public double getSpeed() {
        return this.speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public int getIsPause() {
        return this.isPause;
    }
    public void setIsPause(int isPause) {
        this.isPause = isPause;
    }
    public int getKmStone() {
        return this.kmStone;
    }
    public void setKmStone(int kmStone) {
        this.kmStone = kmStone;
    }
    public int getKmStoneIndex() {
        return this.kmStoneIndex;
    }
    public void setKmStoneIndex(int kmStoneIndex) {
        this.kmStoneIndex = kmStoneIndex;
    }
    public int getSteps() {
        return this.steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
    public int getToPreSteps() {
        return this.toPreSteps;
    }
    public void setToPreSteps(int toPreSteps) {
        this.toPreSteps = toPreSteps;
    }
    public int getToStartSteps() {
        return this.toStartSteps;
    }
    public void setToStartSteps(int toStartSteps) {
        this.toStartSteps = toStartSteps;
    }

    @Override
    public String toString() {
        return "RunDataPoint{" +
                "pointId=" + pointId +
                ", sportId='" + sportId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", elevation=" + elevation +
                ", toPrePace=" + toPrePace +
                ", toPreDistance=" + toPreDistance +
                ", toPreDuration=" + toPreDuration +
                ", toStartDuration=" + toStartDuration +
                ", toStartDistance=" + toStartDistance +
                ", speed=" + speed +
                ", isPause=" + isPause +
                ", kmStone=" + kmStone +
                ", kmStoneIndex=" + kmStoneIndex +
                ", steps=" + steps +
                ", toPreSteps=" + toPreSteps +
                ", toStartSteps=" + toStartSteps +
                '}';
    }
}
