# TODO: we need this to get Python 3 at least 3.4; remove when 21 becomes "the" fedora docker image
FROM fedora:21

{% if venv %}
WORKDIR /srv
{% endif %}
{{ install_command }}

ADD . /srv/{{ basename }}{% if venv %}/{{ basename }}{% endif %}

{% if venv %}
{{ setup_command }}
{% endif %}

EXPOSE 8000
{% if venv %}
CMD source /srv/{{ basename }}/bin/activate && \
 /srv/{{ basename }}/{{ basename }}/manage.py runserver 0.0.0.0:8000
{% else %}
CMD /srv/{{ basename }}/manage.py runserver 0.0.0.0:8000
{% endif %}
