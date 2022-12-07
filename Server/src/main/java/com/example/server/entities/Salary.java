package com.example.server.entities;

import java.io.Serializable;

public class Salary implements Serializable {
    private int person_id;
    private int idFormOfPayment;
    private double bonus;
    private int tariffRate;
    private int produced;
    private double laboriousness;
    private int passedFirst;
    private int rankId;
    private int hoursWorked;
    private double retention;

    public Salary(){
        person_id = 0;
        idFormOfPayment = 0;
        bonus = 0.0;
        tariffRate = 0;
        produced = 0;
        laboriousness = 0.0;
        passedFirst = 0;
        rankId = 0;
        hoursWorked = 0;
        retention = 0.0;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "person_id=" + person_id +
                ", formOfPayment=" + idFormOfPayment +
                ", bonus=" + bonus +
                ", tariffRate=" + tariffRate +
                ", produced=" + produced +
                ", laboriousness=" + laboriousness +
                ", passedFirst=" + passedFirst +
                ", rank=" + rankId +
                ", hoursWorked=" + hoursWorked +
                ", retention=" + retention +
                '}';
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getIdFormOfPayment() {
        return idFormOfPayment;
    }

    public void setIdFormOfPayment(int idFormOfPayment) {
        this.idFormOfPayment = idFormOfPayment;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getTariffRate() {
        return tariffRate;
    }

    public void setTariffRate(int tariffRate) {
        this.tariffRate = tariffRate;
    }

    public int getProduced() {
        return produced;
    }

    public void setProduced(int produced) {
        this.produced = produced;
    }

    public double getLaboriousness() {
        return laboriousness;
    }

    public void setLaboriousness(double laboriousness) {
        this.laboriousness = laboriousness;
    }

    public int getPassedFirst() {
        return passedFirst;
    }

    public void setPassedFirst(int passedFirst) {
        this.passedFirst = passedFirst;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getRetention() {
        return retention;
    }

    public void setRetention(double retention) {
        this.retention = retention;
    }
}
