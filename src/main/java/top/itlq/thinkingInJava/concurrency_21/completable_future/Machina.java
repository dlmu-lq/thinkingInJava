package top.itlq.thinkingInJava.concurrency_21.completable_future;

import java.util.concurrent.TimeUnit;

/**
 * 一个简单的状态机
 */
public class Machina {
    enum State {
        START, ONE, TWO, THREE, END;

        public State step() {
            if (equals(END)) {
                return END;
            }
            return values()[ordinal() + 1];
        }
    }

    public State state = State.START;
    public int id;

    public Machina(int id){
        this.id = id;
    }


    public Machina work() {
        if(!state.equals(State.END)){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state.step();
        }
        return this;
    }

    @Override
    public String toString(){
        return "Machina " + id + ":" + state;
    }
}
