package com.mayank.spamdetector.util;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.util.ArrayList;

public class PredictionUtil {

    private PredictionUtil() {
        // Utility class
    }

    /**
     * Load trained Weka model.
     */
    public static Classifier loadModel(String modelPath) {

        try {

            return (Classifier) SerializationHelper.read(modelPath);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Unable to load trained model.",
                    exception
            );

        }

    }

    /**
     * Predict whether SMS is Spam or Ham.
     */
    public static String predictMessage(
            Classifier classifier,
            String message
    ) {

        try {

            message = TextPreprocessor.preprocess(message);

            ArrayList<Attribute> attributes = new ArrayList<>();

            attributes.add(new Attribute("text", (ArrayList<String>) null));

            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("ham");
            classValues.add("spam");

            attributes.add(new Attribute("class", classValues));

            Instances dataset =
                    new Instances(
                            "PredictionData",
                            attributes,
                            1
                    );

            dataset.setClassIndex(1);

            DenseInstance instance =
                    new DenseInstance(2);

            instance.setValue(
                    attributes.get(0),
                    message
            );

            instance.setDataset(dataset);

            double prediction =
                    classifier.classifyInstance(instance);

            return dataset.classAttribute()
                    .value((int) prediction);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Prediction failed.",
                    exception
            );

        }

    }

}