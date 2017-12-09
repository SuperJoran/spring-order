-------------------------------------------------
-- @author: jorandeboever
-- @since: 9/12/17
-------------------------------------------------
ALTER TABLE spr_size
  ADD CONSTRAINT un01_spr_size UNIQUE (name, food_option_uuid);
