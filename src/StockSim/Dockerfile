FROM microsoft/aspnet:1.0.0-rc1-final-coreclr

COPY . /app
WORKDIR /app
RUN ["dnu", "restore"]
RUN ["apt-get", "-y", "update"]
RUN ["apt-get", "-y", "install", "sqlite3", "libsqlite3-dev"]

EXPOSE 5004
ENTRYPOINT ["dnx", "-p", "project.json", "kestrel"]
