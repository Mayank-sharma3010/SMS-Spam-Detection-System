"""
=========================================================
Advanced SMS Spam Detection System
Model Training Pipeline
=========================================================
"""

import joblib
import warnings
import pandas as pd
from pathlib import Path

print("train_model.py started")

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.feature_extraction.text import TfidfVectorizer

from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.svm import LinearSVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier

from sklearn.metrics import (
    accuracy_score,
    precision_score,
    recall_score,
    f1_score,
)

from config import *
from preprocessing import preprocess_text

warnings.filterwarnings("ignore")


class SpamTrainer:

    def __init__(self):

        self.dataset = None

        self.label_encoder = LabelEncoder()

        self.vectorizer = TfidfVectorizer(
            max_features=MAX_FEATURES,
            ngram_range=NGRAM_RANGE,
            min_df=MIN_DF,
            max_df=MAX_DF,
        )

        self.models = {
            "Multinomial Naive Bayes": MultinomialNB(),

            "Logistic Regression": LogisticRegression(
                max_iter=1000,
                random_state=RANDOM_STATE,
            ),

            "Linear SVM": LinearSVC(
                random_state=RANDOM_STATE
            ),

            "Random Forest": RandomForestClassifier(
                n_estimators=200,
                random_state=RANDOM_STATE,
            ),

            "Decision Tree": DecisionTreeClassifier(
                random_state=RANDOM_STATE
            ),
        }

        self.results = []

    def load_dataset(self):


        print("Inside load_dataset()")


        print("=" * 60)
        print("Loading Dataset...")
        print("=" * 60)

        self.dataset = pd.read_csv(
    DATASET_PATH,
    encoding="latin-1"
)

        self.dataset = self.dataset[["v1", "v2"]]

        self.dataset.columns = ["label", "message"]

        print(self.dataset.head())
        print()

        print("Dataset Shape :", self.dataset.shape)
        print()

        print(self.dataset["label"].value_counts())

        print("=" * 60)

    def preprocess_dataset(self):

        print("\nCleaning Dataset...")

        self.dataset.dropna(inplace=True)

        self.dataset.drop_duplicates(inplace=True)

        self.dataset["message"] = self.dataset["message"].apply(
            preprocess_text
        )

        self.dataset["label"] = self.label_encoder.fit_transform(
            self.dataset["label"]
        )

        print("Dataset cleaned successfully.")
        print("Total Records :", len(self.dataset))

    def prepare_data(self):

        print("\nPreparing Features...")

        X = self.dataset["message"]
        y = self.dataset["label"]

        X_train, X_test, y_train, y_test = train_test_split(
            X,
            y,
            test_size=TEST_SIZE,
            random_state=RANDOM_STATE,
            stratify=y,
        )

        print("Train Size :", len(X_train))
        print("Test Size  :", len(X_test))

        X_train = self.vectorizer.fit_transform(X_train)
        X_test = self.vectorizer.transform(X_test)

        return X_train, X_test, y_train, y_test

    def train_models(self):

        X_train, X_test, y_train, y_test = self.prepare_data()

        print("\n" + "=" * 60)
        print("Training Machine Learning Models")
        print("=" * 60)

        best_accuracy = 0.0
        best_model = None
        best_model_name = ""

        for model_name, model in self.models.items():

            print(f"\nTraining : {model_name}")

            model.fit(X_train, y_train)

            predictions = model.predict(X_test)

            accuracy = accuracy_score(y_test, predictions)

            precision = precision_score(
                y_test,
                predictions,
                average="weighted",
            )

            recall = recall_score(
                y_test,
                predictions,
                average="weighted",
            )

            f1 = f1_score(
                y_test,
                predictions,
                average="weighted",
            )

            self.results.append({
                "Model": model_name,
                "Accuracy": round(accuracy * 100, 2),
                "Precision": round(precision * 100, 2),
                "Recall": round(recall * 100, 2),
                "F1 Score": round(f1 * 100, 2),
            })

            print(f"Accuracy  : {accuracy * 100:.2f}%")
            print(f"Precision : {precision * 100:.2f}%")
            print(f"Recall    : {recall * 100:.2f}%")
            print(f"F1 Score  : {f1 * 100:.2f}%")

            if accuracy > best_accuracy:
                best_accuracy = accuracy
                best_model = model
                best_model_name = model_name

        print("\n" + "=" * 60)
        print("Best Model :", best_model_name)
        print(f"Accuracy   : {best_accuracy * 100:.2f}%")
        print("=" * 60)

        return best_model

    def save_model(self, model):

        print("\nSaving Best Model...")

        joblib.dump(model, MODEL_FILE)
        joblib.dump(self.vectorizer, TFIDF_FILE)
        joblib.dump(self.label_encoder, LABEL_ENCODER_FILE)

        print("Model Saved Successfully.")

        print(MODEL_FILE)
        print(TFIDF_FILE)
        print(LABEL_ENCODER_FILE)

    def generate_report(self):

        print("\nGenerating Report...")

        results_df = pd.DataFrame(self.results)

        results_df = results_df.sort_values(
            by="Accuracy",
            ascending=False,
        )

        results_df.to_csv(
            RESULT_FILE,
            index=False,
        )

        print(results_df)

        print("\nReport Saved Successfully.")

    def run(self):
        

        print("Inside run()")
        

        print("Step 1")
        self.load_dataset()

        print("Step 2")
        self.preprocess_dataset()

        print("Step 3")
        best_model = self.train_models()

        print("Step 4")
        self.save_model(best_model)

        print("Step 5")
        self.generate_report()

        print("Step 6")

        print("\n" + "=" * 60)
        print("MODEL TRAINING COMPLETED SUCCESSFULLY")
        print("=" * 60)


if __name__ == "__main__":

    trainer = SpamTrainer()

    trainer.run()