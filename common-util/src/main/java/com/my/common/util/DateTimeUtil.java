package com.my.common.util;

import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * dateTime 工具
 * @author huqilong
 * @version 1.0
 * @project common
 * @date 2016/9/3
 */
public class DateTimeUtil {

	/**
	 * 按年月日十分秒设置日期
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minuteOfHour
	 * @param secondOfMinute
	 * @param millisOfSecond
	 * @return
	 */
	public static Date getDate(int year, int monthOfYear, int dayOfMonth,
			int hourOfDay, int minuteOfHour, int secondOfMinute,
			int millisOfSecond) {
		DateTime dateTime = new DateTime(year, monthOfYear, dayOfMonth,
				hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
		return dateTime.toDate();
	}

	/**
	 * 按毫秒数创建时间
	 * 
	 * @param millisSeconds
	 * @return
	 */
	public static Date getDate(long millisSeconds) {
		DateTime dateTime = new DateTime(millisSeconds);
		return dateTime.toDate();
	}

	/**
	 * 传递一个字符类型的日期，返回一个date 例如：“1982-11-22” 例如：“1982/11/22” 例如：“1982 11 22”
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr) {
		String regex = "[-|\\s|\\/]+";
		dateStr = dateStr.replaceAll(regex, "-");
		DateTime dateTime = new DateTime(dateStr, DateTimeZone.UTC);
		return dateTime.toDate();
	}

	/**
	 * 传递一个字符类型的日期，返回一个date 例如：“1982-11-22 15:30:22” 例如：“1982/11/22 15:30:22”
	 * 例如：“1982 11 22 15:30:22”
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateTime(String dateStr) {
		String regex = "[-|\\s|\\/|:]+";
		dateStr = dateStr.replaceAll(regex, " ");
		String[] fieldsArray = dateStr.split(" ");
		int year = Integer.parseInt(fieldsArray[0]);
		int month = Integer.parseInt(fieldsArray[1]);
		int day = Integer.parseInt(fieldsArray[2]);
		int hour = Integer.parseInt(fieldsArray[3]);
		int minute = Integer.parseInt(fieldsArray[4]);
		int second = Integer.parseInt(fieldsArray[5]);
		return getDate(year, month, day, hour, minute, second, 0);
	}

	/**
	 * 获取当前日期是当月第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfMonth();
	}

	/**
	 * 获取当日是当年第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfYear();
	}

	/**
	 * 获取当日是本周第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfWeek();
	}

	/**
	 * 获取days天之后的日期，小时、分、秒和date保持一致
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusDays(days);
		return newDateTime.toDate();
	}
	
	public static Date addYears(Date date, int years) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusYears(years);
		return newDateTime.toDate();
	}

	/**
	 * 获取days天之前的日期,小时、分、秒和date保持一致
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date minusDays(Date date, int days) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.minusDays(days);
		return newDateTime.toDate();
	}

	/**
	 * 获取months月之后的日期，小时、分、秒和date保持一致
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addMonth(Date date, int months) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusMonths(months);
		return newDateTime.toDate();
	}

	/**
	 * 获取months月之前的日期，小时、分、秒和date保持一致
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date minusMonth(Date date, int months) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.minusMonths(months);
		return newDateTime.toDate();
	}

	/**
	 * 当前日期之后的几个小时的时间
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusHours(hours);
		return newDateTime.toDate();
	}

	/**
	 * 当前时间几个小时之前的时间
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date minusHours(Date date, int hours) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.minusHours(hours);
		return newDateTime.toDate();
	}

	/**
	 * 几分钟之后的时间
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusMinutes(minutes);
		return newDateTime.toDate();
	}

	/**
	 * 几分钟之前的时间
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date minusMinutes(Date date, int minutes) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.minusMinutes(minutes);
		return newDateTime.toDate();
	}

	/**
	 * 几秒之后的时间
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.plusSeconds(seconds);
		return newDateTime.toDate();
	}

	/**
	 * 几秒之前的时间
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date minusSeconds(Date date, int seconds) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.minusSeconds(seconds);
		return newDateTime.toDate();
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date, String format) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
	}

	/**
	 * 获取一天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginOfDay(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.hourOfDay().setCopy(0).minuteOfHour()
				.setCopy(0).secondOfMinute().setCopy(0);
		return newDateTime.toDate();
	}

	/**
	 * 获取一天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.hourOfDay().setCopy(23).minuteOfHour()
				.setCopy(59).secondOfMinute().setCopy(59);
		return newDateTime.toDate();
	}

	/**
	 * 两个日期之间相差的年数(2014-03到2015-02结果为1)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int yearsDiff(Date date1, Date date2) {
		return Math.abs(getYear(date2) - getYear(date1));
	}

	/**
	 * 两个日期之间相差的年数(2014-03到2015-02结果为0)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int yearsDiff2(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(getBeginOfYear(date1));
		DateTime dateTime2 = new DateTime(getEndOfYear(date2));
		Period p = new Period(dateTime1, dateTime2, PeriodType.years());
		return p.getYears();
	}

	public static int monthsDiff(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(getBeginOfMonth(date1));
		DateTime dateTime2 = new DateTime(getEndOfMonth(date2));
		Period p = new Period(dateTime1, dateTime2, PeriodType.months());
		return p.getMonths();
	}

	/**
	 * 两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysDiff(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(getBeginOfDay(date1));
		DateTime dateTime2 = new DateTime(getEndOfDay(date2));
		Period p = new Period(dateTime1, dateTime2, PeriodType.days());
		return p.getDays();
	}

	/**
	 * 两个日期之间相差的小时数(不满一小时不会进位)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int hoursDiff(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(date1);
		DateTime dateTime2 = new DateTime(date2);
		Period p = new Period(dateTime1, dateTime2, PeriodType.hours());
		return p.getHours();
	}

	/**
	 * 两个日期之间相差的分钟数(不满一分钟的不会进位)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int minutesDiff(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(date1);
		DateTime dateTime2 = new DateTime(date2);
		Period p = new Period(dateTime1, dateTime2, PeriodType.minutes());
		return p.getMinutes();
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.year());
	}
	
	public static int getHour(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.hourOfDay());
	}
	
	public static int getMinute(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.minuteOfHour());
	}
	
	public static int getSecond(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.secondOfMinute());
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.monthOfYear());
	}

	/**
	 * 获取当月最大天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMaxDayOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfMonth().getMaximumValue();
	}

	/**
	 * 获取当前日期为当月第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.get(DateTimeFieldType.dayOfMonth());
	}

	/**
	 * 获取某年第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.monthOfYear().setCopy(1).dayOfMonth()
				.setCopy(1).hourOfDay().setCopy(0).minuteOfHour().setCopy(0)
				.secondOfMinute().setCopy(0);
		return newDateTime.toDate();
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.monthOfYear().setCopy(12).dayOfMonth()
				.setCopy(getMaxDayOfMonth(date)).hourOfDay().setCopy(23)
				.minuteOfHour().setCopy(59).secondOfMinute().setCopy(59);
		return newDateTime.toDate();
	}

	/**
	 * 获取当月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.monthOfYear().setCopy(getMonth(date))
				.dayOfMonth().setCopy(1).hourOfDay().setCopy(0).minuteOfHour()
				.setCopy(0).secondOfMinute().setCopy(0);
		return newDateTime.toDate();
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.monthOfYear().setCopy(getMonth(date))
				.dayOfMonth().setCopy(getMaxDayOfMonth(date)).hourOfDay()
				.setCopy(23).minuteOfHour().setCopy(59).secondOfMinute()
				.setCopy(59);
		return newDateTime.toDate();
	}

	/**
	 * 设置时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date setTime(Date date, int hour, int minute, int second) {
		DateTime dateTime = new DateTime(date);
		DateTime newDateTime = dateTime.hourOfDay().setCopy(hour)
				.minuteOfHour().setCopy(minute).secondOfMinute()
				.setCopy(second);
		return newDateTime.toDate();
	}

	public static boolean between(Date date, Date time1, Date time2) {
		DateTime dateTime = new DateTime(date);
		DateTime startTime = new DateTime(time1);
		DateTime endTime = new DateTime(time2);
		return (dateTime.isEqual(startTime.getMillis()) || dateTime
				.isAfter(startTime.getMillis()))
				&& (dateTime.isEqual(endTime.getMillis()) || dateTime
						.isBefore(endTime.getMillis()));
	}

	public static boolean betweenNow(Date time1, Date time2) {
		DateTime startTime = new DateTime(time1);
		DateTime endTime = new DateTime(time2);
		return (startTime.isEqualNow() || startTime.isBeforeNow())
				&& (endTime.isAfterNow() || endTime.isEqualNow());
	}

	public static boolean isBeforeNow(Date dateTime) {
		DateTime time = new DateTime(dateTime);
		return time.isBeforeNow();
	}

	public static boolean isAfterNow(Date dateTime) {
		DateTime time = new DateTime(dateTime);
		return time.isAfterNow();
	}

	/**
	 * 获取下一个星期三
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date getNextWednesday(Date dateTime) {
		dateTime = addDays(dateTime, 1);
		DateTime time = new DateTime(dateTime);
		if (time.getDayOfWeek() != 3) {
			dateTime = getNextWednesday(dateTime);
		}
		return dateTime;
	}

	/**
	 * 设置日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date newDate(int year, int month, int day) {
		DateTime dateTime = new DateTime();
		DateTime newDateTime = dateTime.year().setCopy(year).monthOfYear()
				.setCopy(month).dayOfMonth().setCopy(day);
		return newDateTime.toDate();
	}
	
	/**
	 * 获取下一个月的第一天
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date getNextMonthDay(Date dateTime) {
		dateTime = addDays(dateTime, 1);
		DateTime time = new DateTime(dateTime);
		if (time.getDayOfMonth() != 1) {
			dateTime = getNextMonthDay(dateTime);
		}
		return dateTime;
	}
	
	/**
	 * 获取七天前的零点时间
	 * @return
	 */
	public static Date getSevenBeforeTime() {
		long current=System.currentTimeMillis();//当前时间毫秒数
	    long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    long seven=zero-1000*3600*24*7;//七天前
		return new Date(seven);
	}

	/**
	 * 获取今天零点时间-1s
	 * @return
	 */
	public static Date getTwelveTime() {
		long current=System.currentTimeMillis();//当前时间毫秒数
	    long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
		return new Date(twelve);
	}
}
