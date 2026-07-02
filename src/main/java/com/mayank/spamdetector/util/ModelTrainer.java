package com.mayank.spamdetector.util;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.Random;

public class ModelTrainer {

    private ModelTrainer() {
        // Utility class
    }

    /**
     * Train Naive Bayes model.
     */
    public static Classifier trainModel(Instances dataset) {

        try {

            StringToWordVector filter = new StringToWordVector();

            filter.setInputFormat(dataset);

            Instances filteredData =
                    Filter.useFilter(dataset, filter);

            filteredData.setClassIndex(
                    filteredData.numAttributes() - 1
            );

            NaiveBayesMultinomial classifier =
                    new NaiveBayesMultinomial();

            classifier.buildClassifier(filteredData);

            return classifier;

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Unable to train model",
                    exception
            );

        }

    }

    /**
     * Evaluate model.
     */
    public static Evaluation evaluateModel(
            Classifier classifier,
            Instances dataset
    ) {

        try {

            StringToWordVector filter =
                    new StringToWordVector();

            filter.setInputFormat(dataset);

            Instances filteredData =
                    Filter.useFilter(dataset, filter);

            filteredData.setClassIndex(
                    filteredData.numAttributes() - 1
            );

            Evaluation evaluation =
                    new Evaluation(filteredData);

            evaluation.crossValidateModel(
                    classifier,
                    filteredData,
                    10,
                    new Random(1)
            );

            return evaluation;

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Unable to evaluate model",
                    exception
            );

        }

    }

    /**
     * Save trained model.
     */
    public static void saveModel(
            Classifier classifier,
            String modelPath
    ) {

        try {

            SerializationHelper.write(
                    modelPath,
                    classifier
            );

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Unable to save model",
                    exception
            );

        }

    }

}