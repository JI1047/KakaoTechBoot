package org.example;

import java.io.*;
import java.util.Scanner;

public class SmartWatch extends SunWatch{
    Scanner sc = new Scanner(System.in);



    //초기 배터리 용량은 100으로 초기화
    int battery = 100;

    //걸음 수 측정 메서드
    public void getWalkCount() throws IOException{


        System.out.println("w를 누르면 걸음 수가 증가합니다 종료 하고 싶을 때에는 q와 엔터를 눌러주세요");
        int count = 0;
        while (true){
            int input = System.in.read();//한번에 하나의 문자만 처리
            char ch = (char)input;//문자로 변환
            //w가입력됏을 때 걸음수 변수(count)가 늘어남
            if (ch == 'w') {
                count++;
            }
            //q입력 후 엔터 시 while문을 빠져나옴
            if (ch == 'q') {
                break;
            }

        }
        //결과 출력
        System.out.println("총 걸음 수는 : "+count+"걸음입니다");
    }

    //메세지를 보내는 기능
    public void sendMessage() {

        String sender;//보내는 사람 이름
        String receiver;//받는 사람 이름
        String message;//보낼 메세지

        System.out.println("보내는 사람의 이름을 입력하세요: ");
        sender = sc.next();

        System.out.println("받는 사람의 이름을 입력하세요: ");
        receiver = sc.next();

        System.out.println("보낼 메세지 내용을 입력하세요: ");
        message = sc.next();

        System.out.println(sender+"님이 ");
        System.out.println(message);
        System.out.println("라고 보냅니다.");
        System.out.println(receiver+"님에게");

    }


    //스마트워치 기능을 이용할 때마다 배터리가 닳는 메서드
    public void batteryUse(){
        battery-=30;
    }

    //배터리 사용량 출력 메서드
    public void showBattery(){
        System.out.println("남은 배터리양: "+battery+"%");
    }

}
