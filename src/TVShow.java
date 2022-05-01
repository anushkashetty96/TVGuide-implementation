import java.util.Objects;
import java.util.Scanner;

public class TVShow implements Cloneable,Watchable{

    private String showID;
    private String showName;
    private Double startTime;
    private Double endTime;

    public TVShow() {
    }

    public TVShow(String showID, String showName, Double startTime, Double endTime) {
        this.showID = showID;
        this.showName = showName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TVShow(TVShow show, String showID) {
        this.showID= showID;
        this.showName= show.showName;
        this.startTime=new Double(show.startTime);
        this.endTime=new Double(show.endTime);
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }


    @Override
    public String toString() {
        return "TVShow{" +
                "showID='" + showID + '\'' +
                ", showName='" + showName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShow tvShow = (TVShow) o;
        return Objects.equals(showName, tvShow.showName) && Objects.equals(startTime, tvShow.startTime) && Objects.equals(endTime, tvShow.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showName, startTime, endTime);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter show ID");
        String id = sc.nextLine();
        TVShow clone = (TVShow) super.clone();
        return new TVShow(clone,id);
    }

    @Override
    public String isOnSameTime(TVShow show) {
        if(this.startTime.equals(show.startTime) && this.endTime.equals(show.endTime))
            return "Same time";
        else if((this.startTime<show.startTime && this.endTime>show.startTime)
                || (this.startTime>show.startTime && this.startTime<show.endTime))
            return "Some Overlap";
        else
            return "Different time";
    }
}
