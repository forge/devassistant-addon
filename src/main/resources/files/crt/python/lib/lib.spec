%if 0%{?rhel} < 7 && 0%{?rhel} > 0
%global _pkgdocdir %{_docdir}/%{name}-%{version}
%global __python2 %{__python}
%endif

%if 0%{?fedora}
%global with_python3 1
%else
%global with_python3 0
%endif

Name:		NAME
Version:	1
Release:	1%{?dist}
Summary:	Short summary up to 80 characters

Group:		Development/Libraries
License:	FIXME
URL:		http://your.project.com
# Source is created by
# git clone https://your.project.com/project.git
# cd NAME
# tito build --tgz
Source0:	%{name}-%{version}.tar.gz
BuildArch:	noarch

BuildRequires:	python2-devel
BuildRequires:	python-setuptools
Requires:	python

%description
Put some description here.
Can be multiline.

%if 0%{?with_python3}
%package -n python3-NAME
Summary:	Short summary
Group:		Development/Libraries

BuildRequires:	python3-devel
BuildRequires:	python3-setuptools
Requires:	python3

%description -n python3-NAME
Put some description here.
Can be multiline.

This is python3 binding.
%endif # with_python3

%prep
%setup -q

%if 0%{?with_python3}
rm -rf %{py3dir}
cp -a . %{py3dir}
find %{py3dir} -name '*.py' | xargs sed -i '1s|^#!/usr/bin/python|#!%{__python3}|'
%endif # with_python3

find -name '*.py' | xargs sed -i '1s|^#!/usr/bin/python|#!%{__python2}|'


%build
CFLAGS="%{optflags}" %{__python2} setup.py build

%if 0%{?with_python3}
pushd %{py3dir}

CFLAGS="%{optflags}" %{__python3} setup.py build

popd
%endif # with_python3



%install
%if 0%{?with_python3}
pushd %{py3dir}
%{__python3} setup.py install --skip-build --root %{buildroot}
popd
%endif # with_python3

%{__python2} setup.py install --skip-build --root %{buildroot}


%check
# FIXME - run test suite here if you have some

%files
%doc LICENSE README.rst
%{python_sitelib}/*

%if 0%{?with_python3}
%files -n python3-NAME
%doc LICENSE README.rst
%{python3_sitelib}/*
%endif # with_python3

%changelog
