public class Constants {
    public enum Error {
        WRONG_ARGUMENTS,
        INVALID_COMMAND,
        NO_LIST,
        NO_ERROR,
        OTHER_ERROR
    }

    public enum Command {
        HELLO,
        BYE,
        ECHO,
        INPUT,
        INSERT_TASK_DEADLINE,
        INSERT_TASK_TODO,
        INSERT_TASK_EVENT,
        SHOW_LIST,
        SHOW_COMMANDS,
        MARK_TASK_AS_DONE
    }

    public enum DaysOfTheWeek{
        MON("Mon"),
        TUES("Tues"),
        WED("Wed"),
        THU("Thu"),
        FRI("Fri"),
        SAT("Sat"),
        SUN("Sun");

        private String literal;
        DaysOfTheWeek(String literal){
            this.literal = literal;
        }
        @Override
        public String toString(){
            return this.literal;
        }
        public DaysOfTheWeek getDaysOfTheWeek(String dayInput){
            switch (dayInput) {
            case "mon":
            case "monday":
                return MON;
            case "tue":
            case "tues":
            case "tuesday":
                return TUES;
            case "wed":
            case "weds":
            case "wednesday":
                return WED;
            case "thu":
            case "thurs":
            case "thursday":
                return THU;
            case "fri":
            case "friday":
                return FRI;
            case "sat":
            case "saturday":
                return SAT;
            case "sun":
            case "sunday":
                return SUN;
            default:
                return null;
            }
        }
    }

    public enum monthsOfTheYear{
        JAN("Jan"),
        FEB("Feb"),
        MAR("Mar"),
        APR("Apr"),
        MAY("May"),
        JUN("Jun"),
        JUL("Jul"),
        AUG("Aug"),
        SEP("Sep"),
        OCT("Oct"),
        NOV("Nov"),
        DEC("Dec");

        private String literal;
        monthsOfTheYear(String literal){
            this.literal = literal;
        }
        @Override
        public String toString(){
            return this.literal;
        }
    }
}
