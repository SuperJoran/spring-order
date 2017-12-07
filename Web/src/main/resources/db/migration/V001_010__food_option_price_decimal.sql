-------------------------------------------------
-- @author: jorandeboever
-- @since: 7/12/17
-------------------------------------------------
ALTER TABLE spr_food_option
  ALTER COLUMN price TYPE DECIMAL USING price :: DECIMAL;