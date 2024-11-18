package src.relationships.between.objects.aggregation.composition.task11;

import java.math.BigDecimal;

public class Room {
    private BigDecimal area;

    public BigDecimal getArea() {
        return area;
    }

    public Room setArea(BigDecimal area) {
        this.area = area;
        return this;
    }
}