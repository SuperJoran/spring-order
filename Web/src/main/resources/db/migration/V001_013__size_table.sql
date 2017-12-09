-------------------------------------------------
-- @AUTHOR: JORANDEBOEVER
-- @SINCE: 9/12/17
-------------------------------------------------
CREATE TABLE SPR_SIZE (
  UUID  VARCHAR(36)  CONSTRAINT NN00_SPR_SIZE NOT NULL,
  NAME  VARCHAR(255) CONSTRAINT NN01_SPR_SIZE NOT NULL,
  PRICE DECIMAL,
  FOOD_OPTION_UUID VARCHAR(36) CONSTRAINT NN02_SPR_SIZE NOT NULL
);

ALTER TABLE SPR_SIZE
  ADD CONSTRAINT PK_SPR_SIZE PRIMARY KEY (UUID);

CREATE INDEX FK01_SPR_SIZE ON SPR_SIZE(FOOD_OPTION_UUID);
ALTER TABLE SPR_SIZE ADD CONSTRAINT FK01_SPR_SIZE FOREIGN KEY (FOOD_OPTION_UUID) REFERENCES SPR_FOOD_OPTION(UUID);
