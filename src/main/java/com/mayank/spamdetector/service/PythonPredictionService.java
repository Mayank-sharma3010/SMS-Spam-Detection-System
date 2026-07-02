package com.mayank.spamdetector.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PythonPredictionService {

    public String predict(String message) {

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(

                    "python",

                    "ml/predict.py",

                    message

            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(

                    new InputStreamReader(process.getInputStream())

            );

            String line;

            String result = "";

            while ((line = reader.readLine()) != null) {

                result = line;

            }

            process.waitFor();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return "Prediction Failed";

        }

    }

}