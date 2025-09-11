package org.example;

import java.time.LocalTime;

//최상위 부모 클래스(시간만 확인할 수 있는 기본적 시계)
public class Watch {

    String material;//시계 재료

    //시간 보여주는 메서드(기본)
    public void showTime() {
        LocalTime now = LocalTime.now();//현재 시각을 저장하는 변수
        int hour = now.getHour();//현재 hour
        int minute = now.getMinute();//현재 minute
        System.out.println("현재 시각은 " + hour + "시 " + minute+"분 입니다.");
    }



}

