#!/usr/bin/perl

use strict;
use warnings;

use Test::More;

use Time::Piece;

BEGIN {
    use_ok 'MyClass';
}

my $my_object = MyClass->new( 'Holiday', 'Baker Street', 'Sherlock Holmes');
ok($my_object, 'Got an object');
isa_ok($my_object, 'MyClass');

my $now = localtime;
$my_object->set_booked_date($now->strftime('%Y-%m-%d'));

is($my_object->get_hotel_name, 'Holiday', 'Correct hotel name');
is($my_object->get_street, 'Baker Street', 'Correct street name');
is($my_object->get_guest_name, 'Sherlock Holmes', 'Correct guest name');
is($my_object->get_booked_date, $now->strftime('%Y-%m-%d'), 'Correct date');

done_testing;

