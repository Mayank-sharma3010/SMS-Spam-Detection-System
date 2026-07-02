package com.mayank.spamdetector.ml;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;

public class ModelTrainer {

    private static final String DATASET_PATH =
            "src/main/resources/dataset/spam.csv";

    private static final String MODEL_PATH =
            "model/spam-model.model";

    private static final String FILTER_PATH =
            "model/tfidf-filter.model";

    public static void trainModel() throws Exception {

        System.out.println("==========================================");
        System.out.println("ADVANCED SMS SPAM DETECTION MODEL TRAINER");
        System.out.println("==========================================");

        // Load CSV Dataset
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(DATASET_PATH));

        Instances data = loader.getDataSet();

        // Class column (v1 = ham/spam)
        data.setClassIndex(0);

        System.out.println("Dataset Loaded Successfully");
        System.out.println("Total SMS : " + data.numInstances());
        System.out.println("Attributes : " + data.numAttributes());

        // TF-IDF Filter
        StringToWordVector filter = new StringToWordVector();

        // Text Column (v2)
        filter.setAttributeIndices("2");

        filter.setTFTransform(true);
        filter.setIDFTransform(true);
        filter.setLowerCaseTokens(true);
        filter.setOutputWordCounts(true);
        filter.setWordsToKeep(5000);

        filter.setInputFormat(data);

        Instances filteredData =
                Filter.useFilter(data, filter);

        System.out.println("TF-IDF Completed");

        // Train Naive Bayes
        Classifier classifier = new NaiveBayes();

        classifier.buildClassifier(filteredData);

        // Save Model
        SerializationHelper.write(MODEL_PATH, classifier);

        // Save Filter
        SerializationHelper.write(FILTER_PATH, filter);

        System.out.println();
        System.out.println("==========================================");
        System.out.println("MODEL TRAINING SUCCESSFUL");
        System.out.println("==========================================");
        System.out.println("Model Saved  : " + MODEL_PATH);
        System.out.println("Filter Saved : " + FILTER_PATH);
        System.out.println("==========================================");
    }

    public static void main(String[] args) {

        try {

            trainModel();

        } catch (Exception e) {

            System.out.println("Training Failed!");

            e.printStackTrace();

        }

    }

}