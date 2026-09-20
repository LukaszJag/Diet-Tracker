-- =============================================================================
-- LOCAL MYSQL QUERIES FOR DIET TRACKER DATABASE
-- compiled on August 31, 2026
-- =============================================================================

-- -----------------------------------------------------------------------------
-- SECTION 1: SCHEMA MIGRATION & BOOTSTRAPPING (LOCAL MYSQL)
-- Adds the mandatory synchronization and unique identifier columns to calendar
-- -----------------------------------------------------------------------------

-- 1.1 Add the row_id column allowing NULLs initially (prevents duplicate key errors on old data)
ALTER TABLE calendar ADD COLUMN row_id VARCHAR(36) NULL;

-- 1.2 Generate a unique UUID for every existing historical record
UPDATE calendar SET row_id = UUID();

-- 1.3 Modify the row_id column to disallow NULL values now that data is populated
ALTER TABLE calendar MODIFY COLUMN row_id VARCHAR(36) NOT NULL;

-- 1.4 Declare the row_id column as the Primary Key for the table
-- (Note: If another PK exists, run: ALTER TABLE calendar DROP PRIMARY KEY; first)
ALTER TABLE calendar ADD PRIMARY KEY (row_id);

-- 1.5 Add the local-only sync tracking flag (0 = Unsynced, 1 = Synced)
ALTER TABLE calendar ADD COLUMN is_synced TINYINT(1) DEFAULT 0;


-- -----------------------------------------------------------------------------
-- SECTION 2: DEDUPLICATION & DATABASE CLEANUP (LOCAL MYSQL)
-- Safely removes duplicate records while preserving a single master copy of each entry
-- -----------------------------------------------------------------------------

-- 2.1 Create a temporary index to speed up the self-join (prevents query timeouts)
CREATE INDEX temp_sync_idx ON calendar (day_date, is_synced);

-- 2.2 Deduplicate the table using a window function 
-- Keeps exactly ONE copy (preferring is_synced = 1) and deletes extra duplicate rows
DELETE FROM calendar 
WHERE row_id IN (
    SELECT row_id FROM (
        SELECT row_id,
               ROW_NUMBER() OVER (
                   PARTITION BY day_date, product_name, amount_of_product, meal_name 
                   ORDER BY is_synced DESC, row_id
               ) as rn
        FROM calendar
    ) as temp
    WHERE rn > 1
);

-- 2.3 Remove the temporary index once deduplication is completed
DROP INDEX temp_sync_idx ON calendar;

-- 2.4 Manually clean up specific Polish accent duplicates that did not match exactly due to character encoding
DELETE FROM calendar WHERE product_name = 'Ketchup Tomatini łagodny' AND is_synced = 0;
DELETE FROM calendar WHERE product_name = 'Kotlet z jajka i bułki tartej' AND is_synced = 0;
DELETE FROM calendar WHERE product_name = 'Śliwki' AND is_synced = 0;


-- -----------------------------------------------------------------------------
-- SECTION 3: DIAGNOSTICS, AUDITING & MONITORING (LOCAL MYSQL)
-- Used to inspect counts, spot potential issues, and check synchronization progress
-- -----------------------------------------------------------------------------

-- 3.1 Get the total row count of the calendar table
SELECT COUNT(*) FROM calendar;

-- 3.2 View synchronization progress (shows how many rows are synced vs. unsynced)
SELECT is_synced, COUNT(*) FROM calendar GROUP BY is_synced;

-- 3.3 Find the "True Unique Count" (ignores row_id and is_synced to count real records)
SELECT COUNT(*) FROM (
    SELECT DISTINCT 
        day_date, 
        product_name, 
        amount_of_product, 
        COALESCE(time_optional, '') as time_opt, 
        meal_name 
    FROM calendar
) as unique_rows;

-- 3.4 Find and list any duplicate entries grouped by meals
SELECT 
    day_date, 
    product_name, 
    amount_of_product, 
    meal_name, 
    COUNT(*) as occurrences
FROM calendar
GROUP BY 
    day_date, 
    product_name, 
    amount_of_product, 
    meal_name
HAVING COUNT(*) > 1
ORDER BY occurrences DESC;

-- 3.5 Check for the presence of Polish accents/diacritics in local entries
SELECT row_id, product_name, meal_name 
FROM calendar 
WHERE product_name REGEXP '[ąęćłńóśźżĄĘĆŁŃÓŚŹŻ]';

-- 3.6 Find any local rows that are currently stuck or failing to sync
SELECT row_id, day_date, product_name, amount_of_product, meal_name 
FROM calendar 
WHERE is_synced = 0;

