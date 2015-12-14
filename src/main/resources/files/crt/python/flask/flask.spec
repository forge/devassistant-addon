Name:		NAME
Version:	0
Release:	1%{?dist}
Summary:	Short summary up to 80 characters

Group:		Applications/Internet
License:	FIXME
URL:		http://your.project.com
# Source is created by
# git clone https://your.project.com/project.git
# cd NAME
# tito build --tgz
Source0:	%{name}-%{version}.tar.gz
BuildArch:  noarch

BuildRequires:	python2-devel
Requires:       python
Requires:       python-flask
Requires:       python-flask-script
Requires:       python-flask-sqlalchemy
Requires:       python-flask-wtf
Requires:       httpd
Requires:       mod_wsgi
Requires:       systemd

%description
Put some description here.
Can be multiline.

%prep
%setup -q


%build
# nothing to do here

%install
install -d %{buildroot}%{_datadir}/NAME/
install -d %{buildroot}%{_sysconfdir}/httpd/conf.d

cp -a NAME application manage.py %{buildroot}%{_datadir}/NAME/
cp -a httpd.NAME.conf %{buildroot}%{_sysconfdir}/httpd/conf.d/NAME.conf


%pre
getent group NAME >/dev/null || groupadd -r NAME
getent passwd NAME >/dev/null || \
useradd -r -g NAME -G NAME -d %{_datadir}/NAME/NAME -s /bin/bash -c "NAME user" NAME
/usr/bin/passwd -l NAME >/dev/null

%post
service httpd condrestart

%files
%doc LICENSE
%{_datadir}/NAME
%config(noreplace) %{_sysconfdir}/httpd/conf.d/NAME.conf

%changelog

