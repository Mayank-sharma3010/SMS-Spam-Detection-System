package com.mayank.spamdetector.util;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DatasetLoader {

    private DatasetLoader() {
        // Utility class
    }

    /**
     * Load ARFF dataset for Weka.
     */
    public static Instances loadDataset(String filePath) {

        try {

            DataSource dataSource = new DataSource(filePath);

            Instances dataset = dataSource.getDataSet();

            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }

            return dataset;

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Unable to load dataset : " + filePath,
                    exception
            );

        }

    }

}