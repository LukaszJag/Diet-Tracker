SELECT 
FORMAT((SUM(kcal_consume)), '0.00') AS "kcal consume sum", 
FORMAT((SUM(protein_consume)), '0.00') AS "protein consume sum",
FORMAT((SUM(fat_consume)), '0.00') AS "fat consume sum",
FORMAT((SUM(carbs_consume)), '0.00') AS "carbs consume sum"

FROM days_statistics_test

WHERE day_date like "2025-07%";
