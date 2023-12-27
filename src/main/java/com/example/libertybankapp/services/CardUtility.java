package com.example.libertybankapp.services;

import lombok.Data;

import javax.smartcardio.*;
import java.util.Random;


public class CardUtility {


    private static TerminalFactory tf ;
    private static CardTerminals lecteurs;
    private static CardTerminal lecteur ;
    private static Card card ;
    private static CardChannel ch;
    private static ResponseAPDU ra;

    static final byte DATA_FILE_LENGTH = 0x07;                 //number of records : 7

//    static final byte PIN_FILE_LENGTH = 0x05;

    static final byte MAX_TRIAL = 0x03;
    static byte trials = 0x00;

//    static final byte MAX_RECORD_LENGTH_PIN = 0X0A;
    static final byte MAX_RECORD_LENGTH_DATA = 0X14;                 //20 char at most

    static final byte[] DATA_FILE_ID = new byte[]{(byte) 0xAA, 0x10};

    public static String byteArrayToHexString(byte[] b){
        String result = "";
        for(int i = 0; i < b.length ; i++){
            result += Integer.toString((b[i] & 0xFF) + 0x100, 16).substring(1);

        }
        return  result;
    }


    public static void setup() throws CardException {

        tf = TerminalFactory.getDefault();
        lecteurs = tf.terminals();
        lecteur =    tf.terminals().list().get(0);  // lecteur id
        card = null;

        System.out.println("Attente de la carte ...");

        while(true){

            if(lecteur.isCardPresent()){
                card = lecteur.connect("*");

//

                System.out.println("Terminal connected successfully !!");

                if(card != null ){
                    byte[] bytes_ATR = card.getATR().getBytes();
                    System.out.println("ATR de la carte : " + byteArrayToHexString(bytes_ATR));
                    //card.disconnect(true);
                    ch = card.getBasicChannel();
                }
                break;
            }
        }
    }


    public static void submitICcode() throws CardException {
        byte[] APDU_Submit_IC = {(byte) 0x80, (byte) 0x20, 0x07, 0x00, 0x08, 0x41, 0x43, 0x4F, 0x53, 0x54, 0x45,
                0x53, 0x54};

        CommandAPDU apdu = new CommandAPDU(APDU_Submit_IC);
        ResponseAPDU ra = ch.transmit(apdu);

        if(ra.getSW() == 0x9000){
            System.out.println("Ok submit IC code !!");
        }
        else System.out.println("IC code error!! " + Integer.toHexString(ra.getSW()));
    }

    public static void selectFile(byte[] fileId) throws CardException {

        byte[] APDU_select_file = {(byte) 0x80, (byte) 0xa4, 0x00, 0x00, 0x02, fileId[0], fileId[1]};

        ra = ch.transmit(new CommandAPDU(APDU_select_file));

        if(ra.getSW() == 0x9000){
            System.out.println("OK select file" + Integer.toHexString(fileId[0])  + Integer.toHexString(fileId[1]) );
            System.out.println(byteArrayToHexString(ra.getData()));

        }
        else if(ra.getSW() == 0x9100){
            System.out.println("OK file selected");

        }
        else {
            System.out.println("erreur " + Integer.toHexString(ra.getSW()));
        }

    }


    public static void createFile() throws CardException {


        selectFile(new byte[]{(byte) 0xff, 0x02});

        byte[] write_record = {(byte) 0x80, (byte) 0xd2, 0x00, 0x00, 0x04, 0x00, 0x00, 0x01, 0x00};

        CommandAPDU write_apdu = new CommandAPDU(write_record);

        ra = ch.transmit(write_apdu);
        if(ra.getSW() == 0x9000){
            System.out.println("OK file updated" );
        }
        else {
            System.out.println("erreur : " + Integer.toHexString(ra.getSW()));
        }

        System.out.println("WARM RESET");

        //warm reset
        card.disconnect(true);
        card = lecteur.connect("*");
        ch = card.getBasicChannel();

    }

    //AA10
    public static void writeFileAttribute(byte fileIdMsb, byte fileIdLsb, byte recordsSize, byte maxRecordLength ) throws CardException {

        selectFile(new byte[]{(byte) 0xFF, 0x04});

        byte[] writeRecord = {(byte) 0x80, (byte) 0xd2, 0x00, 0x00, 0x06, maxRecordLength, recordsSize, 0x00, 0x00, fileIdMsb, fileIdLsb};  //5 taille de records

        ra = ch.transmit(new CommandAPDU(writeRecord));

        if(ra.getSW() == 0x9000){
            System.out.println("Ok MAJ FF04");
        }
        else System.out.println("Erreur etape 8 " + Integer.toHexString(ra.getSW()));
    }


    public static void writeFile(byte[] fileId, String[] records, byte maxRecordLength ) throws CardException {



        byte[] writeRecord;

        selectFile(fileId);

        for(int i = 0; i < records.length; i++){

            byte[] recordBytes = records[i].getBytes();

            writeRecord = new byte[5 + maxRecordLength];
            writeRecord[0] = (byte)  0x80;
            writeRecord[1] = (byte)  0xd2;
            writeRecord[2] = (byte)  i;
            writeRecord[3] = (byte)  0x00;
            writeRecord[4] = (byte)  maxRecordLength;

            for (int j = 0; j < recordBytes.length; j++ ){
                writeRecord[5 + j] = recordBytes[j];
            }

            ra = ch.transmit(new CommandAPDU(writeRecord));
            if(ra.getSW() == 0x9000){
                System.out.println("Ok MAJ file " + Integer.toHexString(fileId[0])  + Integer.toHexString(fileId[1]));
            }
            else {
                System.out.println("Error writeFile " + Integer.toHexString(ra.getSW()));
            }
        }
    }

    public static void readFile(byte fileIdMsb, byte fileIdLsb , byte maxRecordLength, byte fileLength) throws CardException {

        for (int i = 0; i < fileLength; i++) {
            byte[] readRecord = { (byte) 0x80, (byte) 0xb2, (byte) i, 0x00, (byte) maxRecordLength};
            ra = ch.transmit(new CommandAPDU(readRecord));
            if(ra.getSW() == 0x9000){
                System.out.println("record " + i + " : " + new String(ra.getData()));
            }
        }
    }





}
