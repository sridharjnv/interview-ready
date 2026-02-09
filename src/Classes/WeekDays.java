package Classes;

public enum WeekDays {
        MONDAY(1, "1st day of the week"),
        TUESDAY(2, "2nd day of the week"),
        WEDNESDAY(3, "3rd day of the week"),
        THURSDAY(4,"4th day of the week"),
        FRIDAY(5,"5th day of the week"),
        SATURDAY(6,"6th day of the week");


        private final int day;
        private final String dayName;

        WeekDays(int day, String dayName) {
                this.day = day;
                this.dayName = dayName;
        }
}
