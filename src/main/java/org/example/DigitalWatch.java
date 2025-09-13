package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DigitalWatch extends SunWatch{

    String displayType;//디스플레이 타입


    //시간을 전자식으로 보여주기 위해 상속 후 오버라이딩(재정의)
    @Override
    public void showTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("현재 시간은 " + now.format(formatter) + " 입니다");
    }

    //년,월,일을 보여주는 메서드
    public void showYMD() {

        LocalDate today = LocalDate.now();
        System.out.println("오늘은 "+today.getYear()+"년 "
                                    +today.getMonthValue() + "월 "
                                    +today.getDayOfMonth() + "일입니다.");
    }



    //현재 요일을 알려주는 메서드
    public void showDate() {
        LocalDate date = LocalDate.now();

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        System.out.println("오늘의 요일은 "+dayOfWeek+" 입니다.");
    }




}
