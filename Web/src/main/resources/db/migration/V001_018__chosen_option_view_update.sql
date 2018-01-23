-------------------------------------------------
-- @author: jorandeboever
-- @since: 23/01/18
-------------------------------------------------
DROP VIEW V_CHOSEN_OPTION;

CREATE OR REPLACE VIEW V_CHOSEN_OPTION AS (
  SELECT
    event.name                               AS EVENT_NAME,
    foodOption.name                          AS FOOD_OPTION_NAME,
    size.name                                AS SIZE_NAME,
    size.price                               AS SIZE_PRICE,
    COALESCE(extrasCombination.NAME, '') AS EXTRAS_COMBINATION_NAME,
    extrasCombination.PRICE                  AS EXTRAS_COMBINATION_PRICE,
    count(DISTINCT choice.uuid)              AS COUNT
  FROM spr_selected_choice choice
    INNER JOIN spr_size size ON choice.size_uuid = size.uuid
    INNER JOIN spr_food_option foodOption ON size.food_option_uuid = foodOption.uuid
    INNER JOIN spr_food_option_config config ON foodOption.configuration_uuid = config.uuid
    INNER JOIN spr_event event ON config.event_uuid = event.uuid
    LEFT OUTER JOIN
    (
      SELECT
        selectedExtra.selected_choice_uuid,
        SUM(extraOption.price)            AS PRICE,
        STRING_AGG(extraOption.name, ',') AS NAME,
        selected_choice_uuid              AS UUID
      FROM spr_selected_extra selectedExtra
        LEFT OUTER JOIN spr_extra_option extraOption ON selectedExtra.extra_option_uuid = extraOption.uuid
      GROUP BY selectedExtra.selected_choice_uuid
    ) extrasCombination ON extrasCombination.selected_choice_uuid = choice.uuid
  GROUP BY foodOption.name, size.name, event.name, size.price, extrasCombination.NAME, extrasCombination.PRICE, foodOption.uuid
);