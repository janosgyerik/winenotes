#!/bin/sh -e
#
# SCRIPT: backup.sh
# AUTHOR: Janos Gyerik <info@titan2x.com>
# DATE:   2012-10-31
# REV:    1.0.D (Valid are A, B, D, T and P)
#               (For Alpha, Beta, Dev, Test and Production)
#
# PLATFORM: Not platform dependent
#
# PURPOSE: Backup RecipeNotes data into PROJECT/backups/DEVICE
#
# set -n   # Uncomment to check your syntax, without execution.
#          # NOTE: Do not forget to put the comment back in or
#          #       the shell script will not execute!
# set -x   # Uncomment to debug this shell script (Korn shell only)
#

usage() {
    test $# = 0 || echo $@
    echo "Usage: $0 [OPTION]... [ARG]..."
    echo
    echo Backup RecipeNotes data into PROJECT/backups/DEVICE
    echo
    echo Options:
    echo "  -h, --help           Print this help"
    echo
    exit 1
}

args=
#arg=
#flag=off
#param=
while [ $# != 0 ]; do
    case $1 in
    -h|--help) usage ;;
#    --) shift; while [ $# != 0 ]; do args="$args \"$1\""; shift; done; break ;;
    -) usage "Unknown option: $1" ;;
    -?*) usage "Unknown option: $1" ;;
    *) args="$args \"$1\"" ;;  # script that takes multiple arguments
#    *) test "$arg" && usage || arg=$1 ;;  # strict with excess arguments
#    *) arg=$1 ;;  # forgiving with excess arguments
    esac
    shift
done

eval "set -- $args"  # save arguments in $@. Use "$@" in for loops, not $@ 

#test $# = 0 && usage


cd $(dirname "$0"); . config.sh; cd ..

projectname=$(grep project.name build.xml | head -n 1 | sed -e 's/.*project name="\([^"]*\)".*/\1/')

devicename=$(adb get-serialno)
mkdir -p backups/$devicename
adb pull /sdcard/$projectname backups/$devicename

# eof
