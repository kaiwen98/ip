package duke.dukehelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    private LocalDateTime dateTime;

    public DateTimeManager(){
    }

    public DateTimeManager(String startDate, String endDate){
        this.setStartEndDate(startDate, endDate);
    }

    public DateTimeManager(String startDate){
        this.setDateTime(startDate);
    }

    public void setDateTime(String dateTime){
        this.dateTime = this.dateTime.parse(dateTime);
    }

    public void setStartEndDate(String startDate, String endDate){
        this.dateTime = this.dateTime.parse(startDate);
    }

    public String getDateFormatted(String whichFormat){
        String output = "";

        switch (whichFormat) {
        case "date":
            output = this.dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MMM d YYYY"));
            break;
        case "day":
            output = this.dateTime.getDayOfWeek().toString();
            break;
        case "month":
            output = this.dateTime.getMonth().toString();
            break;
        case "year":
            output = Integer.toString(dateTime.getYear());
            break;
        case "time":
            output = this.dateTime.toLocalTime().toString();
            break;
        case "verbose":
            output = this.dateTime.format(DateTimeFormatter.ofPattern("MMM/d/YYYY H:m:s"));
            break;
        default:
            // Fall-through
            break;
        }
        return output;
    }

    public String getDateFormatted(String[] whichFormat){
        String[] output = new String[Constants.MAX_ARRAY_LEN];
        String dateTime;
        for(String format : whichFormat) {
            dateTime = getDateFormatted(format);
            switch (format) {
            case "date":
                // Fall through
            case "verbose":
                // Fall through
            case "day":
                output[0] = dateTime;
                break;
            case "month":
                output[1] = dateTime;
                break;
            case "year":
                output[2] = dateTime;
                break;
            case "time":
                output[3] = dateTime;
                break;
            default:
                // Fall-through
                break;
            }
        }
        return String.join(" ", output).trim();
    }
}
