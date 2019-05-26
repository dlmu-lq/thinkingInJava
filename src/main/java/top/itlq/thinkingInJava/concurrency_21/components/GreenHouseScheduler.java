package top.itlq.thinkingInJava.concurrency_21.components;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用ScheduledThreadPoolExecutor管理线程任务的定时运行，和定时重复运行
 *
 */

public class GreenHouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day"; // 不具有原子性，分为创建String对象，和引用指向两步（初始化时还有创建引用）
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);
    public void schedule(Runnable runnable, long delay){
        scheduledThreadPoolExecutor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable runnable, long delay, long period){
        scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }

    public synchronized void setThermostat(String thermostat){
        this.thermostat = thermostat;
    }

    public synchronized String getThermostat(){
        return thermostat;
    }

    class LightOn implements Runnable{
        @Override
        public void run() {
            System.out.println("LightOn");
            light = true;
        }
    }

    class LightOff implements Runnable{
        @Override
        public void run() {
            System.out.println("LightOff");
            light = false;
        }
    }

    class WaterOn implements Runnable{
        @Override
        public void run() {
            System.out.println("WaterOn");
            water = true;
        }
    }

    class WaterOff implements Runnable{
        @Override
        public void run() {
            System.out.println("WaterOff");
            water = false;
        }
    }

    class ThermostatDay implements Runnable{
        @Override
        public void run() {
            System.out.println("ThermostatDay");
            setThermostat("Day");
        }
    }

    class ThermostatNight implements Runnable{
        @Override
        public void run() {
            System.out.println("ThermostatNight");
            setThermostat("Night");
        }
    }

    class Terminate implements Runnable{
        @Override
        public void run() {
            System.out.println("terminating...");
            scheduledThreadPoolExecutor.shutdownNow();
            new Thread(){
                @Override
                public void run(){
                    for(DataPoint dataPoint:data){
                        System.out.println(dataPoint);
                    }
                }
            }.start();
        }
    }

    static class DataPoint{
        final Calendar time;
        final float temperature;
        final float humidity;
        public DataPoint(Calendar time, float temperature, float humidity){
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }
        @Override
        public String toString(){
            return String.format("temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }

    private List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());
    private float lastTemperature = 23.0f;
    private int temperatureDirection = 1;
    private float lastHumidity = 70.0f;
    private int humidityDirection = 1;

    class CollectData implements Runnable{

        @Override
        public void run() {
            System.out.println("Collecting Data...");
            Random random = new Random();
            synchronized (GreenHouseScheduler.this) { // 锁住父类对象而不是本对象
                if(random.nextInt(5) == 3){
                    temperatureDirection = temperatureDirection * -1;
                }
                lastTemperature = lastTemperature + temperatureDirection;
            }
            if(random.nextInt(5) == 4){
                humidityDirection = humidityDirection * -1;
            }
            lastHumidity = lastHumidity + humidityDirection;
            data.add(new DataPoint(Calendar.getInstance(), lastTemperature, lastHumidity));
        }
    }

    public static void main(String[]args){
        GreenHouseScheduler greenHouseScheduler = new GreenHouseScheduler();
        greenHouseScheduler.schedule(greenHouseScheduler.new LightOn(), 1000);
        greenHouseScheduler.schedule(greenHouseScheduler.new WaterOn(), 2000);
        greenHouseScheduler.schedule(greenHouseScheduler.new WaterOff(), 3000);
        greenHouseScheduler.schedule(greenHouseScheduler.new LightOff(), 4000);
        greenHouseScheduler.repeat(greenHouseScheduler.new ThermostatNight(), 5000, 2000);
        greenHouseScheduler.repeat(greenHouseScheduler.new ThermostatDay(), 6000, 2000);
        greenHouseScheduler.repeat(greenHouseScheduler.new CollectData(), 0, 2000);
        greenHouseScheduler.schedule(greenHouseScheduler.new Terminate(), 8000);
    }
}
