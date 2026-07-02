"""
=========================================================
Advanced SMS Spam Detection System
Text Preprocessing
=========================================================
"""

import re
import string

from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

# NLTK resources pehle se install hone chahiye
STOPWORDS = set(stopwords.words("english"))
LEMMATIZER = WordNetLemmatizer()


def clean_text(text):

    if text is None:
        return ""

    text = str(text).lower()

    # Remove URLs
    text = re.sub(r"http\\S+|www\\S+", " ", text)

    # Remove Email IDs
    text = re.sub(r"\\S+@\\S+", " ", text)

    # Remove Phone Numbers
    text = re.sub(r"\\d{10,}", " ", text)

    # Remove HTML
    text = re.sub(r"<.*?>", " ", text)

    # Remove Punctuation
    text = text.translate(
        str.maketrans("", "", string.punctuation)
    )

    # Remove Numbers
    text = re.sub(r"\\d+", " ", text)

    # Remove Extra Spaces
    text = re.sub(r"\\s+", " ", text).strip()

    return text


def preprocess_text(text):

    text = clean_text(text)

    words = []

    for word in text.split():

        if word not in STOPWORDS:

            word = LEMMATIZER.lemmatize(word)

            words.append(word)

    return " ".join(words)