# 🍎 Diet Tracker - Desktop Application

A comprehensive desktop application built in Java (Swing) designed to help users track their daily diet, manage macro-elements, and monitor their progress over time using interactive calendars and charts.

## 🚀 Features

* **Macro & Calorie Tracking:** Calculate and track your daily intake of kcal, proteins, fats, and carbs.
* **Custom Product Database:** Add, edit, and manage products and their nutritional values using a connected MySQL database.
* **Interactive Calendar View:** Visualize your monthly dietary success. Days are color-coded based on goal completion (e.g., green for staying under BMR, red for exceeding).
* **Data Visualizations:** Generate Bar Charts and Area Charts representing your macro intake over specific months using the `JFreeChart` library.
* **Data Import/Export:** Functionality to export database contents to `.csv` and `.txt` files for backups.

## 🛠️ Technology Stack

* **Language:** Java 17+
* **GUI Framework:** Java Swing
* **Database:** MySQL
* **Database Connection:** JDBC (Java Database Connectivity)
* **Testing:** JUnit
* **Libraries:** JFreeChart (for data visualization)

## 📸 Application Screenshots

> Main Dashboard of Diet Tracker
<img width="726" height="526" alt="main" src="https://github.com/user-attachments/assets/2751ae29-93ce-447e-9f2b-0722af85f996" />

> Adding specific products and meals to the daily schedule with real-time macro calculation.
<img width="1319" height="813" alt="add product to day filled" src="https://github.com/user-attachments/assets/e5b49a4d-79aa-4530-94ed-7208c435131a" />

> Monthly overview with color-coded days showing adherence to macro goals.
<img width="1213" height="820" alt="calendar stats mont window" src="https://github.com/user-attachments/assets/d52edaeb-410a-45fd-91c1-e8f49c484fef" />

> Visual representation of daily caloric intake compared to goals across a month.
<img width="1008" height="922" alt="barchart stats month" src="https://github.com/user-attachments/assets/df00a5ba-fc45-4aee-afe5-2d15547d8fcc" />

## ⚙️ Setup and Installation

1. Clone the repository: `git clone https://github.com/LukaszJag/Diet-Tracker.git`
2. Set up a local MySQL Database:
   - Create a database named `diet_tracker_schema`.
   - Run the SQL scripts located in `src/data_store_and_backup/databases/databases.sql` to generate required tables (`product_table`, `calendar`, `days_statistics_test`).
3. Configure connection: Update credentials in `src/tools/sql_tools/general/get/GetConnection.java` to match your local MySQL setup.
4. Run `src/runners/run_GUI/diet/Main.java` to start the application.
