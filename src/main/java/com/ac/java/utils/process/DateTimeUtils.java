import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class SMSDateTimeUtils {

    public static Date getDateFromString(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            throw new SMSServiceException(SMSExceptionCode.BAD_REQUEST.getResponseCode(), "Date cannot be empty");
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SMSConstants.dd_MM_yyyy);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(strDate);

        } catch (ParseException e) {
            throw new SMSServiceException(SMSExceptionCode.BAD_REQUEST.getResponseCode(), "Invalid Date format provided");
        }
    }

    public static int getWeekdayNumber(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int months) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static Date getOneDayLessThanNextYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE,-1);
        return cal.getTime();
    }

    public static int getMonthsDiff(Date date1, Date date2) {
        YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
        YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

        return (int) (m1.until(m2, ChronoUnit.MONTHS) + 1);
    }

    private static Date getTodayDateWithoutTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int getWeekDayNumberOfToday() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) -1;
    }

    public static boolean isCurrentDateBetweenDates(Date startDate, Date endDate) {
        Date todayDate = getTodayDateWithoutTime();
        return (startDate.compareTo(todayDate) <= 0 && endDate.compareTo(todayDate) >= 0);
    }

    //TODO: rather go with compare function
    public static boolean isTodayBetweenDates(Date startDate, Date endDate) {
        Date todayDate = new Date();
        return ((startDate.equals(todayDate) || startDate.before(todayDate)) &&
                (endDate.equals(todayDate) || endDate.after(todayDate)));
    }

    public static boolean isFirstDayOfMonth(Date effectiveDate) {
        Calendar c = new GregorianCalendar();
        c.setTime(effectiveDate);
        return c.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static Date getLastDateOfMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
        return cal.getTime();
    }
}
