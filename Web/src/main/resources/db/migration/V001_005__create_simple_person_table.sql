-------------------------------------------------
-- @author: jorandeboever
-- @since: 2/12/17
-------------------------------------------------

create table SPR_SIMPLE_USER (
  food_option_uuid VARCHAR2(36 CHAR)  CONSTRAINT NN01_SPR_SIMPLE_USER NOT NULL,
  username         VARCHAR2(255 CHAR) CONSTRAINT NN02_SPR_SIMPLE_USER NOT NULL
);

alter table SPR_SIMPLE_USER add constraint pk_SPR_SIMPLE_USER primary key (food_option_uuid, username);
