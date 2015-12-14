package MyClass;

use strict;
use warnings;

sub new {
    my $class = shift;

    my $self = {
        hotel_name => shift,
        street     => shift,
        name       => shift,
        date       => undef,
    };

    return bless $self, $class;
}

sub set_booked_date {
    my $self = shift;
    my $date = shift;
    $self->{date} = $date;
}

sub get_hotel_name {
    my $self = shift;
    return $self->{hotel_name};
}

sub get_street {
    my $self = shift;
    return $self->{street};
}

sub get_guest_name {
    my $self = shift;
    return $self->{name};
}

sub get_booked_date {
    my $self = shift;
    return $self->{date};
}

1;
