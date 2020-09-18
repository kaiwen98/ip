/**
 * Class that stores and manages the output of Datetime classes
 */

package duke.dukehelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    private LocalDateTime dateTime;

    public DateTimeManager(String startDate){
        this.setDateTime(startDate);
    }

    public void setDateTime(String dateTime){
        this.dateTime = this.dateTime.parse(dateTime);
    }

    /**
     * Takes in one format specifier string and outputs datetime string with the corresponding format
     * @param whichFormat
     * @return Formatted dateTime string
     */
    public String getDateFormatted(String whichFormat){
        String output = "";

        switch (whichFormat) {
        case "date":
            // May 27 2020
            output = this.dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MMM d YYYY"));
            break;
        case "day":
            // Tuesday
            output = this.dateTime.getDayOfWeek().toString();
            break;
        case "month":
            // May
            output = this.dateTime.getMonth().toString();
            break;
        case "year":
            // 2020
            output = Integer.toString(dateTime.getYear());
            break;
        case "time":
            //00:00
            output = this.dateTime.toLocalTime().toString();
            break;
        case "datetime":
            // Show all information
            output = this.dateTime.format(DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss"));
            break;
        default:
            String[] args = {"day", "month", "year", "time"};
            output = this.getDateFormatted(args);
            break;
        }
        return output;
    }

    /**
     * Handles a String array of format specifiers and arranges the tokens of formatted datetime substrings in a proper order
     * @param whichFormat
     * @return Formatted dateTime string
     */
    public String getDateFormatted(String[] whichFormat){
        String[] token = new String[Constants.MAX_ARRAY_LEN];
        String dateTime;
        String output = "";
        if (whichFormat == null){
            return getDateFormatted("dateTime");
        }

        for(String format : whichFormat) {
            dateTime = getDateFormatted(format);
            switch (format) {
            case "day":
                token[0] = dateTime;
                break;
            case "month":
                token[2] = dateTime;
                break;
            case "year":
                token[3] = dateTime;
                break;
            case "time":
                token[4] = dateTime;
                break;
            default:
                // Fall-through
                token[1] = dateTime;
                break;
            }
        }
        for(int i = 0; i < 4; i++){
            if(token[i] != null){
                output += token[i] + " ";
            }
        }
        return output.trim();
    }
}
