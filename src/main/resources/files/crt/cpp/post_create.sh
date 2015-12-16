#!/bin/sh

echo 'Run autoreconf'
autoreconf --install
echo 'Run configure script'
./configure
make clean
echo 'Compiling sources'
make
echo 'Run make distcheck to create a tar ball'
make dist
echo 'The README file describes how to add and build a new C++'
echo 'source file using autotools'
echo 'More information about autotools can be found here:'
echo 'automake: http://www.gnu.org/software/automake/manual/automake.html'
echo 'autoconf: http://www.gnu.org/software/autoconf/manual/autoconf.html' 