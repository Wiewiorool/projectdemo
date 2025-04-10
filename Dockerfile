FROM postgres
ENV POSTGRES_DB="patrykdb"
COPY initial_data.sql /docker-entrypoint-initdb.d/