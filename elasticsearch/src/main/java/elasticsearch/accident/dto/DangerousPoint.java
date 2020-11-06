package elasticsearch.accident.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.geo.Point;

public class DangerousPoint {



    private Point startLocation;
    private Integer amount;



    public DangerousPoint() {
    }

    public Point getStartLocation() {
        return startLocation;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
