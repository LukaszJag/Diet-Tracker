-- =============================================================================
-- AZURE SQL DATABASE QUERIES FOR DIET TRACKER DATABASE
-- compiled on August 31, 2026
-- =============================================================================

-- -----------------------------------------------------------------------------
-- SECTION 1: SCHEMA MIGRATION & BOOTSTRAPPING (AZURE SQL)
-- Establishes the matching row_id primary key on the cloud calendar table
-- -----------------------------------------------------------------------------

-- 1.1 Add the row_id column allowing NULLs initially (prevents blocking on existing rows)
ALTER TABLE diet_tracker_schema.calendar ADD row_id VARCHAR(36) NULL;

-- 1.2 Generate a unique UUID (GUID) for every existing historical record in Azure
UPDATE diet_tracker_schema.calendar SET row_id = CAST(NEWID() AS VARCHAR(36));

-- 1.3 Modify the row_id column to disallow NULL values now that data is populated
ALTER TABLE diet_tracker_schema.calendar ALTER COLUMN row_id VARCHAR(36) NOT NULL;

-- 1.4 Declare the row_id column as the Primary Key for the table
-- (Note: If another PK exists, run: ALTER TABLE diet_tracker_schema.calendar DROP CONSTRAINT <constraint_name>; first)
ALTER TABLE diet_tracker_schema.calendar ADD PRIMARY KEY (row_id);


-- -----------------------------------------------------------------------------
-- SECTION 2: UNICODE & POLISH ACCENT OPTIMIZATION (AZURE SQL)
-- Converts text columns from VARCHAR to NVARCHAR (Unicode) to prevent character corruption
-- -----------------------------------------------------------------------------

-- 2.1 Update text columns in the calendar table to safely support Polish characters
ALTER TABLE diet_tracker_schema.calendar ALTER COLUMN product_name NVARCHAR(255) NOT NULL;
ALTER TABLE diet_tracker_schema.calendar ALTER COLUMN meal_name NVARCHAR(100) NULL;
ALTER TABLE diet_tracker_schema.calendar ALTER COLUMN comment_optional NVARCHAR(MAX) NULL;
ALTER TABLE diet_tracker_schema.calendar ALTER COLUMN day_name NVARCHAR(45) NULL;

-- 2.2 Drop the system-generated Default Constraint blocking the product_table modifications
ALTER TABLE diet_tracker_schema.product_table DROP CONSTRAINT [DF__product_t__comme__2645B050];

-- 2.3 Update text columns in the product_table to safely support Polish characters
ALTER TABLE diet_tracker_schema.product_table ALTER COLUMN product_name NVARCHAR(255) NOT NULL;
ALTER TABLE diet_tracker_schema.product_table ALTER COLUMN product_brand NVARCHAR(255) NULL;
ALTER TABLE diet_tracker_schema.product_table ALTER COLUMN comment_optional NVARCHAR(MAX) NULL;

-- 2.4 (Optional) Re-attach the default constraint to product_table.comment_optional
ALTER TABLE diet_tracker_schema.product_table
ADD CONSTRAINT DF_product_table_comment_optional DEFAULT '' FOR comment_optional;


-- -----------------------------------------------------------------------------
-- SECTION 3: DIAGNOSTICS, AUDITING & MONITORING (AZURE SQL)
-- Used to verify structure, schemas, row counts, and data integrity
-- -----------------------------------------------------------------------------

-- 3.1 Verify existing tables and schemas in the connected database
-- (Helps confirm you are not running commands inside the system 'master' database)
SELECT TABLE_SCHEMA, TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE';

-- 3.2 Get the total row count of the Azure calendar table
SELECT COUNT(*) FROM [diet_tracker_schema].[calendar];

-- 3.3 Find the "True Unique Count" in Azure (ignores row_id to count real database entries)
SELECT COUNT(*) FROM (
    SELECT DISTINCT
        day_date,
        product_name,
        amount_of_product,
        COALESCE(time_optional, '') as time_opt,
        meal_name
    FROM diet_tracker_schema.calendar
) as unique_rows;

-- 3.4 Check for the presence of Polish accents/diacritics in Azure entries
SELECT row_id, product_name, meal_name
FROM diet_tracker_schema.calendar
WHERE product_name LIKE '%[ąęćłńóśźżĄĘĆŁŃÓŚŹŻ]%';