#!/bin/sh

cd $(dirname "$0")

sql=winenotes-lite/src/main/assets/sql_create.sql

db=./docs/sqlite3-init.db

./django-winenotes/gen-sql-create.sh > $sql

./docs/gen-insert.py -t main_region -c name -a ascii_name ./docs/regions.txt >> $sql
./docs/gen-insert.py -t main_region -c name -a ascii_name ./docs/appellation.txt >> $sql
./docs/gen-insert.py -t main_grape -c name -a ascii_name ./docs/grapes.txt >> $sql
./docs/gen-insert.py -t main_aromaimpression -c name ./docs/descriptors.txt >> $sql
./docs/gen-insert.py -t main_tasteimpression -c name ./docs/descriptors.txt >> $sql

sqlite3 $db '.dump main_winetype' | grep INSERT >> $sql
sqlite3 $db '.dump main_flag' | grep INSERT >> $sql

# sample wines
sqlite3 $db '.dump main_wine' | grep INSERT >> $sql
sqlite3 $db '.dump main_winegrape' | grep INSERT >> $sql
sqlite3 $db '.dump main_winearomaimpression' | grep INSERT >> $sql
sqlite3 $db '.dump main_winetasteimpression' | grep INSERT >> $sql
sqlite3 $db '.dump main_wineaftertasteimpression' | grep INSERT >> $sql

cat <<EOF
# How to connect to a sqlite database on Android
# Warning: this works only on Android DEV phones and jailbroken phones
# As a workaround, you can add an activity for debugging purposes which
# copies the database file to /sdcard.
#
$ adb -s emulator-5554 shell
# sqlite3 /data/data/com.example.google.rss.rssexample/databases/rssitems.db
SQLite version 3.3.12
Enter ".help" for instructions
.... enter commands, then quit...
sqlite> .exit 
EOF

# eof
