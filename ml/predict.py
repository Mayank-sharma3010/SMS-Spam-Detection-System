"""
=========================================================
Advanced SMS Spam Detection System
Prediction Module
=========================================================
"""

import sys
import joblib

from config import (
    MODEL_FILE,
    TFIDF_FILE,
    LABEL_ENCODER_FILE
)

from preprocessing import preprocess_text


class SpamPredictor:

    def __init__(self):

        print("Loading Trained Model...")

        self.model = joblib.load(MODEL_FILE)

        self.vectorizer = joblib.load(TFIDF_FILE)

        self.label_encoder = joblib.load(LABEL_ENCODER_FILE)

        print("Model Loaded Successfully.")

    def predict(self, message):

        message = preprocess_text(message)

        vector = self.vectorizer.transform([message])

        prediction = self.model.predict(vector)

        label = self.label_encoder.inverse_transform(prediction)[0]

        return label


def main():

    if len(sys.argv) < 2:

        print("Usage:")
        print('python predict.py "Your SMS Here"')

        return

    sms = " ".join(sys.argv[1:])

    predictor = SpamPredictor()

    result = predictor.predict(sms)

    print("\nPrediction :", result.upper())


if __name__ == "__main__":
    main()