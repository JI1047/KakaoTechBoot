package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class DigitalWatch extends SunWatch{

    String displayType;//디스플레이 타입


    //시간을 전자식으로 보여주기 위해 상속 후 오버라이딩(재정의)
    @Override
    public void showTime() {
        LocalTime now = LocalTime.now();


        //현재 hour
        int hour = now.getHour();

        //현재 minute
        int minute = now.getMinute();

        //현재 second
        int second = now.getSecond();

        System.out.print("현재 시간은 ");
        //hour,minute,second가 한자리 수일 때 앞자리에 0을 채워 전자식으로 표현
        if(hour<10){
            String formatHour = String.format("%02d", hour);
            System.out.print(formatHour);
        }
        else{
            System.out.print(hour);
        }
        if(minute<10){
            String formatMin = String.format("%02d", minute);
            System.out.print(":"+ formatMin);
        }
        else{
            System.out.print(":"+minute);
        }
        if(second<10){
            String formatSec = String.format("%02d", second);
            System.out.println(":"+ formatSec+" 입니다");
        }
        else{
            System.out.println(":"+second+" 입니다");
        }

    }

    //년,월,일을 보여주는 메서드
    public void showYMD() {

        Calendar calendar = Calendar.getInstance();

        //오늘 날짜에 해당하는 year
        int year = calendar.get(Calendar.YEAR);

        //오늘 날짜에 해당하는 month
        int month = calendar.get(Calendar.MONTH) + 1;// 0 부터 시작하기 때문에 1을 더함

        //오늘 날짜에 해당하는 day
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        System.out.println("오늘은 "+year+"년 "+month+"월 "+day+"일입니다.");
    }



    //현재 요일을 알려주는 메서드
    public void showDate() {
        LocalDate date = LocalDate.now();

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        System.out.println("오늘의 요일은 "+dayOfWeek+" 입니다.");
    }




}
