package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String FILE_PATH = "records.txt";

    public static void main(String[] args) {
        HashMap<String, List<Record>> recordsFromFile = null;
        try {
            recordsFromFile = Record.readFile(FILE_PATH);
        } catch (WrongFormatException e) {
            e.getMsg();
            return;
        }
        List<Pair> pairs = new ArrayList<>();

        calculateEachCoupleWorkingDays(recordsFromFile,pairs);
        findCoupleWithBiggestWorkingDays(pairs);

    }

    public static void findCoupleWithBiggestWorkingDays(List<Pair> pairs){
        long max = 0;
        Pair pair = null;

        for (Pair p : pairs) {
            if (p.getDaysOfWorkTogether() > max) {
                max = p.getDaysOfWorkTogether();
                pair = p;
            }
        }

        if (pair != null) {
            System.out.println("The employee with id " + pair.getFirstEmpId() + " and employee with id " + pair.getSecondEmpId());
        } else {
            System.out.println("No found couple");
        }
    }

    public static void calculateEachCoupleWorkingDays( HashMap<String, List<Record>> recordsFromFile,List<Pair> pairs){

        long days;
        Record emp1;
        Record emp2;

        for (List<Record> projectEmployees : recordsFromFile.values()) {
            for (int i = 0; i < projectEmployees.size() - 1; i++) {
                for (int j = 0; j < projectEmployees.size() - 1 ; j++) {
                    emp1 = projectEmployees.get(i);
                    emp2 = projectEmployees.get(j + 1);

                    //Checks all possible pairs which worked together and calculates their days
                    if ((emp1.getDateFrom().compareTo(emp2.getDateFrom()) <= 0) && (emp1.getDateTo().compareTo(emp2.getDateTo()) >= 0)) {
                        days = TimeUnit.DAYS.convert(emp2.getDateTo().getTime() - emp2.getDateFrom().getTime(), TimeUnit.MILLISECONDS);
                        writeToCouple(emp1, emp2, pairs, days);
                    } else if ((emp1.getDateFrom().compareTo(emp2.getDateFrom()) >= 0) && (emp1.getDateTo().compareTo(emp2.getDateTo()) <= 0)) {
                        days = TimeUnit.DAYS.convert(emp2.getDateTo().getTime() - emp2.getDateFrom().getTime(), TimeUnit.MILLISECONDS);
                        writeToCouple(emp1, emp2, pairs, days);
                    } else if ((emp2.getDateFrom().compareTo(emp1.getDateFrom()) >= 0)
                            && (emp2.getDateFrom().compareTo(emp1.getDateTo()) <= 0)
                            && (emp1.getDateTo().compareTo(emp2.getDateTo()) <= 0)) {
                        days = TimeUnit.DAYS.convert(emp2.getDateTo().getTime() - emp2.getDateFrom().getTime(), TimeUnit.MILLISECONDS);
                        writeToCouple(emp1, emp2, pairs, days);
                    } else if ((emp1.getDateFrom().compareTo(emp2.getDateFrom()) >= 0)
                            && (emp1.getDateFrom().compareTo(emp2.getDateTo()) <= 0)
                            && (emp2.getDateTo().compareTo(emp1.getDateTo()) <= 0)) {
                        days = TimeUnit.DAYS.convert(emp2.getDateTo().getTime() - emp2.getDateFrom().getTime(), TimeUnit.MILLISECONDS);
                        writeToCouple(emp1, emp2, pairs, days);
                    }
                }
            }
        }
    }

    public static void writeToCouple(Record recordOne, Record recordTwo, List<Pair> pairs, long days) {
        boolean ifExist = false;

        //If pair exist from previous projects, just increment the days of pair
        for (Pair couple : pairs) {
            if ((couple.getFirstEmpId().equals(recordOne.getEmpID()) && couple.getSecondEmpId().equals(recordTwo.getEmpID()))
                    || couple.getFirstEmpId().equals(recordTwo.getEmpID()) && couple.getSecondEmpId().equals(recordOne.getEmpID())) {
                couple.setDaysOfWorkTogether(couple.getDaysOfWorkTogether() + days);
                ifExist = true;
            }
        }

        //If pair doesn't exist create new and add,
        if (!ifExist) {
            pairs.add(new Pair(recordOne.getEmpID(), recordTwo.getEmpID(), days));
        }
    }

}