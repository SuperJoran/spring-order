-------------------------------------------------
-- @author: jorandeboever
-- @since: 2/12/17
-------------------------------------------------

CREATE TABLE SPR_SIMPLE_USER (
  food_option_uuid VARCHAR(36)  CONSTRAINT NN01_SPR_SIMPLE_USER NOT NULL,
  username         VARCHAR(255) CONSTRAINT NN02_SPR_SIMPLE_USER NOT NULL
);

ALTER TABLE SPR_SIMPLE_USER
  ADD CONSTRAINT pk_SPR_SIMPLE_USER PRIMARY KEY (food_option_uuid, username);
