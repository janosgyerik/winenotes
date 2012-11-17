#!/usr/bin/env python
#
# Purpose: todo
#

import optparse
import os


def to_ascii(item):
    item = item.replace('\xc3\xa9', 'e')
    item = item.replace('\xc3\xb4', 'o')
    item = item.replace('\xc3\xa8', 'e')
    item = item.replace('\xc3\xa2','a')
    item = item.replace('\xc3\xab', 'e')
    item = item.replace('\xc3\xaa', 'e')
    item = item.replace('\xc3\xa7', 'c')
    item = item.replace('\xc3\xa0', 'a')
    #item = item.decode('ascii')  # todo: why oh why this is a problem??
    return item


def gen_sql(path, table, column, ascii_column):
    for line in open(path).readlines():
        item = line.strip()
        item = item.replace("'", "''")
        if ascii_column:
            yield "INSERT INTO %s (%s, %s) VALUES ('%s', '%s');" % (table, column, ascii_column, item, to_ascii(item))
        else:
            yield "INSERT INTO %s (%s) VALUES ('%s');" % (table, column, item)


if __name__ == '__main__':
    parser = optparse.OptionParser()
    parser.set_usage('%prog [options]')
    parser.set_description('todo')
    parser.add_option('--table', '-t')
    parser.add_option('--column', '-c')
    parser.add_option('--ascii-column', '-a')

    (options, args) = parser.parse_args()

    for path in args:
        if os.path.isfile(path):
            for sql in gen_sql(path, options.table, options.column, options.ascii_column):
                print sql


# eof
