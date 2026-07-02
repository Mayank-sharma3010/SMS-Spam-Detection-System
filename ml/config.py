"""
=========================================================
Advanced SMS Spam Detection System
Configuration File
=========================================================
"""

from pathlib import Path

# --------------------------------------------------------
# Project Paths
# --------------------------------------------------------

PROJECT_ROOT = Path(__file__).resolve().parent.parent

DATASET_PATH = PROJECT_ROOT / "src" / "main" / "resources" / "dataset" / "spam.csv"

MODEL_DIR = PROJECT_ROOT / "ml" / "models"

REPORT_DIR = PROJECT_ROOT / "ml" / "reports"

# --------------------------------------------------------
# Saved Files
# --------------------------------------------------------

MODEL_FILE = MODEL_DIR / "spam_model.pkl"

TFIDF_FILE = MODEL_DIR / "tfidf.pkl"

LABEL_ENCODER_FILE = MODEL_DIR / "label_encoder.pkl"

# --------------------------------------------------------
# Machine Learning
# --------------------------------------------------------

TEST_SIZE = 0.20

RANDOM_STATE = 42

MAX_FEATURES = 5000

NGRAM_RANGE = (1, 2)

MIN_DF = 2

MAX_DF = 0.95

# --------------------------------------------------------
# Labels
# --------------------------------------------------------

SPAM_LABEL = "spam"

HAM_LABEL = "ham"

# --------------------------------------------------------
# Reports
# --------------------------------------------------------

RESULT_FILE = REPORT_DIR / "model_results.csv"

CONFUSION_MATRIX = REPORT_DIR / "confusion_matrix.png"

MODEL_COMPARISON = REPORT_DIR / "model_comparison.png"

# --------------------------------------------------------
# Create Folders Automatically
# --------------------------------------------------------

MODEL_DIR.mkdir(parents=True, exist_ok=True)

REPORT_DIR.mkdir(parents=True, exist_ok=True)