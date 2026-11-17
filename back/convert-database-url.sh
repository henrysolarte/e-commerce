#!/bin/sh
# convert-database-url.sh
# Converts Render's DATABASE_URL to Spring Boot JDBC properties

if [ -z "$DATABASE_URL" ]; then
  echo "No DATABASE_URL set, using defaults"
  export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/ecommerce"
  export SPRING_DATASOURCE_USERNAME="postgres"
  export SPRING_DATASOURCE_PASSWORD="postgres"
else
  echo "Converting DATABASE_URL to JDBC format..."
  
  # Remove postgresql:// prefix
  URL_NO_PREFIX=$(echo "$DATABASE_URL" | sed 's|postgresql://||')
  
  # Extract user and password
  USER_PASS=$(echo "$URL_NO_PREFIX" | cut -d '@' -f 1)
  HOST_DB=$(echo "$URL_NO_PREFIX" | cut -d '@' -f 2)
  
  # Extract username and password
  USERNAME=$(echo "$USER_PASS" | cut -d ':' -f 1)
  PASSWORD=$(echo "$USER_PASS" | cut -d ':' -f 2-)
  
  # Extract host and port
  HOST_PORT=$(echo "$HOST_DB" | cut -d '/' -f 1)
  DB_NAME=$(echo "$HOST_DB" | cut -d '/' -f 2)
  
  # Split host and port
  if echo "$HOST_PORT" | grep -q ':'; then
    HOST=$(echo "$HOST_PORT" | cut -d ':' -f 1)
    PORT=$(echo "$HOST_PORT" | cut -d ':' -f 2)
  else
    HOST="$HOST_PORT"
    PORT="5432"
  fi
  
  # Build JDBC URL
  SPRING_DATASOURCE_URL="jdbc:postgresql://$HOST:$PORT/$DB_NAME"
  
  export SPRING_DATASOURCE_URL
  export SPRING_DATASOURCE_USERNAME="$USERNAME"
  export SPRING_DATASOURCE_PASSWORD="$PASSWORD"
  
  echo "Database configuration:"
  echo "  Host: $HOST"
  echo "  Port: $PORT"
  echo "  Database: $DB_NAME"
  echo "  Username: $USERNAME"
  echo "  JDBC URL: $SPRING_DATASOURCE_URL"
fi
