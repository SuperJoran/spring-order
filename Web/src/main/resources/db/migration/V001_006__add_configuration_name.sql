-------------------------------------------------
-- @author: jorandeboever
-- @since: 2/12/17
-------------------------------------------------

ALTER TABLE SPR_FOOD_OPTION_CONFIG ADD COLUMN NAME VARCHAR(255);

UPDATE spr_food_option_config SET NAME = 'Food options';

ALTER TABLE SPR_FOOD_OPTION_CONFIG ALTER COLUMN NAME SET NOT NULL;
