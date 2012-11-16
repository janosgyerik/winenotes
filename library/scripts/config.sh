#!/bin/sh

build_info_file=./bin/build.info.sh

msg() {
    echo '* '$* ...
}

fatal() {
    echo 'ERROR: '$* ...
    exit 1
}

write_build_info() {
    test "$projectname" || fatal projectname variable is empty
    build_id=$(date +%Y%m%d)_r$(bzr revno)
    cat <<EOF >$build_info_file
projectname=$projectname
build_id=$build_id
EOF
}

reset_build_info() {
    rm -fr $build_info_file
}

load_build_info() {
    test -f $build_info_file || fatal build info file missing: $build_info_file
    . $build_info_file
}

# eof
