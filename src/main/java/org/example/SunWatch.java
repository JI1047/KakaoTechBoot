package org.example;



//부모 메서드(1차 상속)
public class SunWatch extends Watch {

    boolean isDay = false;//낮,밤을 구별하기 위한 boolean형 변수(true: 낮,false: 밤)



    //Watch 부모클래스의 메서드 오버라이드(재정의)
    @Override
    public void showTime(){
        //isDay boolean형 변수가 true일 때는(낮 일때는) 해시계가 정상적으로 작동하지만
        if(isDay){
            //부모의 showTime() 재사용
            super.showTime();
        }
        //false일 때는(밤 일때는) 해시계가 작동하지 않는다.
        else{
            System.out.println("밤이기 때문에 해시계가 작동하지 않습니다.");
        }


    }

}
