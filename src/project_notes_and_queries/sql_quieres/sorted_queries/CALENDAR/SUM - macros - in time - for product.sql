SELECT product_name, ROUND(Sum(kcal_consume),2), ROUND(Sum(protein_consume),2), ROUND(Sum(fat_consume),2), ROUND(Sum(carbs_consume),2)
FROM calendar
WHERE day_date Like "2025-02-%" and product_name="Cukier";
