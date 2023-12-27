package com.example.libertybankapp.services;

import org.springframework.stereotype.Service;

import javax.smartcardio.*;
import java.time.LocalDate;
import java.util.Random;

import static com.example.libertybankapp.services.CardUtility.*;

@Service
public class CardService {


    //todo : write in the card : full name + user id + validity date + PIN (4 digit random) + trials + admin_PIN


    // Store fullname + id + PIN, + trials + admin_PIN + exp date in the file 0xAA10

    private String generatePin(){
        Random random = new Random();

        // Generate a random integer between 0 and 9999
        int pin = random.nextInt(10000);

        // Format the PIN to have leading zeros if necessary
        String formattedPin = String.format("%04d", pin);

        System.out.println("Generated PIN: " + formattedPin);
        return formattedPin;
    }

    //write PIN FILE
//    private void writePinFile() throws CardException {
//
//        writeFileAttribute((byte) 0xAB, (byte) 0xCD, (byte) 0x05, MAX_RECORD_LENGTH_PIN);
//
//        String[] records = new String[]{ generatePin(), Byte.toString(trials) , Byte.toString(MAX_TRIAL),
//                                         "123456" , LocalDate.now().plusYears(4).toString()};
//
//        writeFile((byte) 0xAB, (byte) 0xCD, records , MAX_RECORD_LENGTH_PIN );
//
//    }


    private void writeDataFile(String fullName, Long id) throws CardException {

        writeFileAttribute((byte) 0xAA, (byte) 0x10, DATA_FILE_LENGTH, MAX_RECORD_LENGTH_DATA);

        String[] records = new String[]{fullName, Long.toString(id), generatePin(), Byte.toString(trials) ,
                            Byte.toString(MAX_TRIAL), "123456" , LocalDate.now().plusYears(4).toString() };

        writeFile(DATA_FILE_ID, records, MAX_RECORD_LENGTH_DATA);

    }


    public void initCard(String fullName, Long id) throws CardException {

        //todo
        setup();

        submitICcode();
        createFile();
        submitICcode();
        writeDataFile(fullName, id);






    }










}
