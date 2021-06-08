import pandas as pd
from sklearn import neighbors
import numpy as np
from os.path import dirname, join


def main(age, Living_area, Income, Home, Education, Leisure, Health, Groceries, Shopping, Transportation,
         Savings_investments, Loans_cc_fees, additional_expenses, Num_of_People):

    filename = join(dirname(__file__), "data_for_project.csv")

    df = pd.read_csv(filename)
    X = df.iloc[:, : -1]
    y = df.iloc[:, -1]
    samples_to_predict = np.array(
        [age, Living_area, Income, Home, Education, Leisure, Health, Groceries, Shopping, Transportation,
         Savings_investments, Loans_cc_fees, additional_expenses, Num_of_People]).reshape(1, -1)

    model = neighbors.KNeighborsClassifier(n_neighbors=5)
    model.fit(X, y)
    budegt = model.predict(samples_to_predict)
    return budegt[0]
    """if budegt == 0:  # 11%
        return (0.89 * Home, 0.89 * Education, 0.89 * Leisure, 0.89 * Health, 0.89 * Groceries, 0.89 * Shopping,
                0.89 * Transportation, 0.89 * Savings_investments, 0.89 * Loans_cc_fees, 0.89 * additional_expenses)
    elif budegt == 1:  # 9%
        return (0.91 * Home, 0.91 * Education, 0.91 * Leisure, 0.91 * Health, 0.91 * Groceries, 0.91 * Shopping,
                0.91 * Transportation, 0.91 * Savings_investments, 0.91 * Loans_cc_fees, 0.91 * additional_expenses)
    elif budegt == 2:  # 7%
        return (0.93 * Home, 0.93 * Education, 0.93 * Leisure, 0.93 * Health, 0.93 * Groceries, 0.93 * Shopping,
                0.93 * Transportation, 0.93 * Savings_investments, 0.93 * Loans_cc_fees, 0.93 * additional_expenses)
    else:  # budget == 3   # 5%
        return (0.95 * Home, 0.95 * Education, 0.95 * Leisure, 0.95 * Health, 0.95 * Groceries, 0.95 * Shopping,
                0.95 * Transportation, 0.95 * Savings_investments, 0.95 * Loans_cc_fees, 0.95 * additional_expenses)"""

