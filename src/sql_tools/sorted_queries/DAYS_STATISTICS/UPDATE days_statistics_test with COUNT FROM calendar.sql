UPDATE `diet_tracker_schema`.`days_statistics_test`
SET
`amount_of_filled_points_from_notepad` = (SELECT COUNT(day_date) FROM calendar WHERE day_date = '2024-05-12')
WHERE day_date = '2024-05-12'