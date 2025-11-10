package com.rbene.pomodoro;

public class PomodoroManager {

    public enum State { FOCUS, SHORT_BREAK, LONG_BREAK }

    private int focusTime, shortBreakTime, longBreakTime;
    private int numCiclos = 0;
    private State estadoAtual = State.FOCUS;

    public PomodoroManager(int focus, int shortBreak, int longBreak) {
        this.focusTime = focus;
        this.shortBreakTime = shortBreak;
        this.longBreakTime = longBreak;
    }

    public State getState() {
        return estadoAtual;
    }

    public int getCurrentDurationMinutes() {
        switch (estadoAtual) {
            case FOCUS: return focusTime;
            case SHORT_BREAK: return shortBreakTime;
            case LONG_BREAK: return longBreakTime;
        }
        return focusTime;
    }

    public void proximoEstado() {
        if (estadoAtual == State.FOCUS) {
            numCiclos++;
            if (numCiclos % 4 == 0) {estadoAtual = State.LONG_BREAK;}
            else {estadoAtual = State.SHORT_BREAK;};
        } else {
            estadoAtual = State.FOCUS;
        }
    }
}
