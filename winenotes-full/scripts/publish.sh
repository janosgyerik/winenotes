#!/bin/sh -e
#
# SCRIPT: publish.sh
# AUTHOR: Janos Gyerik <info@titan2x.com>
# DATE:   2012-03-31
# REV:    1.0.D (Valid are A, B, D, T and P)
#               (For Alpha, Beta, Dev, Test and Production)
#
# PLATFORM: Not platform dependent
#
# PURPOSE: Upload the latest build to the download site
#
# set -n   # Uncomment to check your syntax, without execution.
#          # NOTE: Do not forget to put the comment back in or
#          #       the shell script will not execute!
# set -x   # Uncomment to debug this shell script (Korn shell only)
#

remote_host=janoscom
remote_dir=webapps/janosgyerik.com/apps/winenotes-full

cd $(dirname "$0"); . config.sh; cd ..

load_build_info

apk=bin/$projectname-release.apk
apk_release=$projectname-$build_id.apk

rsync --progress -v $apk $remote_host:$remote_dir/$apk_release

# eof
