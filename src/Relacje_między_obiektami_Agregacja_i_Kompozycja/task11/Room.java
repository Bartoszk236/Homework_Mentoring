package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task11;

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
