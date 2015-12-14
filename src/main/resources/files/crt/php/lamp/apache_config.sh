#!/bin/bash

MODULE_CONF=/etc/httpd/conf.modules.d/00-base.conf
USERDIR_CONF=/etc/httpd/conf.d/userdir.conf
PHPMYADMIN_CONF=/etc/phpMyAdmin/config.inc.php
PHP=/usr/bin/php

# CORRECT POLICY
echo "Checking SELinux..."
SELINUX=`getenforce`
echo "SELinux is: $SELINUX"

echo "Enabling httpd.service"
systemctl enable httpd.service

echo "Enabling and starting mysqld.service"
systemctl enable mysqld.service
systemctl restart mysqld.service

grep "'auth_type'.*'cookie'" $PHPMYADMIN_CONF

if [ $? -eq 0 ]; then
echo "Enabling HTTP auth method in $PHPMYADMIN_CONF"
    sed -i "s|['auth_type'].*|['auth_type'] = 'http'|" $PHPMYADMIN_CONF
fi

echo "Creating configuration file for Apache httpd"
if [ ! -f "/etc/httpd/$1" ]; then
    if [ ! -f "/etc/httpd/conf.d/$1.conf" ]; then
        rm -f /tmp/$1.conf
        echo "RewriteEngine on" > /tmp/$1.conf
        echo "RewriteRule ^$1 $2/public_html/$1" >> /tmp/$1.conf
        echo "Alias /$1 $2/public_html/$1" >> /tmp/$1.conf
        echo "<Directory \"$2/public_html/$1\" >" >> /tmp/$1.conf
        echo "  Require all granted" >> /tmp/$1.conf
        echo "</Directory>" >> /tmp/$1.conf
        mv /tmp/$1.conf /etc/httpd/conf.d/$1.conf
        chcon -t httpd_config_t /etc/httpd/conf.d/$1.conf # SELinux context
        echo "Configuration file was successfuly created"
    fi
else
    echo "Configuration file was already created"
fi

if [ -f $USERDIR_CONF ]; then
    echo "Checking whether UserDir is enabled"
    grep "UserDir disabled" $USERDIR_CONF
    if [ $? -eq 0 ]; then
        echo "Enabling UserDir"
        sed -i "s|UserDir disabled||" $USERDIR_CONF
    fi
    grep "#UserDir public_html" $USERDIR_CONF
    if [ $? -eq 0 ]; then
        echo "Setting UserDir to ~/public_html"
        sed -i "s|#UserDir public_html|UserDir public_html|" $USERDIR_CONF
    fi
    setsebool httpd_enable_homedirs 1 # Necessary for LAMP to work
fi



echo "Restarting httpd.service"
systemctl restart httpd.service
if [ $? -ne 0 ]; then
    echo "Check messages by journalctl -xn"
fi
echo "Restarting httpd.service done"


