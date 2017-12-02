CREATE TABLE SPR_SELECTED_CHOICE (
  UUID             VARCHAR(36) CONSTRAINT NN00_SPR_SELECTED_CHOICE NOT NULL,
  PERSON_UUID      VARCHAR(36) CONSTRAINT NN01_SPR_SELECTED_CHOICE NOT NULL,
  FOOD_OPTION_UUID VARCHAR(36) CONSTRAINT NN02_SPR_SELECTED_CHOICE NOT NULL
);

ALTER TABLE SPR_SELECTED_CHOICE
  ADD CONSTRAINT PK_SPR_SELECTED_CHOICE PRIMARY KEY (UUID);

CREATE INDEX FK01_SPR_SELECTED_CHOICE
  ON SPR_SELECTED_CHOICE (PERSON_UUID);
ALTER TABLE SPR_SELECTED_CHOICE
  ADD CONSTRAINT FK01_SPR_SELECTED_CHOICE FOREIGN KEY (PERSON_UUID) REFERENCES SPR_PERSON (UUID);

CREATE INDEX FK02_SPR_SELECTED_CHOICE
  ON SPR_SELECTED_CHOICE (FOOD_OPTION_UUID);
ALTER TABLE SPR_SELECTED_CHOICE
  ADD CONSTRAINT FK02_SPR_SELECTED_CHOICE FOREIGN KEY (FOOD_OPTION_UUID) REFERENCES SPR_FOOD_OPTION (UUID);
