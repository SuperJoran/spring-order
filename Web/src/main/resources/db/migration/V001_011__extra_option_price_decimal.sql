-------------------------------------------------
-- @author: jorandeboever
-- @since: 7/12/17
-------------------------------------------------
ALTER TABLE SPR_EXTRA_OPTION
  ALTER COLUMN PRICE TYPE DECIMAL USING PRICE :: DECIMAL;