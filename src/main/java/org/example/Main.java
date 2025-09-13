package org.example;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        //최상위 부모 Watch로 선언됐지만
        Watch watch = new Watch();
        Watch watch2 = new SunWatch();
        Watch watch3 = new DigitalWatch();
        DigitalWatch digitalWatch = new DigitalWatch();
        SmartWatch smartWatch = new SmartWatch();

        Runnable task = new threadTask(smartWatch);
        Thread batteryThread = new Thread(task);
        batteryThread.start();//스레드 시작

        System.out.println("여러 시계의 기능을 체험 해볼 수 있는 시간입니다");
        System.out.println("------------------------------------");
        while (true) {
            System.out.println("어떤 시계의 기능을 체험해보시겠어요?");
            System.out.println("1.기본 시계 / 2. 해 시계 / 3. 디지털 시계 / 4. 스마트워치 / 5. 종료");

            int choice = readNumber();
            switch (choice) {
                case 1:
                    handleWatch(watch);
                    break;
                case 2:
                    handleSunWatch(watch2);
                    break;
                case 3:
                    handleDigitalWatch(watch3, digitalWatch);
                    break;
                case 4:
                    if (smartWatch.battery <= 10) {
                        System.out.println("배터리가 부족하여 스마트 워치를 사용할 수 없습니다.");
                        break;
                    } else {
                        handleSmartWatch(smartWatch);
                        break;
                    }

                case 5:
                    batteryThread.interrupt();//스레드에 중단요청을 보냄
                    System.out.println("프로그램을 종료합니다. 감사합니다");
                    return;
            }
            System.out.println();
        }


    }


    //input이 숫자인지 검증하는 메서드
    static public int readNumber() {
        while (true) {
            try {
                return sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("숫자를 입력하세요.");
                sc.nextLine();

            }
        }

    }

    //가독성을 높이기 위한 로직 분리
    private static void handleWatch(Watch watch) {
        System.out.println("기본 시계의 기능 입니다.");
        watch.showTime();
    }

    //가독성을 높이기 위한 로직 분리
    private static void handleSunWatch(Watch watch2) {
        System.out.println("해 시계의 기능 입니다.");
        watch2.showTime();//부모 타입으로 참조했지만 자식 메서드가 실행(다형성)
    }

    private static void handleDigitalWatch(Watch watch3, DigitalWatch digitalWatch) {
        System.out.println("디지털 시게의 기능들입니다. 체험해보고 싶은 기능을 선택해주세요");
        System.out.println("1.전자식 시간 표시 / 2.년,월,일 확인 / 3. 요일 확인 ");
        int choice2 = readNumber();
        switch (choice2) {
            case 1:
                System.out.println("전자식 시간이 표시됩니다.");
                watch3.showTime();//부모 타입으로 참조했지만 자식 메서드가 실행(다형성)
                break;
            case 2:
                System.out.println("년,월,일이 표시됩니다.");
                digitalWatch.showYMD();
                break;
            case 3:
                System.out.println("요일이 표시됩니다.");
                digitalWatch.showDate();
        }
    }

    private static void handleSmartWatch (SmartWatch smartWatch) throws IOException {
        System.out.println("스마트 워치의 기능들입니다. 체험해보고 싶은 기능을 선택해주세요");
        smartWatch.showBattery();
        System.out.println("1.걸음 수 측정 / 2. 메세지 보내기 ");

        int choice3 = readNumber();
        switch (choice3) {
            case 1:
                System.out.println("걸음 수 측정을 시작합니다!");
                smartWatch.getWalkCount();
                break;
            case 2:
                System.out.println("메시지 기능이 시작됩니다.");
                smartWatch.sendMessage();
                break;

        }
    }

}