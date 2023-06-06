#! /bin/bash
rsync -a --delete site/ blog:/var/www/talks.yannmoisan.com
