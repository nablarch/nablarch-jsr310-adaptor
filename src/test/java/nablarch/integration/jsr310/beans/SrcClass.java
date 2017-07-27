package nablarch.integration.jsr310.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SrcClass {
    private String name;
    private LocalDate date1;
    private LocalDateTime dateTime1;
    private Date date2;
    private Timestamp dateTime2;
    private String date3;
    private String dateTime3;
    private List<LocalDate> dateTimes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public LocalDateTime getDateTime1() {
        return dateTime1;
    }

    public void setDateTime1(LocalDateTime dateTime1) {
        this.dateTime1 = dateTime1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Timestamp getDateTime2() {
        return dateTime2;
    }

    public void setDateTime2(Timestamp dateTime2) {
        this.dateTime2 = dateTime2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getDateTime3() {
        return dateTime3;
    }

    public void setDateTime3(String dateTime3) {
        this.dateTime3 = dateTime3;
    }

    public List<LocalDate> getDateTimes() {
        return dateTimes;
    }

    public void setDateTimes(final List<LocalDate> dateTimes) {
        this.dateTimes = dateTimes;
    }
}
