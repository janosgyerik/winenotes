CREATE TABLE "main_winetype" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(20) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_region" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL,
    "ascii_name" varchar(80) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_flag" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(20) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_winery" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL,
    "ascii_name" varchar(80) NULL,
    "lat" real NULL,
    "lon" real NULL,
    "address" text NULL,
    "website" varchar(200) NULL,
    "tel" varchar(50) NULL,
    "display_order" integer NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "main_wine" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL,
    "ascii_name" varchar(80) NULL,
    "winery_id" integer NULL REFERENCES "main_winery" ("_id"),
    "summary" text NULL,
    "listing_text" text NULL,
    "price" real NULL,
    "currency" varchar(3) NULL,
    "winetype_id" integer NULL REFERENCES "main_winetype" ("_id"),
    "year" integer NULL,
    "region_id" integer NULL REFERENCES "main_region" ("_id"),
    "aroma_rating" integer NULL,
    "taste_rating" integer NULL,
    "aftertaste_rating" integer NULL,
    "overall_rating" integer NULL,
    "flag_id" integer NULL REFERENCES "main_flag" ("_id"),
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
    "ascii_name" varchar(80) NULL UNIQUE,
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

INSERT INTO main_region (name, ascii_name) VALUES ('Bordelais-Aquitaine', 'Bordelais-Aquitaine');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne-Beaujolais', 'Bourgogne-Beaujolais');
INSERT INTO main_region (name, ascii_name) VALUES ('Charentes - Poitou', 'Charentes - Poitou');
INSERT INTO main_region (name, ascii_name) VALUES ('Jura - Savoie', 'Jura - Savoie');
INSERT INTO main_region (name, ascii_name) VALUES ('Languedoc Roussillon', 'Languedoc Roussillon');
INSERT INTO main_region (name, ascii_name) VALUES ('Provence-Corse', 'Provence-Corse');
INSERT INTO main_region (name, ascii_name) VALUES ('Sud-Ouest-Armagnac', 'Sud-Ouest-Armagnac');
INSERT INTO main_region (name, ascii_name) VALUES ('Val de Loire - Vendée', 'Val de Loire - Vendee');
INSERT INTO main_region (name, ascii_name) VALUES ('Vallée du Rhône', 'Vallee du Rhone');
INSERT INTO main_region (name, ascii_name) VALUES ('Australia', 'Australia');
INSERT INTO main_region (name, ascii_name) VALUES ('Chile', 'Chile');
INSERT INTO main_region (name, ascii_name) VALUES ('California', 'California');
INSERT INTO main_region (name, ascii_name) VALUES ('South Afrika', 'South Afrika');
INSERT INTO main_region (name, ascii_name) VALUES ('Germany', 'Germany');
INSERT INTO main_region (name, ascii_name) VALUES ('Italy', 'Italy');
INSERT INTO main_region (name, ascii_name) VALUES ('Hungary', 'Hungary');
INSERT INTO main_region (name, ascii_name) VALUES ('France', 'France');
INSERT INTO main_region (name, ascii_name) VALUES ('Ajaccio', 'Ajaccio');
INSERT INTO main_region (name, ascii_name) VALUES ('Allobrogie', 'Allobrogie');
INSERT INTO main_region (name, ascii_name) VALUES ('Aloxe-Corton', 'Aloxe-Corton');
INSERT INTO main_region (name, ascii_name) VALUES ('Aloxe-Corton 1er Cru', 'Aloxe-Corton 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace', 'Alsace');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru', 'Alsace Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Altenberg de Bergheim', 'Alsace Grand Cru Altenberg de Bergheim');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Altenberg de Wolxheim', 'Alsace Grand Cru Altenberg de Wolxheim');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Brand', 'Alsace Grand Cru Brand');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Bruderthal', 'Alsace Grand Cru Bruderthal');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Eichberg', 'Alsace Grand Cru Eichberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Florimont', 'Alsace Grand Cru Florimont');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Frankstein', 'Alsace Grand Cru Frankstein');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Froehn', 'Alsace Grand Cru Froehn');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Furstentum', 'Alsace Grand Cru Furstentum');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Goldert', 'Alsace Grand Cru Goldert');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Hatschbourg', 'Alsace Grand Cru Hatschbourg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Hengst', 'Alsace Grand Cru Hengst');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Kaefferkopf', 'Alsace Grand Cru Kaefferkopf');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Mambourg', 'Alsace Grand Cru Mambourg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Mandelberg', 'Alsace Grand Cru Mandelberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Muenchberg', 'Alsace Grand Cru Muenchberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Osterberg', 'Alsace Grand Cru Osterberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Pfersigberg', 'Alsace Grand Cru Pfersigberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Pfingstberg', 'Alsace Grand Cru Pfingstberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Praelatenberg', 'Alsace Grand Cru Praelatenberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Rangen', 'Alsace Grand Cru Rangen');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Rosacker', 'Alsace Grand Cru Rosacker');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Schlossberg', 'Alsace Grand Cru Schlossberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Schoenenbourg', 'Alsace Grand Cru Schoenenbourg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Sommerberg', 'Alsace Grand Cru Sommerberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Sonnenglanz', 'Alsace Grand Cru Sonnenglanz');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Spiegel', 'Alsace Grand Cru Spiegel');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Sporen', 'Alsace Grand Cru Sporen');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Steinert', 'Alsace Grand Cru Steinert');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Steingrubler', 'Alsace Grand Cru Steingrubler');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Vorbourg', 'Alsace Grand Cru Vorbourg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Wiebelsberg', 'Alsace Grand Cru Wiebelsberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Wineck-Schlossberg', 'Alsace Grand Cru Wineck-Schlossberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Winzenberg', 'Alsace Grand Cru Winzenberg');
INSERT INTO main_region (name, ascii_name) VALUES ('Alsace Grand Cru Zinnkoepfle', 'Alsace Grand Cru Zinnkoepfle');
INSERT INTO main_region (name, ascii_name) VALUES ('Anjou', 'Anjou');
INSERT INTO main_region (name, ascii_name) VALUES ('Anjou-Villages', 'Anjou-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Anjou-Villages Brissac', 'Anjou-Villages Brissac');
INSERT INTO main_region (name, ascii_name) VALUES ('Arbois', 'Arbois');
INSERT INTO main_region (name, ascii_name) VALUES ('Arbois-Pupillin', 'Arbois-Pupillin');
INSERT INTO main_region (name, ascii_name) VALUES ('Armagnac', 'Armagnac');
INSERT INTO main_region (name, ascii_name) VALUES ('Armagnac-Tenarèze', 'Armagnac-Tenareze');
INSERT INTO main_region (name, ascii_name) VALUES ('Atlantique', 'Atlantique');
INSERT INTO main_region (name, ascii_name) VALUES ('Auxey-Duresses', 'Auxey-Duresses');
INSERT INTO main_region (name, ascii_name) VALUES ('Auxey-Duresses 1er Cru', 'Auxey-Duresses 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Bandol', 'Bandol');
INSERT INTO main_region (name, ascii_name) VALUES ('Banyuls', 'Banyuls');
INSERT INTO main_region (name, ascii_name) VALUES ('Barsac', 'Barsac');
INSERT INTO main_region (name, ascii_name) VALUES ('Bas-Armagnac', 'Bas-Armagnac');
INSERT INTO main_region (name, ascii_name) VALUES ('Béarn', 'Bearn');
INSERT INTO main_region (name, ascii_name) VALUES ('Beaujolais', 'Beaujolais');
INSERT INTO main_region (name, ascii_name) VALUES ('Beaujolais-Villages', 'Beaujolais-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Beaumes de Venise', 'Beaumes de Venise');
INSERT INTO main_region (name, ascii_name) VALUES ('Beaune', 'Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Beaune 1er Cru', 'Beaune 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Bergerac', 'Bergerac');
INSERT INTO main_region (name, ascii_name) VALUES ('Bergerac sec', 'Bergerac sec');
INSERT INTO main_region (name, ascii_name) VALUES ('Bienvenues-Bâtard-Montrachet', 'Bienvenues-Batard-Montrachet');
INSERT INTO main_region (name, ascii_name) VALUES ('Blagny', 'Blagny');
INSERT INTO main_region (name, ascii_name) VALUES ('Blagny 1er Cru', 'Blagny 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Blanche Armagnac', 'Blanche Armagnac');
INSERT INTO main_region (name, ascii_name) VALUES ('Blaye', 'Blaye');
INSERT INTO main_region (name, ascii_name) VALUES ('Blaye Côtes de Bordeaux', 'Blaye Cotes de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Bonnes-Mares', 'Bonnes-Mares');
INSERT INTO main_region (name, ascii_name) VALUES ('Bonnezeaux', 'Bonnezeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux', 'Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux Clairet', 'Bordeaux Clairet');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux côtes de Francs', 'Bordeaux cotes de Francs');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux rosé', 'Bordeaux rose');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux Sec', 'Bordeaux Sec');
INSERT INTO main_region (name, ascii_name) VALUES ('Bordeaux Supérieur', 'Bordeaux Superieur');
INSERT INTO main_region (name, ascii_name) VALUES ('Bouches-du-Rhone', 'Bouches-du-Rhone');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne', 'Bourgogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Aligoté', 'Bourgogne Aligote');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Côtes d''Auxerre', 'Bourgogne Cotes d''Auxerre');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Grand Ordinaire', 'Bourgogne Grand Ordinaire');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Hautes Côtes de Beaune', 'Bourgogne Hautes Cotes de Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Hautes Côtes de Nuits', 'Bourgogne Hautes Cotes de Nuits');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Passe-tout-grains', 'Bourgogne Passe-tout-grains');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Rosé', 'Bourgogne Rose');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgogne Tonnerre', 'Bourgogne Tonnerre');
INSERT INTO main_region (name, ascii_name) VALUES ('Bourgueil', 'Bourgueil');
INSERT INTO main_region (name, ascii_name) VALUES ('Bouzeron', 'Bouzeron');
INSERT INTO main_region (name, ascii_name) VALUES ('Brouilly', 'Brouilly');
INSERT INTO main_region (name, ascii_name) VALUES ('Brulhois', 'Brulhois');
INSERT INTO main_region (name, ascii_name) VALUES ('Bugey', 'Bugey');
INSERT INTO main_region (name, ascii_name) VALUES ('Buzet', 'Buzet');
INSERT INTO main_region (name, ascii_name) VALUES ('Cabardès', 'Cabardes');
INSERT INTO main_region (name, ascii_name) VALUES ('Cabernet d''Anjou', 'Cabernet d''Anjou');
INSERT INTO main_region (name, ascii_name) VALUES ('Cabernet de Saumur', 'Cabernet de Saumur');
INSERT INTO main_region (name, ascii_name) VALUES ('Cadillac', 'Cadillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Cadillac Côtes de Bordeaux', 'Cadillac Cotes de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Cadillac Côtes de Bordeaux Cadillac', 'Cadillac Cotes de Bordeaux Cadillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Cahors', 'Cahors');
INSERT INTO main_region (name, ascii_name) VALUES ('Calvados du Pays d''Auge', 'Calvados du Pays d''Auge');
INSERT INTO main_region (name, ascii_name) VALUES ('Canon-fronsac', 'Canon-fronsac');
INSERT INTO main_region (name, ascii_name) VALUES ('Cartagène', 'Cartagene');
INSERT INTO main_region (name, ascii_name) VALUES ('Cassis', 'Cassis');
INSERT INTO main_region (name, ascii_name) VALUES ('Cassis de Bourgogne', 'Cassis de Bourgogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Castillon Côtes de Bordeaux', 'Castillon Cotes de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Cerdon', 'Cerdon');
INSERT INTO main_region (name, ascii_name) VALUES ('Cérons', 'Cerons');
INSERT INTO main_region (name, ascii_name) VALUES ('Cévennes', 'Cevennes');
INSERT INTO main_region (name, ascii_name) VALUES ('Chablis', 'Chablis');
INSERT INTO main_region (name, ascii_name) VALUES ('Chablis 1er Cru', 'Chablis 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Chablis Grand Cru', 'Chablis Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Chambertin', 'Chambertin');
INSERT INTO main_region (name, ascii_name) VALUES ('Chambolle-Musigny', 'Chambolle-Musigny');
INSERT INTO main_region (name, ascii_name) VALUES ('Chambolle-Musigny 1er Cru', 'Chambolle-Musigny 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Champagne', 'Champagne');
INSERT INTO main_region (name, ascii_name) VALUES ('Champagne 1er Cru', 'Champagne 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Champagne Grand Cru', 'Champagne Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Charmes-Chambertin', 'Charmes-Chambertin');
INSERT INTO main_region (name, ascii_name) VALUES ('Chassagne-Montrachet', 'Chassagne-Montrachet');
INSERT INTO main_region (name, ascii_name) VALUES ('Chassagne-Montrachet 1er Cru', 'Chassagne-Montrachet 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Château Chalon', 'Chateau Chalon');
INSERT INTO main_region (name, ascii_name) VALUES ('Châteauneuf-du-Pape', 'Chateauneuf-du-Pape');
INSERT INTO main_region (name, ascii_name) VALUES ('Chatillon-en-Diois', 'Chatillon-en-Diois');
INSERT INTO main_region (name, ascii_name) VALUES ('Chaume', 'Chaume');
INSERT INTO main_region (name, ascii_name) VALUES ('Chénas', 'Chenas');
INSERT INTO main_region (name, ascii_name) VALUES ('Cheverny', 'Cheverny');
INSERT INTO main_region (name, ascii_name) VALUES ('Chinon', 'Chinon');
INSERT INTO main_region (name, ascii_name) VALUES ('Chiroubles', 'Chiroubles');
INSERT INTO main_region (name, ascii_name) VALUES ('Chorey-les-Beaune', 'Chorey-les-Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Cidre', 'Cidre');
INSERT INTO main_region (name, ascii_name) VALUES ('Cité de Carcassonne', 'Cite de Carcassonne');
INSERT INTO main_region (name, ascii_name) VALUES ('Clairette de Die', 'Clairette de Die');
INSERT INTO main_region (name, ascii_name) VALUES ('Clos de la Roche', 'Clos de la Roche');
INSERT INTO main_region (name, ascii_name) VALUES ('Clos de Vougeot', 'Clos de Vougeot');
INSERT INTO main_region (name, ascii_name) VALUES ('Clos des Lambrays', 'Clos des Lambrays');
INSERT INTO main_region (name, ascii_name) VALUES ('Cognac', 'Cognac');
INSERT INTO main_region (name, ascii_name) VALUES ('Collioure', 'Collioure');
INSERT INTO main_region (name, ascii_name) VALUES ('Comté Tolosan', 'Comte Tolosan');
INSERT INTO main_region (name, ascii_name) VALUES ('Condrieu', 'Condrieu');
INSERT INTO main_region (name, ascii_name) VALUES ('Corbières', 'Corbieres');
INSERT INTO main_region (name, ascii_name) VALUES ('Corbières-Boutenac', 'Corbieres-Boutenac');
INSERT INTO main_region (name, ascii_name) VALUES ('Cornas', 'Cornas');
INSERT INTO main_region (name, ascii_name) VALUES ('Corse', 'Corse');
INSERT INTO main_region (name, ascii_name) VALUES ('Corse Calvi', 'Corse Calvi');
INSERT INTO main_region (name, ascii_name) VALUES ('Corse Figari', 'Corse Figari');
INSERT INTO main_region (name, ascii_name) VALUES ('Corse Porto-Vecchio', 'Corse Porto-Vecchio');
INSERT INTO main_region (name, ascii_name) VALUES ('Corton', 'Corton');
INSERT INTO main_region (name, ascii_name) VALUES ('Corton Grand Cru', 'Corton Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Corton-Bressandres Grand Cru', 'Corton-Bressandres Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Corton-Charlemagne', 'Corton-Charlemagne');
INSERT INTO main_region (name, ascii_name) VALUES ('Costieres de Nimes', 'Costieres de Nimes');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte de Beaune', 'Cote de Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte de Beaune-Villages', 'Cote de Beaune-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte de Brouilly', 'Cote de Brouilly');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte de Nuits-Villages', 'Cote de Nuits-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte Roannaise', 'Cote Roannaise');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte Rotie', 'Cote Rotie');
INSERT INTO main_region (name, ascii_name) VALUES ('Côte Vermeille', 'Cote Vermeille');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux Champenois', 'Coteaux Champenois');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux d''Aix-en-Provence', 'Coteaux d''Aix-en-Provence');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux d''Ensérune', 'Coteaux d''Enserune');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux de Die', 'Coteaux de Die');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux de l''Aubance', 'Coteaux de l''Aubance');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux de Narbonne', 'Coteaux de Narbonne');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux de Peyriac', 'Coteaux de Peyriac');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux de Saumur', 'Coteaux de Saumur');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Giennois', 'Coteaux du Giennois');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Languedoc', 'Coteaux du Languedoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Layon', 'Coteaux du Layon');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Loir', 'Coteaux du Loir');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Quercy', 'Coteaux du Quercy');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Tricastin', 'Coteaux du Tricastin');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux du Vendômois', 'Coteaux du Vendomois');
INSERT INTO main_region (name, ascii_name) VALUES ('Coteaux Varois en Provence', 'Coteaux Varois en Provence');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes Catalanes', 'Cotes Catalanes');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes d''Auvergne', 'Cotes d''Auvergne');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bergerac', 'Cotes de Bergerac');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bergerac Moëlleux', 'Cotes de Bergerac Moelleux');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Blaye', 'Cotes de Blaye');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux', 'Cotes de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux Blaye', 'Cotes de Bordeaux Blaye');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux Cadillac', 'Cotes de Bordeaux Cadillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux Castillon', 'Cotes de Bordeaux Castillon');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux Francs', 'Cotes de Bordeaux Francs');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bordeaux Saint-Macaire', 'Cotes de Bordeaux Saint-Macaire');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Bourg', 'Cotes de Bourg');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Castillon', 'Cotes de Castillon');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Duras', 'Cotes de Duras');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Gascogne', 'Cotes de Gascogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Montravel', 'Cotes de Montravel');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Provence', 'Cotes de Provence');
INSERT INTO main_region (name, ascii_name) VALUES ('COTES DE PROVENCE FREJUS', 'COTES DE PROVENCE FREJUS');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Provence Sainte Victoire', 'Cotes de Provence Sainte Victoire');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Thau', 'Cotes de Thau');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes de Thongue', 'Cotes de Thongue');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Brian', 'Cotes du Brian');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Jura', 'Cotes du Jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Lot', 'Cotes du Lot');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Luberon', 'Cotes du Luberon');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Marmandais', 'Cotes du Marmandais');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Rhône', 'Cotes du Rhone');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Rhône Villages', 'Cotes du Rhone Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Roussillon', 'Cotes du Roussillon');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Roussillon Les Aspres', 'Cotes du Roussillon Les Aspres');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Roussillon-Villages', 'Cotes du Roussillon-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Tarn', 'Cotes du Tarn');
INSERT INTO main_region (name, ascii_name) VALUES ('Côtes du Ventoux', 'Cotes du Ventoux');
INSERT INTO main_region (name, ascii_name) VALUES ('Cour-Cheverny', 'Cour-Cheverny');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant d''Alsace', 'Cremant d''Alsace');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant de Bordeaux', 'Cremant de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant de Bourgogne', 'Cremant de Bourgogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant de Die', 'Cremant de Die');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant de Loire', 'Cremant de Loire');
INSERT INTO main_region (name, ascii_name) VALUES ('Crémant du Jura', 'Cremant du Jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Crème de Fruits', 'Creme de Fruits');
INSERT INTO main_region (name, ascii_name) VALUES ('Crème de Fruits Rouge', 'Creme de Fruits Rouge');
INSERT INTO main_region (name, ascii_name) VALUES ('Crème de Pêche de Vigne', 'Creme de Peche de Vigne');
INSERT INTO main_region (name, ascii_name) VALUES ('Criots-Bâtard-Montrachet', 'Criots-Batard-Montrachet');
INSERT INTO main_region (name, ascii_name) VALUES ('Crozes-Hermitage', 'Crozes-Hermitage');
INSERT INTO main_region (name, ascii_name) VALUES ('Duché d''Uzès', 'Duche d''Uzes');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de vie', 'Eaux de vie');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de Vie d''Alsace', 'Eaux de Vie d''Alsace');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de Vie de  Marc (Alsace Lorraine)', 'Eaux de Vie de  Marc (Alsace Lorraine)');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de Vie de Marc (Bourgogne-Beaujolais)', 'Eaux de Vie de Marc (Bourgogne-Beaujolais)');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de Vie de Marc (Provence-Corse)', 'Eaux de Vie de Marc (Provence-Corse)');
INSERT INTO main_region (name, ascii_name) VALUES ('Eaux de Vie de Vin de Franche-Comté (Jura)', 'Eaux de Vie de Vin de Franche-Comte (Jura)');
INSERT INTO main_region (name, ascii_name) VALUES ('Echezeaux', 'Echezeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Echezeaux Grand Cru', 'Echezeaux Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Entre-Deux-Mers', 'Entre-Deux-Mers');
INSERT INTO main_region (name, ascii_name) VALUES ('Faugeres', 'Faugeres');
INSERT INTO main_region (name, ascii_name) VALUES ('Fiefs Vendéens', 'Fiefs Vendeens');
INSERT INTO main_region (name, ascii_name) VALUES ('Fine Bordeaux', 'Fine Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Fine du Jura', 'Fine du Jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Fine Faugeres', 'Fine Faugeres');
INSERT INTO main_region (name, ascii_name) VALUES ('Fitou', 'Fitou');
INSERT INTO main_region (name, ascii_name) VALUES ('Fixin', 'Fixin');
INSERT INTO main_region (name, ascii_name) VALUES ('Fixin 1er Cru', 'Fixin 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Fleurie', 'Fleurie');
INSERT INTO main_region (name, ascii_name) VALUES ('Floc de Gascogne', 'Floc de Gascogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Fronsac', 'Fronsac');
INSERT INTO main_region (name, ascii_name) VALUES ('Gaillac', 'Gaillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Gaillac doux', 'Gaillac doux');
INSERT INTO main_region (name, ascii_name) VALUES ('Gaillac mousseux', 'Gaillac mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Gaillac Premières Côtes', 'Gaillac Premieres Cotes');
INSERT INTO main_region (name, ascii_name) VALUES ('Gard', 'Gard');
INSERT INTO main_region (name, ascii_name) VALUES ('Gevrey-Chambertin', 'Gevrey-Chambertin');
INSERT INTO main_region (name, ascii_name) VALUES ('Gevrey-Chambertin 1er Cru', 'Gevrey-Chambertin 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Gigondas', 'Gigondas');
INSERT INTO main_region (name, ascii_name) VALUES ('Givry', 'Givry');
INSERT INTO main_region (name, ascii_name) VALUES ('Givry 1er Cru', 'Givry 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Graves', 'Graves');
INSERT INTO main_region (name, ascii_name) VALUES ('Graves de Vayres', 'Graves de Vayres');
INSERT INTO main_region (name, ascii_name) VALUES ('Graves supérieures', 'Graves superieures');
INSERT INTO main_region (name, ascii_name) VALUES ('Grignan-les-Adhémar', 'Grignan-les-Adhemar');
INSERT INTO main_region (name, ascii_name) VALUES ('Gros Plant du Pays Nantais', 'Gros Plant du Pays Nantais');
INSERT INTO main_region (name, ascii_name) VALUES ('Haut-Médoc', 'Haut-Medoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Haut-Montravel', 'Haut-Montravel');
INSERT INTO main_region (name, ascii_name) VALUES ('Hermitage', 'Hermitage');
INSERT INTO main_region (name, ascii_name) VALUES ('Ile de Beauté', 'Ile de Beaute');
INSERT INTO main_region (name, ascii_name) VALUES ('Irancy', 'Irancy');
INSERT INTO main_region (name, ascii_name) VALUES ('Irouléguy', 'Irouleguy');
INSERT INTO main_region (name, ascii_name) VALUES ('Jasnières', 'Jasnieres');
INSERT INTO main_region (name, ascii_name) VALUES ('Juliénas', 'Julienas');
INSERT INTO main_region (name, ascii_name) VALUES ('Jurançon', 'Jurancon');
INSERT INTO main_region (name, ascii_name) VALUES ('Jurançon sec', 'Jurancon sec');
INSERT INTO main_region (name, ascii_name) VALUES ('Jus de raisin', 'Jus de raisin');
INSERT INTO main_region (name, ascii_name) VALUES ('L''Etoile', 'L''Etoile');
INSERT INTO main_region (name, ascii_name) VALUES ('Ladoix', 'Ladoix');
INSERT INTO main_region (name, ascii_name) VALUES ('Ladoix 1er Cru', 'Ladoix 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Lalande-de-Pomerol', 'Lalande-de-Pomerol');
INSERT INTO main_region (name, ascii_name) VALUES ('Landes', 'Landes');
INSERT INTO main_region (name, ascii_name) VALUES ('Languedoc', 'Languedoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Liqueurs', 'Liqueurs');
INSERT INTO main_region (name, ascii_name) VALUES ('Lirac', 'Lirac');
INSERT INTO main_region (name, ascii_name) VALUES ('Listrac-Médoc', 'Listrac-Medoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Lot', 'Lot');
INSERT INTO main_region (name, ascii_name) VALUES ('Loupiac', 'Loupiac');
INSERT INTO main_region (name, ascii_name) VALUES ('Luberon', 'Luberon');
INSERT INTO main_region (name, ascii_name) VALUES ('Lussac-Saint-Emilion', 'Lussac-Saint-Emilion');
INSERT INTO main_region (name, ascii_name) VALUES ('Mâcon', 'Macon');
INSERT INTO main_region (name, ascii_name) VALUES ('Mâcon-Villages', 'Macon-Villages');
INSERT INTO main_region (name, ascii_name) VALUES ('Macvin du Jura', 'Macvin du Jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Madiran', 'Madiran');
INSERT INTO main_region (name, ascii_name) VALUES ('Malepère', 'Malepere');
INSERT INTO main_region (name, ascii_name) VALUES ('Maranges', 'Maranges');
INSERT INTO main_region (name, ascii_name) VALUES ('Maranges 1er Cru', 'Maranges 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Maranges Côte de Beaune', 'Maranges Cote de Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Marc de Bourgogne', 'Marc de Bourgogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Marc de Gigondas', 'Marc de Gigondas');
INSERT INTO main_region (name, ascii_name) VALUES ('Marc du Jura', 'Marc du Jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Marcillac', 'Marcillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Margaux', 'Margaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Marsannay', 'Marsannay');
INSERT INTO main_region (name, ascii_name) VALUES ('Marsannay rosé', 'Marsannay rose');
INSERT INTO main_region (name, ascii_name) VALUES ('Maures', 'Maures');
INSERT INTO main_region (name, ascii_name) VALUES ('Maury', 'Maury');
INSERT INTO main_region (name, ascii_name) VALUES ('Mazis-Chambertin', 'Mazis-Chambertin');
INSERT INTO main_region (name, ascii_name) VALUES ('Mazoyères-Chambertin Gran Cru', 'Mazoyeres-Chambertin Gran Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Méditerranée', 'Mediterranee');
INSERT INTO main_region (name, ascii_name) VALUES ('Médoc', 'Medoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Ménetou Salon', 'Menetou Salon');
INSERT INTO main_region (name, ascii_name) VALUES ('Mercurey', 'Mercurey');
INSERT INTO main_region (name, ascii_name) VALUES ('Mercurey 1er Cru', 'Mercurey 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Meursault', 'Meursault');
INSERT INTO main_region (name, ascii_name) VALUES ('Meursault 1er Cru', 'Meursault 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Meursault 1er Cru Blagny', 'Meursault 1er Cru Blagny');
INSERT INTO main_region (name, ascii_name) VALUES ('Minervois', 'Minervois');
INSERT INTO main_region (name, ascii_name) VALUES ('Minervois la Livinière', 'Minervois la Liviniere');
INSERT INTO main_region (name, ascii_name) VALUES ('Monbazillac', 'Monbazillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Mont Caume', 'Mont Caume');
INSERT INTO main_region (name, ascii_name) VALUES ('Montagne-Saint-Emilion', 'Montagne-Saint-Emilion');
INSERT INTO main_region (name, ascii_name) VALUES ('Montagny', 'Montagny');
INSERT INTO main_region (name, ascii_name) VALUES ('Montagny 1er Cru', 'Montagny 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Monthélie', 'Monthelie');
INSERT INTO main_region (name, ascii_name) VALUES ('Monthelie 1er Cru', 'Monthelie 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Montlouis-sur-Loire', 'Montlouis-sur-Loire');
INSERT INTO main_region (name, ascii_name) VALUES ('Montlouis-sur-Loire mousseux', 'Montlouis-sur-Loire mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Montravel', 'Montravel');
INSERT INTO main_region (name, ascii_name) VALUES ('Morey-Saint-Denis', 'Morey-Saint-Denis');
INSERT INTO main_region (name, ascii_name) VALUES ('Morey-Saint-Denis 1er Cru', 'Morey-Saint-Denis 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Morgon', 'Morgon');
INSERT INTO main_region (name, ascii_name) VALUES ('Morgon Les Charmes', 'Morgon Les Charmes');
INSERT INTO main_region (name, ascii_name) VALUES ('Moulin à Vent', 'Moulin a Vent');
INSERT INTO main_region (name, ascii_name) VALUES ('Moulis-en-Médoc', 'Moulis-en-Medoc');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscadet', 'Muscadet');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscadet Sèvre et Maine sur Lie', 'Muscadet Sevre et Maine sur Lie');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscadet Sèvre-et-Maine', 'Muscadet Sevre-et-Maine');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Beaumes-de-Venise', 'Muscat de Beaumes-de-Venise');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Frontignan', 'Muscat de Frontignan');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Lunel', 'Muscat de Lunel');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Mireval', 'Muscat de Mireval');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Rivesaltes', 'Muscat de Rivesaltes');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat de Saint-Jean-de-Minervois', 'Muscat de Saint-Jean-de-Minervois');
INSERT INTO main_region (name, ascii_name) VALUES ('Muscat du cap Corse', 'Muscat du cap Corse');
INSERT INTO main_region (name, ascii_name) VALUES ('Nuits-Saint-Georges', 'Nuits-Saint-Georges');
INSERT INTO main_region (name, ascii_name) VALUES ('Nuits-Saint-Georges 1er Cru', 'Nuits-Saint-Georges 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Orléans', 'Orleans');
INSERT INTO main_region (name, ascii_name) VALUES ('Orléans-Cléry', 'Orleans-Clery');
INSERT INTO main_region (name, ascii_name) VALUES ('Pacherenc du Vic-Bilh', 'Pacherenc du Vic-Bilh');
INSERT INTO main_region (name, ascii_name) VALUES ('Palette', 'Palette');
INSERT INTO main_region (name, ascii_name) VALUES ('Patrimonio', 'Patrimonio');
INSERT INTO main_region (name, ascii_name) VALUES ('Pauillac', 'Pauillac');
INSERT INTO main_region (name, ascii_name) VALUES ('Pays d''Hérault', 'Pays d''Herault');
INSERT INTO main_region (name, ascii_name) VALUES ('Pays d''Oc', 'Pays d''Oc');
INSERT INTO main_region (name, ascii_name) VALUES ('Pécharmant', 'Pecharmant');
INSERT INTO main_region (name, ascii_name) VALUES ('Périgord', 'Perigord');
INSERT INTO main_region (name, ascii_name) VALUES ('Pernand-Vergelesses', 'Pernand-Vergelesses');
INSERT INTO main_region (name, ascii_name) VALUES ('Pernand-Vergelesses 1er Cru', 'Pernand-Vergelesses 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Pessac-Léognan', 'Pessac-Leognan');
INSERT INTO main_region (name, ascii_name) VALUES ('Petit Chablis', 'Petit Chablis');
INSERT INTO main_region (name, ascii_name) VALUES ('Pineau des Charentes', 'Pineau des Charentes');
INSERT INTO main_region (name, ascii_name) VALUES ('Pomerol', 'Pomerol');
INSERT INTO main_region (name, ascii_name) VALUES ('Pommard', 'Pommard');
INSERT INTO main_region (name, ascii_name) VALUES ('Pommard 1er Cru', 'Pommard 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Pommeau de Normandie', 'Pommeau de Normandie');
INSERT INTO main_region (name, ascii_name) VALUES ('Pouilly-Fuissé', 'Pouilly-Fuisse');
INSERT INTO main_region (name, ascii_name) VALUES ('Pouilly-Fumé', 'Pouilly-Fume');
INSERT INTO main_region (name, ascii_name) VALUES ('Pouilly-Loché', 'Pouilly-Loche');
INSERT INTO main_region (name, ascii_name) VALUES ('Pouilly-sur-Loire', 'Pouilly-sur-Loire');
INSERT INTO main_region (name, ascii_name) VALUES ('Pouilly-Vinzelles', 'Pouilly-Vinzelles');
INSERT INTO main_region (name, ascii_name) VALUES ('Premières Côtes de Blaye', 'Premieres Cotes de Blaye');
INSERT INTO main_region (name, ascii_name) VALUES ('Premières Côtes de Bordeaux', 'Premieres Cotes de Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Principauté d''Orange', 'Principaute d''Orange');
INSERT INTO main_region (name, ascii_name) VALUES ('Puisseguin-Saint-Emilion', 'Puisseguin-Saint-Emilion');
INSERT INTO main_region (name, ascii_name) VALUES ('Puligny-Montrachet', 'Puligny-Montrachet');
INSERT INTO main_region (name, ascii_name) VALUES ('Puligny-Montrachet 1er Cru', 'Puligny-Montrachet 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Pyrénées-Atlantiques', 'Pyrenees-Atlantiques');
INSERT INTO main_region (name, ascii_name) VALUES ('Quarts de Chaume', 'Quarts de Chaume');
INSERT INTO main_region (name, ascii_name) VALUES ('Quincy', 'Quincy');
INSERT INTO main_region (name, ascii_name) VALUES ('Rasteau', 'Rasteau');
INSERT INTO main_region (name, ascii_name) VALUES ('Rasteau VDN', 'Rasteau VDN');
INSERT INTO main_region (name, ascii_name) VALUES ('Ratafia', 'Ratafia');
INSERT INTO main_region (name, ascii_name) VALUES ('Ratafia de Bourgogne', 'Ratafia de Bourgogne');
INSERT INTO main_region (name, ascii_name) VALUES ('Régnié', 'Regnie');
INSERT INTO main_region (name, ascii_name) VALUES ('Reuilly', 'Reuilly');
INSERT INTO main_region (name, ascii_name) VALUES ('Rivesaltes', 'Rivesaltes');
INSERT INTO main_region (name, ascii_name) VALUES ('Rivesaltes Ambré', 'Rivesaltes Ambre');
INSERT INTO main_region (name, ascii_name) VALUES ('Rivesaltes Grenat', 'Rivesaltes Grenat');
INSERT INTO main_region (name, ascii_name) VALUES ('Rivesaltes Rancio', 'Rivesaltes Rancio');
INSERT INTO main_region (name, ascii_name) VALUES ('Rivesaltes Tuilé', 'Rivesaltes Tuile');
INSERT INTO main_region (name, ascii_name) VALUES ('Rosé d''Anjou', 'Rose d''Anjou');
INSERT INTO main_region (name, ascii_name) VALUES ('Rosé de Loire', 'Rose de Loire');
INSERT INTO main_region (name, ascii_name) VALUES ('Roussette de Savoie', 'Roussette de Savoie');
INSERT INTO main_region (name, ascii_name) VALUES ('Roussette de Savoie Marestel', 'Roussette de Savoie Marestel');
INSERT INTO main_region (name, ascii_name) VALUES ('Roussette du Bugey', 'Roussette du Bugey');
INSERT INTO main_region (name, ascii_name) VALUES ('Rully', 'Rully');
INSERT INTO main_region (name, ascii_name) VALUES ('Rully 1er Cru', 'Rully 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Sables du Golfe du Lion', 'Sables du Golfe du Lion');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Amour', 'Saint-Amour');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Aubin', 'Saint-Aubin');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Aubin 1er Cru', 'Saint-Aubin 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Bris', 'Saint-Bris');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Chinian', 'Saint-Chinian');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Emilion', 'Saint-Emilion');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Emilion Grand Cru', 'Saint-Emilion Grand Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Emilion Grand Cru Classé', 'Saint-Emilion Grand Cru Classe');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Estèphe', 'Saint-Estephe');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Georges-Saint-Emilion', 'Saint-Georges-Saint-Emilion');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Joseph', 'Saint-Joseph');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Julien', 'Saint-Julien');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Mont', 'Saint-Mont');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Nicolas-de-Bourgueil', 'Saint-Nicolas-de-Bourgueil');
INSERT INTO main_region (name, ascii_name) VALUES ('SAINT-PERAY', 'SAINT-PERAY');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Pourçain', 'Saint-Pourcain');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Romain', 'Saint-Romain');
INSERT INTO main_region (name, ascii_name) VALUES ('Saint-Véran', 'Saint-Veran');
INSERT INTO main_region (name, ascii_name) VALUES ('Sainte-Croix-du-Mont', 'Sainte-Croix-du-Mont');
INSERT INTO main_region (name, ascii_name) VALUES ('Sainte-Foy-Bordeaux', 'Sainte-Foy-Bordeaux');
INSERT INTO main_region (name, ascii_name) VALUES ('Sancerre', 'Sancerre');
INSERT INTO main_region (name, ascii_name) VALUES ('Santenay', 'Santenay');
INSERT INTO main_region (name, ascii_name) VALUES ('Santenay 1er Cru', 'Santenay 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Saumur', 'Saumur');
INSERT INTO main_region (name, ascii_name) VALUES ('Saumur Champigny', 'Saumur Champigny');
INSERT INTO main_region (name, ascii_name) VALUES ('Saumur mousseux', 'Saumur mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Saumur Puy Notre Dame', 'Saumur Puy Notre Dame');
INSERT INTO main_region (name, ascii_name) VALUES ('Saussignac', 'Saussignac');
INSERT INTO main_region (name, ascii_name) VALUES ('Sauternes', 'Sauternes');
INSERT INTO main_region (name, ascii_name) VALUES ('Savennières', 'Savennieres');
INSERT INTO main_region (name, ascii_name) VALUES ('Savennières Roche-aux-Moines', 'Savennieres Roche-aux-Moines');
INSERT INTO main_region (name, ascii_name) VALUES ('Savigny', 'Savigny');
INSERT INTO main_region (name, ascii_name) VALUES ('Savigny 1er Cru', 'Savigny 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Savigny-les-Beaune', 'Savigny-les-Beaune');
INSERT INTO main_region (name, ascii_name) VALUES ('Savigny-les-Beaune 1er Cru', 'Savigny-les-Beaune 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie', 'Savoie');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Apremont', 'Savoie Apremont');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Arbin', 'Savoie Arbin');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Bergeron', 'Savoie Bergeron');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Chignin', 'Savoie Chignin');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Chignin-Bergeron', 'Savoie Chignin-Bergeron');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Cru Chignin', 'Savoie Cru Chignin');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Cru Chignin-Bergeron', 'Savoie Cru Chignin-Bergeron');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Cruet', 'Savoie Cruet');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Jongieux', 'Savoie Jongieux');
INSERT INTO main_region (name, ascii_name) VALUES ('Savoie Mondeuse', 'Savoie Mondeuse');
INSERT INTO main_region (name, ascii_name) VALUES ('Tarn', 'Tarn');
INSERT INTO main_region (name, ascii_name) VALUES ('Tavel', 'Tavel');
INSERT INTO main_region (name, ascii_name) VALUES ('Terrasses-de-Béziers', 'Terrasses-de-Beziers');
INSERT INTO main_region (name, ascii_name) VALUES ('Thézac-Perricard', 'Thezac-Perricard');
INSERT INTO main_region (name, ascii_name) VALUES ('Torgan', 'Torgan');
INSERT INTO main_region (name, ascii_name) VALUES ('Touraine', 'Touraine');
INSERT INTO main_region (name, ascii_name) VALUES ('Touraine Amboise', 'Touraine Amboise');
INSERT INTO main_region (name, ascii_name) VALUES ('Touraine mousseux', 'Touraine mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Touraine pétillant', 'Touraine petillant');
INSERT INTO main_region (name, ascii_name) VALUES ('Tursan', 'Tursan');
INSERT INTO main_region (name, ascii_name) VALUES ('Vacqueyras', 'Vacqueyras');
INSERT INTO main_region (name, ascii_name) VALUES ('Val de Loire', 'Val de Loire');
INSERT INTO main_region (name, ascii_name) VALUES ('Valencay', 'Valencay');
INSERT INTO main_region (name, ascii_name) VALUES ('Vallée du Torgan', 'Vallee du Torgan');
INSERT INTO main_region (name, ascii_name) VALUES ('Var', 'Var');
INSERT INTO main_region (name, ascii_name) VALUES ('Ventoux', 'Ventoux');
INSERT INTO main_region (name, ascii_name) VALUES ('Vicomté d''Aumelas', 'Vicomte d''Aumelas');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de France', 'Vin de France');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays Charentais', 'Vin de Pays Charentais');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS D''AIGUES', 'VIN DE PAYS D''AIGUES');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS D''ALLOBROGIE', 'VIN DE PAYS D''ALLOBROGIE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays d''Ariège', 'Vin de Pays d''Ariege');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS D''AUDE', 'VIN DE PAYS D''AUDE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays d''Hauterive', 'Vin de pays d''Hauterive');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS D''OC', 'VIN DE PAYS D''OC');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS D''URFE', 'VIN DE PAYS D''URFE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays de Bigorre', 'Vin de pays de Bigorre');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays de Caux', 'Vin de pays de Caux');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE L''ARDECHE', 'VIN DE PAYS DE L''ARDECHE');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE L''ATLANTIQUE', 'VIN DE PAYS DE L''ATLANTIQUE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays de l''Aude', 'Vin de Pays de l''Aude');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE L''HERAULT', 'VIN DE PAYS DE L''HERAULT');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE LA DROME', 'VIN DE PAYS DE LA DROME');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE LA PRINCIPAUTE D''ORANGE', 'VIN DE PAYS DE LA PRINCIPAUTE D''ORANGE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays de la Sainte Baume', 'Vin de Pays de la Sainte Baume');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE LA VICOMTE D''AUMELAS', 'VIN DE PAYS DE LA VICOMTE D''AUMELAS');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE MEDITERRANEE', 'VIN DE PAYS DE MEDITERRANEE');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE THEZAC-PERRICARD', 'VIN DE PAYS DE THEZAC-PERRICARD');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DE VAUCLUSE', 'VIN DE PAYS DE VAUCLUSE');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES CEVENNES', 'VIN DE PAYS DES CEVENNES');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des collines de la Moure', 'Vin de pays des collines de la Moure');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES COLLINES RHODANIENNES', 'VIN DE PAYS DES COLLINES RHODANIENNES');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES COTEAUX D''ENSERUNE', 'VIN DE PAYS DES COTEAUX D''ENSERUNE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des côteaux de Murviel', 'Vin de pays des coteaux de Murviel');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES COTEAUX DE PEYRIAC', 'VIN DE PAYS DES COTEAUX DE PEYRIAC');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des côteaux du Verdon', 'Vin de pays des coteaux du Verdon');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays des Côtes Catalanes', 'Vin de Pays des Cotes Catalanes');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES COTES DE GASCOGNE', 'VIN DE PAYS DES COTES DE GASCOGNE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des côtes de Perignan', 'Vin de pays des cotes de Perignan');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des côtes de Prouilhe', 'Vin de pays des cotes de Prouilhe');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES COTES DE THONGUE', 'VIN DE PAYS DES COTES DE THONGUE');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES MONTS DE LA GRAGE', 'VIN DE PAYS DES MONTS DE LA GRAGE');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES PORTES DE  MEDITERRANEE', 'VIN DE PAYS DES PORTES DE  MEDITERRANEE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des portes de méditerranée', 'Vin de pays des portes de mediterranee');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des Pyrénées Atlantiques', 'Vin de pays des Pyrenees Atlantiques');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des Pyrénées Orientales', 'Vin de pays des Pyrenees Orientales');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des sables de Camargues', 'Vin de pays des sables de Camargues');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DES SABLES DU GOLFE DU LION', 'VIN DE PAYS DES SABLES DU GOLFE DU LION');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays des terroirs landais', 'Vin de pays des terroirs landais');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DU COMTE DE GRIGNAN', 'VIN DE PAYS DU COMTE DE GRIGNAN');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays du Comté Tolosan', 'Vin de Pays du Comte Tolosan');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays du Gard', 'Vin de Pays du Gard');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DU JARDIN DE LA FRANCE', 'VIN DE PAYS DU JARDIN DE LA FRANCE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays du Lot', 'Vin de Pays du Lot');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DU PERIGORD', 'VIN DE PAYS DU PERIGORD');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DU VAL DE LOIRE', 'VIN DE PAYS DU VAL DE LOIRE');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de pays du val de Montferrand', 'Vin de pays du val de Montferrand');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays du Var', 'Vin de Pays du Var');
INSERT INTO main_region (name, ascii_name) VALUES ('VIN DE PAYS DUCHE D''UZES', 'VIN DE PAYS DUCHE D''UZES');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Pays Sable de Camargue', 'Vin de Pays Sable de Camargue');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie', 'Vin de Savoie');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Arbin', 'Vin de Savoie Arbin');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Chignin', 'Vin de Savoie Chignin');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Chignin-Bergeron', 'Vin de Savoie Chignin-Bergeron');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Cruet', 'Vin de Savoie Cruet');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Jongieux', 'Vin de Savoie Jongieux');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Montmélian', 'Vin de Savoie Montmelian');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Mousseux', 'Vin de Savoie Mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Savoie Saint-Jean-de-la-Porte', 'Vin de Savoie Saint-Jean-de-la-Porte');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin de Table', 'Vin de Table');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin du Bugey mousseux', 'Vin du Bugey mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Vin du jura', 'Vin du jura');
INSERT INTO main_region (name, ascii_name) VALUES ('Viré-Clessé', 'Vire-Clesse');
INSERT INTO main_region (name, ascii_name) VALUES ('Volnay', 'Volnay');
INSERT INTO main_region (name, ascii_name) VALUES ('Volnay 1er Cru', 'Volnay 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Vosne-Romanée', 'Vosne-Romanee');
INSERT INTO main_region (name, ascii_name) VALUES ('Vosne-Romanée 1er Cru', 'Vosne-Romanee 1er Cru');
INSERT INTO main_region (name, ascii_name) VALUES ('Vouvray', 'Vouvray');
INSERT INTO main_region (name, ascii_name) VALUES ('Vouvray mousseux', 'Vouvray mousseux');
INSERT INTO main_region (name, ascii_name) VALUES ('Vouvray pétillant', 'Vouvray petillant');
INSERT INTO main_grape (name, ascii_name) VALUES ('Cabernet Franc', 'Cabernet Franc');
INSERT INTO main_grape (name, ascii_name) VALUES ('Cabernet Sauvignon', 'Cabernet Sauvignon');
INSERT INTO main_grape (name, ascii_name) VALUES ('Carignan', 'Carignan');
INSERT INTO main_grape (name, ascii_name) VALUES ('Chardonnay', 'Chardonnay');
INSERT INTO main_grape (name, ascii_name) VALUES ('Chenin Blanc', 'Chenin Blanc');
INSERT INTO main_grape (name, ascii_name) VALUES ('Cinsault', 'Cinsault');
INSERT INTO main_grape (name, ascii_name) VALUES ('Gamay', 'Gamay');
INSERT INTO main_grape (name, ascii_name) VALUES ('Gewürztraminer', 'Gewürztraminer');
INSERT INTO main_grape (name, ascii_name) VALUES ('Grenache', 'Grenache');
INSERT INTO main_grape (name, ascii_name) VALUES ('Grenache Blanc', 'Grenache Blanc');
INSERT INTO main_grape (name, ascii_name) VALUES ('Malbec', 'Malbec');
INSERT INTO main_grape (name, ascii_name) VALUES ('Marsanne', 'Marsanne');
INSERT INTO main_grape (name, ascii_name) VALUES ('Merlot', 'Merlot');
INSERT INTO main_grape (name, ascii_name) VALUES ('Mourvèdre', 'Mourvedre');
INSERT INTO main_grape (name, ascii_name) VALUES ('Muscadet', 'Muscadet');
INSERT INTO main_grape (name, ascii_name) VALUES ('Petit Verdot', 'Petit Verdot');
INSERT INTO main_grape (name, ascii_name) VALUES ('Pinot Blanc', 'Pinot Blanc');
INSERT INTO main_grape (name, ascii_name) VALUES ('Pinot Gris', 'Pinot Gris');
INSERT INTO main_grape (name, ascii_name) VALUES ('Pinot Noir', 'Pinot Noir');
INSERT INTO main_grape (name, ascii_name) VALUES ('Riesling', 'Riesling');
INSERT INTO main_grape (name, ascii_name) VALUES ('Roussanne', 'Roussanne');
INSERT INTO main_grape (name, ascii_name) VALUES ('Sauvignon Blanc', 'Sauvignon Blanc');
INSERT INTO main_grape (name, ascii_name) VALUES ('Syrah', 'Syrah');
INSERT INTO main_grape (name, ascii_name) VALUES ('Sémillon', 'Semillon');
INSERT INTO main_grape (name, ascii_name) VALUES ('Viognier', 'Viognier');
INSERT INTO main_aromaimpression (name) VALUES ('Accessible');
INSERT INTO main_aromaimpression (name) VALUES ('Acacia');
INSERT INTO main_aromaimpression (name) VALUES ('Acidic');
INSERT INTO main_aromaimpression (name) VALUES ('Aftertaste');
INSERT INTO main_aromaimpression (name) VALUES ('Aggressive');
INSERT INTO main_aromaimpression (name) VALUES ('Alcoholic');
INSERT INTO main_aromaimpression (name) VALUES ('Almond');
INSERT INTO main_aromaimpression (name) VALUES ('Apple');
INSERT INTO main_aromaimpression (name) VALUES ('Aroma');
INSERT INTO main_aromaimpression (name) VALUES ('Astringent');
INSERT INTO main_aromaimpression (name) VALUES ('Austere');
INSERT INTO main_aromaimpression (name) VALUES ('Autolytic');
INSERT INTO main_aromaimpression (name) VALUES ('Baked');
INSERT INTO main_aromaimpression (name) VALUES ('Balanced');
INSERT INTO main_aromaimpression (name) VALUES ('Barnyard');
INSERT INTO main_aromaimpression (name) VALUES ('Berries');
INSERT INTO main_aromaimpression (name) VALUES ('Big');
INSERT INTO main_aromaimpression (name) VALUES ('Biscuity');
INSERT INTO main_aromaimpression (name) VALUES ('Bite');
INSERT INTO main_aromaimpression (name) VALUES ('Bitter');
INSERT INTO main_aromaimpression (name) VALUES ('Black pepper');
INSERT INTO main_aromaimpression (name) VALUES ('Blackberry');
INSERT INTO main_aromaimpression (name) VALUES ('Blowzy');
INSERT INTO main_aromaimpression (name) VALUES ('Blueberry');
INSERT INTO main_aromaimpression (name) VALUES ('Body');
INSERT INTO main_aromaimpression (name) VALUES ('Bouquet');
INSERT INTO main_aromaimpression (name) VALUES ('Bright');
INSERT INTO main_aromaimpression (name) VALUES ('Buttery');
INSERT INTO main_aromaimpression (name) VALUES ('Cassis');
INSERT INTO main_aromaimpression (name) VALUES ('Cedarwood');
INSERT INTO main_aromaimpression (name) VALUES ('Character');
INSERT INTO main_aromaimpression (name) VALUES ('Charming');
INSERT INTO main_aromaimpression (name) VALUES ('Cheesy');
INSERT INTO main_aromaimpression (name) VALUES ('Chewy');
INSERT INTO main_aromaimpression (name) VALUES ('Chocolaty');
INSERT INTO main_aromaimpression (name) VALUES ('Cigar-box');
INSERT INTO main_aromaimpression (name) VALUES ('Citrous');
INSERT INTO main_aromaimpression (name) VALUES ('Classic');
INSERT INTO main_aromaimpression (name) VALUES ('Clean');
INSERT INTO main_aromaimpression (name) VALUES ('Clear');
INSERT INTO main_aromaimpression (name) VALUES ('Closed');
INSERT INTO main_aromaimpression (name) VALUES ('Cloves');
INSERT INTO main_aromaimpression (name) VALUES ('Cloying');
INSERT INTO main_aromaimpression (name) VALUES ('Coarse');
INSERT INTO main_aromaimpression (name) VALUES ('Coconut');
INSERT INTO main_aromaimpression (name) VALUES ('Coffee');
INSERT INTO main_aromaimpression (name) VALUES ('Compact');
INSERT INTO main_aromaimpression (name) VALUES ('Complete');
INSERT INTO main_aromaimpression (name) VALUES ('Complex');
INSERT INTO main_aromaimpression (name) VALUES ('Concentrated');
INSERT INTO main_aromaimpression (name) VALUES ('Concoction');
INSERT INTO main_aromaimpression (name) VALUES ('Connected');
INSERT INTO main_aromaimpression (name) VALUES ('Cooked');
INSERT INTO main_aromaimpression (name) VALUES ('Corked');
INSERT INTO main_aromaimpression (name) VALUES ('Cranberry');
INSERT INTO main_aromaimpression (name) VALUES ('Creamy');
INSERT INTO main_aromaimpression (name) VALUES ('Crisp');
INSERT INTO main_aromaimpression (name) VALUES ('Crust');
INSERT INTO main_aromaimpression (name) VALUES ('Deep');
INSERT INTO main_aromaimpression (name) VALUES ('Definition');
INSERT INTO main_aromaimpression (name) VALUES ('Delicate');
INSERT INTO main_aromaimpression (name) VALUES ('Delicious');
INSERT INTO main_aromaimpression (name) VALUES ('Depth');
INSERT INTO main_aromaimpression (name) VALUES ('Dirty');
INSERT INTO main_aromaimpression (name) VALUES ('Dried up');
INSERT INTO main_aromaimpression (name) VALUES ('Dry');
INSERT INTO main_aromaimpression (name) VALUES ('Earthy');
INSERT INTO main_aromaimpression (name) VALUES ('Easy');
INSERT INTO main_aromaimpression (name) VALUES ('Edgy');
INSERT INTO main_aromaimpression (name) VALUES ('Elegant');
INSERT INTO main_aromaimpression (name) VALUES ('Exotic');
INSERT INTO main_aromaimpression (name) VALUES ('Expansive');
INSERT INTO main_aromaimpression (name) VALUES ('Expressive');
INSERT INTO main_aromaimpression (name) VALUES ('Extracted');
INSERT INTO main_aromaimpression (name) VALUES ('Fallen over');
INSERT INTO main_aromaimpression (name) VALUES ('Farmyard');
INSERT INTO main_aromaimpression (name) VALUES ('Fat');
INSERT INTO main_aromaimpression (name) VALUES ('Feminine');
INSERT INTO main_aromaimpression (name) VALUES ('Finesse');
INSERT INTO main_aromaimpression (name) VALUES ('Finish');
INSERT INTO main_aromaimpression (name) VALUES ('Firm');
INSERT INTO main_aromaimpression (name) VALUES ('Flabby');
INSERT INTO main_aromaimpression (name) VALUES ('Flat');
INSERT INTO main_aromaimpression (name) VALUES ('Fleshy');
INSERT INTO main_aromaimpression (name) VALUES ('Floral');
INSERT INTO main_aromaimpression (name) VALUES ('Foxy');
INSERT INTO main_aromaimpression (name) VALUES ('Fresh');
INSERT INTO main_aromaimpression (name) VALUES ('Fruity');
INSERT INTO main_aromaimpression (name) VALUES ('Full');
INSERT INTO main_aromaimpression (name) VALUES ('Gentle');
INSERT INTO main_aromaimpression (name) VALUES ('Grapey');
INSERT INTO main_aromaimpression (name) VALUES ('Grassy');
INSERT INTO main_aromaimpression (name) VALUES ('Green');
INSERT INTO main_aromaimpression (name) VALUES ('Green pepper');
INSERT INTO main_aromaimpression (name) VALUES ('Gutsy');
INSERT INTO main_aromaimpression (name) VALUES ('Hard');
INSERT INTO main_aromaimpression (name) VALUES ('Harsh');
INSERT INTO main_aromaimpression (name) VALUES ('Heavy');
INSERT INTO main_aromaimpression (name) VALUES ('Herbaceous');
INSERT INTO main_aromaimpression (name) VALUES ('Hollow');
INSERT INTO main_aromaimpression (name) VALUES ('Hot');
INSERT INTO main_aromaimpression (name) VALUES ('Inky');
INSERT INTO main_aromaimpression (name) VALUES ('Intense');
INSERT INTO main_aromaimpression (name) VALUES ('Interesting');
INSERT INTO main_aromaimpression (name) VALUES ('Jammy');
INSERT INTO main_aromaimpression (name) VALUES ('Lean');
INSERT INTO main_aromaimpression (name) VALUES ('Leathery');
INSERT INTO main_aromaimpression (name) VALUES ('Legs');
INSERT INTO main_aromaimpression (name) VALUES ('Lemony');
INSERT INTO main_aromaimpression (name) VALUES ('Licorice');
INSERT INTO main_aromaimpression (name) VALUES ('Light');
INSERT INTO main_aromaimpression (name) VALUES ('Lightstruck');
INSERT INTO main_aromaimpression (name) VALUES ('Linalool');
INSERT INTO main_aromaimpression (name) VALUES ('Liquorice');
INSERT INTO main_aromaimpression (name) VALUES ('Liveliness');
INSERT INTO main_aromaimpression (name) VALUES ('Luscious');
INSERT INTO main_aromaimpression (name) VALUES ('Mandarin');
INSERT INTO main_aromaimpression (name) VALUES ('Mature');
INSERT INTO main_aromaimpression (name) VALUES ('Mean');
INSERT INTO main_aromaimpression (name) VALUES ('Meaty');
INSERT INTO main_aromaimpression (name) VALUES ('Medium');
INSERT INTO main_aromaimpression (name) VALUES ('Mediocre');
INSERT INTO main_aromaimpression (name) VALUES ('Mellow');
INSERT INTO main_aromaimpression (name) VALUES ('Mild');
INSERT INTO main_aromaimpression (name) VALUES ('Milky');
INSERT INTO main_aromaimpression (name) VALUES ('Midpalate');
INSERT INTO main_aromaimpression (name) VALUES ('Minerality');
INSERT INTO main_aromaimpression (name) VALUES ('Musky');
INSERT INTO main_aromaimpression (name) VALUES ('Nervy');
INSERT INTO main_aromaimpression (name) VALUES ('Nose');
INSERT INTO main_aromaimpression (name) VALUES ('Nuts');
INSERT INTO main_aromaimpression (name) VALUES ('Oaky');
INSERT INTO main_aromaimpression (name) VALUES ('Oily');
INSERT INTO main_aromaimpression (name) VALUES ('Old');
INSERT INTO main_aromaimpression (name) VALUES ('Olive');
INSERT INTO main_aromaimpression (name) VALUES ('Opulent');
INSERT INTO main_aromaimpression (name) VALUES ('Oxidized');
INSERT INTO main_aromaimpression (name) VALUES ('Oxidative');
INSERT INTO main_aromaimpression (name) VALUES ('Palate');
INSERT INTO main_aromaimpression (name) VALUES ('Peak');
INSERT INTO main_aromaimpression (name) VALUES ('Peppery');
INSERT INTO main_aromaimpression (name) VALUES ('Perfume');
INSERT INTO main_aromaimpression (name) VALUES ('Petrolly');
INSERT INTO main_aromaimpression (name) VALUES ('Pleasant');
INSERT INTO main_aromaimpression (name) VALUES ('Plummy');
INSERT INTO main_aromaimpression (name) VALUES ('Polished');
INSERT INTO main_aromaimpression (name) VALUES ('Powerful');
INSERT INTO main_aromaimpression (name) VALUES ('Prickly');
INSERT INTO main_aromaimpression (name) VALUES ('Pungent');
INSERT INTO main_aromaimpression (name) VALUES ('Racy');
INSERT INTO main_aromaimpression (name) VALUES ('Raspberry');
INSERT INTO main_aromaimpression (name) VALUES ('Reticent');
INSERT INTO main_aromaimpression (name) VALUES ('Rich');
INSERT INTO main_aromaimpression (name) VALUES ('Robust');
INSERT INTO main_aromaimpression (name) VALUES ('Round');
INSERT INTO main_aromaimpression (name) VALUES ('Sassy');
INSERT INTO main_aromaimpression (name) VALUES ('Sharp');
INSERT INTO main_aromaimpression (name) VALUES ('Sherrylike');
INSERT INTO main_aromaimpression (name) VALUES ('Short');
INSERT INTO main_aromaimpression (name) VALUES ('Silky');
INSERT INTO main_aromaimpression (name) VALUES ('Smokey');
INSERT INTO main_aromaimpression (name) VALUES ('Smooth');
INSERT INTO main_aromaimpression (name) VALUES ('Soft');
INSERT INTO main_aromaimpression (name) VALUES ('Sophisticated');
INSERT INTO main_aromaimpression (name) VALUES ('Sour');
INSERT INTO main_aromaimpression (name) VALUES ('Soy Sauce');
INSERT INTO main_aromaimpression (name) VALUES ('Spicy');
INSERT INTO main_aromaimpression (name) VALUES ('Stalky');
INSERT INTO main_aromaimpression (name) VALUES ('Strong');
INSERT INTO main_aromaimpression (name) VALUES ('Structure');
INSERT INTO main_aromaimpression (name) VALUES ('Supple');
INSERT INTO main_aromaimpression (name) VALUES ('Sweet');
INSERT INTO main_aromaimpression (name) VALUES ('Tannic');
INSERT INTO main_aromaimpression (name) VALUES ('Tar');
INSERT INTO main_aromaimpression (name) VALUES ('Tart');
INSERT INTO main_aromaimpression (name) VALUES ('Texture');
INSERT INTO main_aromaimpression (name) VALUES ('Thick');
INSERT INTO main_aromaimpression (name) VALUES ('Thin');
INSERT INTO main_aromaimpression (name) VALUES ('Tight');
INSERT INTO main_aromaimpression (name) VALUES ('Toasty');
INSERT INTO main_aromaimpression (name) VALUES ('Tobacco');
INSERT INTO main_aromaimpression (name) VALUES ('Transparency');
INSERT INTO main_aromaimpression (name) VALUES ('Typicity');
INSERT INTO main_aromaimpression (name) VALUES ('Undertone');
INSERT INTO main_aromaimpression (name) VALUES ('Unoaked');
INSERT INTO main_aromaimpression (name) VALUES ('Unpleasant');
INSERT INTO main_aromaimpression (name) VALUES ('Upfront');
INSERT INTO main_aromaimpression (name) VALUES ('Vanilla');
INSERT INTO main_aromaimpression (name) VALUES ('Vegetal');
INSERT INTO main_aromaimpression (name) VALUES ('Vivid');
INSERT INTO main_aromaimpression (name) VALUES ('Voluptuous');
INSERT INTO main_aromaimpression (name) VALUES ('Warm');
INSERT INTO main_aromaimpression (name) VALUES ('Watery');
INSERT INTO main_aromaimpression (name) VALUES ('Whole');
INSERT INTO main_aromaimpression (name) VALUES ('Woody');
INSERT INTO main_aromaimpression (name) VALUES ('Wow');
INSERT INTO main_aromaimpression (name) VALUES ('Yeasty');
INSERT INTO main_aromaimpression (name) VALUES ('Young');
INSERT INTO main_aromaimpression (name) VALUES ('Zesty');
INSERT INTO main_aromaimpression (name) VALUES ('Zippy');
INSERT INTO main_tasteimpression (name) VALUES ('Accessible');
INSERT INTO main_tasteimpression (name) VALUES ('Acacia');
INSERT INTO main_tasteimpression (name) VALUES ('Acidic');
INSERT INTO main_tasteimpression (name) VALUES ('Aftertaste');
INSERT INTO main_tasteimpression (name) VALUES ('Aggressive');
INSERT INTO main_tasteimpression (name) VALUES ('Alcoholic');
INSERT INTO main_tasteimpression (name) VALUES ('Almond');
INSERT INTO main_tasteimpression (name) VALUES ('Apple');
INSERT INTO main_tasteimpression (name) VALUES ('Aroma');
INSERT INTO main_tasteimpression (name) VALUES ('Astringent');
INSERT INTO main_tasteimpression (name) VALUES ('Austere');
INSERT INTO main_tasteimpression (name) VALUES ('Autolytic');
INSERT INTO main_tasteimpression (name) VALUES ('Baked');
INSERT INTO main_tasteimpression (name) VALUES ('Balanced');
INSERT INTO main_tasteimpression (name) VALUES ('Barnyard');
INSERT INTO main_tasteimpression (name) VALUES ('Berries');
INSERT INTO main_tasteimpression (name) VALUES ('Big');
INSERT INTO main_tasteimpression (name) VALUES ('Biscuity');
INSERT INTO main_tasteimpression (name) VALUES ('Bite');
INSERT INTO main_tasteimpression (name) VALUES ('Bitter');
INSERT INTO main_tasteimpression (name) VALUES ('Black pepper');
INSERT INTO main_tasteimpression (name) VALUES ('Blackberry');
INSERT INTO main_tasteimpression (name) VALUES ('Blowzy');
INSERT INTO main_tasteimpression (name) VALUES ('Blueberry');
INSERT INTO main_tasteimpression (name) VALUES ('Body');
INSERT INTO main_tasteimpression (name) VALUES ('Bouquet');
INSERT INTO main_tasteimpression (name) VALUES ('Bright');
INSERT INTO main_tasteimpression (name) VALUES ('Buttery');
INSERT INTO main_tasteimpression (name) VALUES ('Cassis');
INSERT INTO main_tasteimpression (name) VALUES ('Cedarwood');
INSERT INTO main_tasteimpression (name) VALUES ('Character');
INSERT INTO main_tasteimpression (name) VALUES ('Charming');
INSERT INTO main_tasteimpression (name) VALUES ('Cheesy');
INSERT INTO main_tasteimpression (name) VALUES ('Chewy');
INSERT INTO main_tasteimpression (name) VALUES ('Chocolaty');
INSERT INTO main_tasteimpression (name) VALUES ('Cigar-box');
INSERT INTO main_tasteimpression (name) VALUES ('Citrous');
INSERT INTO main_tasteimpression (name) VALUES ('Classic');
INSERT INTO main_tasteimpression (name) VALUES ('Clean');
INSERT INTO main_tasteimpression (name) VALUES ('Clear');
INSERT INTO main_tasteimpression (name) VALUES ('Closed');
INSERT INTO main_tasteimpression (name) VALUES ('Cloves');
INSERT INTO main_tasteimpression (name) VALUES ('Cloying');
INSERT INTO main_tasteimpression (name) VALUES ('Coarse');
INSERT INTO main_tasteimpression (name) VALUES ('Coconut');
INSERT INTO main_tasteimpression (name) VALUES ('Coffee');
INSERT INTO main_tasteimpression (name) VALUES ('Compact');
INSERT INTO main_tasteimpression (name) VALUES ('Complete');
INSERT INTO main_tasteimpression (name) VALUES ('Complex');
INSERT INTO main_tasteimpression (name) VALUES ('Concentrated');
INSERT INTO main_tasteimpression (name) VALUES ('Concoction');
INSERT INTO main_tasteimpression (name) VALUES ('Connected');
INSERT INTO main_tasteimpression (name) VALUES ('Cooked');
INSERT INTO main_tasteimpression (name) VALUES ('Corked');
INSERT INTO main_tasteimpression (name) VALUES ('Cranberry');
INSERT INTO main_tasteimpression (name) VALUES ('Creamy');
INSERT INTO main_tasteimpression (name) VALUES ('Crisp');
INSERT INTO main_tasteimpression (name) VALUES ('Crust');
INSERT INTO main_tasteimpression (name) VALUES ('Deep');
INSERT INTO main_tasteimpression (name) VALUES ('Definition');
INSERT INTO main_tasteimpression (name) VALUES ('Delicate');
INSERT INTO main_tasteimpression (name) VALUES ('Delicious');
INSERT INTO main_tasteimpression (name) VALUES ('Depth');
INSERT INTO main_tasteimpression (name) VALUES ('Dirty');
INSERT INTO main_tasteimpression (name) VALUES ('Dried up');
INSERT INTO main_tasteimpression (name) VALUES ('Dry');
INSERT INTO main_tasteimpression (name) VALUES ('Earthy');
INSERT INTO main_tasteimpression (name) VALUES ('Easy');
INSERT INTO main_tasteimpression (name) VALUES ('Edgy');
INSERT INTO main_tasteimpression (name) VALUES ('Elegant');
INSERT INTO main_tasteimpression (name) VALUES ('Exotic');
INSERT INTO main_tasteimpression (name) VALUES ('Expansive');
INSERT INTO main_tasteimpression (name) VALUES ('Expressive');
INSERT INTO main_tasteimpression (name) VALUES ('Extracted');
INSERT INTO main_tasteimpression (name) VALUES ('Fallen over');
INSERT INTO main_tasteimpression (name) VALUES ('Farmyard');
INSERT INTO main_tasteimpression (name) VALUES ('Fat');
INSERT INTO main_tasteimpression (name) VALUES ('Feminine');
INSERT INTO main_tasteimpression (name) VALUES ('Finesse');
INSERT INTO main_tasteimpression (name) VALUES ('Finish');
INSERT INTO main_tasteimpression (name) VALUES ('Firm');
INSERT INTO main_tasteimpression (name) VALUES ('Flabby');
INSERT INTO main_tasteimpression (name) VALUES ('Flat');
INSERT INTO main_tasteimpression (name) VALUES ('Fleshy');
INSERT INTO main_tasteimpression (name) VALUES ('Floral');
INSERT INTO main_tasteimpression (name) VALUES ('Foxy');
INSERT INTO main_tasteimpression (name) VALUES ('Fresh');
INSERT INTO main_tasteimpression (name) VALUES ('Fruity');
INSERT INTO main_tasteimpression (name) VALUES ('Full');
INSERT INTO main_tasteimpression (name) VALUES ('Gentle');
INSERT INTO main_tasteimpression (name) VALUES ('Grapey');
INSERT INTO main_tasteimpression (name) VALUES ('Grassy');
INSERT INTO main_tasteimpression (name) VALUES ('Green');
INSERT INTO main_tasteimpression (name) VALUES ('Green pepper');
INSERT INTO main_tasteimpression (name) VALUES ('Gutsy');
INSERT INTO main_tasteimpression (name) VALUES ('Hard');
INSERT INTO main_tasteimpression (name) VALUES ('Harsh');
INSERT INTO main_tasteimpression (name) VALUES ('Heavy');
INSERT INTO main_tasteimpression (name) VALUES ('Herbaceous');
INSERT INTO main_tasteimpression (name) VALUES ('Hollow');
INSERT INTO main_tasteimpression (name) VALUES ('Hot');
INSERT INTO main_tasteimpression (name) VALUES ('Inky');
INSERT INTO main_tasteimpression (name) VALUES ('Intense');
INSERT INTO main_tasteimpression (name) VALUES ('Interesting');
INSERT INTO main_tasteimpression (name) VALUES ('Jammy');
INSERT INTO main_tasteimpression (name) VALUES ('Lean');
INSERT INTO main_tasteimpression (name) VALUES ('Leathery');
INSERT INTO main_tasteimpression (name) VALUES ('Legs');
INSERT INTO main_tasteimpression (name) VALUES ('Lemony');
INSERT INTO main_tasteimpression (name) VALUES ('Licorice');
INSERT INTO main_tasteimpression (name) VALUES ('Light');
INSERT INTO main_tasteimpression (name) VALUES ('Lightstruck');
INSERT INTO main_tasteimpression (name) VALUES ('Linalool');
INSERT INTO main_tasteimpression (name) VALUES ('Liquorice');
INSERT INTO main_tasteimpression (name) VALUES ('Liveliness');
INSERT INTO main_tasteimpression (name) VALUES ('Luscious');
INSERT INTO main_tasteimpression (name) VALUES ('Mandarin');
INSERT INTO main_tasteimpression (name) VALUES ('Mature');
INSERT INTO main_tasteimpression (name) VALUES ('Mean');
INSERT INTO main_tasteimpression (name) VALUES ('Meaty');
INSERT INTO main_tasteimpression (name) VALUES ('Medium');
INSERT INTO main_tasteimpression (name) VALUES ('Mediocre');
INSERT INTO main_tasteimpression (name) VALUES ('Mellow');
INSERT INTO main_tasteimpression (name) VALUES ('Mild');
INSERT INTO main_tasteimpression (name) VALUES ('Milky');
INSERT INTO main_tasteimpression (name) VALUES ('Midpalate');
INSERT INTO main_tasteimpression (name) VALUES ('Minerality');
INSERT INTO main_tasteimpression (name) VALUES ('Musky');
INSERT INTO main_tasteimpression (name) VALUES ('Nervy');
INSERT INTO main_tasteimpression (name) VALUES ('Nose');
INSERT INTO main_tasteimpression (name) VALUES ('Nuts');
INSERT INTO main_tasteimpression (name) VALUES ('Oaky');
INSERT INTO main_tasteimpression (name) VALUES ('Oily');
INSERT INTO main_tasteimpression (name) VALUES ('Old');
INSERT INTO main_tasteimpression (name) VALUES ('Olive');
INSERT INTO main_tasteimpression (name) VALUES ('Opulent');
INSERT INTO main_tasteimpression (name) VALUES ('Oxidized');
INSERT INTO main_tasteimpression (name) VALUES ('Oxidative');
INSERT INTO main_tasteimpression (name) VALUES ('Palate');
INSERT INTO main_tasteimpression (name) VALUES ('Peak');
INSERT INTO main_tasteimpression (name) VALUES ('Peppery');
INSERT INTO main_tasteimpression (name) VALUES ('Perfume');
INSERT INTO main_tasteimpression (name) VALUES ('Petrolly');
INSERT INTO main_tasteimpression (name) VALUES ('Pleasant');
INSERT INTO main_tasteimpression (name) VALUES ('Plummy');
INSERT INTO main_tasteimpression (name) VALUES ('Polished');
INSERT INTO main_tasteimpression (name) VALUES ('Powerful');
INSERT INTO main_tasteimpression (name) VALUES ('Prickly');
INSERT INTO main_tasteimpression (name) VALUES ('Pungent');
INSERT INTO main_tasteimpression (name) VALUES ('Racy');
INSERT INTO main_tasteimpression (name) VALUES ('Raspberry');
INSERT INTO main_tasteimpression (name) VALUES ('Reticent');
INSERT INTO main_tasteimpression (name) VALUES ('Rich');
INSERT INTO main_tasteimpression (name) VALUES ('Robust');
INSERT INTO main_tasteimpression (name) VALUES ('Round');
INSERT INTO main_tasteimpression (name) VALUES ('Sassy');
INSERT INTO main_tasteimpression (name) VALUES ('Sharp');
INSERT INTO main_tasteimpression (name) VALUES ('Sherrylike');
INSERT INTO main_tasteimpression (name) VALUES ('Short');
INSERT INTO main_tasteimpression (name) VALUES ('Silky');
INSERT INTO main_tasteimpression (name) VALUES ('Smokey');
INSERT INTO main_tasteimpression (name) VALUES ('Smooth');
INSERT INTO main_tasteimpression (name) VALUES ('Soft');
INSERT INTO main_tasteimpression (name) VALUES ('Sophisticated');
INSERT INTO main_tasteimpression (name) VALUES ('Sour');
INSERT INTO main_tasteimpression (name) VALUES ('Soy Sauce');
INSERT INTO main_tasteimpression (name) VALUES ('Spicy');
INSERT INTO main_tasteimpression (name) VALUES ('Stalky');
INSERT INTO main_tasteimpression (name) VALUES ('Strong');
INSERT INTO main_tasteimpression (name) VALUES ('Structure');
INSERT INTO main_tasteimpression (name) VALUES ('Supple');
INSERT INTO main_tasteimpression (name) VALUES ('Sweet');
INSERT INTO main_tasteimpression (name) VALUES ('Tannic');
INSERT INTO main_tasteimpression (name) VALUES ('Tar');
INSERT INTO main_tasteimpression (name) VALUES ('Tart');
INSERT INTO main_tasteimpression (name) VALUES ('Texture');
INSERT INTO main_tasteimpression (name) VALUES ('Thick');
INSERT INTO main_tasteimpression (name) VALUES ('Thin');
INSERT INTO main_tasteimpression (name) VALUES ('Tight');
INSERT INTO main_tasteimpression (name) VALUES ('Toasty');
INSERT INTO main_tasteimpression (name) VALUES ('Tobacco');
INSERT INTO main_tasteimpression (name) VALUES ('Transparency');
INSERT INTO main_tasteimpression (name) VALUES ('Typicity');
INSERT INTO main_tasteimpression (name) VALUES ('Undertone');
INSERT INTO main_tasteimpression (name) VALUES ('Unoaked');
INSERT INTO main_tasteimpression (name) VALUES ('Unpleasant');
INSERT INTO main_tasteimpression (name) VALUES ('Upfront');
INSERT INTO main_tasteimpression (name) VALUES ('Vanilla');
INSERT INTO main_tasteimpression (name) VALUES ('Vegetal');
INSERT INTO main_tasteimpression (name) VALUES ('Vivid');
INSERT INTO main_tasteimpression (name) VALUES ('Voluptuous');
INSERT INTO main_tasteimpression (name) VALUES ('Warm');
INSERT INTO main_tasteimpression (name) VALUES ('Watery');
INSERT INTO main_tasteimpression (name) VALUES ('Whole');
INSERT INTO main_tasteimpression (name) VALUES ('Woody');
INSERT INTO main_tasteimpression (name) VALUES ('Wow');
INSERT INTO main_tasteimpression (name) VALUES ('Yeasty');
INSERT INTO main_tasteimpression (name) VALUES ('Young');
INSERT INTO main_tasteimpression (name) VALUES ('Zesty');
INSERT INTO main_tasteimpression (name) VALUES ('Zippy');
INSERT INTO "main_winetype" VALUES(1,'Red',30,NULL,NULL);
INSERT INTO "main_winetype" VALUES(2,'White',30,NULL,NULL);
INSERT INTO "main_winetype" VALUES(3,'Rosé',30,NULL,NULL);
INSERT INTO "main_winetype" VALUES(4,'Dessert',30,NULL,NULL);
INSERT INTO "main_winetype" VALUES(5,'Ice',30,NULL,NULL);
INSERT INTO "main_winetype" VALUES(6,'Sparkling',39,NULL,NULL);
INSERT INTO "main_winetype" VALUES(7,'Champagne',40,NULL,NULL);
INSERT INTO "main_winetype" VALUES(8,'Whisky',40,NULL,NULL);
INSERT INTO "main_winetype" VALUES(9,'Other',90,NULL,NULL);
INSERT INTO "main_flag" VALUES(1,'To Buy',30,NULL,NULL);
INSERT INTO "main_flag" VALUES(2,'Undecided',50,NULL,NULL);
INSERT INTO "main_flag" VALUES(3,'Blacklist',100,NULL,NULL);
INSERT INTO "main_flag" VALUES(4,'Cellar',60,NULL,NULL);
INSERT INTO "main_flag" VALUES(5,'Consumed',70,NULL,NULL);
INSERT INTO "main_flag" VALUES(6,'Other',80,NULL,NULL);
INSERT INTO "main_wine" VALUES(1,'Cotes de duras',NULL,0,NULL,'Red, 2009, Bordeaux, Cabernet Sauvignon, Merlot',0.0,'EUR',1,2009,89,5,5,5,5,1,'Medaille d''or 2011',NULL,NULL,1353187118427,1353187433976);
INSERT INTO "main_wine" VALUES(2,'La guicharde autour de la chapelle',NULL,0,NULL,'White, 2010, Cotes du Rhone, Grenache, Marsanne, Viognier',0.0,'EUR',2,2010,219,5,5,4,5,1,NULL,NULL,NULL,1353187313879,1353187422951);
INSERT INTO "main_winegrape" VALUES(1,1,2,NULL,1353187166348,1353187166348);
INSERT INTO "main_winegrape" VALUES(2,1,13,NULL,1353187166393,1353187166393);
INSERT INTO "main_winegrape" VALUES(3,2,25,NULL,1353187358984,1353187358984);
INSERT INTO "main_winegrape" VALUES(4,2,9,NULL,1353187359060,1353187359060);
INSERT INTO "main_winegrape" VALUES(5,2,12,NULL,1353187359096,1353187359096);
INSERT INTO "main_winearomaimpression" VALUES(1,1,88,NULL,1353187209479,1353187209479);
INSERT INTO "main_winearomaimpression" VALUES(2,1,124,NULL,1353187209516,1353187209516);
INSERT INTO "main_winearomaimpression" VALUES(3,1,90,NULL,1353187209553,1353187209553);
INSERT INTO "main_winearomaimpression" VALUES(4,1,144,NULL,1353187209589,1353187209589);
INSERT INTO "main_winearomaimpression" VALUES(5,1,164,NULL,1353187209629,1353187209629);
INSERT INTO "main_winearomaimpression" VALUES(6,1,59,NULL,1353187209665,1353187209665);
INSERT INTO "main_winearomaimpression" VALUES(7,2,88,NULL,1353187381942,1353187381942);
INSERT INTO "main_winearomaimpression" VALUES(8,2,172,NULL,1353187381978,1353187381978);
INSERT INTO "main_winearomaimpression" VALUES(9,2,111,NULL,1353187382015,1353187382015);
INSERT INTO "main_winetasteimpression" VALUES(1,1,88,NULL,1353187239246,1353187239246);
INSERT INTO "main_winetasteimpression" VALUES(2,1,68,NULL,1353187239283,1353187239283);
INSERT INTO "main_winetasteimpression" VALUES(3,1,62,NULL,1353187239319,1353187239319);
INSERT INTO "main_winetasteimpression" VALUES(4,1,131,NULL,1353187239355,1353187239355);
INSERT INTO "main_winetasteimpression" VALUES(5,1,31,NULL,1353187239393,1353187239393);
INSERT INTO "main_winetasteimpression" VALUES(6,2,103,NULL,1353187405083,1353187405083);
INSERT INTO "main_winetasteimpression" VALUES(7,2,169,NULL,1353187405126,1353187405126);
INSERT INTO "main_winetasteimpression" VALUES(8,2,88,NULL,1353187405171,1353187405171);
INSERT INTO "main_winetasteimpression" VALUES(9,2,153,NULL,1353187405220,1353187405220);
INSERT INTO "main_wineaftertasteimpression" VALUES(1,1,192,NULL,1353187262112,1353187262112);
INSERT INTO "main_wineaftertasteimpression" VALUES(2,2,8,NULL,1353187414785,1353187414785);
