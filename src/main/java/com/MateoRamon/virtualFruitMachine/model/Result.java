package com.MateoRamon.virtualFruitMachine.model;

public class Result {
    private boolean isJackpot;
    private int remainingMoney;
    private String[] slots;

    public Result() {
    }

    public Result(boolean isJackpot, int remainingMoney, String[] slots) {
        this.isJackpot = isJackpot;
        this.remainingMoney = remainingMoney;
        this.slots = slots;
    }

    public boolean isJackpot() {
        return isJackpot;
    }

    public void setJackpot(boolean jackpot) {
        isJackpot = jackpot;
    }

    public int getRemainingMoney() {
        return remainingMoney;
    }

    public void setRemainingMoney(int remainingMoney) {
        this.remainingMoney = remainingMoney;
    }

    public String[] getSlots() {
        return slots;
    }

    public void setSlots(String[] slots) {
        this.slots = slots;
    }
}
