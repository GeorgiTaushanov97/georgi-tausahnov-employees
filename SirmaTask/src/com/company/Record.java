package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Record {

    private String EmpID;
    private String ProjectID;
    private Date DateFrom;
    private Date DateTo;

    private Record(String empID, String projectID, Date dateFrom, Date dateTo) {
        EmpID = empID;
        ProjectID = projectID;
        DateFrom = dateFrom;
        DateTo = dateTo;
    }

    public static HashMap<String, List<Record>> readFile(String FILE_PATH) throws WrongFormatException {
        HashMap<String, List<Record>> records = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {         //Try with resources to auto close the reader
            String line = reader.readLine();
            while (line != null) {
                String[] lineParts = line.split(", ");                                //Read, split and convert any single line in file to Record obj
                Date dateFrom = parseDate(lineParts[2]);     //parse different kind of format
                Date dateTo = parseDate(lineParts[3]);
                if(dateFrom == null || dateTo == null){
                    throw new WrongFormatException("cannot parse the date format");
                }
                Record record = new Record(lineParts[0], lineParts[1], dateFrom, dateTo);      //Create Record of any single line from file
                if (!records.containsKey(record.getProjectID())) {
                    records.put(record.getProjectID(), new ArrayList<>());                   //Save record to collection
                }
                records.get(record.getProjectID()).add(record);


                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot found the file");
        } catch (IOException e) {
            System.out.println("An IO exception occurred");
        }

        return records;
    }

    private static Date parseDate(String s) {

        if (s.equals("NULL")) {
            return new Date();
        }

        //we can list them all, and check them in catch blocks
        //example with 4 formatters
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter3 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter4 = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;

        try {
            date = formatter1.parse(s);
            return date;
        } catch (ParseException e) {
            try {
                date = formatter2.parse(s);
                return date;
            } catch (ParseException parseException) {
                try {
                    date = formatter3.parse(s);
                    return date;
                } catch (ParseException exception) {
                    try {
                        date = formatter4.parse(s);
                        return date;
                    } catch (ParseException ex) {
                        System.out.println("Cannot recognize date format");
                    }
                }
            }
        }
        return date;
    }


    public String getEmpID() {
        return EmpID;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

}
