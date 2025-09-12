package org.example;

//스마트워치의 배터리가 10초마다 10프로씩 줄어드는 스레드 구현
public class threadTask implements Runnable{

    private final SmartWatch smartWatch;
    public threadTask(SmartWatch smartWatch){
        this.smartWatch = smartWatch;
    }
    @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(5000);//5초마다 대기
                    smartWatch.batteryUse();//10프로씩 감소
                    if(smartWatch.battery <= 10){
                        System.out.println("배터리가 부족하여 스마트워치를 더이상 사용할 수 없습니다.");
                        break;
                    }
                    smartWatch.showBattery();//배터리 잔량을 표시
                }
                catch (InterruptedException e) {
                    break;
                }//인터럽트 발생시 즉시 종료
            }
    }
}
