SELECT * FROM days_statistics_test
WHERE amount_of_points_from_notepad = amount_of_filled_points_from_notepad
AND
amount_of_filled_points_from_notepad != 0
AND
day_date LIKE "2026-04%";