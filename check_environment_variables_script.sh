#!/bin/bash

IFS='='
read -ra DB_PASSWORD_CRED <<< $( heroku pg:credentials:url -a bookrobotja7 | grep -Po 'password[^\s]*')
read -ra DB_USER_CRED <<< $( heroku pg:credentials:url -a bookrobotja7 | grep -Po 'user[^\s]*')
read -ra DB_URL_HOST <<< $( heroku pg:credentials:url -a bookrobotja7 | grep -Po 'host[^\s]*')
read -ra DB_URL_NAME <<< $( heroku pg:credentials:url -a bookrobotja7 | grep -Po 'dbname[^\s]*')

IFS=' '
read -ra DB_PASSWORD_CONF <<< $(heroku config -a bookrobotja7 | grep -Po 'DB_PASSWORD:.*')
read -ra DB_USER_CONF <<< $(heroku config -a bookrobotja7 | grep -Po 'DB_USER:.*')
read -ra DB_URL_CONF <<< $(heroku config -a bookrobotja7 | grep -Po 'DB_URL:.*')

DB_URL_CRED="jdbc:postgresql://${DB_URL_HOST[1]}:5432/${DB_URL_NAME[1]}"

if [ "${DB_PASSWORD_CRED[1]}" != "${DB_PASSWORD_CONF[1]}" ] |
 [ "${DB_USER_CRED[1]}" != "${DB_USER_CONF[1]}" ] |
 [ "$DB_URL_CRED" != "${DB_URL_CONF[1]}" ]; then

	heroku config:set DB_PASSWORD=DB_PASSWORD_CRED[1] -a bookrobotja7
	heroku config:set DB_USER=DB_USER_CRED[1] -a bookrobotja7
	heroku config:set DB_URL=DB_URL_CRED[1] -a bookrobotja7

else
	echo "Credentials are OK!"
fi
