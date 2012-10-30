CREATE TABLE "main_color" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(20) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_region" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(20) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_buyflag" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(20) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_wine" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL,
    "winery_name" varchar(80) NULL,
    "summary" text NULL,
    "listing_text" text NULL,
    "price" real NULL,
    "color_id" integer NULL REFERENCES "main_color" ("_id"),
    "year" integer NULL,
    "region_id" integer NULL REFERENCES "main_region" ("_id"),
    "aroma_rating" integer NULL,
    "taste_rating" integer NULL,
    "aftertaste_rating" integer NULL,
    "overall_rating" integer NULL,
    "buy_flag_id" integer NULL REFERENCES "main_buyflag" ("_id"),
    "memo" text NULL,
    "display_image" varchar(80) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_grape" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL UNIQUE,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_winegrape" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "grape_id" integer NULL REFERENCES "main_grape" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "grape_id")
)
;
CREATE TABLE "main_aromaimpression" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL UNIQUE,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_winearomaimpression" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "aroma_impression_id" integer NULL REFERENCES "main_aromaimpression" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "aroma_impression_id")
)
;
CREATE TABLE "main_tasteimpression" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL UNIQUE,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_winetasteimpression" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "taste_impression_id" integer NULL REFERENCES "main_tasteimpression" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "taste_impression_id")
)
;
CREATE TABLE "main_wineaftertasteimpression" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "aftertaste_impression_id" integer NULL REFERENCES "main_tasteimpression" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "aftertaste_impression_id")
)
;
CREATE TABLE "main_tag" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(40) NULL UNIQUE,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_winetag" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "tag_id" integer NULL REFERENCES "main_tag" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "tag_id")
)
;
CREATE TABLE "main_winephoto" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL REFERENCES "main_wine" ("_id"),
    "filename" varchar(50) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("wine_id", "filename")
)
;
CREATE TABLE "main_favoritewine" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "wine_id" integer NULL UNIQUE REFERENCES "main_wine" ("_id"),
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
