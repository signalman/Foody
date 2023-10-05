from sqlalchemy import Table, Column, Integer, Float, MetaData

metadata = MetaData()

member = Table(
    "member",
    metadata,
    Column("id", Integer, primary_key=True),
    # Column("height", Float) 키 정보는 필요 없을듯?,
    Column("weight", Float),
    Column("gender", Integer),
    Column("age", Integer),
    Column("activity_level", Integer),
    Column("mbti_id", Integer),
    Column("height", Float)
)

mbti = Table(
    "mbti",
    metadata,
    Column("id", Integer, primary_key=True),
    # 종류별
    Column("korean_main_dish", Integer),
    Column("western_main_dish", Integer),
    Column("side_dish", Integer),
    Column("dessert", Integer),

    # 상황별
    Column("daily_food", Integer),
    Column("festival_food", Integer),
    Column("convenience_food", Integer),
    Column("snack_food", Integer),
    Column("etc_food", Integer),

    # 재료별
    Column("meat", Integer),
    Column("vegetable_seafood", Integer),
    Column("processed_food", Integer),
    Column("health_food", Integer),
    Column("grain", Integer),

    # 방법별
    Column("low_cook", Integer),
    Column("high_cook", Integer),
    Column("water_cook", Integer),
    Column("raw_cook", Integer),
    Column("etc_cook", Integer)
)
