UPDATE `diet_tracker_schema`.`days_statistics_test`
SET
kcal_consume = (SELECT SUM(kcal_consume) FROM calendar WHERE day_date = '2024-05-17'),
protein_consume = (SELECT SUM(protein_consume) FROM calendar WHERE day_date = '2024-05-17'),
fat_consume = (SELECT SUM(fat_consume) FROM calendar WHERE day_date = '2024-05-17'),
carbs_consume = (SELECT SUM(carbs_consume) FROM calendar WHERE day_date = '2024-05-17')
WHERE day_date = '2024-05-17';