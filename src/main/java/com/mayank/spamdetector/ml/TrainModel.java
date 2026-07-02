package com.mayank.spamdetector.ml;

public class TrainModel {

    public static void main(String[] args) {

        try {

            ModelTrainer.trainModel();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}