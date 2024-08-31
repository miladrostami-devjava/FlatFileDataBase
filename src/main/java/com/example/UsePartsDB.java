package com.example;

import java.io.IOException;

public class UsePartsDB {

    public static void main(String[] args) {
        PartsDB pdb = null;
        try {
            pdb = new PartsDB("parts.db");

            // If database is empty, populate it with initial records
            if (pdb.numRecs() == 0) {
                populateDatabase(pdb);
            }

            // Display all records
            dumpRecords(pdb);

            // Update a specific record
            pdb.update(1, "1-3233-44923-7j", "Parking Brake Cable", 5, 1995);

            // Display all records again after the update
            dumpRecords(pdb);
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            if (pdb != null) {
                pdb.close();
            }
        }
    }

    // Method to populate the database with initial records
    private static void populateDatabase(PartsDB pdb) throws IOException {
        pdb.append("1-9009-3323-4x", "Wiper Blade Micro Edge", 30, 2468);
        pdb.append("1-3233-44923-7j", "Parking Brake Cable", 5, 1439);
        pdb.append("2-3399-6693-2m", "Halogen Bulb H4 55/60W", 22, 813);
        pdb.append("2-599-2029-6k", "Turbo Oil Line O-Ring", 26, 155);
        pdb.append("3-1299-3299-9u", "Air Pump Electric", 9, 20200);
    }

    // Method to dump records to the console
    static void dumpRecords(PartsDB pdb) throws IOException {
        for (int i = 0; i < pdb.numRecs(); i++) {
            PartsDB.Part part = pdb.select(i);
            System.out.print(format(part.getPartnum(), PartsDB.PNUMLEN, true));
            System.out.print(" | ");
            System.out.print(format(part.getDesc(), PartsDB.DESCLEN, true));
            System.out.print(" | ");
            System.out.print(format(String.valueOf(part.getQty()), 10, false));
            System.out.print(" | ");
            String cost = part.getUnitCost() / 100 + "." + part.getUnitCost() % 100;
            if (cost.charAt(cost.length() - 2) == '.') {
                cost += "0"; // Ensure two decimal places
            }
            System.out.println(format(cost, 10, false));
        }
        System.out.println("Total records: " + pdb.numRecs());
        System.out.println();
    }

    // Method to format the output
    static String format(String value, int maxWidth, boolean leftAlign) {
        StringBuilder sb = new StringBuilder();
        int len = value.length();
        if (len > maxWidth) {
            value = value.substring(0, maxWidth);
        }

        if (leftAlign) {
            sb.append(value);
            for (int i = len; i < maxWidth; i++) {
                sb.append(" ");
            }
        } else {
            for (int i = len; i < maxWidth; i++) {
                sb.append(" ");
            }
            sb.append(value);
        }
        return sb.toString();
    }
}

